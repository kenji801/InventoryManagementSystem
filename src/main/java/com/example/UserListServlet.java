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
            String sortBy = request.getParameter("sortBy");
            String sortOrder = request.getParameter("sortOrder");

            if ("sort".equals(action) && sortBy != null && sortOrder != null) {
                // 並び替えを行う
                List<User> userList = userDAO.getAllUsersSorted(sortBy, sortOrder);
                request.setAttribute("userList", userList);
                request.setAttribute("sortOrder", sortOrder); // 現在のソート順を保存
            } else if ("search".equals(action)) {
                // 商品IDで検索を行う
                String idStr = request.getParameter("userid");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    User searchResult = userDAO.getUserById(id);
                    request.setAttribute("searchResult", searchResult);
                }
            } else {
                // すべての商品を表示
                List<User> userList = userDAO.getAllUsers();
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("user_list.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
