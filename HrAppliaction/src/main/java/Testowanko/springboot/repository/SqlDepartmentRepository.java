package Testowanko.springboot.repository;

import Testowanko.springboot.model.Department;
import Testowanko.springboot.model.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlDepartmentRepository  extends DepartmentRepository, JpaRepository<Department, Integer> {
}
