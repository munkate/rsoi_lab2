package ru.rsoi.shipments;

import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rsoi.shipments.entity.enums.Unit;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.service.ShipmentService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShipmentsApplicationTests {

    @Autowired
    @Mock
    private ShipmentService shipmentService;


    @Test
    public void testfindAll_ShipmentsFound_ShouldReturnFoundShipmentEntries() throws Exception {
        ShipmentInfo first = new ShipmentInfo("1",50, Unit.KG,200000, 0);
        ShipmentInfo second = new ShipmentInfo("2",100,Unit.TONNA,500,0);
        List<ShipmentInfo> shipments = new ArrayList<ShipmentInfo>();
        shipments.add(first);
        shipments.add(second);

        shipmentService.createShipment(first);
        shipmentService.createShipment(second);

        Pageable pageable = PageRequest.of(1,2);

        Page<ShipmentInfo> t  = shipmentService.getAll(pageable);

        assertThat(t.getSize()).isEqualTo(2);
        assertThat(t.getContent().get(0).getUid()).isEqualTo(first.getUid());
        assertThat(t.getContent().get(1).getUid()).isEqualTo(second.getUid());

        shipmentService.delete(first.getUid());
        shipmentService.delete(second.getUid());
    }


    @Test
    public void testFindById() {
        ShipmentInfo first = new ShipmentInfo("1",50, Unit.KG,200000, 1);

        shipmentService.createShipment(first);

        ShipmentInfo response = shipmentService.getById(200000);

        assertThat(response.getTitle()).isEqualTo(first.getTitle());

        shipmentService.delete(first.getUid());
    }


    @Test
    public void testFindAllByDeliveryId() {
        ShipmentInfo first = new ShipmentInfo("1",50, Unit.KG,200000, 0);

        shipmentService.createShipment(first);

        List<ShipmentInfo> response = shipmentService.findAllByDeliveryId(0);

        assertThat(response.get(0).getTitle()).isEqualTo(first.getTitle());

        shipmentService.delete(first.getUid());
    }

    @Test
    public void testDeleteAllByDeliveryId() {
        ShipmentInfo first = new ShipmentInfo("1",50, Unit.KG,200000, 0);

        shipmentService.createShipment(first);

        shipmentService.delete(first.getUid());

        List<ShipmentInfo> response = shipmentService.findAllByDeliveryId(0);

        assertThat(response.size()).isEqualTo(0);
    }

    @Test
    public void testDeleteShip() {
        ShipmentInfo first = new ShipmentInfo("1",50, Unit.KG,200000, 1);

        shipmentService.createShipment(first);

        ShipmentInfo response = shipmentService.getById(200000015);

        shipmentService.delete(first.getUid());
        response = shipmentService.getById(200000015);
        assertThat(response).isEqualTo(null);
    }

    @Test
    public void testCreateShipment() {
        ShipmentInfo shipment = new ShipmentInfo("1",50, Unit.KG,200000, 1);

        long response =  shipmentService.createShipment(shipment);
        assertThat(response).isEqualTo(shipment.getUid());

        shipmentService.delete(shipment.getUid());
    }

    @Test
    public void testCreateShipments() {
        ShipmentInfo first = new ShipmentInfo("1",50, Unit.KG,500, 0);
        ShipmentInfo second = new ShipmentInfo("2",100,Unit.TONNA,501,0);
        JSONArray shipments = new JSONArray();
        shipments.add(first);
        shipments.add(second);

        shipmentService.createShipments(shipments);

        ShipmentInfo checkFirst = shipmentService.getById(500);
        ShipmentInfo checkSecond = shipmentService.getById(501);

        assertThat(checkFirst.getTitle()).isEqualTo("1");
        assertThat(checkSecond.getTitle()).isEqualTo("2");

    }

    @Test
    public void testEditShipment() {
        ShipmentInfo shipment = new ShipmentInfo("1",50, Unit.KG,2000001, 1);

        shipmentService.createShipment(shipment);
        shipmentService.editShipment(new ShipmentInfo("1",100, Unit.KG,2000001, 1));
        ShipmentInfo new_shipment = shipmentService.getById(shipment.getUid());

        assertThat(new_shipment.getDeclare_value()).isEqualTo(100);

        shipmentService.delete(shipment.getUid());
    }

    @Test
    public void contextLoads() {
    }

}
