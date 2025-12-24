package Mini_Project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//Design a console-based Bank Management System in Java that allows a user to 
//create a bank account and perform basic banking operations such as deposit, 
//withdrawal, balance enquiry, and account details display. The data should be stored 
//in file. Use the concept of class, object, constructor, hierarchical inheritance, 
//encapsulation (access specifiers), polymorphism (method overloading, method 
//overriding, run time polymorphism) and abstraction (abstract class, abstract method)


abstract class BankAccount{
	protected int accountNumber;
	protected String name;
	protected double balance;
	
	public BankAccount(int accountNumber, String name, double balance) {
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void deposit(double amount) {
		balance += amount;
		System.out.println("Amount deposited successfully.");
	}
	
	public void deposite(double amount, String note) {
		balance += amount;
		System.out.println("Amount deposited. Note: " + note);
	}
	
	public abstract void withdraw(double amount);
	
	public void displayDetails() {
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Name: " + name);
		System.out.println("balance: $" + balance);
	}
}

class SavingsAccount extends BankAccount{
	public SavingsAccount(int accNo, String name, double balance) {
		super(accNo, name, balance);
	}
	
	@Override
	public void withdraw(double amount) {
		if(balance - amount >= 500) {
			balance -= amount;
			System.out.println("Withdrawal successful.");
		}else {
			System.out.println("Minimal balance of $500 required.");
		}
	}
}

class CurrentAccount extends BankAccount{
	public CurrentAccount(int accNo, String name, double balance) {
		super(accNo, name, balance);
	}
	
	@Override
	public void withdraw(double amount) {
		if(amount <= balance) {
			balance -= amount;
			System.out.println("Withdrawal Successful.");
		}else {
			System.out.println("Insufficient balance.");
		}
	}
}

public class BankApp {
	
	static final String FILE_NAME = "accounts.txt";
	
	public static void saveToFile(BankAccount acc) {
		try(FileWriter fw = new FileWriter(FILE_NAME, true)){
			fw.write(acc.accountNumber +  ","+ acc.name + "," + acc.balance+ "\n");
		}catch(IOException e) {
			System.out.println("File Error!");
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BankAccount account = null;
		
		while(true) {
			System.out.println("\n-----Bank Management System-------");
			System.out.println("1. Create Account");
			System.out.println("2. Deposite");
			System.out.println("3. Withdraw");
			System.out.println("4. Balance Enquiry");
			System.out.println("5. Display Account Details");
			System.out.println("6. Exit");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			
			switch(choice) {
			
				case 1:
					System.out.println("Enter Account Number: ");
					int accNo = sc.nextInt();
					sc.nextLine();
					
					System.out.println("Enter Name: ");
					String name = sc.nextLine();
					
					System.out.println("Enter initial balance: ");
					double bal = sc.nextDouble();
					
					System.out.println("1. Savings 2. Current : ");
					int type = sc.nextInt();
					
					
					if(type == 1) {
						account = new SavingsAccount(accNo, name, bal);
					}else {
						account = new CurrentAccount(accNo, name, bal);
					}
					
					saveToFile(account);
					System.out.println("Account created successfully. ");
					break;
					
				case 2:
					if(account != null) {
						System.out.println("Enter amount to deposit: ");
						double amt = sc.nextDouble();
						account.deposit(amt);
					}else {
						System.out.println("Create account first.");
					}
					
					break;
					
				case 3:
					if(account != null) {
						System.out.println("Enter amount to withdraw: ");
						double wAmt = sc.nextDouble();
						account.withdraw(wAmt);
					}else {
						System.out.println("Create account first.");
					}
					
					break;
					
				case 4:
					if(account != null) {
						System.out.println("Balance: $" + account.getBalance());
					}else {
						System.out.println("Create account first. ");
					}
					break;
					
				case 5:
					if(account != null) {
						account.displayDetails();
					}else {
						System.out.println("Create account first. ");
					}
					break;
					
				case 6:
					System.out.println("Thank you for using Bank System.");
					sc.close();
					System.exit(0);
					
				default:
					System.out.println("Invalid choice! ");
			}
		}
	}
}
