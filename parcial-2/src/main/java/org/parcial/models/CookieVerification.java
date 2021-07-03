package org.parcial.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
public class CookieVerification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cookie_ve_id")
    private Integer id;
    @Column
    private String username;
    @Column
    private String token;

    public CookieVerification() {
    }

    public CookieVerification(Integer id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}
