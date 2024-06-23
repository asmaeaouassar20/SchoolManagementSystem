package school.management.system;

public class ExceptionGrade extends Exception{
    //This exception is thrown when the entered grade is invalid (ie grade<0 or grade>20)
    public ExceptionGrade(float grade){
        super("\""+grade+"\" invalid grade! ");
    }
}
