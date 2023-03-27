package Testowanko.springboot.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    Optional<Department> findById(Integer id);

    List<Department> findAll();

    Page<Department> findAll(Pageable pageable);

    Department save(Department entity);

    void deleteById(Integer id);
}

