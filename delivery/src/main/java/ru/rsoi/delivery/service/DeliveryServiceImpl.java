package ru.rsoi.delivery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.delivery.entity.Delivery;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.repository.DeliveryRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public long createDelivery(DeliveryModel delivery) {
        try{
        Delivery new_delivery = new Delivery(delivery.getDeparture_date(), delivery.getArrive_date(),
                delivery.getOrigin(), delivery.getDestination(), delivery.getShip_id(), delivery.getUser_id(), delivery.getUid());

        deliveryRepository.save(new_delivery);
        LOGGER.info("Delivery created.");
        return new_delivery.getUid();
        }
        catch (RuntimeException e){
            LOGGER.error("Delivery didn't create.");
            return 0;
        }
    }
    @Override
    public DeliveryModel getModelFromHashMap(LinkedHashMap<String,Object> model) throws ParseException {
     try {   DeliveryModel del = new DeliveryModel();
       DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        del.setUid(Integer.parseInt(model.get("uid").toString()));
        del.setOrigin((String)model.get("origin"));
        del.setDestination((String)model.get("destination"));
        del.setUser_id(Integer.parseInt(model.get("user_id").toString()));
        del.setShip_id(Integer.parseInt(model.get("ship_id").toString()));
        del.setDeparture_date(format.parse((String)model.get("departure_date")));
        del.setArrive_date(format.parse((String)model.get("arrive_date")));
        LOGGER.info("Successful getting model from hashmap");
       return del;
     }
       catch (RuntimeException e){
         LOGGER.error("Failed to get model from hashmap");
         return null;
       }
    }


    @Override
    public DeliveryModel getDeliveryById(long id) {
       try{
           DeliveryModel model = buildModel( deliveryRepository.findByUid(id));
           return model;
       }
       catch(RuntimeException e)
       {
           LOGGER.error("Delivery with id={} didn't find",id);
           return null;
       }

        }


    @Override
    public Page<DeliveryModel> findAll(Pageable pageable) {
      try {  return deliveryRepository.findAll(pageable).map(this::buildModel);}
      catch (RuntimeException e){
          LOGGER.error("Failed to get all deliveries.");
          return null;
                }
    }

    @Override
    public Page<DeliveryModel> findAllByUserId(Integer id, Pageable pageable) {
        try {
            return deliveryRepository.findAllByUserId(id,pageable).map(this::buildModel);
        }
        catch (RuntimeException e){
            LOGGER.error("Failed to get all user's deliveries.");
            return null;
        }
    }

    @Override
    public void deleteDeliveryByUid(long id) {

        try{deliveryRepository.deleteByUid(id);}
        catch (RuntimeException e) {
            LOGGER.error("Failed to delete delivery with id={}",id);
        }


    }

    @Override
    public void editDelivery(DeliveryModel delivery) {

             try {
                Delivery new_delivery = getEntity(delivery);
                 assert new_delivery != null;
                 BeanUtils.copyProperties(delivery, new_delivery);

                deliveryRepository.save(new_delivery);
            }
            catch (RuntimeException e) {
                LOGGER.error("Delivery didn't update");
                throw new RuntimeException("DAO failed", e);
            }

    }

    @NonNull
    private DeliveryModel buildModel(Delivery delivery) {
        DeliveryModel model = new DeliveryModel();
     try {   BeanUtils.copyProperties(delivery,model);
        return model;}
        catch (RuntimeException e){
         LOGGER.error("Failed build model");
        }
        return null;
    }


    private Delivery getEntity(DeliveryModel model) {
        try {
            Delivery delivery = deliveryRepository.findByUid(model.getUid());
            return delivery;
        } catch (RuntimeException e){ return null;}

    }

}
