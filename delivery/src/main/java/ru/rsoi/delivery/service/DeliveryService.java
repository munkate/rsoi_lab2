package ru.rsoi.delivery.service;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.rsoi.delivery.model.DeliveryModel;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

public interface DeliveryService {


    void createDelivery(DeliveryModel delivery);

    List<DeliveryModel> findAll();

    @Nullable
    DeliveryModel getDeliveryById(@NonNull Integer id);

    void editDelivery(DeliveryModel delivery);

    void deleteDeliveryById(@NonNull Integer id);

    @Nullable
    List<DeliveryModel> findAllByUserId(Integer id);

    DeliveryModel getModelFromHashMap(LinkedHashMap<String,Object> model) throws ParseException;

}
