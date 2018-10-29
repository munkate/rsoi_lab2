package ru.rsoi.shipments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.shipments.entity.Shipment;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.repository.ShipmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public void createShipment(ShipmentInfo shipment) {
        Shipment shipment1 = new Shipment(shipment.getTitle(), shipment.getDeclare_value(), shipment.getUnit_id());
        shipmentRepository.save(shipment1);
    }


    @Override
    public List<ShipmentInfo> getAll() {
        return shipmentRepository.findAll()
                                 .stream()
                                 .map(this::buildModel)
                                 .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        shipmentRepository.deleteById(id);
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
    }

    @Override
    public List<ShipmentInfo> findAllByDeliveryId(Integer del_id) {
        return shipmentRepository.findAllByDeliveryId(del_id)
                                 .stream()
                                 .map(this::buildModel)
                                 .collect(Collectors.toList());
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
