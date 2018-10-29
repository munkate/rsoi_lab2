package ru.rsoi.ships.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rsoi.ships.entity.Ship;
import ru.rsoi.ships.model.ShipInfo;

import java.util.List;
import java.util.UUID;


public interface ShipRepository extends JpaRepository<Ship, Integer> {
    @Query("SELECT p FROM Ship p WHERE p.uid=:uid")
    Ship findByUid(@Param("uid") long uid);
}
