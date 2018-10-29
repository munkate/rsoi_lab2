package ru.rsoi.ships.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.rsoi.ships.entity.Ship;
import ru.rsoi.ships.model.ShipInfo;
import ru.rsoi.ships.repository.ShipRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;


    @Override
    public void editShip(ShipInfo shipInfo) {
        Ship ship = getEntity(shipInfo);
        ship.setCapacity(shipInfo.getCapacity());
        ship.setSh_title(shipInfo.getSh_title());
        ship.setSkipper(shipInfo.getSkipper());
        ship.setType_id(shipInfo.getType_id());
        ship.setYear(shipInfo.getYear());
        shipRepository.saveAndFlush(ship);
    }


    @Override
    public List<ShipInfo> getAll() {
        return shipRepository.findAll()
                .stream()
                .map(this::buildModel)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        shipRepository.deleteById(id);
    }

    @Override
    public ShipInfo getById(Integer id) {
        return shipRepository.findById(id).map(this::buildModel).orElse(null);
    }

    @Override
    public void createShip(ShipInfo shipInfo) {

        Ship ship = new Ship(shipInfo.getSh_title(), shipInfo.getSkipper(), shipInfo.getYear(), shipInfo.getCapacity(), shipInfo.getType_id(), shipInfo.getUid());
        shipRepository.save(ship);
    }

    @NonNull
    private ShipInfo buildModel(Ship ship) {
        ShipInfo model = new ShipInfo();
        model.setType_id(ship.getType_id());
        model.setCapacity(ship.getCapacity());
        model.setSh_title(ship.getSh_title());
        model.setSkipper(ship.getSkipper());
        model.setYear(ship.getYear());
        model.setUid(ship.getUid());
        return model;
    }

    @NonNull
    private Ship getEntity(ShipInfo model) {
        Ship ship = shipRepository.findByUid(model.getUid());
        return ship;
    }
}
