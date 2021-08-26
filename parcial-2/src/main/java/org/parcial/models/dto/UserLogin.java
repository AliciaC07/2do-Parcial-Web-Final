package org.parcial.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {

    private Integer id;

    private String userName;

    private String password;

    private String rol;

    private String token;

    private String type = "Bearer";
}
