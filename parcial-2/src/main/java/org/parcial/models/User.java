package org.parcial.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "app_user")
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String userName;
    @Column
    private String password;
    @Column
    private String rol;

    @Column
    private Boolean active = true;

    @OneToMany(mappedBy = "user")
    List<Url> urls = new ArrayList<>();

    public User() {
    }

    public User(Integer id, String userName, String password, String rol) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.rol = rol;
    }
}
