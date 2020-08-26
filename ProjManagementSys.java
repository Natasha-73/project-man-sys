// This is the main program for the Poised project management System

// Poise can use this program to add, track and update projects, generate invoices when the project is finalised. They can also add
// and update details on customers, architects and contractors.

import java.util.*;

public class ProjManagementSys {

	public static void main(String[] args) {
		System.out.println("This program allows input of details for a project and users ");
		Scanner input = new Scanner(System.in);
		
		// Array lists for the projects and persons associated with the project
		ArrayList<Person> personArray = new ArrayList <Person> ();
		ArrayList <project> projectArray = new ArrayList <project> ();
		
		// Existing project and persons stored to their respective array lists
		project firstExistProj = new project("1", "", "House", "10 Downing Street", "00001", 100000.00, 5000.00, "01 Sep 2020", "Ongoing", "-", "Alex Kilburn", "Zoey Wyk", "Natasha Lopez");
		project secondExistProj = new project("2", "", "Barn", "1 Farmyard rd", "34562", 150000.00, 150000.00, "01 Sep 2020", "Ongoing", "-", "Alex Kilburn", "Zoey Wyk", "Joe Smith");
		Person existArch = new Person("Architect", "Zoey Daniel", "867-5309", "zoey.Daniel@fakemail.com", "9 Downing Street");
		Person existCust = new Person("Customer", "Natasha Lopez", "774-0195", "n.lopez@fakemail.com", "1 No Lane");
		Person existContr = new Person("Contractor", "Alex Kilburn", "780-2204", "alex.Kilburn@fakemail.com", "7 Highway to Heaven");
		
		projectArray.add(firstExistProj);
		projectArray.add(secondExistProj);
		personArray.add(existArch); 
		personArray.add(existCust);
		personArray.add(existContr);
		
		// Add new project or new user objects to their respective arrays
		boolean option = true;
		
		// While loop allows the user to return to this main menu once each option is executed
		while (option) {
			System.out.println("Please enter \nnp - Add a new project \nnu - Add a new user details \ndd - Update the project due date"
				+ "\nf - Update the total amount of fees paid to date \ncd - Update contractor's contact details \nfp - Finalise project \nq - quit");		
			String optionSelect = input.nextLine();
		
		// Adds new user details
			if (optionSelect.equalsIgnoreCase("nu")) {			
				System.out.println("Enter the user type: ");
				String userType = input.nextLine();
			
				System.out.println("Enter the user name: ");
				String personName = input.nextLine();
				
				System.out.println("Enter the user Telephone number: ");
				String telNo = input.nextLine();
				
				System.out.println("Enter the user e-mail address: ");
				String email = input.nextLine();
				
				System.out.println("Enter the user address: ");
				String personAddress = input.nextLine();
				
				Person personData = new Person(userType, personName, telNo, email, personAddress);
				personArray.add(personData);
				
				System.out.println("New user added.");
				System.out.println(personArray);		
		}
		
		// Adds new project data
			else if (optionSelect.equalsIgnoreCase("np")) {
			
			// Program automatically generates a project number which is next in sequence after the previous project number
				int projArrayLen = projectArray.size();
				int newProjNum = projArrayLen + 1;
				String projectNum = Integer.toString(newProjNum);
				
				System.out.println("Enter the project name: ");
				String projName = input.nextLine();
				
				System.out.println("Enter the building type: ");
				String buildType = input.nextLine();
				
				System.out.println("Enter the building address: ");
				String projectAddress = input.nextLine();
				
				System.out.println("Enter the ERF number: ");
				String erfNum = input.nextLine();
				
				System.out.println("Enter the total fee: £");
				double totalFee = input.nextDouble();
				
				System.out.println("Enter the fees paid to date: £");
				double paidToDate = input.nextDouble();
				input.nextLine();
				
				System.out.println("Enter the project due date in format DD MMM YYYY: ");
				String dueDate = input.nextLine();
				
				String status = "Ongoing";
				String complDate = "-";
				
				System.out.println("Enter the contractor assigned to the project: ");
				String contractor = input.nextLine();
				
				System.out.println("Enter the architect assigned to the project: ");
				String architect = input.nextLine();
				
				System.out.println("Enter the customer's firstname and surname: ");
				String customer = input.nextLine();
				String [] splitCustomer = customer.split(" ");
							
				// Gives the project a title if a title isn't named, building type and Surname of customer used
				if (projName.equalsIgnoreCase("")) {
					projName = buildType + " " + splitCustomer[1];
				}
			
			project projectData = new project(projectNum, projName, buildType, projectAddress, erfNum, totalFee, paidToDate, dueDate, status, complDate, contractor, architect, customer);
			projectArray.add(projectData);	
			
			System.out.println("New project added.\n");
			
			System.out.println(projectArray);		
		}
		
		// Updates elements of the project
			
		// Updates due date
		else if (optionSelect.equalsIgnoreCase("dd")) {
			for (project i: projectArray) {
				System.out.println("\nProject number: " + i.getProjectNum() + "\nProject name: " + i.getProjName() + "\nBuilding Type: " + i.getBuildType()
				+ "\nBuilding address: " + i.getProjectAddress() + "\nERF number: " + i.getErfNum() + "\nTotal Fee: " + i.totalFee + "\nFees paid to date: " + i.paidToDate
				+ "\nProject due date: " + i.getDueDate() + "\nCompletetion Date: " + i.getComplDate() + "\nContractor: " + i.getContractor()
				+ "\nArchitect: " + i.getArchitect() + "\nCustomer: " + i.getCustomer() + "\n");		
				}
			System.out.println("Choose the project (by project number) that you would like to update");
				
			String projChoice = input.nextLine();
			System.out.println("Enter the new date: ");
			String newDate = input.nextLine();
			
			for (project i: projectArray) {
				if (i.getProjectNum().equals(projChoice)) {
					i.setDueDate(newDate);
					break;
				}
			}
			System.out.println("Project due date updated");
		}
		
		// Update fees paid to date
		else if (optionSelect.equalsIgnoreCase("f")) {
			
			// Displays the existing projects
			for (project i: projectArray) {
				System.out.println("Project number: " + i.getProjectNum() + "\nProject name: " + i.getProjName() + "\nBuilding Type: " + i.getBuildType()
				+ "\nBuilding address: " + i.getProjectAddress() + "\nERF number: " + i.getErfNum() + "\nTotal Fee: " + i.totalFee + "\nFees paid to date: " + i.paidToDate
				+ "\nProject due date: " + i.getDueDate() + "\nCompletetion Date: " + i.getComplDate()+ "\nContractor: " + i.getContractor()
				+ "\nArchitect: " + i.getArchitect() + "\nCustomer: " + i.getCustomer() + "\n");	
				}
			
			System.out.println("Choose the project (by project number) that you would like to update");
			String projChoice = input.nextLine();
			System.out.println("Enter the total amount of the fee paid to date: £");
			double newPaidFee = input.nextDouble();
			input.nextLine();
			
			for (project i: projectArray) {
				if (i.getProjectNum().equals(projChoice)) {
					i.setPaidToDate(newPaidFee);
					break;
				}
			}
			System.out.println("Fee paid to date for the project updated.");
		}
		
		// Updates user details - one detail can be updated and then the program returns to the main menu
		else if (optionSelect.equalsIgnoreCase("cd")) {
			
			//Displays all person in person array	
			for (Person i: personArray) {
				System.out.println("User Type: " + i.getUserType() + "\nName: " + i.getPersonName() + "\nTel no.: " + i.getTelNo()
				+ "\nE-mail Address: " + i.getEmail() + "\nAddress: " + i.getPersonAddress() + "\n");	
				}
				
			System.out.println("Choose the user (by fullname) that you would like to update");
			String contrChoice = input.nextLine();
			
			System.out.println("User detail to be updated \ntn - Telephone number \nea - Email address \na - Address");
			String updateDetail = input.nextLine();
			
			//Updates Contractor's telephone number
			if (updateDetail.equalsIgnoreCase("tn")) {
				System.out.println("Enter the user new Telephone number ");
				String newTelNo = input.nextLine();
		
				for (Person i: personArray) {
					if (i.getPersonName().equals(contrChoice)) {
						i.setTelNo(newTelNo);
						break;
					}
				}
				System.out.println("User telephone number updated.");
			}
			
			//Updates Contractor's e-mail address
			else if (updateDetail.equalsIgnoreCase("ea")) {
				System.out.println("Enter the User's new e-mail address: ");
				String newEmailAddress = input.nextLine();
		
				for (Person i: personArray) {
					if (i.getPersonName().equals(contrChoice)) {
						i.setEmail(newEmailAddress);
						break;
					}
				}
				System.out.println("User's e-mail address updated.");
			}
			
			//Updates Contractor's address
			else if (updateDetail.equalsIgnoreCase("a")) {
				System.out.println("Enter the contractor's new address: ");
				String newAddress = input.nextLine();
		
				for (Person i: personArray) {
					if (i.getPersonName().equals(contrChoice)) {
						i.setPersonAddress(newAddress);
						break;
						}
					}
				System.out.println("Enter the contractor's new address updated ");
				}
			
		}
		
		// Finalise project
		else if (optionSelect.equalsIgnoreCase("fp")) {
			for (project i: projectArray) {
				System.out.println("Project number: " + i.getProjectNum() + "\nProject name: " + i.getProjName() + "\nBuilding Type: " + i.getBuildType()
				+ "\nBuilding address: " + i.getProjectAddress() + "\nERF number: " + i.getErfNum() + "\nTotal Fee: " + i.totalFee + "\nFees paid to date: " + i.paidToDate
				+ "\nProject due date: " + i.getDueDate() + "\nCompletetion Date: " + i.getComplDate() + "\nContractor: " + i.getContractor()
				+ "\nArchitect: " + i.getArchitect() + "\nCustomer: " + i.getCustomer() + "\n");		
				}
			
			System.out.println("Choose the project (by project number) that you would like to update");
			String projChoice = input.nextLine();
		
			System.out.println("Enter the completion date: ");
			String projFinalDate = input.nextLine();
			
			for (project i: projectArray) {
				if (i.getProjectNum().equals(projChoice)) {
					i.setStatus("Finalised");
					i.setComplDate(projFinalDate);
					break;
				}
			}
			
			// Generate invoice, if there is no outstanding balance, prints a statement that no invoice is required		
			for (project i: projectArray) {
				if (i.getProjectNum().equals(projChoice)) {
					if (i.getFeeBalance() == 0) {
						System.out.println("No balance outstanding, no invoice generated");
					break;
					}
					
					// Get customer's name and outstanding balance for the invoice
					for (Person j: personArray) {
						if (i.customer.equals(j.personName)) {
							System.out.println("INVOICE:\n");
							System.out.println("Name: " + j.getPersonName() + "\nTel no.: " + j.getTelNo()
							+ "\nE-mail Address: " + j.getEmail() + "\nAddress: " + j.getPersonAddress() + "\n");
							System.out.println("Outstanding balance = £" + i.getFeeBalance() + "\n");							
						}	
					}
					
				}			
			}
			
		}
			
		// Ends the program
		else if (optionSelect.equalsIgnoreCase("q")) {
			option = false;
		}

	}
		input.close();
	}
}
		
	 
	


