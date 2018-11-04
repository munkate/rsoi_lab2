package ru.rsoi.shipments.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rsoi.shipments.entity.Shipment;
import ru.rsoi.shipments.model.ShipmentInfo;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ShipmentService {

    void createShipment(ShipmentInfo ship);

    void delete(Integer id);

    ShipmentInfo getById(Integer id);

    void editShipment(ShipmentInfo shipment);

    Page<ShipmentInfo> getAll(Pageable pageable);

    List<ShipmentInfo> findAllByDeliveryId(Integer del_id);

    void deleteAllByDeliveryId(Integer del_id);

    List<ShipmentInfo> getModelFromHashMap(LinkedHashMap<String,Object> shipment) throws ParseException;
    void createShipments(JSONArray shipments);
}
