package com.sitproject.sit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "temp")
@Getter
@Setter
public class Temp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String device_id;
    private Double temperature;
    private LocalDateTime timestamp;

    public Temp(String device_id, Double temperature, LocalDateTime timestamp) {
        this.device_id = device_id;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

//    public Temp(String deviceId, String temperature, String timestamp) {
//    }
}
