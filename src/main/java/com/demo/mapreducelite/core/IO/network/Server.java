package com.demo.mapreducelite.core.IO.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by chenxu on 23.09.16.
 */
abstract public class Server {

    protected Selector selector;
    protected ServerSocketChannel serverChannel;

    protected int port;
    protected boolean terminated = false;

    public Server(int port){
        this.port = port;
    }

    abstract protected void accept(SelectionKey key);
    abstract protected void read(SelectionKey key);

    protected void setTerminated() {
        terminated = true;
    }

    public void startServer() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器已开启...");

        while (!terminated) {
            int num = selector.select();

            if (num == 0)
                continue;

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();

                if (key.isAcceptable()) {

                    accept(key);

                    /*ServerSocketChannel serverChannel1 = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverChannel1.accept();

                    if (socketChannel == null)
                        continue;

                    socketChannel.configureBlocking(false);
                    SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_READ);


                    InetSocketAddress remoteAddress = (InetSocketAddress)socketChannel.getRemoteAddress();
                    File file = new File(remoteAddress.getHostName() + "_" + remoteAddress.getPort() + ".txt");
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();

                    //fileMap.put(key1, fileChannel);
                    System.out.println(socketChannel.getRemoteAddress() + "连接成功...");
                    //writeToClient(socketChannel);*/
                }
                else if (key.isReadable()){
                    //readData(key);
                    read(key);
                }
                // NIO的特点只会累加，已选择的键的集合不会删除，ready集合会被清空
                // 只是临时删除已选择键集合，当该键代表的通道上再次有感兴趣的集合准备好之后，又会被select函数选中
                it.remove();
            }
        }

        selector.close();
        serverChannel.close();
    }

}
