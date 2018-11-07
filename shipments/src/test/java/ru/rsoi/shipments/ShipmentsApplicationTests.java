package ru.rsoi.shipments;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
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
import ru.rsoi.shipments.entity.enums.Unit;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.service.ShipmentService;
import ru.rsoi.shipments.web.ShipmentController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ShipmentController.class)
public class ShipmentsApplicationTests {


    @Autowired
    private MockMvc mockMvc;




    @MockBean
    private ShipmentService shipmentService;

    private static final String TITLE = "1";
    private static final Integer DECLARE_VALUE = 50;
    private static final Unit UNIT_ID = Unit.CENTNER;
    private static final long UID = 1;
    private static final Integer DEL_ID = 1;



    @Test
    public void testfindAll_ShipmentsFound_ShouldReturnFoundShipmentEntries() throws Exception {
        Page<ShipmentInfo> response = new PageImpl<>(Collections.singletonList(getMockEntity()));

        when(shipmentService.getAll(any(Pageable.class))).thenReturn(response);

        Pageable pageable = PageRequest.of(0,2);
        mockMvc.perform(get("/shipments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(response.getTotalPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(response.getTotalElements()));
    }


    @Test
    public void testFindById() throws Exception {
        ShipmentInfo response = getMockEntity();

        when(shipmentService.getById(any(long.class))).thenReturn(response);

        mockMvc.perform(get("/shipments/{id}", UID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.declare_value").value(response.getDeclare_value().toString()))
                .andExpect(jsonPath("$.title").value(response.getTitle()));
    }


    @Test
    public void testFindAllByDeliveryId() throws Exception {
        List<ShipmentInfo> response = new ArrayList<>();

        when(shipmentService.findAllByDeliveryId(any(Integer.class))).thenReturn(response);

        mockMvc.perform(get("/shipments/deliveries/{id}", DEL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testDeleteAllByDeliveryId() {
        ShipmentInfo shipment = new ShipmentInfo("1",50, Unit.KG,200000, 0);

        ObjectMapper mapper = new ObjectMapper();
        doNothing().when(shipmentService).deleteAllByDeliveryId(any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/shipments/deleteAll/{id}",DEL_ID)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(shipment)))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteShipment() {
        ShipmentInfo shipment = getMockEntity();
        ObjectMapper mapper = new ObjectMapper();

        doNothing().when(shipmentService).delete(any(long.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/shipments/delete/{id}",UID)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(shipment)))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateShipment() {
        ShipmentInfo shipment = getMockEntity();

        when(shipmentService.createShipment(any(ShipmentInfo.class))).thenReturn(UID);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mockMvc.perform(post("/shipments/create")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(shipment)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testEditShipment() {
        ShipmentInfo shipment = getMockEntity();
        ObjectMapper mapper = new ObjectMapper();
        ShipmentInfo new_shipment = new ShipmentInfo("1", 100,Unit.TONNA,1,1);
        doAnswer(new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) {
                ShipmentInfo actualShip = (ShipmentInfo) invocation.getArguments()[0];
                assertEquals(new_shipment, actualShip);
                return null;
            }
        }).when(shipmentService).editShipment(any(ShipmentInfo.class));
        try {
            mockMvc.perform(post("/shipments/edit")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(new_shipment)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ShipmentInfo getMockEntity()
    {
        return new ShipmentInfo(TITLE,DECLARE_VALUE,UNIT_ID,UID,DEL_ID);
    }


    @Test
    public void contextLoads() {
    }

}
