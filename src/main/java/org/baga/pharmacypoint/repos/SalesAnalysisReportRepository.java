package org.baga.pharmacypoint.repos;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.dto.SalesAnalysisReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SalesAnalysisReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<SalesAnalysisReport> getReport() {
        String sql = """
                WITH продажи_со_скидкой AS (SELECT пр.код_продукта,
                                                                             SUM(пр.количество_проданного_продукта)                                           AS количество_проданного_продукта,
                                                                             SUM(пр.количество_проданного_продукта * п.цена * (100 - с.процент_скидки) /
                                                                                 100)                                                                         AS общая_стоимость_продаж
                                                                      FROM продажи пр
                                                                               JOIN
                                                                           продукты п ON пр.код_продукта = п.код_продукта
                                                                               JOIN
                                                                           список_акций са ON пр.код_продукта = са.код_продукта
                                                                               JOIN
                                                                           скидки с ON са.код_скидки = с.код_скидки
                                                                               JOIN
                                                                           сроки_действия_скидок ср ON с.дата_начала = ср.дата_начала
                                                                      WHERE ср.дата_окончания >= CURRENT_DATE
                                                                      GROUP BY пр.код_продукта)
                                          SELECT п.название                                                     AS productName,
                                                 с.название                                                     AS discountName,
                                                 с.условия_применения AS discountConditions,
                                                 с.процент_скидки AS discountPercentage,
                                                 ср.дата_начала AS discountStartDate,
                                                 ср.дата_окончания AS discountEndDate,
                                                 COALESCE(продажи_со_скидкой.количество_проданного_продукта, 0) AS quantitySold,
                                                 COALESCE(продажи_со_скидкой.общая_стоимость_продаж, 0)         AS totalSalesAmount
                                          FROM продукты п
                                                   JOIN
                                               список_акций са ON п.код_продукта = са.код_продукта
                                                   JOIN
                                               скидки с ON са.код_скидки = с.код_скидки
                                                   JOIN
                                               сроки_действия_скидок ср ON с.дата_начала = ср.дата_начала
                                                   LEFT JOIN
                                               продажи_со_скидкой ON п.код_продукта = продажи_со_скидкой.код_продукта
                                          WHERE ср.дата_окончания >= CURRENT_DATE
                                          ORDER BY с.процент_скидки DESC;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SalesAnalysisReport.class));
    }
}