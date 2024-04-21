package com.nzp.advanced;

import com.nzp.advanced.entity.Employee;
import org.junit.Test;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class JDBCAdvanced {

    public static void main(String[] args) {

    }

    @Test
    public void querySingleRow() throws Exception {

        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 获取数据库连接
        String localConnection = "jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true";
        Connection connection = DriverManager.getConnection(localConnection,"root","root");

        // 获取预编译sql
        String sqlQuery = "select * from t_emp";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        // 获取sql处理的记过
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Employee> employee = new ArrayList<>();
        while(resultSet.next()){
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_Salary");
            int empAge = resultSet.getInt("emp_age");
            Employee employee1 = new Employee(empId,empName,empSalary,empAge);
            employee.add(employee1);
        }

        for (Employee e : employee){
            System.out.println(e);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }



    @Test
    public void testReturnPK() throws SQLException {
        // 获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true", "root", "root");

        // 获取预编译sql对象
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_emp (emp_name,emp_salary,emp_age) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
        //4.编写SQL语句并执行，获取结果
        Employee employee = new Employee(null,"rose",666.66,28);
        preparedStatement.setString(1,employee.getEmpName());
        preparedStatement.setDouble(2,employee.getEmpSalary());
        preparedStatement.setDouble(3,employee.getEmpAge());

        int i = preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if(i>0){
            if(generatedKeys.next()){
                employee.setEmpId(generatedKeys.getInt(1));
            }
        }

        System.out.println(employee);

        //7.释放资源(先开后关原则)
        generatedKeys.close();
        preparedStatement.close();
        connection.close();

    }


    @Test
    public void testBatch() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu?useUnicode=true&characterEncoding=utf8&useSSL=true","root","root");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_emp(emp_name,emp_salary,emp_age) values (?,?,?)");

        long startTime = System.currentTimeMillis();
        for (int i=0;i<1000;i++){
            preparedStatement.setString(1,"test"+i);
            preparedStatement.setDouble(2,1000+i);
            preparedStatement.setInt(3,1+i);
            // 批处理
            preparedStatement.addBatch();
        }
        //执行批处理命令
        preparedStatement.executeBatch();

        long endTime = System.currentTimeMillis();
        System.out.println("时间为：" + (endTime - startTime));

        preparedStatement.close();
        connection.close();
    }

}
