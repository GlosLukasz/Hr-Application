package Testowanko.springboot.service;

import Testowanko.springboot.model.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    void saveDepartment(Department employee);

    Department getDepartmentById(int id);

    void deleteDepartmentById(int id);

    Page<Department> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
