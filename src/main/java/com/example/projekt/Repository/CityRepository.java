package com.example.projekt.Repository;

import com.example.projekt.Entity.City;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CityRepository extends CrudRepository<City, Long> {
    City findCityById(Long id);

    @Query(value = "SELECT c1.CITY_ID AS CITY_ID, c2.MAX_DATE AS MAX_DATE " +
            "FROM CITY c1 " +
            "LEFT JOIN (SELECT CITY_ID , MAX(date) AS MAX_DATE " +
            "           FROM Measurement " +
            "           GROUP BY CITY_ID) c2 " +
            "ON c1.CITY_ID = c2.CITY_ID " +
            "ORDER BY c2.MAX_DATE " +
            "LIMIT 1", nativeQuery = true)

    Long findCityIdWithOldestData(); // Metoda s kouzelnou query co vybere město co nebylo nikdy aktulizováno nebo město se záznamem co má nejstarší nejnovější záznam (chápem snad)
}
