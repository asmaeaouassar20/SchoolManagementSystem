package school.management.system;

public class ExceptionSalary extends Exception {
    //This exception is thrown when the entered salary negative
    public ExceptionSalary(float salaryToSub) {
        super("\""+salaryToSub+"\" is a negative value");
    }
}
