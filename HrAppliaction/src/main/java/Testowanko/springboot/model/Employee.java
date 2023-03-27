package Testowanko.springboot.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@NotEmpty(message = "Niepoprawne imie")
	private String firstName;

	@NotEmpty(message = "Niepoprawne nazwisko")
	private String lastName;

	@NotEmpty(message = "Niepoprawny email")
	private String email;

	@NotNull(message = "Niepoprawny numer telefonu")
	@Range(min = 100000000,max = 999999999, message = "Niepoprawny numertelefonu ")
	private Long numberPhone;

	@NotNull(message = "Wybierz stanowisko")
	@ManyToOne()
	@JoinColumn(name = "professionId")
	private Profession profession;

	@OneToMany(mappedBy = "employee",cascade = CascadeType.REMOVE)
	private List<Calendar> calendars;

	public Employee() {
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public Long getNumberPhone() {
		return numberPhone;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(final Profession profession) {
		this.profession = profession;
	}

}

