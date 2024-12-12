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
                WITH прогноз_продаж AS (
                    SELECT
                        п.код_продукта,
                        п.название AS название_продукта,
                        AVG(пр.количество_проданного_продукта) AS среднее_количество_проданных_единиц,
                        AVG(пр.количество_проданного_продукта * п.цена * (100 - с.процент_скидки) / 100) AS средняя_стоимость_продаж
                    FROM
                        продажи пр
                    JOIN
                        продукты п ON пр.код_продукта = п.код_продукта
                    JOIN
                        список_акций са ON пр.код_продукта = са.код_продукта
                    JOIN
                        скидки с ON са.код_скидки = с.код_скидки
                    JOIN
                        сроки_действия_скидок ср ON с.дата_начала = ср.дата_начала
                    WHERE
                        ср.дата_окончания >= CURRENT_DATE
                    GROUP BY
                        п.код_продукта, п.название
                ),
                прогноз_на_месяц AS (
                    SELECT
                        пп.код_продукта,
                        пп.название_продукта,
                        пп.среднее_количество_проданных_единиц * 30 AS прогнозируемое_количество_проданных_единиц,
                        пп.средняя_стоимость_продаж * 30 AS прогнозируемая_стоимость_продаж
                    FROM
                        прогноз_продаж пп
                )
                SELECT
                    пп.название_продукта AS productName,
                    пп.прогнозируемое_количество_проданных_единиц AS forecastedQuantitySold,
                    пп.прогнозируемая_стоимость_продаж AS forecastedSalesAmount
                FROM
                    прогноз_на_месяц пп
                ORDER BY
                    пп.прогнозируемая_стоимость_продаж DESC;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SalesForecastReport.class));
    }
}
