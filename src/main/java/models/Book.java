package models;


import java.sql.Date;

public class Book {
	private int id;
	private String name;
	private String author;
	private String publisher;
    private Date pubDate;
    private String subject;
	public Book(int id, String name, String author, String publisher, Date pubDate, String subject) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.subject = subject;
	}
	public Book(String name, String author, String publisher, Date pubDate, String subject) {
		super();
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.subject = subject;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
    
    
	
}
