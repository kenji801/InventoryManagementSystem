package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAO userDAO = new UserDAO();
            String action = request.getParameter("action");
            List<User> userList;

            if ("search".equals(action)) {
                String idStr = request.getParameter("userid");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    User user = userDAO.getUserById(id);
                    request.setAttribute("searchResult", user);
                }
                userList = userDAO.getAllUsers();
            } else if ("sort".equals(action)) {
                String sortOrder = request.getParameter("sortOrder");
                userList = userDAO.getAllUsersSorted(sortOrder);
            } else {
            	userList = userDAO.getAllUsers();
            }
            
            // デバッグ用のログを追加
            System.out.println("User List:");
            for (User user : userList) {
                System.out.println("ID: " + user.getusername() + ", AdminFlag: " + user.getAdminflag());
            }

            request.setAttribute("userList", userList);
            request.getRequestDispatcher("user_list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
