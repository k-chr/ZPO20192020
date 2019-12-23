package Kamil215691.ZPO.LAB8.Zad2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void reflectEquals() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        employee1.setAge(33); employee1.setLastName("Smith"); employee1.setName("John"); employee1.setManager(true); employee1.setDateOfEmployment(LocalDate.of(2004, 1,23));  employee1.setPhoneNumber(515910107);
        employee2.setAge(33); employee2.setLastName("Smith"); employee2.setName("John"); employee2.setManager(true); employee2.setDateOfEmployment(LocalDate.of(2004, 1,23));  employee2.setPhoneNumber(515910107);
        assertTrue(employee1.reflectEquals(employee2) && employee2.equals(employee1));
    }
    @Test
    void reflectNotEquals() {
        Employee employee3 = new Employee();
        employee3.setAge(34); employee3.setLastName("Smith"); employee3.setName("John"); employee3.setManager(true); employee3.setDateOfEmployment(LocalDate.of(2004, 1,23));  employee3.setPhoneNumber(515910107);
        Employee employee4 = new Employee();
        employee4.setAge(34); employee4.setLastName("Johnson"); employee4.setName("Jonathan"); employee4.setManager(false); employee4.setDateOfEmployment(LocalDate.of(2014, 11,3)); employee4.setPhoneNumber(645310107);
        assertFalse(employee3.reflectEquals(employee4) && employee4.equals(employee3));
    }
}