package ru.rsoi.gateway.web;

import com.netflix.zuul.context.RequestContext;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rsoi.gateway.client.DeliveryFullInformation;
import ru.rsoi.models.DeliveryModel;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/agr")
@CrossOrigin
public class AggregatorController {
    @Autowired
    private DeliveryFullInformation deliveryFullService;

    @GetMapping("/users/{user_id}/deliveries")
    public ResponseEntity<JSONObject> getUserDeliveriesFullInfo(@PathVariable Integer user_id, @RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestHeader(value = "usertoken",required = false) String token) {

        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryFullService.getUserDeliveriesFullInfo(user_id,request, token));
    }
    @GetMapping("/users/{user_id}/deliveries/{del_id}")
    public ResponseEntity<JSONObject> getUserDeliveryFullInfo(@PathVariable Integer del_id,
                                                              @RequestHeader(value ="usertoken",required = false) String token) {

        return ResponseEntity.ok(deliveryFullService.getDeliveryFullInfo(del_id,token));
    }

    @DeleteMapping("/delete/users/{user_id}/deliveries/{id}")
    public ResponseEntity<Void> deleteDelivery (@PathVariable Integer id,
                                                @RequestHeader(value ="usertoken",required = false) String token)
    {

        deliveryFullService.deleteDelivery(id,token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{user_id}/delivery")
    public ResponseEntity<Void> createDelivery(@RequestBody JSONObject data,
                                               @RequestHeader(value ="usertoken",required = false) String token)
    {

        if(deliveryFullService.createDelivery(data,token)!=0)
        { return ResponseEntity.ok().build();}
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/editdelivery")
    public ResponseEntity<Void> editDelivery(@RequestBody JSONObject model,
                                             @RequestHeader(value ="usertoken", required = false) String token) {
        deliveryFullService.editDelivery(model,token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ships")
    public ResponseEntity<JSONObject> findAllShips(@RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size){
        Pageable request = PageRequest.of(page,size);
        return ResponseEntity.ok(deliveryFullService.getShips(request));
    }

    @GetMapping("/ships/{ship_id}")
    public ResponseEntity<JSONObject> findShipById(@PathVariable Integer ship_id){
        try{
        return ResponseEntity.ok(deliveryFullService.getShipById(ship_id));
    } catch (RuntimeException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Ship Not Found", e);}
    }

    @DeleteMapping("/ships/delete/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Integer id,@RequestHeader(value ="usertoken", required = false) String token){
        deliveryFullService.deleteShip(id,token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ships/ship")
    public ResponseEntity<Void> createShip(@RequestBody JSONObject data,
                                           @RequestHeader(value ="usertoken", required = false) String token){
        deliveryFullService.createShip(data,token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/ship")
    public ResponseEntity<Void> editShip(@RequestBody JSONObject data,
                                         @RequestHeader(value ="usertoken", required = false) String token){
        try{deliveryFullService.editShip(data,token);
        return ResponseEntity.ok().build();}
        catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("shipments/shipment")
    public ResponseEntity<Void> createShipment(@RequestBody JSONObject data,
                                         @RequestHeader(value ="usertoken", required = false) String token){
        deliveryFullService.createShipment(data,token);
        return ResponseEntity.ok().build();
    }


    //TODO Прописать редиректы на shipment для post-запросов

}
