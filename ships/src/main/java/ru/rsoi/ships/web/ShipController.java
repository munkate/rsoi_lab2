package ru.rsoi.ships.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.service.ShipService;

@RestController
@RequestMapping("/ships")
@CrossOrigin
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
    public ResponseEntity<String> createShip(@RequestBody ShipInfo ship) {

        try{shipService.createShip(ship);
        return ResponseEntity.ok("Ship created.");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Invalid data.");
        }
    }

    @PostMapping("/edit")
    public void editShip(@RequestBody ShipInfo ship) {
        shipService.editShip(ship);
    }


}
