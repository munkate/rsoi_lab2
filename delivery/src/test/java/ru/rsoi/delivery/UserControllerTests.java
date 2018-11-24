package ru.rsoi.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.rsoi.delivery.model.UserModel;
import ru.rsoi.delivery.service.DeliveryService;
import ru.rsoi.delivery.service.UserService;
import ru.rsoi.delivery.web.DeliveryController;
import ru.rsoi.delivery.web.UserController;

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
@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void testFindUserById() throws Exception {
        UserModel response = getUserMockEntity();

        when(userService.getUserById(any(Integer.class))).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.last_name").value(response.getLast_name().toString()))
                .andExpect(jsonPath("$.first_name").value(response.getFirst_name()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        UserModel user = getUserMockEntity();
        ObjectMapper mapper = new ObjectMapper();

        doNothing().when(userService).deleteUserById(any(Integer.class));
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(user)))
                    .andExpect(status().isOk());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testCreateUser() {
        UserModel user = getUserMockEntity();

        doNothing().when(userService).createUser(any(UserModel.class));

        ObjectMapper mapper = new ObjectMapper();
        try {
            mockMvc.perform(post("/users/createuser")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(user)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testEditUser() {
        UserModel user = getUserMockEntity();
        ObjectMapper mapper = new ObjectMapper();
        doAnswer(new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) {
                UserModel actualShip = (UserModel) invocation.getArguments()[0];
                assertEquals(user, actualShip);
                return null;
            }
        }).when(userService).editUser(any(UserModel.class));
        try {
            mockMvc.perform(post("/users/edituser")
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(mapper.writeValueAsBytes(user)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private UserModel getUserMockEntity()
    {
        return new UserModel("dsd","dssd","sdsd","sdfsdf","sdfsdf","565465156451564651");
    }

    @Test
    public void contextLoads() {
    }

}
