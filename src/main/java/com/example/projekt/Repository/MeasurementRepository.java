package com.example.projekt.Repository;

import com.example.projekt.Entity.Measurement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional // Jinak Query padá
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    Measurement findMeasurementById(Long id);

    @Modifying
    @Query("DELETE FROM Measurement m WHERE m.date < :dateInput")
    void deleteExpiratedMeasurements(@Param("dateInput") Long dateInput);

    @Query(value = "SELECT CASE " +
            "    WHEN EXISTS (SELECT * FROM Measurement m WHERE m.DATE = :dateInput AND m.CITY_ID = :cityIdInput) THEN TRUE " +
            "    ELSE FALSE " +
            "END", nativeQuery = true)
    boolean exitsMeasurementForCityByDate(@Param("dateInput") Long dateInput, @Param("cityIdInput") Long cityIdInput);

    @Query(value = "SELECT AVG(m.temperature) FROM Measurement m WHERE m.date > :dateInput AND m.CITY_ID = :cityIdInput", nativeQuery = true)
    Double averageTemperatureForCity(@Param("dateInput") Long dateInput, @Param("cityIdInput") Long cityIdInput);

    @Query(value = "SELECT AVG(m.rain) FROM Measurement m WHERE m.date > :dateInput AND m.CITY_ID = :cityIdInput", nativeQuery = true)
    Double averageRainForCity(@Param("dateInput") Long dateInput, @Param("cityIdInput") Long cityIdInput);

    @Query(value = "SELECT AVG(m.windSpeed) FROM Measurement m WHERE m.date > :dateInput AND m.CITY_ID = :cityIdInput", nativeQuery = true)
    Double averageWindSpeedForCity(@Param("dateInput") Long dateInput, @Param("cityIdInput") Long cityIdInput);

    @Query(value = "SELECT * " +
            "FROM Measurement m " +
            "WHERE m.CITY_ID = :cityIdInput AND m.DATE = (SELECT MAX(m.DATE) " +
            "                                        FROM Measurement m " +
            "                                        GROUP BY m.CITY_ID " +
            "                                        HAVING m.CITY_ID = :cityIdInput)", nativeQuery = true)
    Measurement actualMeasurement(@Param("cityIdInput") Long cityIdInput);
}
