package school.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class School {

    static float TotalFeesForStudent; // That every student should pay

    private  float totalMoneyEarned;
    private  float totalMoneySpent;

    public School(float totalFeesForStudent,float salariesTotal){
        TotalFeesForStudent=totalFeesForStudent;
        totalMoneyEarned=totalFeesForStudent;
        totalMoneySpent=salariesTotal;
    }





     void updateTotalMoneyEarned(Connection connection,float moneyEarned){
        String query="UPDATE school SET totalMoneyEarned=totalMoneyEarned+?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,moneyEarned);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public  void updateTotalMoneySpent(Connection connection,float moneySpent){
        String query="UPDATE teachers SET totalMoneySpent=totalMoneySpent+?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,moneySpent);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    void addStudent(Connection connection, Scanner scanner, float totalFeesForStudent){
        System.out.print("the name of the student: ");
        String name=scanner.next();

        String query="INSERT INTO students(name,feesPaid) VALUES(?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            preparedStatement.setString(1,name);
            preparedStatement.setFloat(2,totalFeesForStudent);

            preparedStatement.executeUpdate();
            System.out.println("student \""+name+"\" added successfully ! \n");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    void deleteStudent(Connection connection,int id){
        String query="DELETE FROM students WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    void viewStudent(Connection connection,int id){
        String query="SELECT * FROM students WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println("id : "+resultSet.getInt("id"));
                System.out.println("name : "+resultSet.getString("name"));
                System.out.println("grade : "+resultSet.getFloat("grade"));
                System.out.println("fees paid : "+resultSet.getFloat("feesPaid"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------
    void addTeacher(Connection connection,Scanner scanner){
        String query="INSERT INTO teachers(name,salary) VALUES(?,?)";
        System.out.print("Enter the name of the new teacher: ");
        String name= scanner.next();
        Float salary=scanner.nextFloat();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setFloat(2,salary);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    void deleteTeacher(Connection connection,int id){
        String query="DELETE FROM teachers WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    void viewTeacher(Connection connection,int id){
        String query="SELECT * FROM teachers WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
