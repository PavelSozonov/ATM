public class Branch {

	private String manager;
	private String address;
	private String phone;
	private String hours;
	
	static final int TYPEB = 5000000;
	static final int TYPES = 50000;
	static final int TYPEC = 1000;
	static final double DAYPERCENTB = 20.0 / 30;
	static final double DAYPERCENTS = 10.0 / 30;
	static final double DAYPERCENTC = 5.0 / 30;
	int index = 0; //Index of last free element in array customers
	Account[] customers = new Account[100];
	int branchId;

	/**
	 * Print all transaction of this branch
	 */
	public void reportBranchTransaction() {
		for (int i = 0; i < index; i++) {
			customers[i].reportAccountTransaction();
		}
	}
	
	public Branch(String manager, String address, String phone, String hours) {
		System.out.println(">>> New branch was created");
		this.manager = manager;
		this.address = address;
		this.phone = phone;
		this.hours = hours;
	}
	
	/**
	 * Print information about branch
	 */
	public void branchInfo() {
		System.out.println("Manager: " + getManager() + ", address: " + getAddress() + ", phone: " + getPhone() + ", hours: " + getHours());
	}
	
	public String getManager() {
		return manager;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getHours() {
		return hours;
	}

	/**
	 * Accrued interest on the day all accounts
	 */
	public void changeDay() {
		if (index != 0) {
			for (int i = 0; i < index; i++) {
				char type = customers[i].getType();
				double percent;
				switch (type) {
					case 'b':
						percent = DAYPERCENTB;
						break;
					case 's':
						percent = DAYPERCENTS;
						break;
					case 'c':
						percent = DAYPERCENTC;
						break;
					default:
						percent = 0;
						System.out.println("Inccorrect account type");
						break;
				}
				if (!customers[i].isStatus()) {
					customers[i].addTransaction(customers[i].getId(), -1, -1, "interest", "rejected, account is closed");
				} else {
					if (customers[i].getBalance() < 0) {
						customers[i].addTransaction(customers[i].getId(), -1, -1, "interest", "rejected, negative balance");
					}
					else {
						double sum = customers[i].getBalance() * percent / 100;
						customers[i].setBalance(customers[i].getBalance() + (int)sum);
						customers[i].addTransaction(customers[i].getId(), -1, (int)sum, "interest", "done");
					}
				}
			}
		}
	}
	
	/**
	 * Verification of the existence id
	 * @param id
	 * @return
	 */
	public int checkId(int id) {
		for (int i = 0; i < index; i++) {
			if (customers[i].getId() == id) return i;
		}
		return -1;
	}
	
	/**
	 * Return account by id from array customers
	 * @param i
	 * @return
	 */
	public Account getCustomer(int i) {
		return customers[i];
	}
	
	/**
	 * Print information about of accounts of this branch
	 */
	public void report() {
		for (int i = 0; i < index; i++) {
			if (customers[i].isStatus()) {
				System.out.println(i
						+ ") id: " + customers[i].getId() 
						+ ", name: " + customers[i].getFirstName()
						+ " " + customers[i].getLastName()
						+ ", birthday: " + customers[i].getBirthday()
						+ ", type: \'" + customers[i].getType() + "\'"
						+ ", balance: " + customers[i].getBalance());
			}
		}
	}
	
	/**
	 * Print information about account by id
	 * @param id
	 * @return
	 */
	public boolean reportId(int id) {
		for (int j = 0; j < index; j++) {
			if (customers[j].getId() == id) {
				if (customers[j].isStatus()) {
					int sum = customers[j].getBalance();
					int i = sum / 100; //int
					int f = sum - i * 100; //fraction
					String s = i + "." + f + ", ";

					System.out.println("id: " + customers[j].getId() 
						+ ", name: " + customers[j].getFirstName()
						+ " " + customers[j].getLastName()
						+ ", birthday: " + customers[j].getBirthday()
						+ ", type: \'" + customers[j].getType() + "\'"
						+ ", balance: " + s);
				} else System.out.println("Account with id " + id + " is closed");
				return true;
			}
		}
		return false;
	}

	/**
	 * Open new account
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param patronym
	 * @param password
	 * @param birth
	 * @param passport
	 * @param type
	 * @param sex
	 */
	public void openAcc(int id, String firstName, String lastName, String patronym, String password,
			String birth, int passport, char type, char sex) {
		int balance;
		switch (type) {
			case 'b':
				balance = TYPEB * 100;
				break;
			case 's':
				balance = TYPES * 100;
				break;
			case 'c':
				balance = TYPEC * 100;
				break;
			default:
				System.out.println("Incorrect type \'" + type + "\', it was changed to \'c\', name: " + firstName + " " + lastName);
				type = 'c';
				balance = TYPEC;
				break;
		}
		customers[index] = new Account(id, firstName, lastName, patronym, password, birth, passport, balance, type, sex);
		index++;
	}
	
	/**
	 * Close account
	 * @param id
	 * @return
	 */
	public boolean closeAcc(int id) {
		for (int i = 0; i < index; i++) {
			if (customers[i].getId() == id) {
				if (customers[i].isStatus() == true) {
					customers[i].setStatus(false);
					return true;
				}
				else {
					System.out.println("Account was closed earlier");
					return false;
				}
			}
		}
		return false;
	}
}
