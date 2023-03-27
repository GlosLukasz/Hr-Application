package Testowanko.springboot.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(Integer id);

    List<Employee> findAll();

    Page<Employee> findAll(Pageable pageable);

    Employee save(Employee entity);

    void deleteById(Integer id);
}

