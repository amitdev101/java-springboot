package dsa;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class StreamExamples {

    static class Employee {
        private String name;
        private double salary;

        public Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        // Method to compute salary band
        public String getSalaryBand() {
            if (salary < 50000) return "LOW";
            else if (salary < 100000) return "MEDIUM";
            else return "HIGH";
        }
    }


    public static void main(String[] args) {
        int[] numArr = {4, 3, 1, -3, 7};
        int sumAll;
        sumAll = stream(numArr).filter(x -> (x > 0)).reduce(0, (sum, cnum) -> sum + cnum);
        System.out.println(sumAll);
        List<Integer> numList = Arrays.asList(1, 2, 5, 4, -9, 0);
        sumAll = numList.stream().map(num -> (int) num).filter(num -> (num > 0))
                .reduce(0, (previousSum, currNum) -> previousSum + currNum);
        System.out.println(sumAll);
        List<String> fruitList = Arrays.asList("apple", "banana", "apple");
        Map<String, Integer> fruitMap = fruitList.stream()
                .collect(Collectors.toMap(fruit -> fruit,
                        fruit -> 1,
                        (existing, newValue) -> existing + newValue
                ));
        System.out.println(fruitMap);
        System.out.println(fruitMap.getClass().getName());

        // Create employee stream
        List<Employee> employeeList = Arrays.asList(
                new Employee("Alice", 52000),
                new Employee("Bob", 61000),
                new Employee("Diana", 47000),
                new Employee("Eve", 102000),
                new Employee("Charlie", 53000),
                new Employee("Frank", 95000)
        );

        // get the average salary in the band
        Map<String, Double> averageSalaryByBand = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getSalaryBand,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        // Print the result
        averageSalaryByBand.forEach((band, avgSalary) ->
                System.out.println("Band: " + band + ", Average Salary: " + avgSalary));

        // Challenge 2 now (unique salaries per band)
        System.out.println("Challenge 2 now (unique salaries per band)");
        Map<String, Set<Employee>> uniqueSalaryByBand = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getSalaryBand, Collectors.toSet()));
        // here we could have collect the specific value (salary) instead of the object (employee) [Collectors.toSet()]
        uniqueSalaryByBand.forEach((band, uniqueSalarySet) ->
                System.out.println("Band: " + band + ", Unique Salary in band = " + uniqueSalarySet.stream().map(e -> e.getSalary()).collect(Collectors.toList())));

        // getting salary directly without employee object
        System.out.println("Challenge 2(B) now (unique salaries per band) but the salary directly in set");
        Map<String, Set<Double>> uniqueSalaryInSet = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getSalaryBand,
                        Collectors.mapping(Employee::getSalary, Collectors.toSet())
                ));
        System.out.println(uniqueSalaryInSet);

        System.out.println("now sort the salaries within each band (Challenge 3)");
        Map<String, List<Double>> sortedSalaryInBand = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getSalaryBand,
                        Collectors.collectingAndThen(Collectors.mapping(Employee::getSalary, Collectors.toList()),
                                list -> {
                                    list.sort((s1, s2) -> {
                                        if (s1 < s2) {
                                            return -1;
                                        } else if (s1 == s2) {
                                            return 0;
                                        } else {
                                            return 1;
                                        }
                                    });
                                    return list;
                                }
                        )));
        System.out.println(sortedSalaryInBand);

        // top paid employee
        Map<String, Optional<Employee>> maxSalaryPerBand = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getSalaryBand,
                                                Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));





    }
}
