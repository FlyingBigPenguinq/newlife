package com.study.boot.Data.server;

import com.alibaba.fastjson.JSONObject;
import com.study.boot.Data.jobEntity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @ClassName SocketServer
 * @Description: TODO
 * @Author lxl
 * @Date 2020/11/23
 * @Version V1.0
 **/
@Service
public class SocketServer {

    private static Logger log = LoggerFactory.getLogger(SocketServer.class);

    private static Map<String, Socket> map;

    @Async("syl.socket.readExecutor")
    public void readMessage(Socket socket) throws IOException {
        InputStream inputStream = null;
        Message message = null;
        try {
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            message = getMessageFromString(sb.toString());
            map.put(message.getFrom(), socket);
            System.out.println("get message from client: " + sb);
            writeMessage(message.getFrom(), message.getTo(), message.getMsg());
        }catch (Exception e){
            log.info("用户from:{}  to:{} readExecutor 发生异常：{}", message.getFrom(),
                    message.getTo(), e.getMessage());
        }finally {
            inputStream.close();
            socket.close();
        }
    }

    @Async("syl.socket.writeExecutor")
    public void writeMessage(final String from, final String to, final String str) throws IOException {
        Socket socket = map.get(to);
        OutputStream outputStream = null;
        Message message = null;
        try {
            outputStream = socket.getOutputStream();
            message = new Message(from, to ,str);
            byte[] bytes = JSONObject.toJSONString(message).getBytes();
            outputStream.write(bytes);
        }catch (Exception e){
            log.info("用户from:{}  to:{} writeExecutor 发生异常：{}", message.getFrom(),
                    message.getTo(), e.getMessage());
        }finally {
            socket.close();
            outputStream.close();
        }


    }

    private static Message getMessageFromString(final String str){
        Message message = JSONObject.parseObject(str, Message.class);
        return message;
    }
}
