package Testowanko.springboot.repository;

import Testowanko.springboot.model.Profession;
import Testowanko.springboot.model.ProfessionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlProfessionRepository extends ProfessionRepository, JpaRepository<Profession, Integer> {
}
