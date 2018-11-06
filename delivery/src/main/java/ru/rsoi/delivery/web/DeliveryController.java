package ru.rsoi.delivery.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.delivery.model.DeliveryModel;
import ru.rsoi.delivery.service.DeliveryService;

import java.text.ParseException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    public DeliveryService deliveryService;

    @GetMapping("/{id}")
    public DeliveryModel DeliveryById(@PathVariable Integer id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping
    public Page<DeliveryModel> findAllDeliveries(@Param("page") Integer page, @Param("size") Integer size) {
        Pageable request = PageRequest.of(page,size);
        return deliveryService.findAll(request);
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable Integer id) {
        deliveryService.deleteDeliveryByUid(id);
    }

    @PostMapping("/createdelivery")
    public void createDelivery(@RequestBody DeliveryModel model) {
        deliveryService.createDelivery(model);
    }

    @PostMapping("/createdeliveryAgr")
    public void createDeliveryAgr(@RequestBody LinkedHashMap<String,Object> model) throws ParseException {
        DeliveryModel del = deliveryService.getModelFromHashMap(model);

        deliveryService.createDelivery(del);
    }

    @PostMapping("/editdelivery")
    public void editDelivery(@RequestBody LinkedHashMap<String,Object> model) throws ParseException {
        DeliveryModel del = deliveryService.getModelFromHashMap(model);

        deliveryService.editDelivery(del);
    }

    @GetMapping("/users/{id}/deliveries")
    public Page<DeliveryModel> findAllDeliveriesById(@PathVariable Integer id, @RequestParam(value = "page") Integer page,@RequestParam(value = "size", required = false) Integer size) {
        Pageable request;
        if (page!=null&& size!=null) request = PageRequest.of(page,size);
        else request = PageRequest.of(0,20);

        return deliveryService.findAllByUserId(id, request);
    }
    @GetMapping("/users/{id}/deliveries/{del_id}")
    public DeliveryModel findUserDeliveryById(@PathVariable Integer del_id) {
        return deliveryService.getDeliveryById(del_id);
    }
}
