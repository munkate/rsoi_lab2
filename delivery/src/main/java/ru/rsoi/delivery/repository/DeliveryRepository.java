package ru.rsoi.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rsoi.delivery.entity.Delivery;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    @Query("SELECT p FROM Delivery p WHERE p.uid=:uid")
    Delivery findByUid(@Param("uid") long uid);

    @Query("SELECT d FROM Delivery d WHERE d.user_id=:user_id")
    List<Delivery> findAllByUserId(@Param("user_id") Integer user_id);
}