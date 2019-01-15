package ru.rsoi.shipments.web;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.service.ShipmentService;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shipments")
@CrossOrigin
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestHeader("clientId") String client_id, @RequestHeader("clientSecret") String client_secret){
        if (shipmentService.checkClient(client_id,client_secret))
        {
            return ResponseEntity.ok(shipmentService.createJWT());
        }
        else return ResponseEntity.status(401).body("invalid_token");
    }

    @PostMapping("/checktoken")
    public ResponseEntity<String> checkToken(@RequestHeader("token") String jwt)
    {
        if (shipmentService.parseJWT(jwt))
        {return ResponseEntity.ok("Работает");}
        else return ResponseEntity.status(401).body("invalid_token");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentInfo> ShipmentById(@PathVariable Integer id,@RequestHeader("token") String jwt) {
        if (shipmentService.parseJWT(jwt)){
        return ResponseEntity.ok(shipmentService.getById(id));}
        else return ResponseEntity.status(401).body(null);
    }

    @GetMapping
    public ResponseEntity<Page<ShipmentInfo>> findAllShipments(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value="size",defaultValue = "2") Integer size,@RequestHeader("token") String jwt) {
        if (shipmentService.parseJWT(jwt)){
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(shipmentService.getAll(request));}
        else return ResponseEntity.status(401).body(null);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createShipment(@RequestBody ShipmentInfo shipment,@RequestHeader("token") String jwt) {
        if (shipmentService.parseJWT(jwt)){
        shipmentService.createShipment(shipment);
            return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @PostMapping("/createAgr")
    public ResponseEntity<Void> createShipmentAgr(@RequestBody JSONArray shipment,@RequestHeader("token") String jwt) throws ParseException
    {
        if (shipmentService.parseJWT(jwt)){
       // List<ShipmentInfo> new_shipment = shipmentService.getModelFromHashMap(shipment);
        shipmentService.createShipments(shipment);
            return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editShipment(@RequestBody ShipmentInfo shipment,@RequestHeader("token") String jwt) {
        if (shipmentService.parseJWT(jwt)){
            shipmentService.editShipment(shipment);
            return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable long id,@RequestHeader("token") String jwt) {
        if (shipmentService.parseJWT(jwt)){
        shipmentService.delete(id);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @GetMapping("/deliveries/{del_id}")
    public ResponseEntity<List<ShipmentInfo>> findAllByDeliveryId(@PathVariable Integer del_id,@RequestHeader("token") String jwt)
    {
        if (shipmentService.parseJWT(jwt)){
        return ResponseEntity.ok(shipmentService.findAllByDeliveryId(del_id));}
        else return ResponseEntity.status(401).body(null);
    }

    @DeleteMapping("/deleteAll/{id}")
    public ResponseEntity<Void> deleteAllByDeliveryId(@PathVariable Integer id,@RequestHeader("token") String jwt)
    {
        if (shipmentService.parseJWT(jwt)){
        shipmentService.deleteAllByDeliveryId(id);
        return ResponseEntity.ok().build();
        }
        else return ResponseEntity.status(401).build();
    }


}
