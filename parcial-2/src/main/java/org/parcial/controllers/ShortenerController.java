package org.parcial.controllers;

import eu.bitwalker.useragentutils.UserAgent;
import io.javalin.Javalin;
import org.parcial.config.UrlEncodeShort;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.Visit;
import org.parcial.services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ShortenerController {
    private final Javalin app;
    Principal principal = Principal.getInstance();
    private UserService userService = UserService.getInstance();
    private UrlService urlService = UrlService.getInstance();
    private VisitService visitService = VisitService.getInstance();
    private CookieVerificationService cookieVerificationService = CookieVerificationService.getInstance();
    private String domain = "localhost:7000/";

    public ShortenerController(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){

        app.routes(() ->{
            app.get("/shorty/:cut", ctx -> {
                Map<String, Object> model = new HashMap<>();
                Url url  = urlService.findUrlByHash(ctx.pathParam("cut"));
                if (url == null){
                    ctx.redirect("/shortener/shorty");
                    return;
                }
                User userLogged = new User();
                if ( ctx.cookie("userToken") != null){

                    Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                    if (cookie.get("user") == null){
                        model.put("logged", false);
                        model.put("userRole", "");
                    }else {
                        if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                            model.put("logged", true);
                            userLogged = userService.findByUserName(cookie.get("user"));
                            model.put("userRole", userLogged.getRol());
                        }else{
                            model.put("logged", false);
                            model.put("userRole", "");
                        }
                    }

                }else if (ctx.sessionAttribute("user") != null){
                    model.put("logged", true);
                    userLogged = ctx.sessionAttribute("user");
                    userLogged = userService.findByUserName(userLogged.getUserName());
                    model.put("userRole", userLogged.getRol());
                }else {
                    model.put("logged", false);
                    model.put("userRole", "");
                }

                model.put("cuttedUrl", url.getCuttedUrl());
                model.put("hashUrl", url.getCuttedUrl());
                model.put("urlCut", domain+url.getCuttedUrl());
                ctx.render("/public/html/landing.html", model);


            });
            app.before("/:hash",ctx -> {
                String has = ctx.pathParam("hash");
                System.out.println(has);
               if (has.isEmpty() || has.equals("shortener")){
                   ctx.redirect("/shortener/shorty");
               }
            });

            app.get("/shortener/shorty", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("cuttedUrl", "");
                User userLogged = new User();
                if ( ctx.cookie("userToken") != null){

                    Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                    if (cookie.get("user") == null){
                        model.put("logged", false);
                        model.put("userRole", "");
                    }else {
                        if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                            model.put("logged", true);
                            userLogged = userService.findByUserName(cookie.get("user"));
                            model.put("userRole", userLogged.getRol());
                        }else{
                            model.put("logged", false);
                            model.put("userRole", "");
                        }
                    }

                }else if (ctx.sessionAttribute("user") != null){
                    model.put("logged", true);
                    userLogged = ctx.sessionAttribute("user");
                    userLogged = userService.findByUserName(userLogged.getUserName());
                    model.put("userLogged", userLogged);
                }else {
                    model.put("logged", false);
                    model.put("userLogged", "");
                }
                ctx.render("/public/html/landing.html", model);


                });

            app.post("/shortener/register/reg-url", ctx -> {
                    String originalUrl = ctx.formParam("originalUrl");
                    UrlEncodeShort urlEncodeShort = new UrlEncodeShort();
                    Url url = new Url();
                    String shortened = urlEncodeShort.encodeUrl(originalUrl);
                    url.setOriginalUrl(urlEncodeShort.decodeUrl(shortened));
                    url.setCuttedUrl(shortened);

                    User user = null;
                    if ( ctx.cookie("userToken") != null){

                        Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                        if (cookie.get("user") != null){
                            if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                                user = userService.findByUserName(cookie.get("user"));
                            }
                        }

                    }else if (ctx.sessionAttribute("user") != null){
                       user = ctx.sessionAttribute("user");
                    }
                    if (user != null){
                        url.setUser(user);
                        url.setDateAdded(LocalDate.now());
                        urlService.create(url);
                        byte [] qrcode = principal.getQRCodeImage("https://"+domain+"/url/info/"+url.getId(),100,100);
                        url.setQrCode(principal.getQrImageBase64(qrcode));
                        urlService.edit(url);
                    }else {
                        List<Url> urls = new ArrayList<>();
                        if (ctx.sessionAttribute("urlsS") != null){
                            urls = ctx.sessionAttribute("urlsS");
                        }
                        url.setDateAdded(LocalDate.now());
                        urlService.create(url);
                        urls.add(url);
                        ctx.sessionAttribute("urlsS", urls);


                    }
                    if (user != null){

                        ctx.redirect("/user/dashboard");

                    }else {
                        ctx.redirect("/shorty/"+url.getCuttedUrl());

                    }

                });

            app.get("/:keyHash", ctx -> {
                    String shorted = ctx.pathParam("keyHash");
                    Url url = urlService.findUrlByHash(shorted);
                    if (url != null){
                        String userAgent = ctx.userAgent();
                        UserAgent userAgentP = UserAgent.parseUserAgentString(userAgent);

                        System.out.println(userAgentP.getOperatingSystem());
                        System.out.println(userAgentP.getBrowser());
                        Visit visit = new Visit();
                        visit.setUrlVisit(url);
                        visit.setBrowser(userAgentP.getBrowser().getName());
                        visit.setOperatingSystem(userAgentP.getOperatingSystem().getName());
                        visit.setIp(ctx.ip());
                        System.out.println(ctx.ip());
                        visit.setDate(LocalDate.now());
                        visit.setTime(LocalTime.now());
                        visitService.create(visit);

                        ctx.redirect("https://"+url.getOriginalUrl());

                    }else {
                        ////como no existe redirecionarlo al home
                        ctx.redirect("/shortener/shorty");

                    }

                });


        });
    }
}
