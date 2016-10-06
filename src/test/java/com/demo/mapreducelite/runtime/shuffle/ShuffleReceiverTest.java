package com.demo.mapreducelite.runtime.shuffle;

import com.demo.mapreducelite.core.config.GlobalConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by chenxu on 30.09.16.
 */
public class ShuffleReceiverTest {
    ShuffleReceiver receiver;
    ShuffleSender sender;
    Thread t;

    /*@Before public void prepareSender(){
        String url = ShuffleSenderTest.class.getClassLoader().getResource("./shuffleSender").getPath();

        File [] file_list = new File(url).listFiles();

        sender = new ShuffleSender("localhost", GlobalConfiguration.port_ShuffleReceiver, file_list);


    }

    @Before public void prepare(){

        String url = ShuffleSenderTest.class.getClassLoader().getResource("").getPath();

        File dir = new File(url);

        receiver = new ShuffleReceiver(GlobalConfiguration.port_ShuffleReceiver, dir);

    }

    @Test
    public void runTest(){

        t = new Thread(sender);
        t.start();
        receiver.run();
        receiver.setTerminated();
    }*/
}
