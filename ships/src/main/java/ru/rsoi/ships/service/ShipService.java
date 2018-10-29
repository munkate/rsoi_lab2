package ru.rsoi.ships.service;

import ru.rsoi.ships.model.ShipInfo;

import java.util.List;

public interface ShipService {
    void delete(Integer id);

    ShipInfo getById(Integer id);

    void editShip(ShipInfo ship);

    void createShip(ShipInfo ship);

    List<ShipInfo> getAll();
}
