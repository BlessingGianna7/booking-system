package models;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class RegisterBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDao bookDao;

	public void init() {
		bookDao = new BookDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertBook(request, response);
				break;
			case "/delete":
				deleteBook(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateBook(request, response);
				break;
			case "/search":
				searchBooksByName(request, response);
				break;
			default:
				listBooks(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listBooks(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Book> books = bookDao.getAllBooks();
		request.setAttribute("books", books);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/registerBook.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = bookDao.getBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/registerBook.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);
	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String pubDateString = request.getParameter("pubDate");
		Date pubDate = Date.valueOf(pubDateString);
		String subject = request.getParameter("subject");

		Book book = new Book(name, author, publisher, pubDate, subject);
		bookDao.insert(book);
		response.sendRedirect("list");
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String pubDateString = request.getParameter("pubDate");
		Date pubDate = Date.valueOf(pubDateString);
		String subject = request.getParameter("subject");

		Book book = new Book(id, name, author, publisher, pubDate, subject);
		bookDao.updateBook(book);
		response.sendRedirect("list");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		bookDao.deleteBook(id);
		response.sendRedirect("list");
	}

	private void searchBooksByName(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String searchName = request.getParameter("searchName");
		List<Book> books = bookDao.searchBooksByName(searchName);
		request.setAttribute("books", books);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookList.jsp");
		dispatcher.forward(request, response);
	}
}
