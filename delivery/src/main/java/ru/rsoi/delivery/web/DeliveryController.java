package ru.rsoi.delivery.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{id}")
    public DeliveryModel DeliveryById(@PathVariable Integer id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryModel>> findAllDeliveries(@Param("page") Integer page, @Param("size") Integer size) {
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryService.findAll(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Integer id) {
        deliveryService.deleteDeliveryByUid(id);
       return ResponseEntity.ok().build();
    }

    @PostMapping("/createdelivery")
    public ResponseEntity<Void> createDelivery(@RequestBody DeliveryModel model) {
        deliveryService.createDelivery(model);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createdeliveryAgr")
    public ResponseEntity<Void> createDeliveryAgr(@RequestBody LinkedHashMap<String,Object> model) throws ParseException {
        DeliveryModel del = deliveryService.getModelFromHashMap(model);
        deliveryService.createDelivery(del);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/editdelivery")
    public ResponseEntity<Void> editDelivery(@RequestBody LinkedHashMap<String,Object> model) throws ParseException {
        DeliveryModel del = deliveryService.getModelFromHashMap(model);

        deliveryService.editDelivery(del);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/deliveries")
    public ResponseEntity<Page<DeliveryModel>> findAllDeliveriesById(@PathVariable Integer id, @RequestParam(value = "page") Integer page,@RequestParam(value = "size", required = false) Integer size) {
        Pageable request;
        if (page!=null&& size!=null) request = PageRequest.of(page,size);
        else request = PageRequest.of(0,20);

        return ResponseEntity.ok(Objects.requireNonNull(deliveryService.findAllByUserId(id, request)));
    }
    @GetMapping("/users/{id}/deliveries/{del_id}")
    public DeliveryModel findUserDeliveryById(@PathVariable Integer del_id) {
        return deliveryService.getDeliveryById(del_id);
    }
}
