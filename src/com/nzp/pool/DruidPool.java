package com.nzp.pool;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidPool {


    // Druid硬编码方式
    @Test
    public void hardCodeDruid() throws SQLException {
        /*
            硬编码 ： 将连接池的配置和Java代码耦合在一起
            1、创建DruidDataSource连接池对象
            2、设置连接池配置信息（必须|非必须）
            3、通过连接池获取连接对象
            4、回收连接
         */
        //创建DruidDataSource连接池对象
        DruidDataSource druidDataSource = new DruidDataSource();

        //设置连接池配置信息（必须|非必须）
        // 1.设置必须的连接
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        // 2.设置非必须连接
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxActive(20);
        // 通过连接池连接对象
        DruidPooledConnection connection = druidDataSource.getConnection();
        System.out.println(connection);

        // 获取到连接池对象之后，就和之前的操作一样 CURD

        //回收链接
        connection.close();
    }



    // Druid软编码
    @Test
    public void resourcesDruid() throws Exception {

        Properties properties = new Properties();

        InputStream resourceAsStream = DruidPool.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(resourceAsStream);

        // 通过properties创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        // curd

        //回收链接
        connection.close();
    }


}
