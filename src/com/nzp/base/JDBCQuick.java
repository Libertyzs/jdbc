package com.nzp.base;

import java.sql.*;

public class JDBCQuick {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 获取连接对象
        String url = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";
        Connection connection = DriverManager.getConnection(url, "root", "root");

        // statement对象
        String sqlQuery = "select emp_id, emp_name, emp_salary,emp_age from t_emp";
        String sqlUpdate = "insert into t_emp(emp_name,emp_salary,emp_age) values ('zhangsan',2000,34)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

        // 接收sql处理的请求
        int i = preparedStatement.executeUpdate();
        if(i > 0){
            System.out.println("数据更新成功");
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()){
                int empId = resultSet.getInt("emp_id");
                String empName = resultSet.getString("emp_name");
                String empSalary = resultSet.getString("emp_salary");
                int empAge = resultSet.getInt("emp_age");
                System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
            }
        }else {
            System.out.println("数据更新失败");
        }
    }
}