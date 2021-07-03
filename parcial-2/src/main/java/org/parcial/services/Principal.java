package org.parcial.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.config.JwtGen;
import org.parcial.models.User;

public class Principal {
    private static Principal principal = null;

    StrongPasswordEncryptor spe = new StrongPasswordEncryptor();

    private Principal() {

    }

    public static Principal getInstance(){
        if(principal == null){
            principal = new Principal();
        }
        return principal;
    }
    public String tokenCreated(User user){
        String jwtId = "PacoFish";
        String jwtIssuer = "JWT Gen";
        String jwtSubject = user.getUserName();
        int jwtTimeToLive = 620000;

        return JwtGen.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );
    }
    public Boolean tokenVerify(String userName, String jwt){
        String jwtId = "PacoFish";
        String jwtIssuer = "JWT Gen";
        try{
            if (jwt != null){
                Claims claims = JwtGen.decodeJWT(jwt);
                if (claims.getId().equals(jwtId) && claims.getIssuer().equals(jwtIssuer)
                        && claims.getSubject().equals(userName)){
                    return true;
                }else {
                    return false;
                }
            }


        }catch (JwtException e){
            System.out.println("Token is invalid");
        }

        return false;
    }


}
