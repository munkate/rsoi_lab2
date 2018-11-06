package ru.rsoi.shipments.service;

import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsoi.shipments.entity.Shipment;
import ru.rsoi.shipments.entity.enums.Unit;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.repository.ShipmentRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    @Override
    public long createShipment(ShipmentInfo shipment) {
        try{
        Shipment shipment1 = new Shipment(shipment.getTitle(), shipment.getDeclare_value(), shipment.getUnit_id(), shipment.getUid(), shipment.getDel_id());
        shipmentRepository.save(shipment1);
        LOGGER.info("Shipment created.");
        return shipment1.getUid();
        }
        catch (RuntimeException e){
            LOGGER.error("Failed to create shipment");
            return 0;
        }
    }
    @Override
    @Transactional
    public void createShipments(JSONArray shipments) {
        List<Shipment> new_shipments = new ArrayList<Shipment>();

        LinkedHashMap<String,Object> buf = null;
        try{
            for (int i=0; i<shipments.toArray().length;i++){
                Shipment shipment = new Shipment();
               buf = (LinkedHashMap<String,Object>)shipments.get(i);
               // for (int j=0; j<buf.values().toArray().length;j++) {
                    shipment.setTitle((String)buf.get("title"));
                    shipment.setDeclare_value((Integer) buf.get("declare_value"));
                    shipment.setUnit_id(Unit.valueOf((Integer) buf.get("unit_id")) );
                    shipment.setUid((long) buf.get("uid"));
                    shipment.setDel_id((Integer) buf.get("del_id"));
                new_shipments.add(shipment);
            }
           // for (Shipment _entity: new_shipments) {

                shipmentRepository.saveAll(new_shipments);

           // }
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
            LOGGER.error("Failed to get all shipments");
            return null;
        }
    }



    @Override
    public void delete(long id) {

       try{ shipmentRepository.deleteByUid(id);
        LOGGER.info("Shipment deleted");}
        catch(RuntimeException e){
           LOGGER.error("Failed to delete shipment with id={}",id);
        }
    }

    @Override
    public ShipmentInfo getById(long id) {
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
