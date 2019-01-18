package ru.rsoi.shipments.service;

import net.minidev.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rsoi.shipments.model.ShipmentInfo;

import java.util.List;

public interface ShipmentService {
    boolean checkClient(String client_id, String client_secret);
    String createJWT();
    boolean parseJWT(String jwt);

    long createShipment(ShipmentInfo ship);

    void delete(long id);

    ShipmentInfo getById(long id);

    void editShipment(ShipmentInfo shipment);

    Page<ShipmentInfo> getAll(Pageable pageable);

    List<ShipmentInfo> findAllByDeliveryId(Integer del_id);

    void deleteAllByDeliveryId(Integer del_id);


    boolean createShipments(JSONArray shipments);
}
