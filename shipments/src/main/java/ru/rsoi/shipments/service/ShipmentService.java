package ru.rsoi.shipments.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rsoi.shipments.entity.Shipment;
import ru.rsoi.shipments.model.ShipmentInfo;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

public interface ShipmentService {

    void createShipment(ShipmentInfo ship);

    void delete(Integer id);

    ShipmentInfo getById(Integer id);

    void editShipment(ShipmentInfo shipment);

    Page<ShipmentInfo> getAll(Pageable pageable);

    List<ShipmentInfo> findAllByDeliveryId(Integer del_id);

    void deleteAllByDeliveryId(Integer del_id);

    ShipmentInfo getModelFromHashMap(LinkedHashMap<String,Object> shipment) throws ParseException;
}
