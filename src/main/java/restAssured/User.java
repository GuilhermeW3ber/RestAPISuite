package restAssured;

import javax.xml.bind.annotation.XmlAcessType;
import javax.xml.bind.annotation.XmlAcessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
@XmlAcessorType(XmlAcessType.FIELD)
public class User {
	
	@XmlAttribute
	private Long id;
	private String nome;
	private Integer age;
	private Double salary;
	
	public User() {}
	
	public User(String nome, Integer age) {
		super();
		this.nome = nome;
		this.age = age;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [nome=" + nome + ", age=" + age + ", salary=" + salary + ", id=" + id + "]";
	}
	
}
