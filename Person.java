/** This is the person class for the Poised project Management System
 * 
 * @author Natasha Lopez
 * @version 2.0 28-Sep-2020
 */

/**
 * Declare attributes of the Person class
 */
public class Person {
	private String personNum;
	private String personType;
	private String personName;
	private String personTelNo;
	private String personEmail;
	private String personAddress;
		
	/**
	 * Constructor method for class person
	 * @param personNum String variable for person's reference number
	 * @param personType String variable for the person's role
	 * @param personName String variable for the person's name
	 * @param personTelNo String variable for the person's telephone number
	 * @param personEmail String variable for the person's email address
	 * @param personAddress String variable for the person's address
	 */
	public Person(String personNum, String personType, String personName, String personTelNo, String personEmail, String personAddress) {
		this.personNum = personNum;
		this.personType = personType;
		this.personName = personName;
		this.personTelNo = personTelNo;
		this.personEmail = personEmail;
		this.personAddress = personAddress;
	}
	
	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setPersonTelNo(String personTelNo) {
		this.personTelNo = personTelNo;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}
	
	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}
	
	/**
	 * Setter for private attributes of the Person class
	 * @return the attributes
	 */
	public String getPersonNum() {
		return personNum;
	}
	
	public String getPersonType() {
		return personType;
	}
	
	public String getPersonName() {
	      return personName;
	   }
	   

	public String getPersonTelNo() {
	   return personTelNo;
   }
	   
	public String getPersonEmail() {
		return personEmail;
   }
   
	public String getPersonAddress() {
		return personAddress;
	}
   
	/** Person class string output
	 * @return output String variable 
	 */
	public String toString() {
		String output = personNum + "; " + personType + "; " + personName + "; " + personTelNo + "; " + personEmail + "; " 
				+ personAddress + "\n";   
	    return output;
	}
}