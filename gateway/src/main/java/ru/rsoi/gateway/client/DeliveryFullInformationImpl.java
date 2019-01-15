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
import redis.clients.jedis.Jedis;
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
    private Jedis jedis = new Jedis("127.0.0.1",6379);
    private boolean checkToken(String checkUrl, String jwt) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost checkRequest = new HttpPost(checkUrl);
            checkRequest.addHeader("token", jwt);
            try (CloseableHttpResponse httpResponse = httpClient.execute(checkRequest)) {
                String res = EntityUtils.toString(httpResponse.getEntity());
                if (res.equals("true")){return true;}
                else return false;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private String getToken(String service, String tokenUrl, String checkUrl)
    {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String jwt = jedis.get(service);
            if (jwt==null || !checkToken(checkUrl,jwt)){
                HttpPost httpPost = new HttpPost(tokenUrl);
                httpPost.addHeader("clientId","gateway");
                httpPost.addHeader("clientSecret","gateway");
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String res = EntityUtils.toString(response.getEntity());
                    System.out.print(res);
                    jedis.set(service+":"+res,res);
                    return res;
                }
            }

            else return jwt;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

    }
    @Override
    public boolean checkUserToken(String token){
        if (token == null) {
            return false;
        } else {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet req = new HttpGet("http://localhost:8080/checktoken");
                req.addHeader("token", token);
                try (CloseableHttpResponse httpResponse = httpClient.execute(req)) {
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    if (result.equals("true")) {
                        return true;
                    } else { return false;}
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }


    @Override
    public void editDelivery(JSONObject delivery, String userToken) {
        if (!checkUserToken(userToken)) {
            return;
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String token = getToken("deliveries","http://localhost:8083/deliveries/token", "http://localhost:8083/deliveries/checktoken");
            HttpPost httpPost = new HttpPost("http://localhost:8083/deliveries/editdelivery");
            httpPost.addHeader("token", token);
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) delivery), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                LOGGER.info("Delivery updated");
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }

    }

    @Override
    public JSONObject getShips(Pageable pageable) {
        JSONObject response = new JSONObject();
        ResponsePageImpl<ShipInfo> ships_model = null;
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String token = getToken("ships","http://localhost:8082/ships/token", "http://localhost:8082/ships/checktoken");
            HttpGet httpGet = new HttpGet("http://localhost:8082/ships?page=" + pageable.getPageNumber() + "&size="+pageable.getPageSize());
            httpGet.addHeader("token", token);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {

                ships_model =mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), new TypeReference<ResponsePageImpl<ShipInfo>>(){});
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        response.put("ships",ships_model);
        return response;
    }

    @Override
    public JSONObject getShipById(Integer id) {
        JSONObject response = new JSONObject();
        ShipInfo ship_model = null;
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String token = getToken("ships","http://localhost:8082/ships/token", "http://localhost:8082/ships/checktoken");
            HttpGet httpGet = new HttpGet("http://localhost:8082/ships/" + id + "");
            httpGet.addHeader("token", token);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {

                ship_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), ShipInfo.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        response.put("ship",ship_model);
        return response;
    }

    @Override
    public void editShip(JSONObject ship, String userToken) {
        if (checkUserToken(userToken)||userToken==null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String token = getToken("ships","http://localhost:8082/ships/token", "http://localhost:8082/ships/checktoken");
                HttpPost httpPost = new HttpPost("http://localhost:8082/ships/edit");
                httpPost.addHeader("token", token);
                httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) ship), ContentType.APPLICATION_JSON));
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                    LOGGER.info("Ship's data updated.");
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
        }
    }

    @Override
    public void createShip(JSONObject ship, String userToken) {
        if (checkUserToken(userToken)||userToken==null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String token = getToken("ships","http://localhost:8082/ships/token", "http://localhost:8082/ships/checktoken");
                HttpPost httpPost = new HttpPost("http://localhost:8082/ships/createship");
                httpPost.addHeader("token", token);
                httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) ship), ContentType.APPLICATION_JSON));
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                    LOGGER.info("Ship created.");
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
        }
    }

    @Override
    public void deleteShip(Integer id, String token) {
       /* if (checkUserToken(token)||token==null) {*/
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String mictoken = getToken("ships","http://localhost:8082/ships/token", "http://localhost:8082/ships/checktoken");
            HttpDelete httpDelete = new HttpDelete("http://localhost:8082/ships/delete/" + id + "");
            httpDelete.addHeader("token", mictoken);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpDelete)) {
                LOGGER.info("Ship deleted.");

            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
      //  }
    }



    @Override
    public JSONObject getUserDeliveriesFullInfo(Integer user_id, Pageable pageable,String token) {
        if (checkUserToken(token)||token==null) {
            JSONObject response = new JSONObject();
            ResponsePageImpl<DeliveryModel> del_model = null;
            ObjectMapper mapper = new ObjectMapper();
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String mictoken = getToken("deliveries","http://localhost:8083/deliveries/token", "http://localhost:8083/deliveries/checktoken");

                HttpGet httpGet = new HttpGet("http://localhost:8083/deliveries/users/"+user_id+"/deliveries?page="+pageable.getPageNumber()+"&size="+pageable.getPageSize());
                httpGet.addHeader("token", mictoken);
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                    del_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), new TypeReference<ResponsePageImpl<DeliveryModel>>(){});
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
            response.put("deliveries",del_model);
            return response;} else {
                return null;
            }
    }

    @Override
    public JSONObject getDeliveryFullInfo(Integer del_id, String usertoken) {
        if (checkUserToken(usertoken)||usertoken==null) {
        JSONObject response = new JSONObject();
        DeliveryModel del_model = null;
        ShipInfo ship_model = null;
        List<ShipmentInfo> shipment_model=null;
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String token = getToken("deliveries","http://localhost:8083/deliveries/token", "http://localhost:8083/deliveries/checktoken");
            HttpGet httpGet = new HttpGet("http://localhost:8083/deliveries/" + del_id + "");
            httpGet.addHeader("token", token);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                del_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), DeliveryModel.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String token = getToken("ships","http://localhost:8082/ships/token", "http://localhost:8082/ships/checktoken");
            HttpGet httpGet = new HttpGet("http://localhost:8082/ships/" + del_model.getShip_id() + "");
            httpGet.addHeader("token", token);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {

                ship_model = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), ShipInfo.class);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String token = getToken("shipments","http://localhost:8081/shipments/token", "http://localhost:8081/shipments/checktoken");
            HttpGet httpGet = new HttpGet("http://localhost:8081/shipments/deliveries/" + del_id + "");
            httpGet.addHeader("token", token);
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
        } else {
            return null;
        }
    }

    @Override
    public void deleteDelivery(Integer del_id, String usertoken) {
        if (checkUserToken(usertoken)||usertoken==null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String token = getToken("shipments", "http://localhost:8081/shipments/token", "http://localhost:8081/shipments/checktoken");
                HttpDelete httpDelete = new HttpDelete("http://localhost:8081/shipments/deleteAll/" + del_id + "");
                httpDelete.addHeader("token", token);
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpDelete)) {
                    LOGGER.info("All shipments with del_id=" + del_id + " deleted.");
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String token = getToken("deliveries", "http://localhost:8083/deliveries/token", "http://localhost:8083/deliveries/checktoken");
                HttpDelete httpDelete = new HttpDelete("http://localhost:8083/deliveries/" + del_id + "");
                httpDelete.addHeader("token", token);
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpDelete)) {
                    LOGGER.info("Delivery with id=" + del_id + " deleted.");
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
        }

    }

    @Override
    public void createDelivery(JSONObject data, String userToken) {
        if (checkUserToken(userToken)||userToken==null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String token = getToken("deliveries", "http://localhost:8083/deliveries/token", "http://localhost:8083/deliveries/checktoken");
                HttpPost httpPost = new HttpPost("http://localhost:8083/deliveries/createdeliveryAgr");
                httpPost.addHeader("token", token);
                httpPost.setEntity(new StringEntity(JSONObject.toJSONString((Map<String, ?>) data.get("delivery")), ContentType.APPLICATION_JSON));
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                    LOGGER.info("Delivery created.");
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String token = getToken("shipments", "http://localhost:8081/shipments/token", "http://localhost:8081/shipments/checktoken");
                HttpPost httpPost = new HttpPost("http://localhost:8081/shipments/createAgr");
                httpPost.addHeader("token", token);
                httpPost.setEntity(new StringEntity(JSONArray.toJSONString((List<? extends Object>) data.get("shipments")), ContentType.APPLICATION_JSON));
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                    LOGGER.info("Shipment created.");
                }
            } catch (IOException e) {
                LOGGER.error("Exception caught.", e);
            }
        }

    }

}
