package org.parcial.Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.parcial.api.gRPCshortlyController;

import java.io.IOException;

public class UrlServer {
    private static final int PORT = 50051;
    private Server server;

    public void start() throws IOException{
        server = ServerBuilder.forPort(PORT).addService(new gRPCshortlyController()).build().start();
    }

    public void blockUntilShutdown() throws InterruptedException{
        if (server == null){
            return;
        }
        server.awaitTermination();
    }
}
