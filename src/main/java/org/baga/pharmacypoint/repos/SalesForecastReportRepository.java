package org.baga.pharmacypoint.repos;

import lombok.AllArgsConstructor;
import org.baga.pharmacypoint.dto.SalesForecastReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SalesForecastReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<SalesForecastReport> getReport() {
        String sql = """
                WITH Продажи_со_скидкой AS (
                    SELECT
                        Пр.Код_продукта,
                        SUM(Пр.Количество_проданного_продукта) AS Количество_проданного_продукта,
                        SUM(Пр.Количество_проданного_продукта * П.Цена * (100 - С.Процент_скидки) / 100) AS Общая_стоимость_продаж
                    FROM
                        продажи Пр
                    JOIN
                        продукты П ON Пр.Код_продукта = П.Код_продукта
                    JOIN
                        список_акций СА ON Пр.Код_продукта = СА.Код_продукта
                    JOIN
                        скидки С ON СА.Код_скидки = С.Код_скидки
                    JOIN
                        сроки_действия_скидок Ср ON С.Дата_начала = Ср.Дата_начала
                    WHERE
                        Ср.Дата_окончания >= CURRENT_DATE
                    GROUP BY
                        Пр.Код_продукта
                )
                SELECT
                    П.Название AS productName,
                    С.Название AS discountName,
                    С.Условия_применения AS discountConditions,
                    С.Процент_скидки AS discountPercentage,
                    Ср.Дата_начала AS discountStartDate,
                    Ср.Дата_окончания AS discountEndDate,
                    COALESCE(Продажи_со_скидкой.Количество_проданного_продукта, 0) AS quantitySold,
                    COALESCE(Продажи_со_скидкой.Общая_стоимость_продаж, 0) AS totalSalesAmount
                FROM
                    продукты П
                JOIN
                    список_акций СА ON П.Код_продукта = СА.Код_продукта
                JOIN
                    скидки С ON СА.Код_скидки = С.Код_скидки
                JOIN
                    сроки_действия_скидок Ср ON С.Дата_начала = Ср.Дата_начала
                LEFT JOIN
                    Продажи_со_скидкой ON П.Код_продукта = Продажи_со_скидкой.Код_продукта
                WHERE
                    Ср.Дата_окончания >= CURRENT_DATE
                ORDER BY
                    С.Процент_скидки DESC;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SalesForecastReport.class));
    }
}