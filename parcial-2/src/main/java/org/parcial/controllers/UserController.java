package org.parcial.controllers;

import io.javalin.Javalin;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.models.CookieVerification;
import org.parcial.models.User;
import org.parcial.services.CookieVerificationService;
import org.parcial.services.Principal;
import org.parcial.services.UserService;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private final Javalin app;
    Principal principal = Principal.getInstance();
    private UserService userService = UserService.getInstance();
    private CookieVerificationService cookieVerificationService = CookieVerificationService.getInstance();

    public UserController(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){

        app.routes(() -> {
            path("/user", () ->{
                get("/", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Loging");
                    if (ctx.cookie("userToken") != null){
                        //Haqcer validacion
                        //ctx.redirect("/product");
                    }else if (ctx.sessionAttribute("user") != null){
                        //ctx.redirect("/product");
                    }
                    ctx.render("/public/login.html",model);
                });
                post("/login", ctx -> {
                    String userName = ctx.formParam("userName");
                    String password = ctx.formParam("password");

                    if (userName == null || password == null){
                        ctx.redirect("/public/login.html");
                        return;
                    }
                    User userLog = userService.authUser(userName,password);
                    if (userLog != null){

                        if (ctx.formParam("signed") != null){
                            String tokenCookie = principal.tokenCreated(userLog);
                            ctx.cookie("userToken", tokenCookie);
                            Map<String, String> cookieFound = cookieVerificationService.findByCookieUsername(userLog.getUserName());
                            if (cookieFound.get("user") != null){
                                CookieVerification cookieVerification = new CookieVerification();
                                cookieVerification.setUsername(userLog.getUserName());
                                cookieVerification.setToken(tokenCookie);
                                cookieVerificationService.edit(cookieVerification);
                            }else {
                                CookieVerification cookieVerification = new CookieVerification();
                                cookieVerification.setUsername(userLog.getUserName());
                                cookieVerification.setToken(tokenCookie);
                                cookieVerificationService.create(cookieVerification);
                            }
                        }
                        ctx.sessionAttribute("user", userLog);


                    }
                    //Haccer redirect
                    ///ctx.redirect("/product");

                });
                get("/register", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Register");
                    //hacer render al html del registrar
                });
                post("/register", ctx -> {
                    String userName = ctx.formParam("userName");
                    String password = ctx.formParam("password");
                    String role = "No asignado";
                    User newUser = new User();
                    newUser.setUserName(userName);
                    StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
                    newUser.setPassword(spe.encryptPassword(password));
                    newUser.setRol(role);
                    if (userService.findByUserName(userName) != null){
                        System.out.println("User Already exist");
                        //hacer un redirect o un html comunicando ese error
                    }else {
                        userService.create(newUser);
                    }
                    ///hacer redirect al login para que se loguee ya que fue creado el usuario

                });

            });
        });



    };
}
