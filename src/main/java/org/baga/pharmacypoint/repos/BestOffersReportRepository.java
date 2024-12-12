package org.baga.pharmacypoint.repos;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.dto.BestOffersReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BestOffersReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<BestOffersReport> getReport() {
        String sql = """
                WITH лучшие_предложения AS (
                    SELECT
                        п.код_продукта,
                        п.название AS название_продукта,
                        с.название AS название_скидки,
                        с.условия_применения,
                        с.процент_скидки,
                        ср.дата_начала,
                        ср.дата_окончания,
                        п.цена * (100 - с.процент_скидки) / 100 AS цена_со_скидкой,
                        ROW_NUMBER() OVER (PARTITION BY п.код_продукта ORDER BY с.процент_скидки DESC) AS rn
                    FROM
                        продукты п
                    JOIN
                        список_акций са ON п.код_продукта = са.код_продукта
                    JOIN
                        скидки с ON са.код_скидки = с.код_скидки
                    JOIN
                        сроки_действия_скидок ср ON с.дата_начала = ср.дата_начала
                    WHERE
                        ср.дата_окончания >= CURRENT_DATE
                )
                SELECT
                    название_продукта AS productName,
                    название_скидки AS discountName,
                    условия_применения AS discountConditions,
                    процент_скидки AS discountPercentage,
                    дата_начала AS discountStartDate,
                    дата_окончания AS discountEndDate,
                    цена_со_скидкой AS discountedPrice
                FROM
                    лучшие_предложения
                WHERE
                    rn = 1
                ORDER BY
                    процент_скидки DESC;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BestOffersReport.class));
    }
}
