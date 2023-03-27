package Testowanko.springboot.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository {

    Optional<Calendar> findById(Integer id);

    List<Calendar> findAll();

    Page<Calendar> findAll(Pageable pageable);

    Calendar save(Calendar entity);

    void deleteById(Integer id);
}
