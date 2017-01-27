package main.com.sampleService.model;

public class Employee {
	
	public int slNo;
	
	public String EmployeeName;
	
	public String DeptName;

	public Employee() {
		super();
	}

	public Employee(int slNo, String employeeName, String deptName) {
		super();
		this.slNo = slNo;
		EmployeeName = employeeName;
		DeptName = deptName;
	}

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public String getEmployeeName() {
		return EmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	@Override
	public String toString() {
		return "Employee [slNo=" + slNo + ", EmployeeName=" + EmployeeName + ", DeptName=" + DeptName + "]";
	}

}
