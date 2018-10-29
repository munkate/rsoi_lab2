package ru.rsoi.ships.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.service.ShipService;

import java.util.List;

@RestController
@RequestMapping("/ships")

public class ShipController {
    @Autowired
    private ShipService shipService;

    @GetMapping("/{id}")
    public ShipInfo ShipById(@PathVariable Integer id) {
        return shipService.getById(id);
    }

    @GetMapping
    public List<ShipInfo> findAllShips() {
        return shipService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShip(@PathVariable Integer id) {
        shipService.delete(id);
    }

    @PostMapping("/createship")
    public void createShip(@RequestBody ShipInfo ship) {
        shipService.createShip(ship);
    }

    @PostMapping("/edit")
    public void editShip(@RequestBody ShipInfo ship) {
        shipService.editShip(ship);
    }


}
