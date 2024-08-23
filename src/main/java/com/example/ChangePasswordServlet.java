package com.example;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
        	 HttpSession session = request.getSession();
        	 int userid = (int) session.getAttribute("userid");
        	 String password = request.getParameter("password");
        	 String newPassword = request.getParameter("newPassword");
        	
            



            
            UserDAO userDAO = new UserDAO();
            userDAO.updatePassword(userid, password, newPassword);

            response.sendRedirect("main.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "ユーザー情報の更新に失敗しました。");
            request.getRequestDispatcher("main.jsp").forward(request, response);
        } catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} 
    }
}