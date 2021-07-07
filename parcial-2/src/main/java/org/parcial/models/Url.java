package org.parcial.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Url implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String originalUrl;
    @Column
    private String cuttedUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private Boolean active = true;

    @Column(length = 1500)
    private String qrCode;

    @Column
    private LocalDate dateAdded;

    @OneToMany(mappedBy = "urlVisit", fetch = FetchType.EAGER)
    private Set<Visit> visits = new HashSet<>();

    public Url() {
    }

    public Url(Integer id, String originalUrl, String cuttedUrl, User user) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.cuttedUrl = cuttedUrl;
        this.user = user;
    }



}
