package org.parcial.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "app_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String rol;

    public User() {
    }

    public User(Integer id, String userName, String password, String rol) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.rol = rol;
    }
}
