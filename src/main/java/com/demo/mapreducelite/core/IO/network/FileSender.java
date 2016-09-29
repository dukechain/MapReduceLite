package com.demo.mapreducelite.core.IO.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by chenxu on 25.09.16.
 */
public class FileSender extends Client {

    private final int sender_buffer_size = 100;
    private File file = null;

    public FileSender(String hostname, int port) {
        super(hostname, port);
    }

    //@Override
    protected void send_Data_to_Server() {
        try {
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
        }
    }
}
