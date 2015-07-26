 
public class Transaction {
	/**
	 * Embedded class transaction data storage
	 * @author user
	 *
	 */
	static class Log {
		static int transactionCount = 0;
		int id1;
		int id2;
		int sum;
		int transactionId;
		String type;
		String result;
		
		public Log(int id1, int id2, int sum, String type, String result) {
			setId1(id1);
			setId2(id2);
			setSum(sum);
			setType(type);
			setTransactionId(getTransactionCount());
			setTransactionCount(getTransactionCount() + 1);
			setResult(result);
		}

		public void setResult(String result) {
			this.result = result;
		}

		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}

		public void setType(String type) {
			this.type = type;
		}

		public static int getTransactionCount() {
			return transactionCount;
		}

		public void setTransactionCount(int transactionCount) {
			this.transactionCount = transactionCount;
		}
		
		public String getId1() {
			return "id1: " + id1 + ", ";
		}
		
		public void setId1(int id1) {
			this.id1 = id1;
		}
		
		public String getId2() {
			if (id2 == -1) return "";
			return "id2: " + id2 + ", ";
		}
		
		public void setId2(int id2) {
			this.id2 = id2;
		}
		
		/**
		 * Separate integer and fraction parts of balance
		 * @return
		 */
		public String getSum() {
			if (sum == -1) {
				return "";
			} else {
				int i = sum / 100; //int
				int f = sum - i * 100; //fraction
				return "sum: " + i + "." + f + ", ";
			}
		}
		
		public String getType() {
			return type + ", ";
		}
		
		public void setSum(int sum) {
			this.sum = sum;
		}
		
		public String getResult() {
			return "result: " + result;
		}
		
		public String getTransactionId() {
			return "Transaction id: " + transactionId + ", ";
		}

		public void print() {
			System.out.println(getTransactionId() + getId1() + getId2() + getSum() + getType() + getResult());
		}
	}
	
	Log log;
	
	public Transaction(int id1, int id2, int sum, String type, String result) {
		this.log = new Log(id1, id2, sum, type, result);
	}

	/**
	 * Deposit transaction
	 * @param bank
	 * @param id
	 * @param sum
	 */
	public static void deposit(Bank bank, int id, int sum) {
		System.out.println(">>> Deposit: id " + id + ", sum " + sum);
		Account current = bank.accountById(id);
		if (current.getId() != -1) {
			if (current.isStatus()) {
				current.setBalance(current.getBalance() + sum);
				current.addTransaction(id, -1, sum, "deposit", "done");
			} else {
				System.out.println("Account id " + id + " is closed");
				current.addTransaction(id, -1, sum, "deposit", "rejected, account is closed");
			}
		}
		else System.out.println("Id " + id + " not found");
	}
	
	/**
	 * Withdraw transaction
	 * @param bank
	 * @param id
	 * @param sum
	 */
	public static void withdraw(Bank bank, int id, int sum) {
		System.out.println(">>> Withdraw: id " + id + ", sum " + sum);
		Account current = bank.accountById(id);
		if (current.getId() != -1) {
			if (current.isStatus()) {
				if (sum >= 0) {
					if (current.getBalance() >= sum) {
						current.setBalance(current.getBalance() - sum);
						current.addTransaction(id, -1, sum, "withdrow", "done");
					} else current.addTransaction(id, -1, sum, "withdrow", "rejected, not enough money");
				} else current.addTransaction(id, -1, sum, "withdrow", "rejected, summ is negative");
			} else {
				System.out.println("Account id " + id + " is closed");
				current.addTransaction(id, -1, sum, "withdraw", "rejected, account is closed");
			}
		}
		else System.out.println("Id " + id + " not found");
	}
	
	/**
	 * Transfer transaction
	 * @param bank
	 * @param id1
	 * @param id2
	 * @param sum
	 */
	public static void transfer(Bank bank, int id1, int id2, int sum) {
		System.out.println(">>> Transfer: id1 " + id1 + ", id2 " + id2 + ", sum " + sum);
		Account current1 = bank.accountById(id1);
		Account current2 = bank.accountById(id2);
		String err;
		if (current1.getId() == -1) {
			err = "rejected, id1 not found";
			current1.addTransaction(id1, id2, sum, "transfer", err);
			current2.addTransaction(id1, id2, sum, "transfer", err);
			bank.addTransaction(id1, id2, sum, "transfer", err);
		} else {
			if (current2.getId() == -1) {
				err = "rejected, id2 not found";
				current1.addTransaction(id1, id2, sum, "transfer", err);
				current2.addTransaction(id1, id2, sum, "transfer", err);
				bank.addTransaction(id1, id2, sum, "transfer", err);
			} else {
				if ((current1.getId() == -1) && (current2.getId() == -1)) {
					err = "rejected, id1 and id2 not found";
					current1.addTransaction(id1, id2, sum, "transfer", err);
					current2.addTransaction(id1, id2, sum, "transfer", err);
					bank.addTransaction(id1, id2, sum, "transfer", err);
				} else {
					if (!current1.isStatus()) {
						err = "rejected, id1 is closed";
						current1.addTransaction(id1, id2, sum, "transfer", err);
						current2.addTransaction(id1, id2, sum, "transfer", err);
						bank.addTransaction(id1, id2, sum, "transfer", err);
					} else {
						if (!current2.isStatus()) {
							err = "rejected, id2 is closed";
							current1.addTransaction(id1, id2, sum, "transfer", err);
							current2.addTransaction(id1, id2, sum, "transfer", err);
							bank.addTransaction(id1, id2, sum, "transfer", err);
						} else {
							if ((!current1.isStatus()) && (!current2.isStatus())) {
								err = "rejected, id1 and id2 is closed";
								current1.addTransaction(id1, id2, sum, "transfer", err);
								current2.addTransaction(id1, id2, sum, "transfer", err);
								bank.addTransaction(id1, id2, sum, "transfer", err);
							} else {
								if (sum < 0) {
									err = "rejected, sum < 0";
									current1.addTransaction(id1, id2, sum, "transfer", err);
									current2.addTransaction(id1, id2, sum, "transfer", err);
									bank.addTransaction(id1, id2, sum, "transfer", err);
								} else {
									if (current1.getBalance() < sum) {
										err = "rejected, balance id1 < sum";
										current1.addTransaction(id1, id2, sum, "transfer", err);
										current2.addTransaction(id1, id2, sum, "transfer", err);
										bank.addTransaction(id1, id2, sum, "transfer", err);
									} else {
										current1.setBalance(current1.getBalance() - sum);
										current2.setBalance(current2.getBalance() + sum);
										current1.addTransaction(id1, id2, sum, "transfer", "done");
										current2.addTransaction(id1, id2, sum, "transfer", "done");
										bank.addTransaction(id1, id2, sum, "transfer", "done");
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Accrued interest on the day all accounts
	 * @param bank
	 */
	public static void changeDay(Bank bank) {
		System.out.println(">>> Change day");
		for (int i = 0; i < Bank.BRANCH; i ++) {
			bank.branches[i].changeDay();
		}
	}

	/**
	 * Transaction report by id
	 * @param bank
	 * @param id
	 */
	public static void reportId(Bank bank, int id) {
		System.out.println(">>> All transaction by the months of a user with id " + id);
		bank.reportIdTransaction(id);
	}
	
	/**
	 * Transaction report by branch id
	 * @param bank
	 * @param branchId
	 */
	public static void reportBranch(Bank bank, int branchId) {
		System.out.println(">>> All transaction by branch #" + branchId);
		bank.branches[branchId].reportBranchTransaction();
	}
	
	/**
	 * Transaction report of all bank
	 * @param bank
	 */
	public static void reportBank(Bank bank) {
		System.out.println(">>> All transaction of the bank");
		for (int i = 0; i < Bank.BRANCH; i++) {
			reportBranch(bank, i);
		}
		reportInterBranch(bank);
	}
	
	/**
	 * Change account type
	 * @param bank
	 * @param id
	 * @param type
	 */
	public static void changeAccountType(Bank bank, int id, char type) {
		System.out.println(">>> Change type of id " + id + " to \"" + type + "\"");
		Account current = bank.accountById(id);
		if (current.getId() == -1) System.out.println("Id not found");
		else {
			switch (type) {
				case 'b':
					if (current.getBalance() >= Branch.TYPEB * 100) {
						current.setType('b');
						current.addTransaction(id, -1, -1, "change type to \'b\'", "done");
					} else current.addTransaction(id, -1, -1, "change type to \'b\'", "reject, not enough money");
					break;
				case 's':
					if (current.getBalance() >= Branch.TYPES * 100) {
						current.setType('s');
						current.addTransaction(id, -1, -1, "change type to \'s\'", "done");
					} else current.addTransaction(id, -1, -1, "change type to \'s\'", "reject, not enough money");
					break;
				case 'c':
					if (current.getBalance() >= Branch.TYPEC * 100) {
						current.setType('c');
						current.addTransaction(id, -1, -1, "change type to \'c\'", "done");
					} else current.addTransaction(id, -1, -1, "change type to \'c\'", "reject, not enough money");
					break;
				default:
					System.out.println("Incorrect type");
					current.addTransaction(id, -1, -1, "change type", "reject, incorrect type");
					break;
			}
		}
	}
	
	/**
	 * Report of inter-branch transaction
	 * @param bank
	 */
	public static void reportInterBranch(Bank bank) {
		System.out.println(">>> All inter-branch transactions");
		bank.reportInterBranch();
	}
}
