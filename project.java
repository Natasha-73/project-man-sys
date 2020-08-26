// This is the project class for the poised program


public class project {
	// Class for defining project objects

	String projectNum;
	String projName;
	String buildType;
	String projectAddress;
	String erfNum;
	double totalFee;
	double paidToDate;
	String dueDate;
	String status;
	String complDate;
	String contractor;
	String architect;
	String customer;
		
	//Constructor method for class person
	public project (String projectNum, String projName, String buildType, String projectAddress, String erfNum, double totalFee, double paidToDate,
			String dueDate, String status, String complDate, String contractor, String architect, String customer) {
		this.projectNum = projectNum;
		this.projName = projName;
		this.buildType = buildType;
		this.projectAddress = projectAddress;
		this.erfNum = erfNum;
		this.totalFee = totalFee;
		this.paidToDate = paidToDate;
		this.dueDate = dueDate;
		this.status = status;
		this.complDate = complDate;
		this.contractor = contractor;
		this.architect = architect;
		this.customer = customer;
			
	}
	
	public void setBuildType(String anyName) {
		buildType = anyName;
	}
	
	public void setPaidToDate(double anyValue) {
		paidToDate = anyValue;
	}
	
	public void setDueDate(String anyValue) {
		dueDate = anyValue;
	}
	
	public void setStatus(String anyWord) {
		status = anyWord;
	}
	
	public void setComplDate(String anyValue) {
		complDate = anyValue;
	}
	
	public void setCustomer(String anyName) {
		customer = anyName;
	}
	
	// Methods to return data from Person
	public String getProjectNum() {
		return projectNum;
	}
	
	public String getProjName() {
	      return projName;
	   }
	   
	public String getBuildType() {
	   return buildType;
   }
	
	public String getProjectAddress() {
		return projectAddress;
	}
	   
	public String getErfNum() {
		return erfNum;
   }
	
	public String getDueDate () {
		return dueDate;
	}
	
	public String getStatus () {
		return status;
	}
	
	public String getComplDate () {
		return complDate;
	}
	
	public String getContractor () {
		return contractor;
	}
	
	public String getArchitect () {
		return architect;
	}
	
	public String getCustomer () {
		return customer;
	}
	
	public double getFeeBalance () {
		double feeBalance = totalFee - paidToDate;
		return feeBalance;
	}
		
	
	// Project class output
	public String toString() {
		String output = "\nProject Number: " + projectNum ;
	    output += "\nProject Title: " + projName;
	    output += "\nBuilding Type: " + buildType;
	    output += "\nAddress: " + projectAddress;
	    output += "\nERF Number: " + erfNum;
	    output += "\nTotal Fee: " + totalFee;
	    output += "\nPaid to Date: " + paidToDate;
	    output += "\nDue Date: " + dueDate;
	    output += "\nProject Status: " + status;
	    output += "\nCompletion Date: " + complDate;
	    output += "\nContractor: " + contractor;
	    output += "\nArchitect: " + architect;
	    output += "\nCustomer: " + customer + "\n";
	    
	    return output;
	}

}

	
		

	

