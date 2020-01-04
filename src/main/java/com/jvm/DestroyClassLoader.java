package com.jvm;

import java.sql.*;

/**
 * 破坏 类加载双亲委派机制 ：让父类加载器请求子类加载器去加载类
 *      只要在父类加载器中设置Thread.currentThread().getContextClassLoader()
 *      就能让父类请求子类去加载类
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/3 15:08
 */
public class DestroyClassLoader {
    public static void main(String[] args) throws SQLException {
        // 详细文档 https://blog.csdn.net/sinat_34976604/article/details/86723663
        Connection connection = DriverManager.getConnection("jdbc:mysql://10.10.9.18:3306/information_schema");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from character_sets");
        while (resultSet.next()){
            String character_set_name = resultSet.getString("character_set_name");
            String default_collate_name = resultSet.getString("default_collate_name");
            System.out.println(character_set_name + "   " + default_collate_name);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
