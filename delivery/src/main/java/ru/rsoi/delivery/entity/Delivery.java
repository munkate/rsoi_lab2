package ru.rsoi.delivery.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Nullable
    @Column(name = "departure_date")
    private Date departure_date;
    @Nullable
    @Column(name = "arrive_date")
    private Date arrive_date;
    @Nullable
    @Column(name = "origin", length = 20)
    private String origin;
    @Nullable
    @Column(name = "destination", length = 20)
    private String destination;
    @Nullable
    @Column(name = "ship_id")
    private Integer ship_id;
    @Nullable
    //  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Column(name = "user_id", nullable = false)
    private Integer user_id;
    @Nullable
    @Column(name = "uid", unique = true)
    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Delivery() {

    }

    public Delivery(Date departure_date, Date arrive_date, String origin, String destination, Integer ship_id, Integer user_id, long uid) {
        this.arrive_date = arrive_date;
        this.departure_date = departure_date;
        this.destination = destination;
        this.origin = origin;
        this.ship_id = ship_id;
        this.user_id = user_id;
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }


    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public Date getArrive_date() {
        return arrive_date;
    }

    public void setArrive_date(Date arrive_date) {
        this.arrive_date = arrive_date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getShip_id() {
        return ship_id;
    }

    public void setShip_id(Integer ship_id) {
        this.ship_id = ship_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}

