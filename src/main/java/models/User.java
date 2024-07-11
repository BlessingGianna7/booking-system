package models;

public class User {
	private int id;
	private String fName;
	private String lName;
	private String account;
    private String email;
    private String password;
	public User(int id, String fName, String lName, String account, String email, String password) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.account = account;
		this.email = email;
		this.password = password;
	}
	public User(String fName, String lName, String account, String email, String password) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.account = account;
		this.email = email;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
