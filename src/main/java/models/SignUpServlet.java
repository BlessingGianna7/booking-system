package models;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fName = request.getParameter("fName");
        String lName = request.getParameter("lName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String account = request.getParameter("account");

        System.out.println("Received data: fName=" + fName + ", lName=" + lName + ", email=" + email + ", password=" + password + ", account=" + account);

        if (RegistrationValidator.validateEmail(email) && RegistrationValidator.validatePassword(password)) {

            System.out.println("All validations passed!");

            User user = new User(fName, lName, account, email, password);

            UserDao userDao = new UserDao();
            userDao.insert(user);

            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            System.out.println("All validations failed!");
            response.sendRedirect("registration-failure.jsp");
        }
    }
}
