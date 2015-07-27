
public class Account {
	private String firstName;
	private String lastName;
	private String patronym;
	private String password;
	private String birthday;
	private int passport;
	private int id;
	private int balance; //converted to fraction
	private char type; //b, s, c
	private char sex; //m - male, f - female
	private boolean status; 
	private Transaction[] transactions = new Transaction[100];
	private int transactionCount = 0;
	private int branch;
	
	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public void reportAccountTransaction() {
		for (int i = 0; i < getTransactionCount(); i++) {
			transactions[i].log.print(); 
		}
	}
	
	/**
	 * Print all transactions of this account
	 */
	public void reportIdTransaction() {
		if (getTransactionCount() == 0) System.out.println("No transactions");
		else {
			for (int i = 0; i < getTransactionCount(); i++) {
				transactions[i].log.print();
			}
		}
	}

	/**
	 * Add information record about transaction
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
	
	public int getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}

	public Account(int id, String firstName, String lastName, String patronym, String password, String birthday, int passport, int balance, char type, char sex) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronym = patronym;
		this.password = password;
		this.birthday = birthday;
		this.passport = passport;
		this.id = id;
		this.balance = balance;
		this.type = type;
		this.sex = sex;
		this.status = true;
	}
	
	public int getBalance() {
		return balance;
	}

	public int getId() {
		return id;
	}

	public void setType(char type) {
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setBalance(int balance) {
		if (balance < 0) System.out.println("Set balance < 0");
		this.balance = balance;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getPatronym() {
		return patronym;
	}

	public String getBirthday() {
		return birthday;
	}

	public char getType() {
		return type;
	}
}
