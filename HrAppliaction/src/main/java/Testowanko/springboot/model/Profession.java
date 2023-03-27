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
@Table(name = "professions")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int professionId;

    @NotEmpty(message = "Wprowadz nazwę stanowiska")
    private String positionName;

    @NotNull(message = "Uzupełnij minimalną stawkę")
    @Range(min = 2000,max = 12_000, message = "Stawka minimalna powinna się zawierać w przedziale od 2000zł do 12 000zł")
    private Integer minSalary;

    @NotNull(message = "Uzupełnij maksymalną stawkę")
    @Range(min = 2000,max = 12_000, message = "Stawka maksymalna powinna się zawierać w przedziale od 2000zł do 12 000zł")
    private Integer maxSalary;

    @NotNull(message = "Aby dodać stanowsiko, musisz najpierw utworzyć dział")
    @ManyToOne()
    @JoinColumn(name = "departmentId")
    private Department department;

    @OneToMany(mappedBy = "profession",cascade = CascadeType.REMOVE)
    private List<Employee> employees;

    public Profession() {
    }

    public int getProfessionId() {
        return professionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(final String positionName) {
        this.positionName = positionName;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public  void setDepartment(final Department department) {
        this.department = department;
    }

}
