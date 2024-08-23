package com.example;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
        	long millis = System.currentTimeMillis();
        	int userid = Integer.parseInt(request.getParameter("userid"));
        	String adminFlagStr = request.getParameter("adminflag");
        	int adminflag = (adminFlagStr != null && adminFlagStr.equals("1")) ? 1 : 0;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String mail = request.getParameter("mail");
            



            // 商品オブジェクトを作成
            User user = new User(userid, username, password, adminflag,mail,new Date(millis),new Date(millis),new Date(millis));
            UserDAO userDAO = new UserDAO();
            userDAO.updateUser(user);

            response.sendRedirect("UserListServlet");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "商品情報の更新に失敗しました。");
            request.getRequestDispatcher("user_list.jsp").forward(request, response);
        } 
    }
}
