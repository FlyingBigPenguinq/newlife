package com.study.boot.Data.socket;

import com.study.boot.Data.server.SocketServer;
import com.study.boot.Data.socket.SocketReadThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SocketServerCommandLineRunner
 * @Description: TODO
 * @Author lxl
 * @Date 2020/11/23
 * @Version V1.0
 **/
public class SocketServerCommandLineRunner implements CommandLineRunner {

    private static final int port = 5555;

    @Autowired
    private SocketServer socketServer;

    @Override
    public void run(String... args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setReceiveBufferSize(10240);
        while(true){
            Socket socket = serverSocket.accept();
            socketServer.readMessage(socket);
        }
    }
}
