package ru.rsoi.shipments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rsoi.shipments.entity.Shipment;

import java.util.List;


public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    @Query("SELECT p FROM Shipment p WHERE p.uid=:uid")
    Shipment findByUid(@Param("uid") long uid);

    @Query("SELECT d FROM Shipment d WHERE d.del_id =:del_id")
    List<Shipment> findAllByDeliveryId(@Param("del_id") Integer del_id);
}
