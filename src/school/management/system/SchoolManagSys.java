package school.management.system;

import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class SchoolManagSys {
    // information to connect to database
    // **********************************************************
    String url="jdbc:mysql://localhost:3306/schoolmanagsystem";
    String user="root";
    String password="";
    Connection connection=null;
    // **********************************************************
    float totalFeesForStudent; // Before adding any student in the database, totalFeesForStudent specified by the school must be paid
    float salariesTotal;

    public SchoolManagSys(){

        // Connect to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("database connection successful ! \n\n");
        }catch(ClassNotFoundException | SQLException c){ // "multi-catch" introduced in Java 7, allows to catch multiple exceptions in one catch block
            c.printStackTrace();
            System.out.println("failed to connect to database ! \n\n");
        }



        //-------------------------------------------------------------------------------------
        // fill the database with information from files
        // pick up the files
        File fileOfTeachers=new File("D://javaPoject//schoolManagementSystem//src/teachers.txt");
        File fileOfStudents=new File("D://javaPoject//schoolManagementSystem//src/students.txt");

        Scanner scanner=new Scanner(System.in);
        totalFeesForStudent=0; // Before adding any student in the database, totalFeesForStudent specified by the school must be paid
        salariesTotal=0;
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
                    if(isFloat(w)) {
                        salary=Float.parseFloat(w);
                        salariesTotal+=salary;
                    }
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


            System.out.print("the total fees each student should pay is : ");
            totalFeesForStudent=scanner.nextFloat();
            line=bufferedReader2.readLine();
            while(line!=null){
                String query="INSERT INTO students(name,feesPaid) VALUES(?,?)";

                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,line);
               // preparedStatement.setFloat(2,0);
                preparedStatement.setFloat(2,totalFeesForStudent);

                preparedStatement.executeUpdate();
                line =bufferedReader2.readLine();
            }
            System.out.println("the 'students' table is filled with success ! \n\n");

        } catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }




     public  void createSchool() {
        School school=new School(connection,totalFeesForStudent,salariesTotal);
        Scanner scanner=new Scanner(System.in);

        while(true){
            menu();
            System.out.print("Enter your choice: ");
            int choice= scanner.nextInt();
            switch (choice){
                case 1 : {
                    System.out.println("1 : add a student");
                    System.out.println("2 : set a student");
                    System.out.println("3 : delete a student");
                    System.out.println("4 : view student");
                    System.out.println("5 : view all students");
                    System.out.print("enter your choice: ");
                    int choice2=scanner.nextInt();
                    scanner.nextLine(); //consume the new line
                    if(choice2==1){
                        school.addStudent(connection, scanner, totalFeesForStudent);
                    }else if(choice2==5){
                        school.viewAllStudents(connection);
                    }
                    else{
                        System.out.print("enter id of the student: ");
                        int id=scanner.nextInt();
                        scanner.nextLine(); //consume the new line
                        try {
                            String query = "SELECT name FROM students WHERE id=?";

                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, id);
                            ResultSet resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {
                                String name=resultSet.getString("name");
                                switch (choice2) {
                                    case 1:
                                        //school.addStudent(connection, scanner, totalFeesForStudent);
                                        break;
                                    case 2: {
                                        System.out.println("student selected is <<" + name + ">>");
                                        System.out.println("1: modify name");
                                        System.out.println("2: modify the grade");
                                        System.out.println("3: pay fees");

                                        System.out.print("Enter your choice : ");
                                        int choice3 = scanner.nextInt();
                                        switch (choice3) {
                                            case 1: {
                                                modifyNameStudent(connection, scanner, id);
                                                break;
                                            }
                                            case 2: {
                                                try {
                                                    modifyGrade(connection, scanner, id, name);
                                                } catch (ExceptionGrade e) {
                                                    System.out.println("entered grade not valid ! ");
                                                    e.printStackTrace();
                                                }
                                                break;
                                            }
                                            case 3: {
                                                System.out.print("fees paid: ");
                                                float feesPaid = scanner.nextFloat();
                                                payFees(school, connection, id, feesPaid);
                                                break;
                                            }
                                            default:
                                                System.out.println("invalid choice  ! ");
                                                break;
                                        }
                                        break;
                                    }
                                    case 3: {
                                        school.deleteStudent(connection,scanner, id,name);
                                        break;
                                    }
                                    case 4: {
                                        school.viewStudent(connection, id);
                                        break;
                                    }
                                    default: {
                                        System.out.println("invalid choice ! ");
                                        break;
                                    }
                                }  }else {
                                System.out.println("id " + id + " not found !");
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 2 : {
                    System.out.println("1 : add a teacher");
                    System.out.println("2 : set a teacher");
                    System.out.println("3 : delete a teacher");
                    System.out.println("4 : view teacher");
                    System.out.println("5 : view all students");
                    System.out.print("Enter your choice: ");
                    int choice2=scanner.nextInt();
                    scanner.nextLine();//consume line
                  if(choice2==1){
                      school.addTeacher(connection,scanner);
                  }else if(choice2==5){
                      school.viewAllTeachers();
                  }
                  else{
                      System.out.print("enter the id of the teacher : ");
                      int id=scanner.nextInt();
                      scanner.nextLine();
                      String query="SELECT name FROM teachers WHERE id=?";
                      try{
                          PreparedStatement ps=connection.prepareStatement(query);
                          ps.setInt(1,id);
                          ResultSet rs=ps.executeQuery();
                          if(rs.next()){
                              String nameTeacher=rs.getString("name");
                              switch (choice2){
                                  case 1 :{
                                      //school.addTeacher(connection,scanner);
                                      break;
                                  }
                                  case 2 :{
                                      try{
                                          System.out.println("you want to set the teachers : <<"+nameTeacher+">>");

                                          System.out.println("1: modify name");
                                          System.out.println("2: modify salary");

                                          System.out.print("Enter your choice : ");
                                          int choice3=scanner.nextInt();
                                          switch (choice3){
                                              case 1:{
                                                  modifyNameTeacher(connection,scanner,id,nameTeacher);
                                                  break;
                                              }
                                              case 2 :{
                                                  System.out.println("1 : add to salary");
                                                  System.out.println("2 : substruct from a salary");
                                                  System.out.print("Enter your choice : ");
                                                  int choice4=scanner.nextInt();
                                                  switch (choice4){
                                                      case 1 :{
                                                          System.out.print("amount to add to salary : ");
                                                          float  salaryToAdd=scanner.nextFloat();
                                                          addTosalary(school,connection,salaryToAdd,id);
                                                          break;
                                                      }
                                                      case 2 :{
                                                          System.out.print("amount to substruct from salary : ");
                                                          float  salaryToSub=scanner.nextFloat();
                                                          if(salaryToSub<0) throw new ExceptionSalary(salaryToSub);
                                                          subFromsalary(school,connection,salaryToSub,id);
                                                          break;
                                                      }
                                                      default:
                                                          System.out.println("invalid choice ! ");
                                                          break;
                                                  }
                                                  break;
                                              }
                                              default:
                                                  System.out.println("invalid choice ! ");
                                                  break;

                                          }
                                      }catch (ExceptionSalary e){
                                          e.printStackTrace();
                                      }
                                      break;
                                  }
                                  case 3 :{
                                      school.deleteTeacher(connection,scanner,id);
                                      break;
                                  }
                                  case 4 :{
                                      school.viewTeacher(connection,id);
                                      break;
                                  }
                                  default:
                                      System.out.println("invalid choice !");
                                      break;
                              }}
                          else{
                              System.out.println("oops! id "+id+" not found !");
                          }
                      }catch (SQLException e){
                          e.printStackTrace();
                      }
                  }

                    break;
                }
                case 3 :{
                    System.out.println("\nthank you for using School Management System");
                    return;
                } default:
                    System.out.println("invalid choice !");
            }
        }

     }


    public static void main(String[] args) {
        SchoolManagSys schoolManagementSystem=new SchoolManagSys();
        schoolManagementSystem.createSchool();

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
        System.out.println("|1| : students");
        System.out.println("|2| : teachers");
        System.out.println("|3| : exit");
    }




    static void modifyNameStudent(Connection connection,Scanner scanner,int id){
        System.out.print("Enter the new name : ");
        String name=scanner.next();
        String query="UPDATE students SET name=? WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            System.out.println("the name is changed successfully ! ");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    static void modifyGrade(Connection connection,Scanner scanner,int id,String student) throws ExceptionGrade {
        System.out.print("Enter the new grade : ");
        float grade= scanner.nextFloat();
        if(grade<0 || grade>20) throw new ExceptionGrade(grade);
        else{
            String query="UPDATE students SET grade=? WHERE id=?";
            try{
                String r="SELECT grade FROM students WHERE id=?";
                PreparedStatement pr=connection.prepareStatement(r);
                pr.setInt(1,id);
                ResultSet rs=pr.executeQuery();
                rs.next();
                float oldGrade=rs.getFloat("grade");
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setFloat(1,grade);
                preparedStatement.setInt(2,id);
                preparedStatement.executeUpdate();
                System.out.println("the grade of \""+student+"\" is updated from "+oldGrade+" to "+grade);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    static void payFees(School school,Connection connection,int id,float feesP){
        String query="UPDATE students SET feesPaid=feesPaid+? WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,feesP);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            school.updateTotalMoneyEarned(connection,feesP);
            System.out.println("fees paid successfully !");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    static void modifyNameTeacher(Connection connection,Scanner scanner,int id,String oldName){
            System.out.print("Enter the new name : ");
            String name=scanner.next();
            String query="UPDATE teachers SET name=? WHERE id=?";
            try{
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,name);
                preparedStatement.setInt(2,id);
                preparedStatement.executeUpdate();
                System.out.println("name changed successfully from "+oldName+" to "+name);
            }catch(SQLException e){
                e.printStackTrace();
            }
    }
    static void addTosalary(School school,Connection connection,float salaryToAdd,int id){
        String query="UPDATE teachers SET salary=salary+? WHERE id=?";
        String q="SELECT salary FROM teachers WHERE id=?";
        try{
            PreparedStatement ps=connection.prepareStatement(q);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            rs.next();
            float oldSalary=rs.getFloat("salary");
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setFloat(1,salaryToAdd);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            System.out.println("old salary : "+oldSalary);
            System.out.println("salary changed from "+oldSalary+" to "+(oldSalary+salaryToAdd));
        }catch(SQLException e){
            e.printStackTrace();
        }
        school.updateTotalMoneySpent(connection,salaryToAdd);
    }
    static void subFromsalary(School school,Connection connection,float salaryToSub,int id){
        String query1="SELECT salary FROM teachers WHERE id=?";
        try{
            PreparedStatement preparedStatement1=connection.prepareStatement(query1);
            preparedStatement1.setInt(1,id);
            ResultSet resultSet=preparedStatement1.executeQuery();
            resultSet.next();
            float oldSalary=resultSet.getFloat("salary");
            if(oldSalary>=salaryToSub){
                String query2="UPDATE teachers SET salary=salary-? WHERE id=?";
                PreparedStatement preparedStatement2=connection.prepareStatement(query2);
                preparedStatement2.setFloat(1,salaryToSub);
                preparedStatement2.setInt(2,id);
                preparedStatement2.executeUpdate();
                System.out.println("the salary is changed from "+oldSalary+" to "+(oldSalary-salaryToSub));
            }else{
                System.out.println("oops! negative salary value");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
