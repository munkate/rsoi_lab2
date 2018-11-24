package ru.rsoi.ships.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.service.ShipService;

@RestController
@RequestMapping("/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @GetMapping("/{id}")
    public ShipInfo ShipById(@PathVariable long id) {

        return shipService.getById(id);
    }

    @GetMapping
    public Page<ShipInfo> findAllShips(@RequestParam("page") Integer page,@RequestParam("size") Integer size) {
        Pageable request = PageRequest.of(page,size);
        return shipService.listAllByPage(request);
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
