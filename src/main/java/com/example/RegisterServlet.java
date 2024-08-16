package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String adminFlagStr = request.getParameter("adminflag");
        int adminflag = (adminFlagStr != null && adminFlagStr.equals("1")) ? 1 : 0;

        User user = new User(username, password,adminflag);
        user.setusername(username);
        user.setPassword(password);
        user.setAdminflag(adminflag);

        UserDAO userDAO = new UserDAO();
        try {
			userDAO.addUser(user);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        response.sendRedirect("UserListServlet");
    }
}
