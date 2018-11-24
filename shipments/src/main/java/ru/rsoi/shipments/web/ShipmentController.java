package ru.rsoi.shipments.web;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
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
import java.util.Map;

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
    public Page<ShipmentInfo> findAllShipments(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value="size",defaultValue = "2") Integer size) {

        Pageable request = PageRequest.of(page,size);
        return shipmentService.getAll(request);
    }

    @PostMapping("/create")
    public void createShipment(@RequestBody ShipmentInfo shipment) {
        shipmentService.createShipment(shipment);
    }

    @PostMapping("/createAgr")
    public void createShipmentAgr(@RequestBody JSONArray shipment) throws ParseException
    {
       // List<ShipmentInfo> new_shipment = shipmentService.getModelFromHashMap(shipment);
        shipmentService.createShipments(shipment);
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
