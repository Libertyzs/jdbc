package com.nzp.base;

import org.junit.Test;

import java.sql.*;

public class JDBCOperation {
    public static void main(String[] args) {
        new JDBCOperation().singleMoreLineQurey("zhangsan");


    }


    // 查询单行单列
    @Test
    public void singleLineQuery() {
        // 注册驱动（可以省略）
        // 数据库连接路径
        String localConnection = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";

        try {
            // 获取连接数据库对象
            Connection connection = DriverManager.getConnection(localConnection, "root", "root");

            // 获取执行 PrepareStatement对象
            String sqlQuery = "select count(emp_age) from t_emp";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            // 接收sql执行的结果
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("count =" + count);
            }
            // 释放资源
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 查询单行多列
    public void singleMoreLineQurey(String name) {

        // 数据库连接路径
        String localConnection = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";

        try {
            // 注册驱动（可以省略）
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // 获取连接数据库对象
            Connection connection = DriverManager.getConnection(localConnection, "root", "root");

            // 获取执行 PrepareStatement对象
            String sqlQuery = "select emp_id,emp_name,emp_salary,emp_age from t_emp where emp_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);

            // 接收sql执行的结果
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int empId = resultSet.getInt("emp_id");
                String empName = resultSet.getString("emp_name");
                String empSalary = resultSet.getString("emp_salary");
                int empAge = resultSet.getInt("emp_age");
                System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
            }
            // 释放资源
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 查询多行多列
    @Test
    public void MoreQuery() {

        // 获取连接对象
        String url = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "root");

            // statement对象
            String sqlQuery = "select emp_id, emp_name, emp_salary,emp_age from t_emp";

            // 接收sql处理的请求
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int empId = resultSet.getInt("emp_id");
                String empName = resultSet.getString("emp_name");
                String empSalary = resultSet.getString("emp_salary");
                int empAge = resultSet.getInt("emp_age");
                System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
            }

            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 插入数据
    @Test
    public void singleLineInsert() {

        // 获取mysql数据库连接对象
        String localConnection = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";
        try {
            Connection connection = DriverManager.getConnection(localConnection, "root", "root");

            // 获取执行sql的语句
            String sqlInsert = "insert into t_emp(emp_name, emp_salary, emp_age) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, "test");
            preparedStatement.setDouble(2, 3400);
            preparedStatement.setInt(3, 32);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("数据插入成功");
                new JDBCOperation().singleMoreLineQurey("test");
            } else {
                System.out.println("数据插入失败");
            }

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    // 更新数据
    @Test
    public void singleLineUpdate() {

        // 获取连接数据库的对象
        String localConnection = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";

        try {
            Connection connection = DriverManager.getConnection(localConnection, "root", "root");

            // 获取将sql语句传递给数据库的对象 PreparedStatement
            String sqlUpdate = "update t_emp set emp_name = ? where emp_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, "ssssssss");
            preparedStatement.setInt(2, 7);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("数据修改成功");
                new JDBCOperation().singleMoreLineQurey("ssssssss");
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 删除数据
    @Test
    public void singleLineDelect() {

        // 获取连接mysql数据库的对象
        String localConnection = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";
        try {
            Connection connection = DriverManager.getConnection(localConnection, "root", "root");

            // 获取将sql语句发送到数据库的对象
            String sqlDelete = "delete from t_emp where emp_id > ? and emp_id < ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, 7);
            preparedStatement.setInt(2, 12);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("数据删除成功");
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
