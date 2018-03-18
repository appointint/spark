package spark0913;
public class Employee {
    
    private String firstName;
    private String lastName;
    private double salary;
    private String department;


	private int age;

    public Employee(){
    	System.out.println("Employee() is called.");
    }
    
    public Employee(int age){
    	this.age = age;
    }
    
    public Employee(String firstName, int age){
    	this.firstName = firstName;
    	this.age = age;
    }
    
    public Employee(String firstName, int age, double salary){
    	this.firstName = firstName;
    	this.age = age;
    	this.salary = salary;
    }
    
    public Employee(String firstName, String lastName, double salary, String department) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.department = department;
    }

    public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
    // set firstName
    public void setFirstName(String firstName) {
    this.firstName = firstName;
    }

    // get firstName
    public String getFirstName() {
    return firstName;
    }

    // set lastName
    public void setLastName(String lastName) {
    this.lastName = lastName;
    }

    // get lastName
    public String getLastName() {
    return lastName;
    }

    // set salary
    public void setSalary(double salary) {
    this.salary = salary;
    }

    // get salary
    public double getSalary() {
    return salary;
    }

    // set department
    public void setDepartment(String department) {
        this.department = department;
    }

    // get department
    public String getDepartment() {
        return department;
    }

    // return Employee's first and last name combined
    public String getName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return String.format("%-8s %-8s %d %8.2f %s", getFirstName(), getLastName(), age, getSalary(), getDepartment());
    } 
} 