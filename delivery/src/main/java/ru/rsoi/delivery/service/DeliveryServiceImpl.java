package ru.rsoi.delivery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.delivery.entity.Delivery;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.repository.DeliveryRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public void createDelivery(DeliveryModel delivery) {

        Delivery new_delivery = new Delivery(delivery.getDeparture_date(), delivery.getArrive_date(),
                delivery.getOrigin(), delivery.getDestination(), delivery.getShip_id(), delivery.getUser_id(), delivery.getUid());
        LOGGER.info("Delivery created.");
        deliveryRepository.save(new_delivery);
    }
    @Override
    public DeliveryModel getModelFromHashMap(LinkedHashMap<String,Object> model) throws ParseException {
        DeliveryModel del = new DeliveryModel();

        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date dep_date = format.parse((String)model.get("departure_date"));
        Date arr_date = format.parse((String)model.get("arrive_date"));
        String origin = (String)model.get("origin");
        del.setOrigin(origin);
        del.setDestination((String)model.get("destination"));
        int uid = (Integer)model.get("uid");
        int user_id = (Integer)model.get("user_id");
        int ship_id = (Integer)model.get("ship_id");
        del.setUid(uid);
        del.setUser_id(user_id);
        del.setShip_id(ship_id);
        del.setDeparture_date(dep_date);
        del.setArrive_date(arr_date);

       return del;
    }

    @Override
    public DeliveryModel getDeliveryById(Integer id) {
        return deliveryRepository.findById(id).map(this::buildModel).orElse(null);
    }

    @Override
    public List<DeliveryModel> findAll() {
        return deliveryRepository.findAll()
                .stream()
                .map(this::buildModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryModel> findAllByUserId(Integer id) {
        return deliveryRepository.findAllByUserId(id)
                .stream()
                .map(this::buildModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDeliveryById(Integer id) {

        deliveryRepository.deleteById(id);
        LOGGER.info("Delivery deleted.");

    }

    @Override
    public void editDelivery(DeliveryModel delivery) {
        Delivery new_delivery = getEntity(delivery);
        new_delivery.setArrive_date(delivery.getArrive_date());
        new_delivery.setDeparture_date(delivery.getDeparture_date());
        new_delivery.setDestination(delivery.getDestination());
        new_delivery.setOrigin(delivery.getOrigin());
        new_delivery.setShip_id(delivery.getShip_id());
        new_delivery.setUser_id(delivery.getUser_id());

        deliveryRepository.saveAndFlush(new_delivery);
        LOGGER.info("Delivery updated.");

    }

    @NonNull
    private DeliveryModel buildModel(Delivery delivery) {
        DeliveryModel model = new DeliveryModel();
        model.setArrive_date(delivery.getArrive_date());
        model.setDeparture_date(delivery.getDeparture_date());
        model.setDestination(delivery.getDestination());
        model.setOrigin(delivery.getOrigin());
        model.setUid(delivery.getUid());
        model.setShip_id(delivery.getShip_id());
        model.setUser_id(delivery.getUser_id());
        return model;
    }

    @NonNull
    private Delivery getEntity(DeliveryModel model) {
        Delivery delivery = deliveryRepository.findByUid(model.getUid());
        return delivery;
    }

}
