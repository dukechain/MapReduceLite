package com.demo.mapreducelite.runtime.shuffle;

import com.demo.mapreducelite.core.IO.network.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by chenxu on 23.09.16.
 */
public class ShuffleSender extends Client implements Runnable {

    private final int sender_buffer_size = 100;
    private File[] files = null;

    public ShuffleSender(String hostname, int port){
        super(hostname,port);
    }

    public ShuffleSender(String hostname, int port, File[] files){
        super(hostname,port);

        this.files = files;
    }

    protected void send_file_to_Server(File file) {
        try {

            FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(sender_buffer_size);

            //socketChannel.read(buffer);
            //buffer.flip();

            //System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));

            //buffer.clear();

            int num = 0;

            while ((num=fileChannel.read(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

            if (num == -1) {
                fileChannel.close();
                //socketChannel.shutdownOutput();
            }
            // accept the response from server
            //socketChannel.read(buffer);
            //buffer.flip();

            //System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));

            buffer.clear();
            //socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Override
    protected void send_Data_to_Server() {

        for (int i = 0; i < files.length; i++) {
            send_file_to_Server(files[i]);
        }

        /*try {
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(sender_buffer_size);

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
            // accept the response from server
            socketChannel.read(buffer);
            buffer.flip();

            System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));

            buffer.clear();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public void run() {
        startClient();
        send_Data_to_Server();
        closeClient();
    }

}
