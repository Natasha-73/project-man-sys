import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
* This is the program serves as the main interface for the Poised Project Management System
* 
* Poise can use this program to add, track and update projects, generate invoices when the project is finalised. They can 
* also add and update details on customers, architects and contractors.
*
* @author Natasha Lopez
* @version 2.0 28-Sep-2020
*/
public class ProjManagementSys {

	public static void main(String[] args) {
		/**
		 * @exception RunTime
		 * @exception Generic exception
		 */
		try {
			System.out.println("This program allows input of details for various projects and persons"
					+ " associated with the project.");
			
			/** Connect to poised_db using username: otheruser, password: swordfish
			 */
			Connection databaseConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/poised_db?useSSL=false",
					"otheruser", "swordfish"
					);
			
			Statement statementObject = databaseConnection.createStatement();
			
			
			// Creates array lists for the projects and persons associated with the project 
			 
			ArrayList<Person> personArray = new ArrayList <Person> ();
			ArrayList <Project> projectArray = new ArrayList <Project> ();
			
			
			// Reads tables from the poised_db for projects and persons info
			
			readProjTableMethod(projectArray, statementObject);
			readPersTableMethod(personArray, statementObject);
			
			
			//Adds new project or new user objects to their respective arrays
			 
			// Main Menu while loop allows the user to return to this main menu once each option is executed
			 			
			Scanner anyInput = new Scanner(System.in);
			while (true) {
				System.out.println("\n*** MAIN MENU ***\nPlease enter \nnp - Add a new project \nnu - Add a new person's details \ndd - Update the project due date"
					+ "\nf - Update the total amount of fees paid to date \ncd - Update person's contact details \nfp - Finalise project \n"
					+ "vp - View projects Status \nq - quit");	
				
				String optionSelect = anyInput.nextLine();
			
				
				// Adds new user details to person array list
				
				if (optionSelect.equalsIgnoreCase("nu")) {			
					addNewUser(anyInput, personArray);						
				}
			
				
				// Adds new project data to project array list
				 
				else if (optionSelect.equalsIgnoreCase("np")) {
					addNewProject(anyInput, projectArray, personArray);		
				}
						
				 // UPDATES ELEMENTS OF THE PROJECT AND PERSONS
				 
				 // Updates due date for this project
				 
				else if (optionSelect.equalsIgnoreCase("dd")) {		
					updateDueDateMethod(anyInput, projectArray);
				}
			
				
				// Update fees paid to date for this project
				 
				else if (optionSelect.equalsIgnoreCase("f")) {
					updateFeePaidMethod(anyInput, projectArray);
				}
			
				// Updates person's details - one detail can be updated and then the program returns to the main menu
				 
				else if (optionSelect.equalsIgnoreCase("cd")) {	
					updateUserDetails(anyInput, personArray);		
				}
			
				// Finalises this project and generates an invoice
				 
				else if (optionSelect.equalsIgnoreCase("fp")) {
					finaliseProjectMethod(anyInput, personArray, projectArray);		
				}
				
				
				// Shows all projects, incomplete projects and ongoing projects past due date
				
				else if (optionSelect.equalsIgnoreCase("vp")) {
					viewProjects(personArray, projectArray, anyInput);
				}
							
				// Ends the program
				// Gives the option to save changes to the database or exit without saving
				
				else if (optionSelect.equalsIgnoreCase("q")) {
					System.out.println("Would you like to save changes to the text files before exiting? Enter yes or no");				
					String saveOption = anyInput.nextLine();
					
					if (saveOption.equalsIgnoreCase("yes")) {
						
						writeToPersonTable(statementObject, personArray);
						
						writeToProjTable(statementObject, projectArray);
						
						writeToErfNumTable(statementObject, projectArray);
																									
						break;
					}
					
					else {
						break;
					}
				}
			}
			
			anyInput.close();			
		}
		
		// Catch any runtime exceptions

		catch (RuntimeException errorFile) {
			System.out.println("Runtime Error.");
			System.out.println();
			System.out.println(errorFile.getMessage());
			System.out.println();
			errorFile.printStackTrace();
		}
		
		// Catch any unforeseen exceptions
		catch (Exception errorFile) {
			System.out.println("Unforeseen Error!");
			System.out.println();
			System.out.println(errorFile.getMessage());
			System.out.println();
			errorFile.printStackTrace();
		}	
	}

	//********************************************************************************************************************
	//*********************************************************METHODS****************************************************
	/**
	 * Writes data from the project array to the erfNum table in the database
	 * @param statementObject Object inside of the connection
	 * @param projectArray String array list for person object details
	 * @throws SQLException Provides information of database access error
	 */
	private static void writeToErfNumTable(Statement statementObject, ArrayList<Project> projectArray)
			throws SQLException {
		String deleteRows = "DELETE FROM erfNumTable";
		statementObject.executeUpdate(deleteRows);
				
		for (Project i: projectArray) {
			
			String erfNumTableData = "INSERT INTO erfNumTable VALUES ("
					+ "'"+i.getProjAddress()+"',"
					+ "'"+i.getErfNum()+"')";

			statementObject.executeUpdate(erfNumTableData);
		}
	}

	/**
	 * Writes data from the project array to the project table in the database
	 * @param statementObject Object inside of the connection
	 * @param projectArray String array list for person object details
	 * @throws SQLException Provides information of database access error
	 */
	private static void writeToProjTable(Statement statementObject, ArrayList<Project> projectArray)
			throws SQLException {
		String deleteRows = "DELETE FROM projectTable";
		statementObject.executeUpdate(deleteRows);
				
		for (Project i: projectArray) {
			
			String projTableData = "INSERT INTO projectTable VALUES ("
					+ "'"+ i.getProjNum()+"',"
					+ "'"+ i.getProjName()+"',"
					+ "'"+ i.getBuildType()+"', "
					+ "'"+ i.getProjAddress()+"', "
					+ "'"+ i.getTotalFee()+"', "
					+ "'"+ i.getPaidToDate()+"', "
					+ "'"+ i.getDueDate()+"', "
					+ "'"+ i.getProjStatus()+"', "
					+ "'"+ i.getComplDate()+"', "
					+ "'"+ i.getContractorID()+"', "
					+ "'"+ i.getArchitectID()+"', "
					+ "'"+ i.getCustomerID()+"')";

			statementObject.executeUpdate(projTableData);
		}
	}

	/**
	 * Writes data from the person array to the person table within the database
	 * @param statementObject Object inside of the connection
	 * @param personArray String array list for person object details
	 * @throws SQLException Provides information of database access error
	 */
	private static void writeToPersonTable(Statement statementObject, ArrayList<Person> personArray) throws SQLException {
		String deleteRows = "DELETE FROM personTable";
		statementObject.executeUpdate(deleteRows);
		
		for (Person i: personArray) {
			
			String personData = "INSERT INTO personTable VALUES ("
					+ "'"+ i.getPersonNum() +"', "
					+ "'"+ i.getPersonType() +"', "
					+ "'"+ i.getPersonName() +"', "
					+ "'"+ i.getPersonTelNo() +"', "
					+ "'"+ i.getPersonEmail() +"', "
					+ "'"+ i.getPersonAddress() +"')";

			statementObject.executeUpdate(personData);
		}
	}	

	/**
	 * Reads personTable from the poised database and puts into the Person Array list
	 * @param personArray String array list for person object details
	 * @param statementObject Object inside of the connection 
	 */
	private static void readPersTableMethod(ArrayList<Person> personArray, Statement statementObject) {
		try {

			ResultSet queryResults = statementObject.executeQuery("SELECT * FROM personTable");
			
			while (queryResults.next()) {
					
				Person personObject = new Person(
						queryResults.getString("personID"), 
						queryResults.getString("personType"),
						queryResults.getString("personName"), 
						queryResults.getString("personTelNo"), 
						queryResults.getString("personEmail"), 
						queryResults.getString("personAddress"));
				personArray.add(personObject);
			}	
		}	
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads project table from the Poised database and stores into the project Array list
	 * @param projectArray String Array list which stores project details
	 * @param statementObject Object inside of the connection
	 */
	private static void readProjTableMethod(ArrayList<Project> projectArray, Statement statementObject) {
		
		try {
			
			// Selects attributes from related tables in the Poised database
			String selectQuery = "SELECT projNum, "
					+ "projName, "
					+ "buildType, "
					+ "projectTable.projAddress, "
					+ "erfNumTable.erfNum, "
					+ "totalFee, "
					+ "paidToDate, "
					+ "dueDate, "
					+ "status, "
					+ "complDate,"
					+ "contractID, "
					+ "architectID, "
					+ "customerID "
					+ "FROM projectTable ";
					
			String joinQuery = "INNER JOIN erfNumTable ON projectTable.projAddress = erfNumTable.projAddress ";
							
			ResultSet queryResults = statementObject.executeQuery(selectQuery + joinQuery);		
			
			// Writes to array list
			while (queryResults.next()) {
				
				Project projectObject = new Project(
						queryResults.getString("projNum"), 
						queryResults.getString("projName"), 
						queryResults.getString("buildType"), 
						queryResults.getString("projAddress"), 
						queryResults.getString("erfNum"), 
						queryResults.getString("totalFee"), 
						queryResults.getString("paidToDate"),
						queryResults.getString("dueDate"), 
						queryResults.getString("status"), 
						queryResults.getString("complDate"),
						queryResults.getString("contractID"), 
						queryResults.getString("architectID"), 
						queryResults.getString("customerID"));
				projectArray.add(projectObject);
				}
		}
			
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds a new user to the person array list
	 * @param anyInput String variable for any input
	 * @param personArray String array list for person object details
	 */
	private static void addNewUser(Scanner anyInput, ArrayList<Person> personArray) {
		int personArrayLen = personArray.size();
		int intPersonNum = personArrayLen + 1;
		String newPersonNum = Integer.toString(intPersonNum);
		
		System.out.println("Enter the person's type: ");
		String newPersonType = anyInput.nextLine();

		System.out.println("Enter the person's name: ");
		String newPersonName = anyInput.nextLine();
		
		System.out.println("Enter the person's Telephone number: ");
		String newTelNo = anyInput.nextLine();
		
		System.out.println("Enter the person's e-mail address: ");
		String newEmail = anyInput.nextLine();
		
		System.out.println("Enter the person's address: ");
		String newPersonAddress = anyInput.nextLine();
		
		Person personData = new Person(newPersonNum, newPersonType, newPersonName, newTelNo, newEmail, newPersonAddress);
		personArray.add(personData);
		
		displayPerson(personArray);
		System.out.println("New person associated with the project added.");
	}
	
	/**
	 * Adds a new project to project array list
	 * @param anyInput String variable for any input
	 * @param projectArray String array list for project object details
	 * @param personArray String array of all persons associated with the project
	 */
	private static void addNewProject(Scanner anyInput, ArrayList<Project> projectArray, ArrayList<Person> personArray) {
		
		
		// Method generates a project number which is next in sequence after the previous project number
		
		int projArrayLen = projectArray.size();
		int newProjNum = projArrayLen + 1;
		String projNum = Integer.toString(newProjNum);
		
		System.out.println("Enter the project name: ");
		String newProjName = anyInput.nextLine();
		
		System.out.println("Enter the building type: ");
		String newBuildType = anyInput.nextLine();
		
		System.out.println("Enter the building address: ");
		String newProjAddress = anyInput.nextLine();
		
		System.out.println("Enter the ERF number: ");
		String newErfNum = anyInput.nextLine();
		
		System.out.println("Enter the total fee: £");
		String newTotalFee = anyInput.nextLine();
		newTotalFee = newTotalFee.replace(",", "");
		
		System.out.println("Enter the fees paid to date: £");
		String newPaidToDate = anyInput.nextLine();
		newPaidToDate = newPaidToDate.replace(",", "");
			
		System.out.println("Enter the project due date in format yyyy-MM-dd (eg 2020-09-27): ");
		String newDueDate = anyInput.nextLine();
		
		String status = "Ongoing";
		String newComplDate = "-";
		
		System.out.println("Enter the contractor (firstname and surname) assigned to the project: ");
		String newContractor = anyInput.nextLine();
		
		// Checks if person exists in the database, retrieves person (Contractor, Architect and Customer) number, 
		// if not asks the user to input person details
		String newContractorID = "";
		for (Person i: personArray) {

			if (newContractor.equalsIgnoreCase(i.getPersonName())) {				
				newContractorID = i.getPersonNum();
				break;
			}
		}
		
		if (newContractorID.equals("")) {
			System.out.println("Contractor doesn't exist in the database, please add person name and details");
			addNewUser(anyInput, personArray);
			int intContractorID = personArray.size();
			newContractorID = Integer.toString(intContractorID);
		}
				
		System.out.println("Enter the architect firstname and surname assigned to the project: ");
		String newArchitect = anyInput.nextLine();
		
		String newArchitectID = "";
		
		for (Person i: personArray) {
			if (newArchitect.equalsIgnoreCase(i.getPersonName())) {					
				newArchitectID = i.getPersonNum();
				break;
			}			
		}
		
		if (newArchitectID.equals("")) {	
			System.out.println("Architect doesn't exist in the database, please add person name and details");
			addNewUser(anyInput, personArray);
			int intArchitectID = personArray.size();
			newArchitectID = Integer.toString(intArchitectID);						
		}		
		
		System.out.println("Enter the customer's firstname and surname: ");
		String newCustomer = anyInput.nextLine();
		
		String newCustomerID = "";
												
		for (Person i: personArray) {
			
			if (newCustomer.equalsIgnoreCase(i.getPersonName())) {
				newCustomerID = i.getPersonNum();
				break;
			}
		}
		
		if (newCustomerID.equals("")){
			System.out.println("Person doesn't exist in the database, please add person name and details");
			addNewUser(anyInput, personArray);
			int intCustomerID = personArray.size();
			newCustomerID = Integer.toString(intCustomerID);
		}
		
		String [] splitCustomer = newCustomer.split(" ");
							
		// Gives this project a title if a title isn't entered by the user, building type and Surname of customer used
		if (newProjName.equalsIgnoreCase("")) {
			newProjName = newBuildType + " " + splitCustomer[1];
		}

		Project projectData = new Project(projNum, newProjName, newBuildType, newProjAddress, newErfNum, 
				newTotalFee, newPaidToDate, newDueDate, status, newComplDate, newContractorID, newArchitectID, 
				newCustomerID);
		
		projectArray.add(projectData);	
		System.out.println("New project added.");
		System.out.println("\nNOTE: Please add the details of the persons associated with the project if they"
			+ " are not already in the system. From the main menu, select nu.\n");
	}
	
	/**
	 * Updates this project's due date
	 * @param anyInput String variable for any input
	 * @param projectArray String array list for project object details
	 */
	private static void updateDueDateMethod(Scanner anyInput, ArrayList<Project> projectArray) {
		displayProject(projectArray);
		
		System.out.println("Choose the project (by project number) that you would like to update");
			
		String projChoice = anyInput.nextLine();
		System.out.println("Enter the new due date in the format yyyy-MM-dd: ");
		String updateDueDate = anyInput.nextLine();
		
		for (Project i: projectArray) {
			if (i.getProjNum().equals(projChoice)) {
				i.setDueDate(updateDueDate);
				break;
			}
		}
		System.out.println("Project due date updated.");
	}
	
	/**
	 * Update the fees paid to date to this project
	 * @param anyInput String variable for any input
	 * @param projectArray String array list for project object details
	 */
	private static void updateFeePaidMethod(Scanner anyInput, ArrayList<Project> projectArray) {
		displayProject(projectArray);
		
		System.out.println("Choose the project (by project number) that you would like to update");
		String projChoice = anyInput.nextLine();
		System.out.println("Enter the total amount of the fee paid to date: £");
		String updatePaidFee = anyInput.nextLine();
		updatePaidFee = updatePaidFee.replace(",", "");
		
		for (Project i: projectArray) {
			if (i.getProjNum().equals(projChoice)) {
				i.setPaidToDate(updatePaidFee);
				break;
			}
		}
		System.out.println("Fee paid to date for the project updated.");
	}	
	
	/**
	 * Update User Details
	 * @param anyInput String variable for any input
	 * @param personArray String array list for person object details
	 */
	private static void updateUserDetails(Scanner anyInput, ArrayList<Person> personArray) {
		displayPerson(personArray);
		
		System.out.println("Choose the person by identifier number that you would like to update");
		String personIdenChoice = anyInput.nextLine();
		
		System.out.println("User detail to be updated \ntn - Telephone number \nea - Email address \na - Address");
		String updateDetail = anyInput.nextLine();

		// Updates person's telephone number	 
		if (updateDetail.equalsIgnoreCase("tn")) {
			updateTelNoMethod(anyInput, personArray, personIdenChoice);
		}
			
		 // Updates person's e-mail address
		else if (updateDetail.equalsIgnoreCase("ea")) {
			updateEmailAddMethod(anyInput, personArray, personIdenChoice);
		}

		// Updates Contractor's address
		else if (updateDetail.equalsIgnoreCase("a")) {
			updateAddressMethod(anyInput, personArray, personIdenChoice);
		}
	}

	/**
	 * Update a person's address
	 * @param anyInput String variable for any input
	 * @param personArray String array list for person object details
	 * @param personIdenChoice String variable of number for person identification
	 */
	private static void updateAddressMethod(Scanner anyInput, ArrayList<Person> personArray, String personIdenChoice) {
		System.out.println("Enter the person's new address: ");
		String updateAddress = anyInput.nextLine();

		for (Person i: personArray) {
			if (i.getPersonNum().equals(personIdenChoice)) {
				i.setPersonAddress(updateAddress);
				break;
			}
		}
		System.out.println("Address updated. ");
	}

	/**
	 * Update a person's email address
	 * @param anyInput String variable for any input
	 * @param personArray String array list for person object details
	 * @param personIdenChoice String variable of number for person identification
	 */
	private static void updateEmailAddMethod(Scanner anyInput, ArrayList<Person> personArray, String personIdenChoice) {
		System.out.println("Enter the person's new e-mail address: ");
		String updateEmailAddress = anyInput.nextLine();

		for (Person i: personArray) {
			if (i.getPersonNum().equals(personIdenChoice)) {
				i.setPersonEmail(updateEmailAddress);
				break;
			}
		}
		System.out.println("Person's e-mail address updated.");
	}

	/** 
	 * Updates a person's telephone number
	 * @param anyInput String variable for any input
	 * @param personArray String array list for person object details
	 * @param personIdenChoice String variable of number for person identification
	 */
	private static void updateTelNoMethod(Scanner anyInput, ArrayList<Person> personArray, String personIdenChoice) {
		System.out.println("Enter the person's new Telephone number ");
		String updateTelNo = anyInput.nextLine();

		for (Person i: personArray) {
			if (i.getPersonNum().equals(personIdenChoice)) {
				i.setPersonTelNo(updateTelNo);
				break;
			}
		}
		System.out.println("Person telephone number updated.");
	}
	
	/**
	 * Finalises the project
	 * Changes this project status to completed and user inputs the completion date
	 * @param anyInput String variable for any input
	 * @param personArray String array list for person object details
	 * @param projectArray String array list for project object details
	 */
	private static void finaliseProjectMethod(Scanner anyInput, ArrayList<Person> personArray,
		ArrayList<Project> projectArray) {
		displayProject(projectArray);
		
		System.out.println("Choose the project (by project number) that you would like to finalise.");
		String projChoice = anyInput.nextLine();

		System.out.println("Enter the completion date in the format yyyy-MM-dd: ");
		String projFinalDate = anyInput.nextLine();
		
		for (Project i: projectArray) {
			if (i.getProjNum().equals(projChoice)) {
				i.setProjStatus("Finalised");
				i.setComplDate(projFinalDate);
				break;
			}
		}
		
		//Generate invoice, if there is no outstanding balance, prints a statement that no invoice is required		
		generateInvoice(personArray, projectArray, projChoice);	
	}
	
	/** 
	 * Generates invoice once the project is finalised and determines the outstanding balance
	 * Does not generate an invoice if no balance is outstanding
	 * @param personArray String array list for person object details
	 * @param projectArray String array list for project object details
	 * @param projChoice String variable of project choice
	 */
	private static void generateInvoice(ArrayList<Person> personArray, ArrayList<Project> projectArray, 
			String projChoice) {
		for (Project i: projectArray) {
			if (i.getProjNum().equals(projChoice)) {				
				if (i.getFeeBalance() == 0) {
					System.out.println("No balance outstanding, no invoice generated");
					break;
				}
							
				// Get customer's name and outstanding balance for the invoice			
				for (Person j: personArray) {				
					if (i.getCustomerID().equals(j.getPersonNum())) {
						System.out.println("INVOICE:\n");
						System.out.println("Name: " + j.getPersonName() 
						+ "\nTel no.: " + j.getPersonTelNo()
						+ "\nE-mail Address: " + j.getPersonEmail() 
						+ "\nAddress: " + j.getPersonAddress() + "\n");
						
						System.out.println("Outstanding balance = £" + String.format("%.2f", i.getFeeBalance()) + "\n");							
					}	
				}					
			}			
		}
	}
	
	/**
	 * Displays details of contractors, architects and customers
	 * @param personArray String array list for person object details
	 */
	private static void displayPerson(ArrayList<Person> personArray) {
		for (Person i: personArray) {
			System.out.println(
				"Person Identifier no: " + i.getPersonNum() 
				+ "\nPerson Type: " + i.getPersonType()
				+ "\nName: " + i.getPersonName() 
				+ "\nTel no.: " + i.getPersonTelNo() 
				+ "\nE-mail Address: " + i.getPersonEmail() 
				+ "\nAddress: " + i.getPersonAddress() + "\n");	
		}
	}
	
	/**
	 * Displays details of the projects
	 * @param projectArray String array list for project object details
	 */
	private static void displayProject(ArrayList<Project> projectArray) {
		for (Project i: projectArray) {
			System.out.println(
				"Project number: " + i.getProjNum() 
				+ "\nProject name: " + i.getProjName() 
				+ "\nBuilding Type: " + i.getBuildType() 
				+ "\nBuilding address: " + i.getProjAddress() 
				+ "\nERF number: " + i.getErfNum() 
				+ "\nTotal Fee: " + i.getTotalFee() 
				+ "\nFees paid to date: " + i.getPaidToDate() 
				+ "\nProject due date: " + i.getDueDate() 
				+ "\nProject status: " + i.getProjStatus()
				+ "\nCompletion Date: " + i.getComplDate()
				+ "\nContractor ID: " + i.getContractorID()
				+ "\nArchitect ID: " + i.getArchitectID() 
				+ "\nCustomer ID: " + i.getCustomerID() + "\n");	
		}	
	}
	
	/**
	 * @param personArray String array list for person object details
	 * @param projectArray String array list for project object details
	 * @param anyInput String variable for any input
	 */
	private static void viewProjects(ArrayList<Person> personArray, ArrayList<Project> projectArray, Scanner anyInput) {
		System.out.println("ap - View all projects\nip - View incomplete projects\nlp - View ongoing"
				+ " projects past duedate");
		String viewSelect = anyInput.nextLine();
		
		if (viewSelect.equalsIgnoreCase("ap")) {
			System.out.println("All projects and persons within the project management system.");
			displayProject(projectArray);
			displayPerson(personArray);
		}
		
		else if (viewSelect.equalsIgnoreCase("ip")) {
			displaysIncompProjects(projectArray);	
		}
		
		else if (viewSelect.equalsIgnoreCase("lp")) {
			displaysProjPastDue(projectArray);
		}
	}

	/**
	 * Displays all incomplete project by use of "Ongoing" in project status 
	 * @param projectArray String Array list of project objects
	 */
	private static void displaysIncompProjects(ArrayList<Project> projectArray) {
		System.out.println("Projects still to be completed:");

		for (Project i: projectArray) {
			if (i.getProjStatus().equals("Ongoing")) {	
				System.out.println("Project number: " + i.getProjNum() 
				+ "\nProject name: " + i.getProjName() 
				+ "\nBuilding Type: " + i.getBuildType() 
				+ "\nBuilding address: " + i.getProjAddress() 
				+ "\nERF number: " + i.getErfNum() 
				+ "\nTotal Fee: " + i.getTotalFee() 
				+ "\nFees paid to date: " + i.getPaidToDate() 
				+ "\nProject due date: " + i.getDueDate() 
				+ "\nCompletion Date: " + i.getComplDate()
				+ "\nContractor ID: " + i.getContractorID() 
				+ "\nArchitect ID: " + i.getArchitectID() 
				+ "\nCustomer ID: " + i.getCustomerID() + "\n");		
			}						
		}
	}
	
	/**
	 * Displays project that are ongoing and past due date
	 * @param projectArray String Array list of project objects
	 */
	private static void displaysProjPastDue(ArrayList<Project> projectArray) {
		System.out.println("Ongoing projects passed their due date.");
		
		try {
			for (Project i: projectArray) {	
				
				LocalDate dueDateFormat = LocalDate.parse(i.getDueDate(), DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate todaysDate = LocalDate.now();					
				
				if (dueDateFormat.isBefore(todaysDate) && i.getProjStatus().equals("Ongoing")) {
					System.out.println("Project number: " + i.getProjNum() 
					+ "\nProject name: " + i.getProjName() 
					+ "\nBuilding Type: " + i.getBuildType()
					+ "\nBuilding address: " + i.getProjAddress() 
					+ "\nERF number: " + i.getErfNum() 
					+ "\nTotal Fee: " + i.getTotalFee() 
					+ "\nFees paid to date: " + i.getPaidToDate()
					+ "\nProject due date: " + i.getDueDate() 
					+ "\nCompletetion Date: " + i.getComplDate()
					+ "\nContractor ID: " + i.getContractorID()
					+ "\nArchitect ID: " + i.getArchitectID() 
					+ "\nCustomer ID: " + i.getCustomerID() + "\n");								
				}							
			}
		}
		
		catch (DateTimeException e) {
			System.out.println("Date format does not match required. Ensure dates are entered in yyyy-MM-dd format.");
			System.out.println();
			System.out.println(e.getMessage());
			System.out.println();
			e.printStackTrace();
		}
	}		
}	
