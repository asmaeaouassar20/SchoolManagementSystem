package school.management.system;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Before adding any student in the database, totalFeesForStudent specified by the school must be paid

public class Main {
    public static void main(String[] args) {

        // information to connect to database
        // **********************************************************
        String url="jdbc:mysql://localhost:3306/school";
        String user="root";
        String password="";
        Connection connection=null;
        // **********************************************************


        // Connect to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException | SQLException c){ // "multi-catch" introduced in Java 7, allows to catch multiple exceptions in one catch block
            c.printStackTrace();
        }
        System.out.println("database connection successful ! \n\n");

        Scanner scanner=new Scanner(System.in);
        System.out.print("the total fees each student should pay is : ");
        float totalFeesForStudent=scanner.nextFloat();



        // fill the database with information from files
        // pick up the files
        File fileOfTeachers=new File("D://javaPoject//schoolManagementSystem//src/teachers.txt");
        File fileOfStudents=new File("D://javaPoject//schoolManagementSystem//src/students.txt");

        // To read from files
       try{
           FileReader fileReader1=new FileReader(fileOfTeachers);
           BufferedReader bufferedReader1=new BufferedReader(fileReader1);

           FileReader fileReader2=new FileReader(fileOfStudents);
           BufferedReader bufferedReader2=new BufferedReader(fileReader2);



           // populate the database from the files
           String line=bufferedReader1.readLine();
           while(line!=null){
               String[] words=line.split(" ");

               float salary=0;
               StringBuilder stringBuilder=new StringBuilder();

               for(String w:words){
                    if(isFloat(w)) salary=Float.parseFloat(w);
                    else{
                        stringBuilder.append(w);
                        stringBuilder.append(" ");
                    }
               }
               String name=stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);

               String query="INSERT INTO teachers(name,salary) VALUES(?,?)";
               PreparedStatement preparedStatement=connection.prepareStatement(query);

               preparedStatement.setString(1,name);
               preparedStatement.setFloat(2,salary);

               preparedStatement.executeUpdate();
               line=bufferedReader1.readLine();
           }
           System.out.println("the 'teachers' table is filled with success ! \n\n");

           line=bufferedReader2.readLine();
           while(line!=null){
                String query="INSERT INTO students(name,feesPaid) VALUES(?,?)";

                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,line);
                preparedStatement.setFloat(2,totalFeesForStudent);

                preparedStatement.executeUpdate();
           }
           System.out.println("the 'students' table is filled with success ! \n\n");

       } catch (IOException | SQLException e){
           e.printStackTrace();
       }


       while(true){
           menu();
           int choice= scanner.nextInt();
           switch (choice){
               case 1 : {
                   System.out.println("1 : add a student");
                   System.out.println("2 : set a student");
                   int choice2=scanner.nextInt();
                   switch (choice2){
                       case 1 : addStudent(connection,scanner,totalFeesForStudent);
                                break;
                       case 2: {
                           System.out.print("Enter the id of the student you want to modify: ");
                           int id=scanner.nextInt();
                           String query="SELECT id FROM students WHERE id=?";
                         try{
                             PreparedStatement preparedStatement=connection.prepareStatement(query);

                             preparedStatement.setInt(1,id);

                             ResultSet resultSet=preparedStatement.executeQuery();
                             if(resultSet.next()){
                                 System.out.println("1: modify name");
                                 System.out.println("2: modify the grade");

                                 System.out.print("Enter your choice : ");
                                 int choice3=scanner.nextInt();
                                 switch (choice3){
                                     case 1:{
                                         modifyName(connection,scanner,id);
                                         break;
                                     }
                                     case 2 :{

                                         break;
                                     }
                                 }

                             }else{
                                 System.out.println("id="+id+" not found");
                             }
                         }catch (SQLException e){
                             e.printStackTrace();
                         }


                           break;
                       }
                   }

                   break;
               }
               case 2 : {

                   break;
               }
               case 3 :{
                   return;
               }
           }
       }


    }


    /**
     * verify if the word can be parsed to float
     * @param word this is a word from the file, it can be a string or a float
     * @return if the word is a salary(float) so it returns true
     */
    static boolean isFloat(String word){
        try{
            Float.parseFloat(word);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    static void menu(){
        System.out.println("|1| : teachers");
        System.out.println("|2| : students");
        System.out.println("|3| : exit");
    }



    static void addStudent(Connection connection,Scanner scanner,float totalFeesForStudent){
        System.out.print("the name of the student: ");
        String name=scanner.next();

        String query="INSERT INTO students(name,feesPaid) VALUES(?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);

            preparedStatement.setString(1,name);
            preparedStatement.setFloat(2,totalFeesForStudent);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    static void modifyName(Connection connection,Scanner scanner,int id){
        System.out.print("Enter the new name : ");
        String name=scanner.next();
        String query="UPDATE students SET name=? WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    static void modifyGrade(Connection connection,Scanner scanner) throws ExceptionGrade {
        System.out.print("Enter the new grade : ");
        float grade= scanner.nextFloat();
        if(grade<0 || grade>20) throw new ExceptionGrade(grade);
        else{
            String query="UPDATE students SET grade=? WHERE id=?";

        }
    }

}
