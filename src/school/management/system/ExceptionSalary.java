package school.management.system;

public class ExceptionSalary extends Exception {
    public ExceptionSalary(float salaryToSub) {
        super("\""+salaryToSub+"\" is a negative value");
    }
}
