package school.management.system;

public class Teacher {
    private int id;
    private String name;
    private float salary;

    public Teacher(int id,String name,float salary){
        this.id=id;
        this.name=name;
        this.salary=salary;
    }


    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public float getSalary(){
        return salary;
    }


    /**
     * update the salary of the teacher
     * @param salary the salary of the teacher
     */
    public void setSalary(float salary){
        this.salary=salary;
    }
}
