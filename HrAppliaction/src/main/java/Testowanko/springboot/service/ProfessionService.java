package Testowanko.springboot.service;

import Testowanko.springboot.model.Profession;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfessionService {
	List<Profession> getAllProfessions();

	void saveProfession(Profession profession);

	Profession getProfessionById(int id);

	void deleteProfessionById(int id);

	Page<Profession> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
