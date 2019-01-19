package ru.rsoi.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.rsoi.gateway.client.DeliveryFullInformation;
import ru.rsoi.gateway.web.AggregatorController;
import ru.rsoi.models.DeliveryModel;
import ru.rsoi.models.ShipInfo;
import ru.rsoi.models.ShipmentInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AggregatorController.class)
public class GatewayApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryFullInformation deliveryfullService;
    @Test
    public void testGetUserDeliveriesFullInfo() throws Exception {
        JSONObject response = getMockEntity();


        when(deliveryfullService.getUserDeliveriesFullInfo(any(Integer.class),any(Pageable.class),anyString())).thenReturn(response);

        Pageable pageable = PageRequest.of(0,2);

        mockMvc.perform(MockMvcRequestBuilders.get("/agr/users/"+0+"/deliveries?page="+pageable.getPageNumber()+"&size="+pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

    }

    @Test
    public void testDeleteDelivery() throws InterruptedException {
        ObjectMapper mapper = new ObjectMapper();

        doNothing().when(deliveryfullService).deleteDelivery(any(Integer.class),anyString());
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/agr/delete/users/1/deliveries/1")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(1)))
                    .andExpect(status().isNoContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateDelivery() {
        JSONObject response = getMockEntity();

        doNothing().when(deliveryfullService).createDelivery(any(JSONObject.class),anyString());

        ObjectMapper mapper = new ObjectMapper();
        try {
            mockMvc.perform(post("/agr/users/1/delivery")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(response)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEditDelivery() throws ParseException {
        JSONObject response =  getMockEntity();

        ObjectMapper mapper = new ObjectMapper();
        doAnswer(new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) {
                JSONObject actualDel = (JSONObject) invocation.getArguments()[0];
                assertEquals(response.get("delivery"), actualDel);
                return null;
            }
        }).when(deliveryfullService).editDelivery(any(JSONObject.class),anyString());
        try {
            mockMvc.perform(MockMvcRequestBuilders.patch("/agr/editdelivery")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(response.get("delivery"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetUserDeliveryFullInfo() throws Exception {
        JSONObject response = getMockEntity();


        when(deliveryfullService.getDeliveryFullInfo(any(Integer.class),anyString())).thenReturn(response);

        Pageable pageable = PageRequest.of(0,2);

        mockMvc.perform(MockMvcRequestBuilders.get("/agr/users/"+0+"/deliveries/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.delivery.uid").value(1));
    }

    private JSONObject getMockEntity()
    {
        DeliveryModel delivery = new DeliveryModel();
        delivery.setUser_id(1);
        delivery.setUid(1);
        ShipInfo ship = new ShipInfo();
        ShipmentInfo shipment = new ShipmentInfo();
        JSONObject response = new JSONObject();
        response.put("delivery",delivery);
        response.put("ship",ship);
        response.put("shipments",shipment);
        return response;
    }


    @Test
    public void contextLoads() {
    }

}
