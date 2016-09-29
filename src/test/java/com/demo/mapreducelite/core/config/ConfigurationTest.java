package com.demo.mapreducelite.core.config;


import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by chenxu on 23.09.16.
 */
public class ConfigurationTest {

    Configuration config;

    @Before
    public void prepare(){
        config = new Configuration();
    }

    @Test
    public void loadPropertiesTest(){
        config.loadProperties(new File("conf/config.properties.test"));

        Assert.assertEquals(config.properties.getProperty("hostname"), "localhost");
         /*System.out.println("url:" + properties.getProperty("url"));
            System.out.println("username:" + properties.getProperty("username"));
            System.out.println("password:" + properties.getProperty("password"));
            System.out.println("database:" + properties.getProperty("database"));*/
    }
}
