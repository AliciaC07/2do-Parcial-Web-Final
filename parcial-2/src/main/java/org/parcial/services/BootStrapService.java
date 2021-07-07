package org.practica.services;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BootStrapService {
    private static Server server;


    public static void startDb()  {
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    public static void stopDb() throws SQLException {
        if(server!=null) {
            server.stop();
        }
    }



}
