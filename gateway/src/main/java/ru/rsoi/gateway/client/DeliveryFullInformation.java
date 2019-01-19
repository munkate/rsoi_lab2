package ru.rsoi.gateway.client;

import net.minidev.json.JSONObject;
import org.springframework.data.domain.Pageable;

public interface DeliveryFullInformation {


    JSONObject getDeliveryFullInfo(Integer del_id, String token);

    JSONObject getUserDeliveriesFullInfo(Integer user_id, Pageable pageable, String token);

    void deleteDelivery(Integer del_id, String token) throws InterruptedException;

    long createDelivery(JSONObject data, String token);

    void editDelivery(JSONObject delivery, String token);

    JSONObject getShips(Pageable pageable);
    JSONObject getShipById(Integer id);

    void editShip(JSONObject ship, String token);
    void createShip (JSONObject data, String token);
    void deleteShip (Integer id, String token);
    boolean checkUserToken(String token);

    void createShipment(JSONObject data, String token);
}
