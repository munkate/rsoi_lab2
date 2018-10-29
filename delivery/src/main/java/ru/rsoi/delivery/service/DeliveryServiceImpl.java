package ru.rsoi.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.delivery.entity.Delivery;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.model.UserModel;
import ru.rsoi.delivery.repository.DeliveryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public void createDelivery(DeliveryModel delivery) {

        Delivery new_delivery = new Delivery(delivery.getDeparture_date(), delivery.getArrive_date(),
                delivery.getOrigin(), delivery.getDestination(), delivery.getShip_id(), delivery.getUser_id(), delivery.getUid());
        deliveryRepository.save(new_delivery);
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
