package ru.rsoi.shipments.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.rsoi.shipments.model.ShipmentInfo;
import ru.rsoi.shipments.service.ShipmentService;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/{id}")
    public ShipmentInfo ShipmentById(@PathVariable Integer id) {
        return shipmentService.getById(id);
    }

    @GetMapping
    public Page<ShipmentInfo> findAllShipments(@RequestParam("page") Integer page,@RequestParam("size") Integer size) {

        Pageable request = PageRequest.of(page,size);
        return shipmentService.getAll(request);
    }

    @PostMapping("/create")
    public void createShipment(@RequestBody ShipmentInfo shipment) {
        shipmentService.createShipment(shipment);
    }

    @PostMapping("/createAgr")
    public void createShipmentAgr(@RequestBody LinkedHashMap<String,Object> shipment) throws ParseException
    {
        ShipmentInfo new_shipment = shipmentService.getModelFromHashMap(shipment);
        shipmentService.createShipment(new_shipment);
    }

    @PostMapping("/edit")
    public void editShipment(@RequestBody ShipmentInfo shipment) {
        shipmentService.editShipment(shipment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShipment(@PathVariable Integer id) {
        shipmentService.delete(id);
    }

    @GetMapping("/deliveries/{del_id}")
    public List<ShipmentInfo> findAllByDeliveryId(@PathVariable Integer del_id)
    {
        return shipmentService.findAllByDeliveryId(del_id);
    }

    @DeleteMapping("/deleteAll/{id}")
    public void deleteAllByDeliveryId(@PathVariable Integer id)
    {
        shipmentService.deleteAllByDeliveryId(id);
    }


}
