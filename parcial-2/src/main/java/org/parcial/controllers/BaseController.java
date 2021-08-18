package org.parcial.controllers;

import io.javalin.Javalin;

public abstract class BaseController {
    protected Javalin app;

    public BaseController(Javalin app){
        this.app = app;
    }

    abstract public void applyRoutes();
}
