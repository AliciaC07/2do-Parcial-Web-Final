package org.parcial.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Visit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String operatingSystem;

    @Column
    private String ip;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @Column
    private String browser;

    @ManyToOne(fetch = FetchType.EAGER)
    private Url urlVisit;

    public Visit() {
    }

    public Visit(String operatingSystem, String ip, LocalDate date, LocalTime time, String browser, Url urlVisit) {
        this.operatingSystem = operatingSystem;
        this.ip = ip;
        this.date = date;
        this.time = time;
        this.browser = browser;
        this.urlVisit = urlVisit;
    }


}
