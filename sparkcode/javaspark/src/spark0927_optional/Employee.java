package spark0927_optional;
public class Employee {
    
    private String firstName;
    private String lastName;
    private double salary;
    private String department;
	private int age;
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age != other.age)
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (Double.doubleToLongBits(salary) != Double
				.doubleToLongBits(other.salary))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	private Status status;
	
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

    public Employee(String firstName, int age, double salary, Status status){
    	this.firstName = firstName;
    	this.age = age;
    	this.salary = salary;
    	this.status=status;
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
    
    public enum Status{
    	BUSY,
    	FREE,
    	VOCATION
    }    
} 