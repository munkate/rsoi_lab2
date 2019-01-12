package ru.rsoi.ships.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.service.ShipService;
import ru.rsoi.ships.service.ShipServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/ships")
@CrossOrigin
public class ShipController {
    @Autowired
    private ShipService shipService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipController.class);

    @GetMapping("/{id}")

    public ShipInfo ShipById(@PathVariable long id) {

          return shipService.getById(id);
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestHeader("clientId") String client_id, @RequestHeader("clientSecret") String client_secret){
    if (shipService.checkClient(client_id,client_secret))
    {
        return ResponseEntity.ok(shipService.createJWT());
    }
    else return ResponseEntity.status(401).body("invalid_token");
    }

    @PostMapping("/checktoken")
    public ResponseEntity<Boolean> checkToken(@RequestHeader("token") String jwt)
    {
        if (shipService.parseJWT(jwt))
        {return ResponseEntity.ok(true);}
        else return ResponseEntity.status(401).body(false);
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
