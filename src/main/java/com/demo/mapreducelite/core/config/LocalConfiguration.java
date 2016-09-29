package com.demo.mapreducelite.core.config;

import java.io.File;

/**
 * Created by chenxu on 23.09.16.
 */
public class LocalConfiguration {

    public static final String dir_main_mapreducelite =
            Configuration.properties.getProperty("dir_main_mapreducelite", "/tmp/mapreducelite/");

    public static final String dir_ShuffleReceiver =
            Configuration.properties.getProperty(
                    "dir_ShuffleReceiver",
                    dir_main_mapreducelite+"ShuffeReceiver"+ File.separator);

    public static final String dir_ShuffleSender =
            Configuration.properties.getProperty(
                    "dir_ShuffleSender",
                    dir_main_mapreducelite+"ShuffleSender"+ File.separator);

    public static final int size_MessageBuffer =
            Integer.valueOf(Configuration.properties.getProperty(
                    "size_MessageBuffer",
                    "1024")
            );

    public static final String dir_mapoutput =
            Configuration.properties.getProperty(
                    "dir_mapoutput",
                    dir_main_mapreducelite+"mapoutput"+File.separator
            );

    public static final String dir_reduceinput =
            Configuration.properties.getProperty(
                    "dir_reduceinput",
                    dir_main_mapreducelite+"reduceinput"+File.separator
            );
}
