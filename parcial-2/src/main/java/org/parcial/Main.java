package org.parcial;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinVelocity;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.parcial.config.UrlEncodeShort;
import org.parcial.controllers.ShortenerController;
import org.parcial.controllers.UserController;
import org.parcial.models.User;
import org.parcial.services.UserService;

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
        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        User user = new User(null, "alicruz0703@gmail.com", spe.encryptPassword("123"), "Admin");
        UserService.getInstance().create(user);
        User otro = new User(null, "123@gmail.com", spe.encryptPassword("123"),"User");
        UserService.getInstance().create(otro);

    }
}
