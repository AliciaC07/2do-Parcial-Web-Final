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
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Shortener {
    private final Javalin app;
    Principal principal = Principal.getInstance();
    private UserService userService = UserService.getInstance();
    private UrlService urlService = UrlService.getInstance();
    private VisitService visitService = VisitService.getInstance();
    private CookieVerificationService cookieVerificationService = CookieVerificationService.getInstance();

    public Shortener(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){
        app.routes(() ->{
            path("/shortener", () ->{
                get("/", ctx -> {


                });
                post("/reg-url", ctx -> {
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

                });
                get("/redirect/:keyHash", ctx -> {
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
                        visit.setIp(ctx.ip());
                        visit.setDateTime(LocalDateTime.now());
                        visitService.create(visit);

                        ctx.redirect("https://"+url.getOriginalUrl());

                    }else {
                        ////como no existe redirecionarlo al home

                    }

                });

            });
        });
    }
}
