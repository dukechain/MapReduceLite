package com.demo.mapreducelite.core.IO.network;

import com.demo.mapreducelite.core.config.LocalConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chenxu on 25.09.16.
 */
public class FileReceiver extends Server {

    private ByteBuffer buffer = ByteBuffer.allocate(1024*1024);

    private File file;
    private FileChannel fileChannel;

    public FileReceiver(int port, File file) {
        super(port);

        this.file = file;
        fileChannel = null;
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


            /*InetSocketAddress remoteAddress = (InetSocketAddress)socketChannel.getRemoteAddress();

            File file = new File(LocalConfiguration.dir_ShuffleReceiver+
                    remoteAddress.getHostName() + "_" + remoteAddress.getPort());*/

            fileChannel = new FileOutputStream(file).getChannel();
            //fileMap.put(key1, fileChannel);

            System.out.println(socketChannel.getRemoteAddress() + "连接成功...");

            //writeToClient(socketChannel);


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
            while ((num = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                // 写入文件
                fileChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return;
        }
        // 调用close为-1 到达末尾
        if (num == -1) {
            try {
                fileChannel.close();

                System.out.println("上传完毕");

                buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
                buffer.clear();
                socketChannel.write(buffer);
                key.cancel();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
