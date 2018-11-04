package ru.rsoi.shipments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        try{
        Shipment shipment1 = new Shipment(shipment.getTitle(), shipment.getDeclare_value(), shipment.getUnit_id(), shipment.getUid(), shipment.getDel_id());
        shipmentRepository.save(shipment1);
        LOGGER.info("Shipment created.");
        }
        catch (RuntimeException e){
            LOGGER.error("Failed to create shipment");
        }
    }
    @Override
    public Page<ShipmentInfo> getAll(Pageable pageable) {
        try{
        return shipmentRepository.findAll(pageable).map(this::buildModel);
        }
        catch (RuntimeException e){
            LOGGER.error("Failed to get all shioments");
            return null;
        }
    }

    @Override
    public ShipmentInfo getModelFromHashMap(LinkedHashMap<String, Object> shipment) {
        ShipmentInfo model = new ShipmentInfo();
    try{
        model.setTitle((String)shipment.get("title"));
        model.setDeclare_value((Integer) shipment.get("declare_value"));
        Unit unit = Unit.valueOf((Integer) shipment.get("unit_id"));
        model.setUnit_id(unit);
        model.setDel_id((Integer) shipment.get("del_id"));
        model.setUid((Integer)shipment.get("uid"));
        return model;
    }
    catch(RuntimeException e){
        LOGGER.error("Failed get model from hashmap");
        return null;
    }

    }

    @Override
    public void delete(Integer id) {

       try{ shipmentRepository.deleteById(id);
        LOGGER.info("Shipment deleted");}
        catch(RuntimeException e){
           LOGGER.error("Failed to delete shipment with id={}",id);
        }
    }

    @Override
    public ShipmentInfo getById(Integer id) {
      try{  ShipmentInfo model = buildModel(shipmentRepository.findByUid(id));
      return model;}
      catch (RuntimeException e){
          LOGGER.error("Failed to get shipment by id");
          return null;
      }
    }

    @Override
    public void editShipment(ShipmentInfo shipment) {

        Shipment new_shipment = getEntity(shipment);
       try{ BeanUtils.copyProperties(shipment,new_shipment);
        shipmentRepository.save(new_shipment);
        LOGGER.info("Shipment updated");}
        catch (RuntimeException e){
           LOGGER.error("Failed to edit shipment with id={}", shipment.getUid());
        }
    }

    @Override
    public List<ShipmentInfo> findAllByDeliveryId(Integer del_id) {
       try{ return shipmentRepository.findAllByDeliveryId(del_id)
                                 .stream()
                                 .map(this::buildModel)
                                 .collect(Collectors.toList());}
                                 catch(RuntimeException e){
           LOGGER.error("Failed to find shipments with del_id={}",del_id);
           return null;
                                 }
    }

    @Override
    public void deleteAllByDeliveryId(Integer del_id) {

       try{ shipmentRepository.deleteAllByDeliveryId(del_id);
        LOGGER.info("Shipments deleted");}
        catch(RuntimeException e){
           LOGGER.error("Failed to delete shipments with del_id={}",del_id);
        }
    }

    @NonNull
    private Shipment getEntity(ShipmentInfo model) {
      try{  Shipment shipment = shipmentRepository.findByUid(model.getUid());
        return shipment;}
        catch(RuntimeException e) {
          LOGGER.error("Failed to get entity by model.");
          return null;
        }
    }
    @NonNull
    private ShipmentInfo buildModel(Shipment shipment) {
        ShipmentInfo model = new ShipmentInfo();
        try {   BeanUtils.copyProperties(shipment,model);
            return model;}
        catch (RuntimeException e){
            LOGGER.error("Failed build model");
        }
        return null;
    }


}
