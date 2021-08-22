package org.parcial.controllers;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.models.CookieVerification;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    private final Javalin app;
    Principal principal = Principal.getInstance();
    private UserService userService = UserService.getInstance();
    private VisitService visitService = VisitService.getInstance();
    private CookieVerificationService cookieVerificationService = CookieVerificationService.getInstance();

    public UserController(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){

        app.routes(() -> {

            app.get("/user/login", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Loging");
                    if (ctx.cookie("userToken") != null){
                        //Haqcer validacion
                        ctx.redirect("/shortener");
                    }else if (ctx.sessionAttribute("user") != null){
                        ctx.redirect("/shortener");
                    }
                    model.put("logged", false);
                    ctx.render("/public/html/login.html",model);
                });
            app.get("/user/all-urls", ctx -> {
               Map<String, Object> model = new HashMap<>();
               User userLogged = null;
                if ( ctx.cookie("userToken") != null){

                    Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                    if (cookie.get("user") == null){
                        model.put("logged", false);
                        ctx.redirect("/user/login");
                        return;
                    }else {
                        if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                            model.put("logged", true);
                            userLogged = userService.findByUserName(cookie.get("user"));
                            if (!userLogged.getRol().equalsIgnoreCase("Admin")){
                                ctx.redirect("/shortener");
                                return;
                            }
                        }else{
                            model.put("logged", false);
                            ctx.redirect("/user/login");
                            return;
                        }
                    }

                }else if (ctx.sessionAttribute("user") != null){
                    model.put("logged", true);
                    userLogged = ctx.sessionAttribute("user");
                    userLogged = userService.findByUserName(userLogged.getUserName());
                    if (!userLogged.getRol().equalsIgnoreCase("Admin")){
                        ctx.redirect("/shortener");
                        return;
                    }
                }else {
                    model.put("logged", false);
                    ctx.redirect("/user/login");
                    return;
                }
                Integer currentPage = 1;
                Integer pageIndex = 0;
                if (ctx.queryParam("pag") != null){
                    pageIndex = ctx.queryParam("pag",Integer.class).get();
                }else {
                    pageIndex = currentPage;
                }
                Integer pageSize = 4;
                Integer total = (UrlService.getInstance().findAllUrlsByActive().size()+(pageSize-1))/pageSize;
                ArrayList<Integer> pages = new ArrayList<>();
                for (int i =0; i < total; i++){
                    pages.add(i+1);
                }
                if (ctx.queryParam("pag") != null){
                    double div = Math.ceil((pageIndex.doubleValue()-1)/pageSize.doubleValue());
                    currentPage = Double.valueOf(div).intValue()+1;
                    if (currentPage < ctx.queryParam("pag",Integer.class).get()){
                        currentPage = ctx.queryParam("pag",Integer.class).get();
                    }
                }else {
                    currentPage = 1;
                }
                model.put("userLogged", userLogged);
                model.put("currentPage",currentPage);
                model.put("pages", pages);
                model.put("totalPages", total);
                model.put("urls", UrlService.getInstance().findAllByActiveTruePagination(pageSize, currentPage));
                ctx.render("public/html/listUrl.html", model);
            });
            app.get("/url/delete/:id", ctx -> {
                Integer id = ctx.pathParam("id", Integer.class).get();
                Url url = UrlService.getInstance().find(id);
                if (url != null){
                    url.setActive(false);
                    UrlService.getInstance().edit(url);
                }else {
                    ctx.redirect("/user/all-urls");
                }
                ctx.redirect("/user/all-urls");
            });
            app.get("/url/delete-user/:id", ctx -> {
                Integer id = ctx.pathParam("id", Integer.class).get();
                Url url = UrlService.getInstance().find(id);
                if (url != null){
                    url.setActive(false);
                    UrlService.getInstance().edit(url);
                }else {
                    ctx.redirect("/user/dashboard");
                }
                ctx.redirect("/user/dashboard");
            });
            app.get("/url/info/:id", ctx -> {
                Map<String, Object> model = new HashMap<>();
                User userLogged = null;
                if ( ctx.cookie("userToken") != null){

                    Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                    if (cookie.get("user") == null){
                        model.put("logged", false);
                        ctx.redirect("/user/login");
                        return;
                    }else {
                        if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                            model.put("logged", true);
                            userLogged = userService.findByUserName(cookie.get("user"));
                            if (!userLogged.getRol().equalsIgnoreCase("Admin")){
                                ctx.redirect("/shortener");
                                return;
                            }
                        }else{
                            model.put("logged", false);
                            ctx.redirect("/user/login");
                            return;
                        }
                    }

                }else if (ctx.sessionAttribute("user") != null){
                    model.put("logged", true);
                    userLogged = ctx.sessionAttribute("user");
                    userLogged = userService.findByUserName(userLogged.getUserName());
                }else {
                    model.put("logged", false);
                    ctx.redirect("/user/login");
                    return;
                }
                model.put("userLogged", userLogged);
                Integer id = ctx.pathParam("id", Integer.class).get();
                Url url = UrlService.getInstance().find(id);
                model.put("url", url);
                model.put("osGraph", new Gson().toJson(visitService.getQuantityByOperatingSystem(url.getId())));
                model.put("browserGraph", new Gson().toJson(visitService.getQuantityByBrowser(url.getId())));
                ctx.render("public/html/infoUrl.html", model);
            });

            app.get("/user/users", ctx -> {
                Map<String, Object> model = new HashMap<>();
                User userLogged = null;
                Integer currentPage = 1;
                Integer pageIndex = 0;
                if (ctx.queryParam("pag") != null){
                    pageIndex = ctx.queryParam("pag",Integer.class).get();
                }else {
                    pageIndex = currentPage;
                }
                Integer pageSize = 4;
                Integer total = (userService.findAllUsersByActive().size()+(pageSize-1))/pageSize;
                System.out.println(total);
                ArrayList<Integer> pages = new ArrayList<>();
                for (int i =0; i < total; i++){
                    pages.add(i+1);
                }
                if (ctx.queryParam("pag") != null){
                    double div = Math.ceil((pageIndex.doubleValue()-1)/pageSize.doubleValue());
                    currentPage = Double.valueOf(div).intValue()+1;
                    if (currentPage < ctx.queryParam("pag",Integer.class).get()){
                        currentPage = ctx.queryParam("pag",Integer.class).get();
                    }
                }else {
                    currentPage = 1;
                }
                model.put("currentPage",currentPage);
                model.put("pages", pages);
                model.put("totalPages", total);

                    if ( ctx.cookie("userToken") != null){

                        Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                        if (cookie.get("user") == null){
                            model.put("logged", false);
                            ctx.redirect("/user/login");
                            return;
                        }else {
                            if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                                model.put("logged", true);
                                userLogged = userService.findByUserName(cookie.get("user"));
                            }else{
                                model.put("logged", false);
                                ctx.redirect("/user/login");
                                return;
                            }
                        }

                    }else if (ctx.sessionAttribute("user") != null){
                        model.put("logged", true);
                        userLogged = ctx.sessionAttribute("user");
                        userLogged = userService.findByUserName(userLogged.getUserName());
                    }else {
                        model.put("logged", false);
                        ctx.redirect("/user/login");
                        return;
                    }
                    if (!userLogged.getRol().equalsIgnoreCase("Admin")){
                        ctx.redirect("/shortener");
                        return;
                    }

                     model.put("users", userService.findAllByActiveTruePagination(pageSize, currentPage));
                     model.put("userLogged", userLogged);


                    ctx.render("/public/html/listUser.html", model);
                });
            app.post("/user/login", ctx -> {
                    String userName = ctx.formParam("email");
                    String password = ctx.formParam("password");

                    if (userName == null || password == null){
                        ctx.redirect("/public/login.html");
                        return;
                    }
                    User userLog = userService.authUser(userName,password);
                    if (userLog != null){

                        if (ctx.formParam("signedIn") != null){
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


                    }else {
                        ctx.redirect("/user/register");
                        return;
                    }
                    List<Url> urlsSotore = ctx.sessionAttribute("urlsS");
                    if (urlsSotore != null){
                        UrlService.getInstance().changeUrlCreator(userLog, urlsSotore);
                    }
                    //Haccer redirect
                    ctx.redirect("/user/dashboard");

                });
            app.get("/user/register", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Register");
                    //hacer render al html del registrar
                    if (ctx.cookie("userToken") != null){
                        //Haqcer validacion
                        ctx.redirect("/shortener");
                    }else if (ctx.sessionAttribute("user") != null){
                        ctx.redirect("/shortener");
                    }
                    model.put("logged", false);
                    ctx.render("/public/html/CreateUser.html",model);
                });
            app.post("/user/register", ctx -> {
                    String userName = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    String role = "User";
                    User newUser = new User();
                    newUser.setUserName(userName);
                    StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
                    newUser.setPassword(spe.encryptPassword(password));
                    newUser.setRol(role);
                    if (userService.findByUserName(userName) != null){
                        System.out.println("User Already exist");
                        //hacer un redirect o un html comunicando ese error
                        ctx.redirect("/user/register");
                        return;
                    }else {
                        userService.create(newUser);
                    }
                    List<Url> urlsSotore = ctx.sessionAttribute("urlsS");
                    if (urlsSotore != null){
                        UrlService.getInstance().changeUrlCreator(newUser, urlsSotore);
                    }


                    ctx.redirect("/user/login");

                });
            app.get("/user/logout", ctx -> {

                    if (ctx.cookie("userToken") != null){

                        cookieVerificationService.delete(cookieVerificationService.findByCookieTokenVeri(ctx.cookie("userToken")).getId());
                        ctx.removeCookie("userToken","/");
                    }
                    if (ctx.sessionAttribute("user") != null){
                        ctx.req.getSession().invalidate();
                    }
                    ctx.redirect("/shortener");
                });

            app.get("/user/delete/:id", ctx -> {
                    Integer id = ctx.pathParam("id", Integer.class).get();
                    User user = userService.findById(id);
                    if (user != null){
                        user.setActive(false);
                        userService.edit(user);
                    }else {
                        ///redirect ese user no existe
                        System.out.println("No existe");
                    }
                    ctx.redirect("/user/users");
                });
            app.get("/user/edit/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    User userLogged = new User();
                    if ( ctx.cookie("userToken") != null){

                        Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                        if (cookie.get("user") == null){
                            model.put("logged", false);
                            model.put("userRole", "");
                            ctx.redirect("/user/login");
                            return;
                        }else {
                            if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                                model.put("logged", true);
                                userLogged = userService.findByUserName(cookie.get("user"));
                                model.put("userRole", userLogged.getRol());
                            }else{
                                model.put("logged", false);
                                ctx.redirect("/user/login");
                                return;
                            }
                        }

                    }else if (ctx.sessionAttribute("user") != null){
                        model.put("logged", true);
                        userLogged = ctx.sessionAttribute("user");
                        userLogged = userService.findByUserName(userLogged.getUserName());
                        model.put("userRole", userLogged.getRol());
                    }else {
                        model.put("logged", false);
                        ctx.redirect("/user/login");
                        return;
                    }
                    if (!userLogged.getRol().equalsIgnoreCase("Admin")){
                        ctx.redirect("/shortener");
                        return;
                    }
                   User editUser = userService.findById(ctx.pathParam("id", Integer.class).get());
                    model.put("user", editUser);
                    ctx.render("/public/html/editUser.html", model);
                });
            app.post("/user/edit/:id", ctx -> {
                    Integer id = ctx.pathParam("id", Integer.class).get();
                    User user = userService.findById(id);
                    if (user != null){
                        if (ctx.formParam("role") != null){
                            user.setRol("Admin");
                            userService.edit(user);
                        }else {
                            user.setRol("No asignado");
                            userService.edit(user);
                        }
                    }
                    ctx.redirect("/user/users");
                });


        });



    };
}
