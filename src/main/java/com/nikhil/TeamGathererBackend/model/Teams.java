package com.nikhil.TeamGathererBackend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
//    @Column(nullable = false, updatable = false)
    //TODO: Timestamp in DB is different from the one returning by application. Need to check
    @CreationTimestamp
    private LocalDateTime time;
    private int owner;
    private String gameTime;
    private String gameDate;
    private String gamePeriod;
    private int max;
    private int min;
    private int current;
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String game_time) {
        this.gameTime = game_time;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String game_date) {
        this.gameDate = game_date;
    }

    public String getGamePeriod() {
        return gamePeriod;
    }

    public void setGamePeriod(String game_period) {
        this.gamePeriod = game_period;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Teams{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", owner=" + owner +
                ", game_time='" + gameTime + '\'' +
                ", game_date='" + gameDate + '\'' +
                ", game_period='" + gamePeriod + '\'' +
                ", max=" + max +
                ", min=" + min +
                ", current=" + current +
                ", location='" + location + '\'' +
                '}';
    }
}
