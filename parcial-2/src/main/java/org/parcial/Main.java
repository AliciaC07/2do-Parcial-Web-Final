package org.parcial;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinVelocity;
import org.parcial.config.UrlEncodeShort;
import org.parcial.controllers.ShortenerController;
import org.parcial.controllers.UserController;

public class Main {
    public static void main(String[] args){
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/public"));
            config.addStaticFiles("/public");
            config.enableCorsForAllOrigins();
            JavalinRenderer.register(JavalinVelocity.INSTANCE, ".html");
        }).start(7000);
        org.practica.services.BootStrapService.startDb();
        //app.get("/", ctx -> ctx.result("Hola Mundo en Javalin :-D"));
        app.get("/",ctx -> {
            ctx.redirect("shortener");
        });
        new UserController(app).applyRoutes();
        new ShortenerController(app).applyRoutes();
        UrlEncodeShort urlEncodeShort = new UrlEncodeShort();
        String url = "https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-sync/4.2.3";
        String urlshorte = urlEncodeShort.encodeUrl(url);
        System.out.println(urlshorte);
        System.out.println(urlEncodeShort.decodeUrl(urlshorte));
    }
}
