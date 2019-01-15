package ru.rsoi.ships.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipController.class);

    @GetMapping("/{id}")

    public ResponseEntity<ShipInfo> ShipById(@PathVariable long id,@RequestHeader("token") String jwt) {
        if (shipService.parseJWT(jwt)){
          return ResponseEntity.ok(shipService.getById(id));}
          else return ResponseEntity.status(401).body(null);
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
    public ResponseEntity<Page<ShipInfo>> findAllShips(@RequestParam("page") Integer page,@RequestParam("size") Integer size,@RequestHeader("token") String jwt) {
        if (shipService.parseJWT(jwt)){
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(shipService.listAllByPage(request));}
        else return ResponseEntity.status(401).body(null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Integer id,@RequestHeader("token") String jwt) {
        if (shipService.parseJWT(jwt)){
        shipService.delete(id);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @PostMapping("/createship")
    public ResponseEntity<String> createShip(@RequestBody ShipInfo ship,@RequestHeader("token") String jwt) {
        if (shipService.parseJWT(jwt)){
        try{shipService.createShip(ship);
        return ResponseEntity.ok("Ship created.");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Invalid data.");}
        }
        else return ResponseEntity.status(401).body("Отказано в доступе.");
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editShip(@RequestBody ShipInfo ship,@RequestHeader("token") String jwt) {
        if (shipService.parseJWT(jwt)){
        shipService.editShip(ship);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }


}
