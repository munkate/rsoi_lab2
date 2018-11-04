package ru.rsoi.ships.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.rsoi.ships.entity.Ship;


public interface ShipRepository extends PagingAndSortingRepository<Ship, Integer> {
    @Query("SELECT p FROM Ship p WHERE p.uid=:uid")
    Ship findByUid(@Param("uid") long uid);
}
