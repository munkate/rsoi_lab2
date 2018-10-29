package ru.rsoi.aggregator.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rsoi.models.DeliveryModel;
import ru.rsoi.models.ShipInfo;
import ru.rsoi.models.ShipmentInfo;

import java.io.IOException;
import java.util.List;

@Service
public class DeliveryFullInformationImpl implements DeliveryFullInformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFullInformationImpl.class);

    @Override
    public JSONObject getDeliveryFullInfo(Integer del_id) {
        JSONObject response = new JSONObject();
        DeliveryModel del_model = null;
        String del_response, ship_response,shipment_response;
        ShipInfo ship_model = null;
        List<ShipmentInfo> shipment_model=null;
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8083/deliveries/" + del_id + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                del_response = EntityUtils.toString(httpResponse.getEntity());
                del_model = mapper.readValue(del_response, DeliveryModel.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8082/ships/" + del_model.getShip_id() + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                ship_response = EntityUtils.toString(httpResponse.getEntity());
                ship_model = mapper.readValue(ship_response, ShipInfo.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8081/shipments/deliveries/" + del_id + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                shipment_response = EntityUtils.toString(httpResponse.getEntity());
                shipment_model = mapper.readValue(shipment_response, new TypeReference<List<ShipmentInfo>>() {});
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        response.put("delivery",del_model);
        response.put("ship",ship_model);
        response.put( "shipments",shipment_model);
        return response;
    }
}
