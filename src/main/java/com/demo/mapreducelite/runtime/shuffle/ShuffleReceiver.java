package com.demo.mapreducelite.runtime.shuffle;

import com.demo.mapreducelite.core.IO.network.Server;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenxu on 23.09.16.
 */
public class ShuffleReceiver extends Server {

    private ExecutorService exec = Executors.newFixedThreadPool(2);

    private ByteBuffer buffer = ByteBuffer.allocate(1024*1024);

    //使用Map保存每个连接，当OP_READ就绪时，根据key找到对应的文件对其进行写入。若将其封装成一个类，作为值保存，可以再上传过程中显示进度等等
    Map<SelectionKey, FileChannel> fileMap = new HashMap<SelectionKey, FileChannel>();

    public ShuffleReceiver(int port){
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


            InetSocketAddress remoteAddress = (InetSocketAddress)socketChannel.getRemoteAddress();

            File file = new File(LocalConfiguration.dir_ShuffleReceiver+
                    remoteAddress.getHostName() + "_" + remoteAddress.getPort());

            FileChannel fileChannel = new FileOutputStream(file).getChannel();
            fileMap.put(key1, fileChannel);

            System.out.println(socketChannel.getRemoteAddress() + "连接成功...");

            writeToClient(socketChannel);


        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void read(final SelectionKey key) {

        key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
        exec.execute(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                FileChannel fileChannel = fileMap.get(key);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 只有调用cancel才会真正从已选择的键的集合里面移除，否则下次select的时候又能得到
                    // 一端close掉了，其对端仍然是可读的，读取得到EOF，返回-1
                    key.cancel();
                    return;
                }
                // Channel的read方法可能返回0，返回0并不一定代表读取完了。
                // 工作线程结束对通道的读取，需要再次更新键的ready集合，将感兴趣的集合重新放在里面
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                // 调用wakeup，使得选择器上的第一个还没有返回的选择操作立即返回即重新select
                key.selector().wakeup();
            }
        });

    }

    private void writeToClient(SocketChannel socketChannel) throws IOException {
        buffer.clear();
        buffer.put((socketChannel.getRemoteAddress() + "连接成功").getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }
}
