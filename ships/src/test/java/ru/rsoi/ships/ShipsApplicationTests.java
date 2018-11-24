package ru.rsoi.ships;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
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
import ru.rsoi.ships.entity.enums.ShipType;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.service.ShipService;
import ru.rsoi.ships.web.ShipController;

import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ShipController.class)
public class ShipsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipService shipService;

    private static final String TITLE = "1";
    private static final String SKIPPER = "Ivanov";
    private static final Integer YEAR = 1986;
    private static final Integer CAPACITY = 200;
    private static final ShipType TYPE = ShipType.TANKER;
    private static final long UID = 125;



    @Test
    public void testfindAll_ShipsFound_ShouldReturnFoundShipEntries() throws Exception {
        Page<ShipInfo> response = new PageImpl<>(Collections.singletonList(getMockEntity()));

        when(shipService.listAllByPage(any(Pageable.class))).thenReturn(response);

        Pageable pageable = PageRequest.of(0,2);

        mockMvc.perform(MockMvcRequestBuilders.get("/ships?page="+pageable.getPageNumber()+"&size="+pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(response.getTotalPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(response.getTotalElements()));

    }


    @Test
    public void testFindById() throws Exception {
        ShipInfo response = getMockEntity();

        when(shipService.getById(any(long.class))).thenReturn(response);

        mockMvc.perform(get("/ships/{id}", UID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.sh_title").value(response.getSh_title().toString()))
                .andExpect(jsonPath("$.skipper").value(response.getSkipper()));
    }

   @Test
    public void testDeleteShip() throws Exception {
        ShipInfo ship = getMockEntity();
       ObjectMapper mapper = new ObjectMapper();

       doNothing().when(shipService).delete(any(long.class));
       try {
           mockMvc.perform(MockMvcRequestBuilders.delete("/ships/delete/{id}",UID)
                   .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                   .content(mapper.writeValueAsBytes(ship)))
                   .andExpect(status().isOk());

       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Test
    public void testCreateShip() {
        ShipInfo ship = getMockEntity();

        when(shipService.createShip(any(ShipInfo.class))).thenReturn(UID);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mockMvc.perform(post("/ships/createship")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(ship)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testEditShip() {
        ShipInfo ship = getMockEntity();
        ShipInfo new_ship = new ShipInfo("1", "Petrov", 1997, 200, ShipType.TANKER,200000015);
        ObjectMapper mapper = new ObjectMapper();
        doAnswer(new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) {
                ShipInfo actualShip = (ShipInfo) invocation.getArguments()[0];
                assertEquals(new_ship, actualShip);
                return null;
            }
        }).when(shipService).editShip(any(ShipInfo.class));
        try {
            mockMvc.perform(post("/ships/edit")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(new_ship)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ShipInfo getMockEntity()
    {
        return new ShipInfo(TITLE,SKIPPER,YEAR,CAPACITY, TYPE,UID);
    }


    @Test
    public void contextLoads() {

    }





}
