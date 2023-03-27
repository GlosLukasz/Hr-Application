package Testowanko.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "calendars")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int calendarId;

    @NotNull(message = "Wybierz datę")
    @Temporal(TemporalType.DATE)
    private Date date;

    private String note;

    @NotNull(message = "Wybierz osobę")
    @ManyToOne()
    @JoinColumn(name = "employeeId")
    private Employee employee;

    public Calendar() {
    }

    public int getCalendarId() {
        return calendarId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
    }
}

