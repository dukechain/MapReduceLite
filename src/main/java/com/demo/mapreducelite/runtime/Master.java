package com.demo.mapreducelite.runtime;

import com.demo.mapreducelite.core.IO.network.MessageReceiver;

import java.io.IOException;
import java.util.Map;

/**
 * Created by chenxu on 26.09.16.
 */
public class Master extends MessageReceiver implements Runnable {

    public Master(int port){
        super(port);
    }

    @Override
    protected boolean setTerminated() {
        return false;
    }

    @Override
    protected void messageHander(Object obj) {
        Action msg = (Action) obj;

        switch(msg.msgType){
            case ClusterStart:
                break;
            case ClusterReady:
            case JobSubmit:
                break;
            case JobSubmitReady:
            case MapperStart:
                break;
            case MapperFinished:
            case ReducerStart:
                break;
            case ReducerFinished:
            case JobEnd:
                break;
            case JobFinished:
                break;
        }
    }

    @Override
    public void run() {
        try{
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
