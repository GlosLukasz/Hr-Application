package Testowanko.springboot.repository;

import Testowanko.springboot.model.Calendar;
import Testowanko.springboot.model.CalendarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlCalendarRepository extends CalendarRepository, JpaRepository<Calendar, Integer> {
}
