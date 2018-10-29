package ru.rsoi.ships.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import ru.rsoi.ships.entity.enums.ShipType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ships")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sh_id;

    @Column(name = "sh_title", length = 60)
    private String sh_title;

    @Column(name = "skipper", length = 100)
    private String skipper;

    @Column(name = "year")
    private Integer year;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "uid")
    private long uid;

    @Column(name = "type_id")
    @Enumerated(EnumType.ORDINAL)
    private ShipType type_id;

    public Ship() {

    }

    public Ship(String title, String skipper, Integer year, Integer capacity, ShipType type, long uid) {
        this.sh_title = title;
        this.skipper = skipper;
        this.year = year;
        this.capacity = capacity;
        this.type_id = type;
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return sh_id;
    }

    public String getSh_title() {
        return sh_title;
    }

    public void setSh_title(String sh_title) {
        this.sh_title = sh_title;
    }

    public String getSkipper() {
        return skipper;
    }

    public void setSkipper(String skipper) {
        this.skipper = skipper;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ShipType getType_id() {
        return type_id;
    }

    public void setType_id(ShipType type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}