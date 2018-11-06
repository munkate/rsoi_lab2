package ru.rsoi.delivery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.service.DeliveryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryApplicationTests {

    @Autowired
    @Mock
    private DeliveryService deliveryService;


    @Test
    public void testfindAll_DeliveryFound_ShouldReturnFoundDeliveryEntries() throws Exception {
        DeliveryModel first = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Москва","Хабаровск",1,2,13);
        DeliveryModel second = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-22"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Москва","Ялта",2,1,12);

        deliveryService.createDelivery(first);
        deliveryService.createDelivery(second);

        Pageable pageable = PageRequest.of(0,2);

        Page<DeliveryModel> t  = deliveryService.findAll(pageable);

        assertThat(t.getSize()).isEqualTo(2);
        assertThat(t.getContent().get(0).getUid()).isEqualTo(first.getUid());
        assertThat(t.getContent().get(1).getUid()).isEqualTo(second.getUid());

        deliveryService.deleteDeliveryByUid(first.getUid());
        deliveryService.deleteDeliveryByUid(second.getUid());
    }


    @Test
    public void testFindById() {
        DeliveryModel first = null;
        try {
            first = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Москва","Хабаровск",1,2,13);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        deliveryService.createDelivery(first);

        DeliveryModel response = deliveryService.getDeliveryById(13);

        assertThat(response.getUid()).isEqualTo(first.getUid());


        deliveryService.deleteDeliveryByUid(first.getUid());
    }

    @Test
    public void testDeleteDelivery() {
        DeliveryModel first = null;
        try {
            first = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Москва","Хабаровск",1,2,13);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        deliveryService.createDelivery(first);

        DeliveryModel response = deliveryService.getDeliveryById(13);

        deliveryService.deleteDeliveryByUid(first.getUid());
        response = deliveryService.getDeliveryById(13);
        assertThat(response).isEqualTo(null);
    }

    @Test
    public void testCreateDelivery() {
        DeliveryModel delivery = null;
        try {
            delivery = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Москва","Хабаровск",1,2,13);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long response =  deliveryService.createDelivery(delivery);
        assertThat(response).isEqualTo(delivery.getUid());

        deliveryService.deleteDeliveryByUid(delivery.getUid());
    }

    @Test
    public void testEditDelivery() {
        DeliveryModel delivery = null;
        try {
            delivery = new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Москва","Хабаровск",1,2,13);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        deliveryService.createDelivery(delivery);
        try {
            deliveryService.editDelivery(new DeliveryModel(new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-23"), new SimpleDateFormat("yyyy-mm-dd").parse("2018-10-28"), "Харьков","Хабаровск",1,2,13));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DeliveryModel new_delivery = deliveryService.getDeliveryById(delivery.getUid());

            assertThat(new_delivery.getOrigin()).isEqualTo("Харьков");

        deliveryService.deleteDeliveryByUid(delivery.getUid());
    }

    @Test
    public void contextLoads() {
    }

}
