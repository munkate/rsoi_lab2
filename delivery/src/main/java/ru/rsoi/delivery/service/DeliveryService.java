package ru.rsoi.delivery.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.rsoi.delivery.model.DeliveryModel;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

public interface DeliveryService {


    long createDelivery(DeliveryModel delivery);

    Page<DeliveryModel> findAll(Pageable pageable);

    @Nullable
    DeliveryModel getDeliveryById(@NonNull long id);

    void editDelivery(DeliveryModel delivery);

    void deleteDeliveryByUid(@NonNull long id);

    @Nullable
    Page<DeliveryModel> findAllByUserId(Integer id, Pageable pageable);

    DeliveryModel getModelFromHashMap(LinkedHashMap<String,Object> model) throws ParseException;

}
