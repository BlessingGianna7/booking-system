package models;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
	private String dburl = "jdbc:mysql://localhost:3306/library";
	private String username = "root";
	private String password = "kayumba@";
	private String dbdriver = "com.mysql.cj.jdbc.Driver";
	private static final String INSERT_BOOK_SQL = "INSERT INTO library.books (name, author, publisher, pubDate, subject) VALUES (?, ?, ?, ?, ?);";
	private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM library.books;";
	private static final String DELETE_BOOK_SQL = "DELETE FROM library.books WHERE id = ?";
	private static final String GET_BOOK_SQL = "SELECT * FROM library.books WHERE id = ?";
	private static final String UPDATE_BOOK_SQL = "UPDATE library.books SET name = ?, author = ?, publisher = ?, pubDate = ?, subject = ? WHERE id = ?";
	private static final String SEARCH_BOOKS_BY_NAME_SQL = "SELECT * FROM library.books WHERE name LIKE ?";

	public Connection getConnection() throws SQLException {
		try {
			Class.forName(dbdriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = DriverManager.getConnection(dburl, username, password);

		// Create the table if it doesn't exist
		createBookTableIfNotExists(connection);

		return connection;
	}

	private void createBookTableIfNotExists(Connection connection) {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS library.books (" +
				"id INT AUTO_INCREMENT PRIMARY KEY," +
				"name VARCHAR(255) NOT NULL," +
				"author VARCHAR(255) NOT NULL," +
				"publisher VARCHAR(255) NOT NULL," +
				"pubDate DATE NOT NULL," +
				"subject VARCHAR(255) NOT NULL)";

		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(createTableSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String insert(Book book) {
		try (Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(INSERT_BOOK_SQL)) {

			stm.setString(1, book.getName());
			stm.setString(2, book.getAuthor());
			stm.setString(3, book.getPublisher());
			stm.setDate(4, book.getPubDate());
			stm.setString(5, book.getSubject());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Data not added!";
		}
		return "Successfully added!";
	}

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(SELECT_ALL_BOOKS_SQL);
				ResultSet res = stm.executeQuery()) {

			while (res.next()) {
				int id = res.getInt("id");
				String name = res.getString("name");
				String author = res.getString("author");
				String publisher = res.getString("publisher");
				Date pubDate = res.getDate("pubDate");
				String subject = res.getString("subject");
				Book book = new Book(id, name, author, publisher, pubDate, subject);
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	public Book getBook(int bookId) {
		try (Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(GET_BOOK_SQL)) {

			stm.setInt(1, bookId);
			try (ResultSet res = stm.executeQuery()) {
				if (res.next()) {
					int id = res.getInt("id");
					String name = res.getString("name");
					String author = res.getString("author");
					String publisher = res.getString("publisher");
					Date pubDate = res.getDate("pubDate");
					String subject = res.getString("subject");
					return new Book(id, name, author, publisher, pubDate, subject);
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateBook(Book book) {
		try (Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(UPDATE_BOOK_SQL)) {

			stm.setString(1, book.getName());
			stm.setString(2, book.getAuthor());
			stm.setString(3, book.getPublisher());
			stm.setDate(4, book.getPubDate());
			stm.setString(5, book.getSubject());
			stm.setInt(6, book.getId());

			int rowsAffected = stm.executeUpdate();

			if (rowsAffected > 0) {
				return "Book updated successfully!";
			} else {
				return "Book not found or not updated!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return "Error updating book!";
		}
	}

	public String deleteBook(int bookId) {
		try (Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(DELETE_BOOK_SQL)) {

			stm.setInt(1, bookId);
			int rowsAffected = stm.executeUpdate();

			if (rowsAffected > 0) {
				return "Book deleted successfully!";
			} else {
				return "Book not found or not deleted!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return "Error deleting book!";
		}


	}

	public List<Book> searchBooksByName(String searchName) {
		List<Book> books = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(SEARCH_BOOKS_BY_NAME_SQL)) {

			stm.setString(1, "%" + searchName + "%");
			try (ResultSet res = stm.executeQuery()) {
				while (res.next()) {
					int id = res.getInt("id");
					String name = res.getString("name");
					String author = res.getString("author");
					String publisher = res.getString("publisher");
					Date pubDate = res.getDate("pubDate");
					String subject = res.getString("subject");
					Book book = new Book(id, name, author, publisher, pubDate, subject);
					books.add(book);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
}

