package ru.rsoi.gateway.client;

import net.minidev.json.JSONObject;

public interface DeliveryFullInformation {


    JSONObject getDeliveryFullInfo(Integer del_id);

    void deleteDelivery(Integer del_id);

    void createDelivery(JSONObject data);
}
