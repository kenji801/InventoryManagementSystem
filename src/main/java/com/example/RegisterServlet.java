package com.example;

import java.io.IOException;
import java.sql.Date;

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
        
        long millis = System.currentTimeMillis();
        
        String useridStr = request.getParameter("userid");
        int userid = Integer.parseInt(useridStr);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String adminFlagStr = request.getParameter("adminflag");
        String mail = request.getParameter("mail");
        int adminflag = (adminFlagStr != null && adminFlagStr.equals("1")) ? 1 : 0;

        User user = new User(userid,username, password, adminflag,mail,new Date(millis),new Date(millis),new Date(millis));
        user.setUserid(userid);
        user.setusername(username);
        user.setPassword(password);
        user.setAdminflag(adminflag);
        user.setMaile(mail);
        user.setRegistered_date(new Date(millis));
        user.setUpdated_date(new Date(millis));
        user.setLast_login_date(new Date(millis));

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
