package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReleaseUserServlet")
public class ReleaseUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DeleteUserServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("userid"));
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.releaseUser(id);
            response.sendRedirect("UserListServlet");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
