package Testowanko.springboot.service;


import Testowanko.springboot.model.Calendar;
import Testowanko.springboot.model.CalendarRepository;
import Testowanko.springboot.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarServiceml implements CalendarService{

	private final CalendarRepository calendarRepository;

	CalendarServiceml(final CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}

	@Override
	public List<Calendar> getAllCalendars() {
		return calendarRepository.findAll();
	}

	@Override
	public void saveCalendar(Calendar calendar) {
		this.calendarRepository.save(calendar);
	}

	@Override
	public Calendar getCalendarById(int id) {
		Optional<Calendar> optional = calendarRepository.findById(id);
		Calendar calendar = null;
		if (optional.isPresent()) {
			calendar = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return calendar;
	}

	@Override
	public void deleteCalendarById(int id) {
		this.calendarRepository.deleteById(id);
	}

	@Override
	public Page<Calendar> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.calendarRepository.findAll(pageable);
	}
}
