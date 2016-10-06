package com.demo.mapreducelite.core.config;

import scala.Int;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by chenxu on 28.09.16.
 */
public class GlobalConfiguration {

    public static String master_address;

    public static List<String> slave_address_list;

    public static final int port_ShuffleReceiver =
            Integer.valueOf(Configuration.properties.getProperty(
                    "port_ShuffleReceiver",
                    "8080"));

}
