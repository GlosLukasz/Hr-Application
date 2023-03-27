package Testowanko.springboot.service;

import Testowanko.springboot.model.Profession;
import Testowanko.springboot.model.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionServiceml implements ProfessionService{

	@Autowired
	private ProfessionRepository professionRepository;

	@Override
	public List<Profession> getAllProfessions() {
		return professionRepository.findAll();
	}

	@Override
	public void saveProfession(Profession profession) {
		this.professionRepository.save(profession);
	}

	@Override
	public Profession getProfessionById(int id) {
		Optional<Profession> optional = professionRepository.findById(id);
		Profession profession = null;
		if (optional.isPresent()) {
			profession = optional.get();
		} else {
			throw new RuntimeException(" profession not found for id :: " + id);
		}
		return profession;
	}

	@Override
	public void deleteProfessionById(int id) {
		this.professionRepository.deleteById(id);
	}

	@Override
	public Page<Profession> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.professionRepository.findAll(pageable);
	}
}
