package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        List<String> employeesFirstName = employeeService.readAll().stream()
                .map(employee -> employee.getFirstName())
                .collect(Collectors.toList());
        return employeesFirstName;
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        List<String> countriesName = getAllCountries().stream()
                .map(country -> country.getCountryName())
                .collect(Collectors.toList());
        return countriesName;
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        List<String> managerFirstName = departmentService.readAll().stream()
                .map(department -> department.getManager().getFirstName())
                .collect(Collectors.toList());
        return managerFirstName;
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        List<Department> managerFirstNameIsSteven = departmentService.readAll().stream()
                .filter(department -> department.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
        return managerFirstNameIsSteven;
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        List<Department> postalCode = departmentService.readAll().stream()
                .filter(department -> department.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());
        return postalCode;
    }

    //Display the region of the IT department
   public static Region getRegionOfITDepartment() throws Exception {
       Optional<Region> it = departmentService.readAll().stream()
               .filter(department -> department.getDepartmentName().equals("IT"))
               .map(department -> department.getLocation().getCountry().getRegion())
               .findAny();
       return it.get();
}

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
      List<Department> europe = departmentService.readAll().stream()
              .filter(department -> department.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
              .collect(Collectors.toList());
        return europe;
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        boolean higherThan1000 = employeeService.readAll().stream()
                .allMatch(employee -> employee.getSalary() > 1000);
        return higherThan1000;
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        boolean greaterThan2000 = employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
                .allMatch(employee -> employee.getSalary() > 2000);
        return greaterThan2000;
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        List<Employee> lessSalaryThan5000 = employeeService.readAll().stream()
                .filter(employee -> employee.getSalary() < 5000)
                .collect(Collectors.toList());
        return lessSalaryThan5000;
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        List<Employee> salaryBetween = employeeService.readAll().stream()
                .filter(employee -> employee.getSalary() > 6000 && employee.getSalary() < 7000)
                .collect(Collectors.toList());
        return salaryBetween;
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        Long salary = employeeService.readAll().stream()
                .filter(employee -> employee.getFirstName().equals("Douglas") && employee.getLastName().equals("Grant"))
                .map(employee -> employee.getSalary())
                .findAny().get();
        return salary;
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {

        Optional<Long> maxSalary = employeeService.readAll().stream()
                .map(emp -> emp.getSalary())
                .reduce(Long::max);
        return maxSalary.get();

        /*

        return employeeService.readAll().stream()
                .max(Comparator.comparing(Employee::getSalary))
                .get().getSalary();

         */
        /*

        Optional<Long> maxSalary = employeeService.readAll().stream()
                .map(emp -> emp.getSalary())
                .collect(Collectors.reducing(Long::max));
        return maxSalary.get();

         */
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        List<Employee> maxSalaryEmp = employeeService.readAll().stream()
                .sorted(comparing(Employee::getSalary).reversed())
                .limit(1)
                .collect(Collectors.toList());
        return maxSalaryEmp;
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        /*
        Optional<Job> maxSalEmpJob = employeeService.readAll().stream()
                .map(Employee::getJob)
                .max(comparing(Job::getMaxSalary));
        return maxSalEmpJob.get();
         */
        return getMaxSalaryEmployee().get(0).getJob();
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
/*
        return getAllEmployees().stream()
                .filter(emp -> emp.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .max(Comparator.comparing(Employee::getSalary)).get().getSalary();
 */

        return getAllEmployees().stream()
                .filter(emp-> emp.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .map(Employee::getSalary).reduce(Long::max).get();
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary()  {

        Optional<Long> secondMaxSalary = employeeService.readAll().stream()
                .map(Employee::getSalary)
                .distinct()
                .skip(1)
                .reduce(Long::max);
        return secondMaxSalary.get();

        /*
        return getAllEmployees().stream()
                .filter(emp -> emp.getSalary().compareTo(getMaxSalary()) < 0)
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst().get().getSalary();

         */
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {

        return getAllEmployees().stream()
                .filter(emp -> emp.getSalary().equals(getSecondMaxSalary()))
                .collect(Collectors.toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        Optional<Long> minSalary = employeeService.readAll().stream()
                .map(emp -> emp.getSalary())
                .reduce(Long::min);
        return minSalary.get();
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        List<Employee> minSalaryEmp = employeeService.readAll().stream()
                .sorted(comparing(Employee::getSalary))
                .limit(1)
                .collect(Collectors.toList());
        return minSalaryEmp;
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() {
        Optional<Long> secondMinSalary = employeeService.readAll().stream()
                .map(emp -> emp.getSalary())
                .sorted()
                .distinct()
                .skip(1)
                .reduce(Long::min);
        return secondMinSalary.get();
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {

        return getAllEmployees().stream()
                .filter(emp -> emp.getSalary().equals(getSecondMinSalary()))
                .collect(Collectors.toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        Double averageSalary = employeeService.readAll().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        return averageSalary;
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {

        return employeeService.readAll().stream()
                .filter(emp -> emp.getSalary() > getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return employeeService.readAll().stream()
                .filter(emp -> emp.getSalary() < getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return getAllEmployees().stream()
                .collect(Collectors.groupingBy(p -> p.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        // return (long) getAllDepartments().size();
        return departmentService.readAll().stream().count();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {

        Employee employee = employeeService.readAll().stream()
                .filter(emp -> emp.getFirstName().equals("Alyssa") && emp.getManager().getFirstName().equals("Eleni") && emp.getDepartment().getDepartmentName().equals("Sales"))
                .findFirst().get();
        return employee;
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        List<JobHistory> jobHistories = jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
        return jobHistories;
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        List<JobHistory> jobHistoriesDescending = jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
        return jobHistoriesDescending;
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
       return getAllJobHistories().stream()
                .filter(p -> p.getStartDate().isAfter(LocalDate.of(2005,01,01)))
                .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        return getAllJobHistories().stream()
                .filter(p -> p.getEndDate().isEqual(LocalDate.of(2007,12,31)) && p.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
       return getAllJobHistories().stream()
                .filter(p -> p.getStartDate().isEqual(LocalDate.of(2007, 01, 01)))
                .filter(p -> p.getEndDate().isEqual(LocalDate.of(2007, 12, 31)))
                .filter(p -> p.getDepartment().getDepartmentName().equals("Shipping"))
                .findFirst().get().getEmployee();
       /*
       return getAllJobHistories().stream()
                .filter(p -> p.getStartDate().isEqual(LocalDate.of(2007, 01, 01)))
                .filter(p -> p.getEndDate().isEqual(LocalDate.of(2007, 12, 31)))
                .filter(p -> p.getDepartment().getDepartmentName().equals("Shipping"))
                .map(JobHistory::getEmployee)
                .findFirst().get();
        */
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return getAllEmployees().stream()
                .filter(emp -> emp.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return getAllEmployees().stream()
                .filter(emp -> emp.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        return getAllEmployees().stream()
                .filter(emp -> emp.getJob().getJobTitle().equals("Programmer"))
                .filter(emp -> emp.getDepartment().getDepartmentName().equals("IT"))
                .count();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return getAllEmployees().stream()
                .filter(emp -> emp.getDepartment().getId().equals(50L) || emp.getDepartment().getId().equals(80L) || emp.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return getAllEmployees().stream()
                .map(p -> p.getFirstName().substring(0,1) + p.getLastName().substring(0,1))
                .collect(Collectors.toList());
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return getAllEmployees().stream()
                .map(emp -> emp.getFirstName() + " " + emp.getLastName())
                .collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() {
        return getAllEmployeesFullNames().stream()
                .max(Comparator.comparing(String::length))
                .get().length();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        return getAllEmployees().stream()
                .filter(emp -> emp.getFirstName().length() + emp.getLastName().length() + 1 == getLongestNameLength())
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(p -> p.getDepartment().getId().equals(60L) || p.getDepartment().getId().equals(90L) || p.getDepartment().getId().equals(100L)
                        || p.getDepartment().getId().equals(100L)|| p.getDepartment().getId().equals(130L))
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(emp -> !getAllEmployeesDepartmentIdIs90or60or100or120or130().contains(emp))
                .collect(Collectors.toList());
    }

}
