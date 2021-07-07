package org.parcial.controllers;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.parcial.models.User;
import org.parcial.models.dto.VisitsDateDto;
import org.parcial.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisitController {
    private final Javalin app;
    private VisitService visitService = VisitService.getInstance();
    Principal principal = Principal.getInstance();
    private UserService userService = UserService.getInstance();
    private UrlService urlService = UrlService.getInstance();
    private CookieVerificationService cookieVerificationService = CookieVerificationService.getInstance();
    private String domain = "localhost:7000/";


    public VisitController(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){
        app.get("/user/dashboard", ctx -> {
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
            model.put("totalClicks", userLogged.getTotalCliks());
            model.put("totalMonth", userLogged.getQuantityPerMonth());
            Integer currentPage = 1;
            Integer pageIndex = 0;
            if (ctx.queryParam("pag") != null){
                pageIndex = ctx.queryParam("pag",Integer.class).get();
            }else {
                pageIndex = currentPage;
            }
            int pageSize = 2;
            int total = (urlService.findAllUrlByUserActiveTrue(userLogged.getId()).size()+(pageSize-1))/pageSize;
            ArrayList<Integer> pages = new ArrayList<>();
            for (int i =0; i < total; i++){
                pages.add(i+1);
            }
            if (ctx.queryParam("pag") != null){
                double div = Math.ceil((pageIndex.doubleValue()-1)/ (double) pageSize);
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
            model.put("urls", urlService.findUrlsByUserActivePagination(userLogged.getId(),pageSize,currentPage));
            model.put("graph", new Gson().toJson(visitService.getAllQuantityByDate()));
            ctx.render("public/html/dashboard.html", model);
        });
    }
}
