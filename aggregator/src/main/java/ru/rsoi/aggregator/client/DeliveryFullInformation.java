package ru.rsoi.aggregator.client;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public interface DeliveryFullInformation {


    JSONObject getDeliveryFullInfo(Integer del_id);
}