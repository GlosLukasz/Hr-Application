package Testowanko.springboot.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProfessionRepository {

    Optional<Profession> findById(Integer id);

    List<Profession> findAll();

    Page<Profession> findAll(Pageable pageable);

    //POST
    Profession save(Profession entity);

    //DELETE
    void deleteById(Integer id);

}
