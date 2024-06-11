package school.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class School {
    // The school supposed to provide files of students and teachers,
    // so we don't know in advance the number of students and teachers
    // so we use ArrayList

    private List<Teacher> teachers;
    private List<Student> students;

    static float TotalFeesForStudent;

    private static float totalMoneyEarned;
    private static float totalMoneySpent;

    public School(List<Teacher> teachers,List<Student> students,float totalFeesForStudent,float salariesTotal){
        this.teachers=teachers;
        this.students=students;
        TotalFeesForStudent=totalFeesForStudent;
        totalMoneyEarned=totalFeesForStudent;
        totalMoneySpent=salariesTotal;
    }


    /**
     * add the teacher to ArrayList teachers and to the table teachers of the data base
     * @param teacher the teacher to add
     * @param connection to connect to the database
     */
    public void addTeacher(Teacher teacher, Connection connection){
        this.teachers.add(teacher);
        try{
            String requete="INSERT INTO teachers(id, name, salary) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(requete);

            preparedStatement.setInt(1,teacher.getId());
            preparedStatement.setString(2,teacher.getName());
            preparedStatement.setFloat(3,teacher.getSalary());

            int teacherAdded=preparedStatement.executeUpdate();

            if(teacherAdded>0) System.out.println("teacher added successfully ! ");
            else System.out.println("failed to add the teacher ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * add the student to the ArrayList students and to the table students of the database
     * @param student the student ot add
     * @param connection to connect to the database
     */
    public void addStudent(Student student,Connection connection){
        this.students.add(student);
        try{
            String requete="INSERT INTO students(id,name,grade,feesPaid) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(requete);

            preparedStatement.setInt(1,student.getId());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setFloat(3,student.getGrade());
            preparedStatement.setFloat(4,student.getFeesPaid());

            int studentAdded=preparedStatement.executeUpdate();
            if(studentAdded>0) System.out.println("student added successfully ! ");
            else System.out.println("failed to add the student ! ");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void updateTotalMoneyEarned(float moneyEarned){
        totalMoneyEarned+=moneyEarned;
    }
    public static void updateTotalMoneySpent(float moneySpent){
        totalMoneySpent+=moneySpent;
    }


}
