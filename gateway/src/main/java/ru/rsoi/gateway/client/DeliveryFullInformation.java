package ru.rsoi.gateway.client;

import net.minidev.json.JSONObject;
import org.springframework.data.domain.Pageable;

public interface DeliveryFullInformation {


    JSONObject getDeliveryFullInfo(Integer del_id);

    JSONObject getUserDeliveriesFullInfo(Integer user_id, Pageable pageable);

    void deleteDelivery(Integer del_id);

    void createDelivery(JSONObject data);
}
