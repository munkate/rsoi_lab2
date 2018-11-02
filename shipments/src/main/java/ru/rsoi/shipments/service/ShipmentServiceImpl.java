package ru.rsoi.shipments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.shipments.entity.Shipment;
import ru.rsoi.shipments.entity.enums.Unit;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.repository.ShipmentRepository;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    @Override
    public void createShipment(ShipmentInfo shipment) {
        Shipment shipment1 = new Shipment(shipment.getTitle(), shipment.getDeclare_value(), shipment.getUnit_id(), shipment.getUid(), shipment.getDel_id());
        shipmentRepository.save(shipment1);
        LOGGER.info("Shipment created.");
    }


    @Override
    public List<ShipmentInfo> getAll() {
        return shipmentRepository.findAll()
                                 .stream()
                                 .map(this::buildModel)
                                 .collect(Collectors.toList());
    }

    @Override
    public ShipmentInfo getModelFromHashMap(LinkedHashMap<String, Object> shipment) {
        ShipmentInfo model = new ShipmentInfo();

        model.setTitle((String)shipment.get("title"));
        model.setDeclare_value((Integer) shipment.get("declare_value"));
        Unit unit = Unit.valueOf((Integer) shipment.get("unit_id"));
        model.setUnit_id(unit);
        model.setDel_id((Integer) shipment.get("del_id"));
        model.setUid((Integer)shipment.get("uid"));
        return model;

    }

    @Override
    public void delete(Integer id) {

        shipmentRepository.deleteById(id);
        LOGGER.info("Shipment deleted");
    }

    @Override
    public ShipmentInfo getById(Integer id) {
        return shipmentRepository.findById(id).map(this::buildModel).orElse(null);
    }

    @Override
    public void editShipment(ShipmentInfo shipment) {

        Shipment new_shipment = getEntity(shipment);
        new_shipment.setDeclare_value(shipment.getDeclare_value());
        new_shipment.setTitle(shipment.getTitle());
        new_shipment.setUid(shipment.getUid());
        new_shipment.setUnit_id(shipment.getUnit_id());
        shipmentRepository.saveAndFlush(new_shipment);
        LOGGER.info("Shipment updated");
    }

    @Override
    public List<ShipmentInfo> findAllByDeliveryId(Integer del_id) {
        return shipmentRepository.findAllByDeliveryId(del_id)
                                 .stream()
                                 .map(this::buildModel)
                                 .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByDeliveryId(Integer del_id) {

        shipmentRepository.deleteAllByDeliveryId(del_id);
        LOGGER.info("Shipments deleted");
    }

    @NonNull
    private ShipmentInfo buildModel(Shipment shipment) {
        ShipmentInfo model = new ShipmentInfo();
        model.setDeclare_value(shipment.getDeclare_value());
        model.setTitle(shipment.getTitle());
        model.setUnit_id(shipment.getUnit_id());
        model.setDel_id(shipment.getDel_id());
        return model;
    }

    @NonNull
    private Shipment getEntity(ShipmentInfo model) {
        Shipment shipment = shipmentRepository.findByUid(model.getUid());
        return shipment;
    }

}
