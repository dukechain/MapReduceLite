package com.demo.mapreducelite.core.IO.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by chenxu on 23.09.16.
 */
abstract public class Client {

    protected SocketChannel socketChannel;

    String hostname;
    int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    //abstract protected void send_Data_to_Server();

    protected void startClient() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.socket().connect(new InetSocketAddress(hostname, port));

            //send_Data_to_Server();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    protected void closeClient() {
        try {
            socketChannel.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /*
    public void startClient() {
        try {

            //File file = new File("./text.txt");
            //FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(100);
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
            buffer.clear();
            int num = 0;
            while ((num=fileChannel.read(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
            if (num == -1) {
                fileChannel.close();
                socketChannel.shutdownOutput();
            }
            // 接受服务器
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
            buffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }*/


}
