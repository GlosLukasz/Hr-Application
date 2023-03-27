package Testowanko.springboot.repository;

import Testowanko.springboot.model.Employee;
import Testowanko.springboot.model.EmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlEmployeeRepository extends EmployeeRepository, JpaRepository<Employee, Integer> {
}
