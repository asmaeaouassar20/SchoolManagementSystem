package school.management.system;

public class Student {
    private int id;
    private String name;
    private float grade;
    private float feesPaid;


    public Student(int id, String name,float grade){
        this.id=id;
        this.name=name;
        this.grade=grade;
        feesPaid=0;  // initially
    }


    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public float getGrade(){
        return grade;
    }
    public float getFeesPaid(){
        return this.feesPaid;
    }


    // Look at the attributes in class Student
    // Which attribute whose value can be changed during studies => grade => let's create setGrade() method

    public void setGrade(float grade){
        this.grade=grade;
    }

    public void payFees(float feesP){
        this.feesPaid+=feesP;
        School.updateTotalMoneyEarned(feesP);
    }

}
