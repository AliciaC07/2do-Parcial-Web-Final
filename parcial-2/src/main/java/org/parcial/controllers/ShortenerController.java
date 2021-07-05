package org.parcial.controllers;

import eu.bitwalker.useragentutils.UserAgent;
import io.javalin.Javalin;
import org.parcial.config.UrlEncodeShort;
import org.parcial.models.Url;
import org.parcial.models.User;
import org.parcial.models.Visit;
import org.parcial.services.*;

import java.time.LocalDateTime;
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

    public ShortenerController(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){

        app.routes(() ->{
            app.get("/shorty/:cut", ctx -> {
                Map<String, Object> model = new HashMap<>();
                Url url  = urlService.findUrlByHash(ctx.pathParam("cut"));
                if ( ctx.cookie("userToken") != null){

                    Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                    if (cookie.get("user") == null){
                        model.put("logged", false);
                    }else {
                        if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                            model.put("logged", true);
                        }else{
                            model.put("logged", false);
                        }
                    }

                }else if (ctx.sessionAttribute("user") != null){
                    model.put("logged", true);
                }else {
                    model.put("logged", false);
                }
                model.put("cuttedUrl", url.getCuttedUrl());
                model.put("hashUrl", url.getCuttedUrl());
                model.put("urlCut", "localhost:7000/"+url.getCuttedUrl());
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
                    if ( ctx.cookie("userToken") != null){

                        Map<String, String> cookie  = cookieVerificationService.findByCookieToken(ctx.cookie("userToken"));
                        if (cookie.get("user") == null){
                            model.put("logged", false);
                        }else {
                            if (principal.tokenVerify(cookie.get("user"), (cookie.get("token")))){
                                model.put("logged", true);
                            }else{
                                model.put("logged", false);
                            }
                        }

                    }else if (ctx.sessionAttribute("user") != null){
                        model.put("logged", true);
                    }else {
                        model.put("logged", false);
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
                    System.out.println(url.getOriginalUrl());
                    System.out.println(url.getCuttedUrl());
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
                        urlService.create(url);
                    }else {
                        List<Url> urls = new ArrayList<>();
                        if (ctx.sessionAttribute("urlsS") != null){
                            urls = ctx.sessionAttribute("urlsS");
                        }
                        urlService.create(url);
                        urls.add(url);
                        ctx.sessionAttribute("urlsS", urls);


                    }
                    ////hacer redirect a la pagina
                    ctx.redirect("/shorty/"+url.getCuttedUrl());

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
                        visit.setDateTime(LocalDateTime.now());
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
