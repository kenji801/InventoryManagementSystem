package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String useridstr = request.getParameter("userid");
        int userid = Integer.parseInt(useridstr);
        String password = request.getParameter("password");
        int adminflag = 0; 

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE userid=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userid);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("userid", userid);
                adminflag = rs.getInt("adminflag");
                Boolean deleteflag = rs.getObject("deleteflag") != null ? rs.getBoolean("deleteflag") : null;
                if(deleteflag != null && deleteflag) {
                	response.sendRedirect("index.html");
                }else {
                	if(adminflag == 1) {
                   	 response.sendRedirect("UserListServlet");
                   }else {
                   	response.sendRedirect("main.jsp");
                   }
                }
                
            } else {
                response.sendRedirect("index.html");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
