package ru.rsoi.ships.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rsoi.ships.model.ShipInfo;

import java.text.ParseException;
import java.util.LinkedHashMap;

public interface ShipService {
    void delete(long id);

    ShipInfo getById(long id);

    void editShip(ShipInfo ship);

    long createShip(ShipInfo ship);

    Page<ShipInfo> listAllByPage(Pageable pageable);
    ShipInfo getModelFromHashMap(LinkedHashMap<String,Object> model);
}
