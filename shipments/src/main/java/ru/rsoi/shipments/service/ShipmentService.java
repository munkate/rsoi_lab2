package ru.rsoi.shipments.service;

import ru.rsoi.shipments.model.ShipmentInfo;

import java.util.List;

public interface ShipmentService {

    void createShipment(ShipmentInfo ship);

    void delete(Integer id);

    ShipmentInfo getById(Integer id);

    void editShipment(ShipmentInfo shipment);

    List<ShipmentInfo> getAll();

    List<ShipmentInfo> findAllByDeliveryId(Integer del_id);

    void deleteAllByDeliveryId(Integer del_id);
}
