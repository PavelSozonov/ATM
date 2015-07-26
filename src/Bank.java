
public class Bank {

	final static int BRANCH = 5; // Count of branches
	private static int counterId = 0; // Counter of unique id
	public Branch[] branches = new Branch[BRANCH]; // All branches of the bank
	private Transaction[] transactions = new Transaction[100];
	private int transactionCount = 0;
	
	public Bank() {
		System.out.println(">>> New bank was created");
	}
	
	/**
	 * Add information record about inter-branch transaction
	 * @param id1
	 * @param id2
	 * @param sum
	 * @param type
	 * @param result
	 */
	public void addTransaction(int id1, int id2, int sum, String type, String result) {
		transactions[getTransactionCount()] = new Transaction(id1, id2, sum, type, result);
		setTransactionCount(getTransactionCount() + 1);
	}
	
	/**
	 * Print information about all transaction between branches
	 */
	public void reportInterBranch() {
		if (getTransactionCount() == 0) System.out.println("No inter-brunch transactions");
		else {
			for (int i = 0; i < getTransactionCount(); i++) {
				transactions[i].log.print();
			}
		}
	}
	
	public int getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}

	/**
	 * Print information about all users
	 */
	public void report() {
		System.out.println(">>> Accounts report");
		for (int i = 0; i < BRANCH; i++) {
			System.out.println("Branch #" + i + ":");
			branches[i].report();
		}
	}
	
	/**
	 * Print information about transaction of account with id
	 * @param id
	 */
	public void reportIdTransaction(int id) {
		Account current = accountById(id);
		if (current.getId() != -1) {
			current.reportIdTransaction();
		} else System.out.println("Id not found");
	}

	/**
	 * Print information about account with id
	 * @param id
	 */
	public void reportId(int id) {
		System.out.println(">>> Account id " + id + " report");
		boolean flag = true;
		for (int i = 0; i < BRANCH; i++) {
			if (branches[i].reportId(id)) flag = false;
		}
		if (flag) System.out.println("Id not found");
	}
	
	/**
	 * Return account by id, through all branches
	 * @param id
	 * @return
	 */
	public Account accountById(int id) {
		Account customer = new Account(-1, "", "", "", "", "", 0, 0, 'b', 'm');
		boolean flag = true;
		for (int i = 0; i < BRANCH; i++) {
			int check = branches[i].checkId(id);
			if (check > -1) {
				flag = false;
				customer = branches[i].getCustomer(check);
			}
		}
		if (flag) System.out.println("Id " + id + " not found, possible account has not been created");
		return customer;
	}

	/**
	 * Open new account
	 * id - auto increment
	 * balance - depending of type
	 * @param branchId
	 * @param firstName
	 * @param lastName
	 * @param patronym
	 * @param password
	 * @param birth
	 * @param passport
	 * @param type
	 * @param sex
	 */
	public void openAcc(int branchId, String firstName, String lastName, String patronym, 
			String password, String birth, int passport, char type, char sex) 
	{
		System.out.println(">>> New account was created");
		if (branchId > BRANCH) {
			System.out.println("Excited branch number, branch " + branchId + " was changed to 0");
			branchId = 0;
		}
		else {
			branches[branchId].openAcc(counterId, firstName, lastName, patronym, password, birth, passport, type, sex);
			counterId++;
		}
	}
	
	/**
	 * Close account
	 * @param id
	 */
	public void closeAcc(int id) {
		boolean flag = false;
		String branch = "";
		for (int i = 0; i < BRANCH; i ++) {
			if (branches[i].closeAcc(id)) {
				flag = true;
				branch = "Account closed in branch " + i;
			}
		}
		if (!flag) System.out.println("Account with id " + id + " not found");
		else {
			System.out.println(">>> Account with id " + id + " was closed" );
			System.out.println(branch);
		}
	}
}
