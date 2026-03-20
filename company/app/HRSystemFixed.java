package company.app;

import company.core.EmployeeFixed;

public class HRSystemFixed {
    public static void main(String[] args) {
        EmployeeFixed emp = new EmployeeFixed("Иван", 30, 80000, "secret");

        // Используем геттеры вместо прямого доступа
        System.out.println(emp.getName());              // Вместо emp.name
        System.out.println(emp.getAge());               // Вместо emp.age
        System.out.println(emp.getSalary());            // Вместо emp.salary
        // System.out.println(emp.password);            // НЕ работает (private)

        System.out.println(emp.getRole());              // Работает (public)
        emp.promote(5000);                              // Работает (теперь public)
        emp.printSummary();                             // Работает (теперь public)

        // Аутентификация через публичный метод
        System.out.println("Аутентификация с 'secret': " + emp.authenticate("secret"));
        System.out.println("Аутентификация с 'wrong': " + emp.authenticate("wrong"));
    }
}