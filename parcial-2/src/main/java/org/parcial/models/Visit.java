package org.parcial.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime dateTime;

    @Column
    private String browser;

    @ManyToOne(fetch = FetchType.EAGER)
    private Url urlVisit;

    public Visit() {
    }

    public Visit(Integer id, String operatingSystem, String ip, LocalDateTime dateTime, String browser, Url urlVisit) {
        this.id = id;
        this.operatingSystem = operatingSystem;
        this.ip = ip;
        this.dateTime = dateTime;
        this.browser = browser;
        this.urlVisit = urlVisit;
    }
}
