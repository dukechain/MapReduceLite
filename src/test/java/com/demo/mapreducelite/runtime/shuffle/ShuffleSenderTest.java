package com.demo.mapreducelite.runtime.shuffle;

import com.demo.mapreducelite.core.Util.FileUtil;
import com.demo.mapreducelite.core.config.GlobalConfiguration;
import com.demo.mapreducelite.core.config.LocalConfiguration;
import com.demo.mapreducelite.runtime.Slave;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by chenxu on 30.09.16.
 */
public class ShuffleSenderTest {

    ShuffleSender sender;

    ShuffleReceiver receiver;
    Thread t;

    @Before public void prepareReceiver(){
        Slave.set_terminated_Receiver(false);
        File f = new File(LocalConfiguration.dir_main_mapreducelite);
        if(f.exists()){
            FileUtil.deleteDir(f);
        }

        String url = ShuffleSenderTest.class.getClassLoader().getResource("").getPath();

        File dir = new File(url);

        receiver = new ShuffleReceiver(8010, dir);

        t = new Thread(receiver);
        t.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        url = ShuffleSenderTest.class.getClassLoader().getResource("./shuffleSender").getPath();

        File [] file_list = new File(url).listFiles();

        sender = new ShuffleSender("localhost", 8010, file_list);

    }

    @Test
    public void runtest(){
        sender.run();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Slave.set_terminated_Receiver(true);
        //t.interrupt();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
