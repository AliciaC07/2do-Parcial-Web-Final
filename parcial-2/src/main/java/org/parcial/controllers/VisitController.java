package org.parcial.controllers;

import io.javalin.Javalin;
import org.parcial.models.dto.VisitsDateDto;
import org.parcial.services.VisitService;

public class VisitController {
    private final Javalin app;
    private VisitService visitService = VisitService.getInstance();

    public VisitController(Javalin app) {
        this.app = app;
    }
    public void applyRoutes(){
        app.get("/prueba/visit", ctx -> {
            for (var v : visitService.getAllQuantityByDate()) {
                System.out.println(v);
            }
            ctx.render("public/html/dashboard.html");
        });
    }
}
