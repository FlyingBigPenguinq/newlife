package com.study.boot.Data.socket;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SocketThread
 * @Description: TODO
 * @Author lxl
 * @Date 2020/11/15
 * @Version V1.0
 **/
public class SocketReadThread implements Runnable {

    private Socket socket;

    private static Map<String, Socket> socketMap = new HashMap<>();

    public SocketReadThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println("get message from client: " + sb);
            inputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
