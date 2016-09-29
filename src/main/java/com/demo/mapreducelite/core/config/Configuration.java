package com.demo.mapreducelite.core.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by chenxu on 23.09.16.
 */
public class Configuration {

    public static Properties properties = new Properties();

    public void loadProperties(File file) {

        InputStream input = null;

        try {
            input = new FileInputStream(file);//加载Java项目根路径下的配置文件
            properties.load(input);// 加载属性文件

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String> loadAddressList(File file){
        List<String> address_list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;

            while ((tempString = reader.readLine()) != null) {
                //System.out.println("line " + line + ": " + tempString);

                address_list.add(tempString);

                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return  address_list;
    }

    public String loadAddress(File file){
        BufferedReader reader = null;
        String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            tempString = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return  tempString;
    }
}
