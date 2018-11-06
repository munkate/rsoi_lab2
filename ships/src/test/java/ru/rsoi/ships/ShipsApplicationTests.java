package ru.rsoi.ships;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rsoi.ships.entity.enums.ShipType;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.service.ShipService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShipsApplicationTests {

    @Autowired
    @Mock
    private ShipService shipService;


    @Test
    public void testfindAll_ShipsFound_ShouldReturnFoundShipEntries() throws Exception {
        ShipInfo first = new ShipInfo("1","Ivanov",1986,200, ShipType.TANKER,125);
        ShipInfo second = new ShipInfo("2","Gusev",1985,500,ShipType.BULK_CARRIER,124);
        List<ShipInfo> ships = new ArrayList<ShipInfo>();
        ships.add(first);
        ships.add(second);

        shipService.createShip(first);
        shipService.createShip(second);

        Pageable pageable = PageRequest.of(0,2);

        Page<ShipInfo> t  = shipService.listAllByPage(pageable);

        assertThat(t.getSize()).isEqualTo(2);
        assertThat(t.getContent().get(0).getUid()).isEqualTo(first.getUid());
        assertThat(t.getContent().get(1).getUid()).isEqualTo(second.getUid());

        shipService.delete(first.getUid());
        shipService.delete(second.getUid());
    }


    @Test
    public void testFindById() {
        ShipInfo first = new ShipInfo("1","Ivanov",1986,200, ShipType.TANKER,200000015);

        shipService.createShip(first);

        ShipInfo response = shipService.getById(200000015);

        assertThat(response.getSh_title()).isEqualTo(first.getSh_title());

        shipService.delete(first.getUid());
    }

    @Test
    public void testDeleteShip() {
        ShipInfo ship = new ShipInfo("1","Ivanov",1986,200, ShipType.TANKER,200000015);

        shipService.createShip(ship);

        ShipInfo response = shipService.getById(200000015);

        shipService.delete(ship.getUid());
        response = shipService.getById(200000015);
        assertThat(response).isEqualTo(null);
    }

    @Test
    public void testCreateShip() {
        ShipInfo ship = new ShipInfo("1","Ivanov",1986,200, ShipType.TANKER,200000015);

       long response =  shipService.createShip(ship);
        assertThat(response).isEqualTo(ship.getUid());

        shipService.delete(ship.getUid());
    }

    @Test
    public void testEditShip() {
        ShipInfo ship = new ShipInfo("1","Ivanov",1986,200, ShipType.TANKER,200000015);

        shipService.createShip(ship);
        shipService.editShip(new ShipInfo("1", "Petrov", 1997, 200, ShipType.TANKER,200000015));
        ShipInfo new_ship = shipService.getById(ship.getUid());

        assertThat(new_ship.getSkipper()).isEqualTo("Petrov");
        assertThat(new_ship.getYear()).isEqualTo(1997);

        shipService.delete(ship.getUid());
    }


    @Test
    public void contextLoads() {

    }





}
