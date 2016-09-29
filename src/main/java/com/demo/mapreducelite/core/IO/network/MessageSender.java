package com.demo.mapreducelite.core.IO.network;

import com.demo.mapreducelite.core.Util.ByteUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Created by chenxu on 25.09.16.
 */
public class MessageSender extends Client{

    //private final int sender_buffer_size = 1024;

    //private Serializable obj = null;

    public MessageSender(String hostname, int port) {
        super(hostname, port);
    }

    /*public MessageSender(String hostname, int port, Serializable obj) {
        super(hostname, port);

        this.obj = obj;
    }*/


    public void send_Data_to_Server(Serializable obj) {
        try {
            ByteBuffer buffer = ByteUtil.getByteBuffer(obj);

            socketChannel.write(buffer);
            buffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
