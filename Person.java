// Class for defining Person object

// Declare attributes of the Person class
public class Person {
	String userType;
	String personName;
	String telNo;
	String email;
	String personAddress;
		
	//Constructor method for class person
	public Person(String userType, String personName, String telNo, String email, String personAddress) {
		this.userType = userType;
		this.personName = personName;
		this.telNo = telNo;
		this.email = email;
		this.personAddress = personAddress;
	}
	
	// Methods to get and set data from Person
	public void setTelNo(String anyName) {
		telNo = anyName;
	}
	
	public void setEmail(String anyName) {
		email = anyName;
	}
	
	public void setPersonAddress(String anyAddress) {
		personAddress = anyAddress;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public String getPersonName() {
	      return personName;
	   }
	   
	public String getTelNo() {
	   return telNo;
   }
	   
	public String getEmail() {
		return email;
   }
   
	public String getPersonAddress() {
		return personAddress;
	}
   
	// Output of class
	public String toString() {
		String output = "\nUser Type:" + userType;
	    output += "\nName: " + personName;
	    output += "\nTelephone number:" + telNo;
	    output += "\nE-mail Address: " + email;
	    output += "\nAddress: " + personAddress + "\n";
	   
	    return output;
	}

}


