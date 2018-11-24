package ru.rsoi.gateway.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rsoi.gateway.response.ResponsePageImpl;
import ru.rsoi.models.DeliveryModel;
import ru.rsoi.models.ShipInfo;
import ru.rsoi.models.ShipmentInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryFullInformationImpl implements DeliveryFullInformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryFullInformationImpl.class);

    @Override
    public void editDelivery(JSONObject delivery) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8083/deliveries/editdelivery");
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) delivery), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                LOGGER.info("Delivery updated");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
    }

    @Override
    public JSONObject getUserDeliveriesFullInfo(Integer user_id, Pageable pageable) {
            JSONObject response = new JSONObject();
            ResponsePageImpl<DeliveryModel> del_model = null;
            ObjectMapper mapper = new ObjectMapper();
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet("http://localhost:8083/deliveries/users/"+user_id+"/deliveries?page="+pageable.getPageNumber()+"&size="+pageable.getPageSize());
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                    del_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), new TypeReference<ResponsePageImpl<DeliveryModel>>(){});
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
            response.put("deliveries",del_model);
            return response;
    }

    @Override
    public JSONObject getDeliveryFullInfo(Integer del_id) {
        JSONObject response = new JSONObject();
        DeliveryModel del_model = null;
        ShipInfo ship_model = null;
        List<ShipmentInfo> shipment_model=null;
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8083/deliveries/" + del_id + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                del_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), DeliveryModel.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8082/ships/" + del_model.getShip_id() + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {

                ship_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), ShipInfo.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8081/shipments/deliveries/" + del_id + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                shipment_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), new TypeReference<List<ShipmentInfo>>() {});
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        response.put("delivery",del_model);
        response.put("ship",ship_model);
        response.put( "shipments",shipment_model);
        return response;
    }

    @Override
    public void deleteDelivery(Integer del_id) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete("http://localhost:8081/shipments/deleteAll/" + del_id+ "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpDelete)) {
                LOGGER.info("All shipments with del_id="+del_id+" deleted.");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete("http://localhost:8083/deliveries/" + del_id + "");
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpDelete)) {
                LOGGER.info("Delivery with id="+del_id+" deleted.");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }

    }

    @Override
    public void createDelivery(JSONObject data) {
          try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8083/deliveries/createdeliveryAgr");
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) data.get("delivery")), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                LOGGER.info("Delivery created.");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8081/shipments/createAgr");
            httpPost.setEntity(new StringEntity(JSONArray.toJSONString((List<? extends Object>) data.get("shipments")), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                LOGGER.info("Shipment created.");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }

    }
}
