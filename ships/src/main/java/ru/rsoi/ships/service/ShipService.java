package ru.rsoi.ships.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rsoi.ships.model.ShipInfo;

public interface ShipService {
    void delete(Integer id);

    ShipInfo getById(Integer id);

    void editShip(ShipInfo ship);

    void createShip(ShipInfo ship);

    Page<ShipInfo> listAllByPage(Pageable pageable);
}
