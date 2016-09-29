package com.demo.mapreducelite.core.IO.network;

import com.demo.mapreducelite.core.Util.ByteUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chenxu on 25.09.16.
 */
abstract public class MessageReceiver extends Server {

    private final int message_buffer_size = 1024;
    private ByteBuffer buffer = ByteBuffer.allocate(message_buffer_size);



    abstract protected void messageHander(Object obj);

    public MessageReceiver(int port) {
        super(port);
    }

    @Override
    protected void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverChannel1 = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverChannel1.accept();

            if (socketChannel == null)
                return;

            socketChannel.configureBlocking(false);
            SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_READ);


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void read(SelectionKey key) {
        buffer.clear();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;
        try {
            /*while ((num = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                // 写入文件
                fileChannel.write(buffer);
                buffer.clear();
            }*/

            socketChannel.read(buffer);

            Object obj = ByteUtil.getObject(buffer);

            messageHander(obj);

        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException cle){
            cle.printStackTrace();
        }
        // 调用close为-1 到达末尾
       /* if (num == -1) {
            try {


                System.out.println("上传完毕");

                buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
                buffer.clear();
                socketChannel.write(buffer);
                key.cancel();
            } catch (IOException e){
                e.printStackTrace();
            }
        }*/
    }
}
