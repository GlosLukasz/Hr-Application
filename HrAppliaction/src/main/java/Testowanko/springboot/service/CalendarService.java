package Testowanko.springboot.service;

import Testowanko.springboot.model.Calendar;
import Testowanko.springboot.model.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CalendarService {
    List<Calendar> getAllCalendars();

    void saveCalendar(Calendar calendar);

    Calendar getCalendarById(int id);

    void deleteCalendarById(int id);

    Page<Calendar> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
