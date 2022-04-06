/*
 *author Muhammed Enes Onal
 *@since 25.03.2022
 */

import java.util.ArrayList;
import java.util.Random;

public class Assignment02_20170808007 {
    public static void main(String[] args) {
        Bank b = new Bank("My Bank", "My Bank's Address");
        b.addCompany(1, "Company 1");
        b.getCompany(1).openAccount("1234", 0.05);
        b.addAccount(b.getCompany(1).getAccount("1234"));
        b.getAccount("1234").deposit(500000);
        b.getCompany(1).getAccount("1234").deposit(500000);
        b.getCompany(1).openAccount("1235", 0.03);
        b.addAccount(b.getCompany(1).getAccount("1235"));
        b.getCompany(1).getAccount("1235").deposit(25000);
        b.addCompany(2, "Company 2");
        b.getCompany(2).openAccount("2345", 0.03);
        b.addAccount(b.getCompany(2).getAccount("2345"));
        b.getCompany(2).getAccount("2345").deposit(350);
        b.addCustomer(1, "Customer", "1");
        b.addCustomer(2, "Customer" ,"2");
        Customer c = b.getCustomer(1);
        c.openAccount("3456");
        c.openAccount("3457");
        c.getAccount("3456").deposit(150);
        c.getAccount("3457").deposit(250);
        c = b.getCustomer(2);c.openAccount("4567");
        c.getAccount("4567").deposit(1000);
        b.addAccount(c.getAccount("4567"));
        c = b.getCustomer(1);
        b.addAccount(c.getAccount("3456"));
        b.addAccount(c.getAccount("3457"));
        System.out.println(b.toString());

    }

}

class Bank{
    private String name;
    private String address;

    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Company> companies = new ArrayList<>();
    ArrayList<Account> accounts = new ArrayList<>();

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addCustomer(int id, String name, String surname){
        Customer c = new Customer(name, surname, id);
        customers.add(c);
    }

    public void addCompany(int id, String name){
        Company cp = new Company(name, id);
        companies.add(cp);
    }

    public void addAccount(Account account){
        if(accounts.contains(account)){
            System.out.println("Account still available in list.");
        }else{
            accounts.add(account);
        }
    }

    public Customer getCustomer(int id) throws CustomerNotFoundException{
        for(int i=0; i<customers.size(); i++){
            if(customers.get(i).getId() == id){
                return customers.get(i);
            }
        }
        throw new CustomerNotFoundException(id);
    }

    public Customer getCustomer(String name, String surname) throws CustomerNotFoundException{
        for(int i=0; i<customers.size(); i++){
            if(customers.get(i).getName() == name  && customers.get(i).getSurname() == surname){
                return customers.get(i);
            }
        }
        throw new CustomerNotFoundException(name, surname);
    }

    public Company getCompany(int id) throws CompanyNotFoundException{
        for(int i=0; i<companies.size(); i++){
            if(companies.get(i).getId() == id){
                return companies.get(i);
            }
        }
        throw new CompanyNotFoundException(id);
    }

    public Company getCompany(String name) throws CompanyNotFoundException{
        for(int i=0; i<companies.size(); i++){
            if(companies.get(i).getName() == name){
                return companies.get(i);
            }
        }
        throw new CompanyNotFoundException(name);
    }

    public Account getAccount(String accountNum) throws AccountNotFoundException{
        for(int i=0; i<accounts.size(); i++){
            if(accounts.get(i).getAcctNum() == accountNum){
                return accounts.get(i);
            }
        }
        throw new AccountNotFoundException();
    }

    @Override
    public String toString() {
        String summary = name + " " + address;
        for(Company cmp1 : companies){
            summary = summary + "\n" + "  " + cmp1.getName();
            for(BusinessAccount ba1 : cmp1.businessAcct){
                summary = summary + "\n" + "     " + ba1.getAcctNum() +
                        " " + ba1.getRate() + " " + ba1.getBalance();
            }
        }

        for(Customer ctm1 : customers){
            summary = summary + "\n" + "  " + ctm1.getName() + ctm1.getId();
            for(PersonalAccount pa1 : ctm1.personAcct){
                summary = summary + "\n" + "     " + pa1.getAcctNum() +
                        " " + pa1.getBalance();
            }
        }
        return summary;
    }
}

class Account {

    String acctNum;
    double balance;

    public Account(String acctNum) {
        this.acctNum = acctNum;
        this.balance = 0;
    }

    public Account(String acctNum, double balance) {
        this.acctNum = acctNum;
        if (balance < 0) {
            this.balance = 0;
        }else{
            this.balance = balance;
        }
    }

    public String getAcctNum() {
        return acctNum;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidAmountException{
        if (amount < 0) {
            throw  new InvalidAmountException();
        } else {
            balance = balance + amount;
        }
    }

    public void withdrawal(double amount) {
        if (amount < 0 && this.balance < amount) {
            throw new InvalidAmountException();
        }else{
            balance = balance - amount;
        }
    }


    @Override
    public String toString() {
        return "Account{" +
                "acctNum='" + acctNum + '\'' +
                "has balance=" + balance +
                '}';
    }

}

class PersonalAccount extends Account {
    String name;
    String surname;
    String pin;

    Random rndm = new Random();
    int a1 = rndm.nextInt(10000);

    public PersonalAccount(String acctNum, String name, String surname) {
        super(acctNum);
        this.name = name;
        this.surname = surname;
        this.pin = String.format("%04d%n", a1);
    }

    public PersonalAccount(String acctNum, String name, String surname, double balance) {
        super(acctNum, balance);
        this.name = name;
        this.surname = surname;
        this.pin = String.format("%04d%n", a1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "PersonalAccount " +
                "acctNum= " + acctNum + " belonging to " +
                name + " " + surname.toUpperCase() + " has " +  balance;
    }
}

class BusinessAccount extends Account {
    double rate;

    public BusinessAccount(String acctNum, double rate) {
        super(acctNum);
        this.rate = rate;
    }

    public BusinessAccount(String acctNum, double balance, double rate) {
        super(acctNum, balance);
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double calculateInterest() {
        return getBalance() * getRate();
    }

}

class Customer {
    String name;
    String surname;
    int id;
    private PersonalAccount pa1;
    ArrayList<PersonalAccount> personAcct = new ArrayList<>();


    public Customer(String name, String surname, int id) {
        this.name = name;
        this.surname = surname;
        if(id >= 0){
            this.id = id;
        }else{
            System.out.println("ID must be positive.");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void openAccount(String acctNum) {
        pa1 = new PersonalAccount(acctNum, name, surname);
        personAcct.add(pa1);
    }

    public PersonalAccount getAccount(String accountNum) throws AccountNotFoundException{
        for(int i=0; i<personAcct.size(); i++){
            if(personAcct.get(i).getAcctNum() == accountNum){
                return personAcct.get(i);
            }
        }
        throw new AccountNotFoundException();
    }

    public void closeAccount(String accountNum) throws AccountNotFoundException{
        for(int i=0; i<personAcct.size(); i++){
            if(personAcct.get(i).getAcctNum() == accountNum){
                if(personAcct.get(i).getBalance() > 0){
                    throw new BalanceRemainingException();
                }
            }
        }
        throw new AccountNotFoundException();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname.toUpperCase() + '\'' +
                '}';
    }
}

class Company{
    int id;
    String name;
    private BusinessAccount ba1;
    ArrayList<BusinessAccount> businessAcct = new ArrayList<>();

    public Company(String name, int id) {
        if(id >= 0){
            this.id = id;
        }else{
            System.out.println("ID must be positive.");
        }
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void openAccount(String acctNum, double rate){
        ba1 = new BusinessAccount(acctNum, rate);
        businessAcct.add(ba1);
    }

    public BusinessAccount getAccount(String acctNum)throws AccountNotFoundException{
        for(int i=0; i<businessAcct.size(); i++){
            if(businessAcct.get(i).getAcctNum() == acctNum){
                return businessAcct.get(i);
            }
        }
        throw new AccountNotFoundException();
    }

    public void closeAccount(String accountNum){
        for(int i=0; i<businessAcct.size(); i++){
            if(businessAcct.get(i).getAcctNum() == accountNum){
                if(businessAcct.get(i).getBalance() > 0){
                    throw new BalanceRemainingException();
                }
            }
        }
        throw new AccountNotFoundException();
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name +"'}";
    }
}

class CustomerNotFoundException extends RuntimeException{
    private int id;
    private String name;
    private String surname;

    public CustomerNotFoundException(int id){
        this.id = id;
        this.name = null;
        this.surname = null;
    }

    public CustomerNotFoundException(String name, String surname){
        this.name = name;
        this.surname = surname;

        Random rndm = new Random();
        this.id = rndm.nextInt(10000);
    }

    @Override
    public String toString() {
        if(name != null && surname != null){
            return "CustomerNotFoundException: name-" + name + ""
                    + surname;
        }else{
            return "CustomerNotFoundException: id-" + id;
        }
    }
}

class CompanyNotFoundException extends RuntimeException{
    private int id;
    private String name;

    public CompanyNotFoundException(int id){
        this.id = id;
        this.name = null;
    }

    public CompanyNotFoundException(String name){
        this.name = name;

        Random rndm = new Random();
        this.id = rndm.nextInt(10000);
    }

    @Override
    public String toString() {
        if(name != null){
            return "CompanyNotFoundException: name-" + name;
        }else{
            return "CompanyNotFoundException: id-" + id;
        }
    }
}

class AccountNotFoundException extends RuntimeException{
    private String acctNUm;

    @Override
    public String toString() {
        return "AccountNotFoundException{" +
                "acctNUm='" + acctNUm + '\'' +
                '}';
    }
}

class InvalidAmountException extends RuntimeException{
    private double amount;

    @Override
    public String toString() {
        return "InvalidAmountException{" +
                "amount=" + amount +
                '}';
    }
}

class BalanceRemainingException extends RuntimeException{
    private double balance;

    @Override
    public String toString() {
        return "BalanceRemainingException{" +
                "balance=" + balance +
                '}';
    }

    public double getBalance() {
        return balance;
    }
}

