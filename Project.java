/** This is the project class for the Poised project Management System
 * 
 * @author Natasha Lopez
 * @version 2.0 28-Sep-2020
 */

public class Project {
	
	/** project class attributes
	 * 
	 */
	private String projNum;
	private String projName;
	private String buildType;
	private String projAddress;
	private String erfNum;
	private String totalFee;
	private String paidToDate;
	private String dueDate;
	private String projStatus;
	private String complDate;
	private String contractor;
	private String architect;
	private String customer;
		
	/** Constructor method
	 * 
	 * @param projNum String variable of the project number generated when new project is added
	 * @param projName String variable of the project name
	 * @param buildType String variable of the building type
	 * @param projAddress String variable of the building address
	 * @param erfNum String variable of property designation value
	 * @param totalFee String variable of the total cost of the project
	 * @param paidToDate String variable of the fees paid to date
	 * @param dueDate String variable of the date that the project is due
	 * @param projStatus String variable of the project status
	 * @param complDate String variable of the project completion date
	 * @param contractor String variable of the contract's name assigned to the project
	 * @param architect String variable of the architect's name assigned to the project
	 * @param customer String variable of the customer's name
	 */
	public Project (String projNum, String projName, String buildType, String projAddress, String erfNum, String totalFee, String paidToDate,
			String dueDate, String projStatus, String complDate, String contractor, String architect, String customer) {
		
		this.projNum = projNum;
		this.projName = projName;
		this.buildType = buildType;
		this.projAddress = projAddress;
		this.erfNum = erfNum;
		this.totalFee = totalFee;
		this.paidToDate = paidToDate;
		this.dueDate = dueDate;
		this.projStatus = projStatus;
		this.complDate = complDate;
		this.contractor = contractor;
		this.architect = architect;
		this.customer = customer;		
	}

	/**
	 * Getters to access private attributes
	 * @return the attributes
	 */
	public String getProjNum() {
		return projNum;
	}
	
	public String getProjName() {
	      return projName;
	   }
	   
	public String getBuildType() {
	   return buildType;
   }
	
	public String getProjAddress() {
		return projAddress;
	}
	   
	public String getErfNum() {
		return erfNum;
   }
	
	public String getTotalFee() {
		return totalFee;
	}
	
	public String getPaidToDate() {
		return paidToDate;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public String getProjStatus() {
		return projStatus;
	}
	
	public String getComplDate() {
		return complDate;
	}
	
	public String getContractor() {
		return contractor;
	}
	
	public String getArchitect() {
		return architect;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	/**
	 * Method for calculating fee balance
	 * Total cost of project - fee paid to date
	 * @return the fee balance 
	 */
	public double getFeeBalance() {
		double doubleTotalFee = Double.parseDouble(totalFee);
		double doublePaidToDate = Double.parseDouble(paidToDate);	
		
		double feeBalance = doubleTotalFee - doublePaidToDate;
		return feeBalance;
	}
	
	
	public void setProjNum(String projNum) {
		this.projNum = projNum;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}
	
	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public void setProjAddress(String projectAddress) {
		this.projAddress = projectAddress;
	}
	
	public void setErfNum(String erfNum) {
		this.erfNum = erfNum;
	}
	
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	public void setPaidToDate(String paidToDate) {
		this.paidToDate = paidToDate;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setProjStatus(String projStatus) {
		this.projStatus = projStatus;
	}
	
	public void setComplDate(String complDate) {
		this.complDate = complDate;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public void setArchitect(String architect) {
		this.architect = architect;
	}

	/** Project class string output
	 * @return output String variable 
	 */
	public String toString() {
		String output = projNum + "; " + projName + "; " + buildType + "; " + projAddress + "; " + erfNum + "; " + totalFee 
				+ "; " + paidToDate + "; " + dueDate + "; " + projStatus + "; " + complDate + "; " + contractor + "; "
				+ architect + "; " + customer + "\n";
	    
	    return output;
	}
}

	
		

	

