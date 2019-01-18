package ru.rsoi.delivery.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.service.DeliveryService;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin
public class DeliveryController {

    @Autowired
    public DeliveryService deliveryService;
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestHeader("clientId") String client_id, @RequestHeader("clientSecret") String client_secret){
        if (deliveryService.checkClient(client_id,client_secret))
        {
            return ResponseEntity.ok(deliveryService.createJWT());
        }
        else return ResponseEntity.status(401).body("invalid_token");
    }

    @PostMapping("/checktoken")
    public ResponseEntity<String> checkToken(@RequestHeader("token") String jwt)
    {
        if (deliveryService.parseJWT(jwt))
        {return ResponseEntity.ok("Работает");}
        else return ResponseEntity.status(401).body("invalid_token");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryModel> DeliveryById(@PathVariable Integer id,@RequestHeader("token") String jwt) {
        if (deliveryService.parseJWT(jwt)){
        return ResponseEntity.ok(Objects.requireNonNull(deliveryService.getDeliveryById(id)));}
        else return ResponseEntity.status(401).body(null);


    }

    @GetMapping
    public ResponseEntity<Page<DeliveryModel>> findAllDeliveries(@Param("page") Integer page, @Param("size") Integer size,@RequestHeader("token") String jwt) {
        if (deliveryService.parseJWT(jwt)){
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryService.findAll(request));}
        else return ResponseEntity.status(401).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Integer id,@RequestHeader("token") String jwt) {
        if (deliveryService.parseJWT(jwt)){
        deliveryService.deleteDeliveryByUid(id);
       return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @PostMapping("/createdelivery")
    public ResponseEntity<Long> createDelivery(@RequestBody DeliveryModel model,@RequestHeader("token") String jwt) {
        if (deliveryService.parseJWT(jwt)){
        return ResponseEntity.ok(deliveryService.createDelivery(model));}
        else return ResponseEntity.status(401).build();
    }

    @PostMapping("/createdeliveryAgr")
    public ResponseEntity<Long> createDeliveryAgr(@RequestBody LinkedHashMap<String,Object> model,@RequestHeader("token") String jwt) throws ParseException {
        if (deliveryService.parseJWT(jwt)){
        DeliveryModel del = deliveryService.getModelFromHashMap(model);
        return ResponseEntity.ok(deliveryService.createDelivery(del));}
        else return ResponseEntity.status(401).build();
    }

    @PatchMapping("/editdelivery")
    public ResponseEntity<Void> editDelivery(@RequestBody LinkedHashMap<String,Object> model,@RequestHeader("token") String jwt) throws ParseException {
        if (deliveryService.parseJWT(jwt)){
        DeliveryModel del = deliveryService.getModelFromHashMap(model);

        deliveryService.editDelivery(del);
        return ResponseEntity.ok().build();}
        else return ResponseEntity.status(401).build();
    }

    @GetMapping("/users/{id}/deliveries")
    public ResponseEntity<Page<DeliveryModel>> findAllDeliveriesById(@PathVariable Integer id, @RequestParam(value = "page") Integer page,

                                                                     @RequestParam(value = "size", required = false) Integer size, @RequestHeader("token") String jwt) {
        Pageable request;
        if (page!=null&& size!=null) request = PageRequest.of(page,size);
        else request = PageRequest.of(0,20);
    if (deliveryService.parseJWT(jwt))
    { return ResponseEntity.ok(Objects.requireNonNull(deliveryService.findAllByUserId(id, request)));}
    else return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/users/{id}/deliveries/{del_id}")
    public ResponseEntity<DeliveryModel> findUserDeliveryById(@PathVariable Integer del_id, @RequestHeader("token") String jwt) {
        if (deliveryService.parseJWT(jwt)){
        return ResponseEntity.ok(Objects.requireNonNull(deliveryService.getDeliveryById(del_id)));}
        else return ResponseEntity.status(401).body(null);
    }

    @DeleteMapping("/rollback/{del_id}")
    public ResponseEntity<Void> rollbackCreate(@PathVariable Integer del_id,@RequestHeader("token") String jwt)
    {
        if (deliveryService.parseJWT(jwt)){
            deliveryService.deleteDeliveryByUid(del_id);
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.status(401).build();
    }
}
