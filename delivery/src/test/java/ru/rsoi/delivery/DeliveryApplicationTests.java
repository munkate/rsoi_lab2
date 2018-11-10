package ru.rsoi.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.service.DeliveryService;
import ru.rsoi.delivery.web.DeliveryController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedHashMap;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DeliveryController.class)
public class DeliveryApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeliveryService deliveryService;

    private static final String ORIGIN = "1";
    private static final String DESTINATION = "1";
    private static final Integer SHIP_ID = 1;
    private static final Integer USER_ID = 1;
    private static final long UID = 1;

    @Test
    public void testfindAll_DeliveryFound_ShouldReturnFoundDeliveryEntries() throws Exception {
        Page<DeliveryModel> response = new PageImpl<>(Collections.singletonList(getMockEntity()));

        when(deliveryService.findAll(any(Pageable.class))).thenReturn(response);

        Pageable pageable = PageRequest.of(0,2);

        mockMvc.perform(MockMvcRequestBuilders.get("/deliveries?page="+pageable.getPageNumber()+"&size="+pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(response.getTotalPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(response.getTotalElements()));
    }


    @Test
    public void testFindById() throws Exception {
        DeliveryModel first = getMockEntity();

        when(deliveryService.getDeliveryById(any(long.class))).thenReturn(first);

        mockMvc.perform(get("/deliveries/{id}", UID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.origin").value(first.getOrigin()))
                .andExpect(jsonPath("$.destination").value(first.getDestination()));
    }

    @Test
    public void testDeleteDelivery() {
        DeliveryModel response = getMockEntity();
        ObjectMapper mapper = new ObjectMapper();

        doNothing().when(deliveryService).deleteDeliveryByUid(any(long.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/deliveries/{id}",UID)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(response)))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateDelivery() {
        DeliveryModel response = getMockEntity();

        when(deliveryService.createDelivery(any(DeliveryModel.class))).thenReturn(UID);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mockMvc.perform(post("/deliveries/createdelivery")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(response)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateDeliveryAgr() throws ParseException {
        LinkedHashMap<String,Object> delivery = new LinkedHashMap<>();
        delivery.put("departure_date", new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"));
        delivery.put("arrive_date",new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-24"));
        delivery.put("origin",ORIGIN);
        delivery.put("destination",DESTINATION);
        delivery.put("ship_id",SHIP_ID);
        delivery.put("user_id",USER_ID);
        delivery.put("uid",UID);
        when(deliveryService.createDelivery(any(DeliveryModel.class))).thenReturn(UID);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mockMvc.perform(post("/deliveries/createdeliveryAgr")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(delivery)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testEditDelivery() throws ParseException {
        DeliveryModel delivery = getMockEntity();

        DeliveryModel new_delivery = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Харьков","Хабаровск",1,2,13);

        ObjectMapper mapper = new ObjectMapper();
        doAnswer(new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) {
                DeliveryModel actualDelivery = (DeliveryModel) invocation.getArguments()[0];
                assertEquals(new_delivery, actualDelivery);
                return null;
            }
        }).when(deliveryService).editDelivery(any(DeliveryModel.class));
        try {
            mockMvc.perform(patch("/deliveries/editdelivery")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(new_delivery)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private DeliveryModel getMockEntity()
    {
        try {
            return new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"),new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"),ORIGIN,DESTINATION,SHIP_ID,USER_ID,UID);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void contextLoads() {
    }

}
