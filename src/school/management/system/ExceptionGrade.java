package school.management.system;

public class ExceptionGrade extends Exception{
    public ExceptionGrade(float grade){
        super("\""+grade+"\" invalid grade! ");
    }
}
