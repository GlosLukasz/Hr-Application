package Testowanko.springboot.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int departmentId;

	@NotEmpty(message = "Niepoprawna nazwa działu")
	private String departmentName;

	@NotEmpty(message = "Niepoprawna nazwa adresu")
	private String departmentAdress;

	@OneToMany(mappedBy = "department",cascade = CascadeType.REMOVE)
	private List<Profession> profession;

	public Department() {
	}

	public int getIdDepartment() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(final String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentAdress() {
		return departmentAdress;
	}

	public void setDepartmentAdress(final String departmentAdress) {
		this.departmentAdress = departmentAdress;
	}

	public List<Profession> getProfession() {
		return profession;
	}

	public void setProfession(final List<Profession> profession) {
		this.profession = profession;
	}
}

