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

    public School(Connection connection,float totalFeesForStudent,float salariesTotal){
        TotalFeesForStudent=totalFeesForStudent;
        totalMoneyEarned=totalFeesForStudent;
        totalMoneySpent=salariesTotal;
        String query="INSERT INTO school(TotalFeesForStudent,totalMoneyEarned,totalMoneySpent) VALUE(?,?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,totalFeesForStudent);
            preparedStatement.setFloat(2,totalFeesForStudent);
            preparedStatement.setFloat(3,salariesTotal);
            preparedStatement.executeUpdate();
            System.out.println("create a school !\n\n ");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }





     void updateTotalMoneyEarned(Connection connection,float moneyEarned){
        String query="UPDATE school SET totalMoneyEarned=totalMoneyEarned+?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,moneyEarned);
            preparedStatement.executeUpdate();

            String q="SELECT totalMoneyEarned from school";
            PreparedStatement ps=connection.prepareStatement(q);
            ResultSet rs=ps.executeQuery();
            rs.next();
            Float totalMoneyEarned=rs.getFloat("totalMoneyEarned");
            System.out.println("__ Actual total money earned : "+totalMoneyEarned);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public  void updateTotalMoneySpent(Connection connection,float moneySpent){
        String query="UPDATE school SET totalMoneySpent=totalMoneySpent+?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,moneySpent);
            preparedStatement.executeUpdate();
             String q="SELECT totalMoneySpent from school";
             PreparedStatement ps=connection.prepareStatement(q);
             ResultSet rs=ps.executeQuery();
            float totalMoneySpent=0;
             if(rs.next()){
                 totalMoneySpent=rs.getFloat("totalMoneySpent");
             }
            System.out.println("__ Actual total money spent : "+totalMoneySpent);
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
        System.out.print("Enter the salary of this new teacher: ");
        float salary=scanner.nextFloat();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setFloat(2,salary);
            preparedStatement.executeUpdate();
            System.out.println("the new teacher "+name+" is added successfully!");
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
            System.out.println("teacher deleted successfully ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    void viewTeacher(Connection connection,int id){
        String query="SELECT * FROM teachers WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                System.out.println("id : "+rs.getInt("id"));
                System.out.println("name : "+rs.getString("name"));
                System.out.println("salary : "+rs.getFloat("salary"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
