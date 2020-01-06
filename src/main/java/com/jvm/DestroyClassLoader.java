package com.jvm;

import java.sql.*;
import java.util.Enumeration;

/**
 * 破坏 类加载双亲委派机制 ：让父类加载器请求子类加载器去加载类
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/3 15:08
 */
public class DestroyClassLoader {
    public static void main(String[] args) throws SQLException {
        // 详细文档 https://blog.csdn.net/sinat_34976604/article/details/86723663
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements())
        {
            driver = drivers.nextElement();
            System.out.println(driver.getClass() + "------" + driver.getClass().getClassLoader());
        }
        System.out.println(DriverManager.class.getClassLoader());
        // null DriverManager由BootstrapClassLoader加载的 BootstrapClassLoader由c++实现  并不存在与java中 所以为null


        /*Connection connection = DriverManager.getConnection("jdbc:mysql://10.10.9.18:3306/information_schema");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from character_sets");
        while (resultSet.next()){
            String character_set_name = resultSet.getString("character_set_name");
            String default_collate_name = resultSet.getString("default_collate_name");
            System.out.println(character_set_name + "   " + default_collate_name);
        }
        resultSet.close();
        statement.close();
        connection.close();*/
    }
}
