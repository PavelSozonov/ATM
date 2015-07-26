
public class Base {
	/**
	 * Initialization bank, branch, accounts	
	 * @param args
	 */
	public static void main(String[] args) {
		//Create bank
		Bank bank = new Bank();
		//Create branches (manager, address, phone, business hours)
		bank.branches[0] = new Branch("Manager Ivan",   "Kazan, st. Lenina, 1",   "+7-919-111-1111", "8:00-20:00");
		bank.branches[1] = new Branch("Manager Pavel",  "Izhevsk, st. Lenina, 2", "+7-919-222-2222", "8:00-20:00");
		bank.branches[2] = new Branch("Manager Stepan", "Moscow, st. Lenina, 3",  "+7-919-333-3333", "8:00-20:00");
		bank.branches[3] = new Branch("Manager Egor",   "Samara, st. Lenina,4",   "+7-919-444-4444", "8:00-20:00");
		bank.branches[4] = new Branch("Manager Oleg",   "Ufa, st. Lenina, 5",     "+7-919-123-4567", "8:00-20:00");
		//Create accounts (branch id, first name, second name, patronym, password, birth date, passport, type of account, sex)
		bank.openAcc(3, "Pavel", "Sozonov",  "Sergeevich", "pass1", "26.04.1989", 100111, 'c', 'm');
		bank.openAcc(3, "Elena", "Steklova", "Sergevna",   "pass5", "16.12.1989", 100555, 'r', 'f');
		bank.openAcc(4, "Ivan",  "Stepanov", "Petrovich",  "pass2", "23.03.1977", 100222, 'c', 'm');
		bank.openAcc(3, "Olga",  "Petrova",  "Egorovna",   "pass3", "12.02.1978", 100333, 's', 'f');
		bank.openAcc(0, "Elena", "Ivanova",  "Ivanovna",   "pass4", "01.10.1990", 100444, 'b', 'f');
		//bank.report(); //Show information about all customers in all branches 
		bank.closeAcc(1);
		bank.reportId(1);
		bank.reportId(0);
		Transaction.withdraw(bank, 0, 300);
		Transaction.transfer(bank, 1, 2, 100);
		Transaction.deposit(bank, 0, 10000);
		Transaction.deposit(bank, 1, 500); 
		Transaction.reportBranch(bank, 3);
		Transaction.reportId(bank, 0);
		Transaction.changeAccountType(bank, 3, 'c');
		bank.reportId(0);
		bank.reportId(4);
		Transaction.transfer(bank, 0, 4, 1023);
		Transaction.transfer(bank, 1, 2, 2023);
		bank.reportId(0);
		bank.reportId(4);
		Transaction.reportInterBranch(bank);
		Transaction.reportBank(bank);
		Transaction.changeAccountType(bank, 0, 'b');
		Transaction.reportId(bank, 0);
		Transaction.changeDay(bank);
		Transaction.reportBank(bank);
	}
}
