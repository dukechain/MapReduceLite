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
        System.out.println("classpath路径： "+ConfigurationTest.class.getClassLoader().getResource("").getPath());
        System.out.println("当前类加载路径： "+ConfigurationTest.class.getResource("").getPath());


        String url = ConfigurationTest.class.getClassLoader().getResource("./config.properties.test").getPath();

        System.out.println(url);
        config.loadProperties(new File(url));

        Assert.assertEquals(config.properties.getProperty("hostname"), "localhost");
         /*System.out.println("url:" + properties.getProperty("url"));
            System.out.println("username:" + properties.getProperty("username"));
            System.out.println("password:" + properties.getProperty("password"));
            System.out.println("database:" + properties.getProperty("database"));*/
    }
}
