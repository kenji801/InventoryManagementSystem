package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductListServlet")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        ProductDAO productDAO = new ProductDAO();

        try {
            if ("sort".equals(action) && sortBy != null && sortOrder != null) {
                // 並び替えを行う
                List<Product> productList = productDAO.getAllProductsSorted(sortBy, sortOrder);
                request.setAttribute("productList", productList);
                request.setAttribute("sortOrder", sortOrder); // 現在のソート順を保存
            } else if ("search".equals(action)) {
                // 商品IDで検索を行う
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    Product searchResult = productDAO.getProductById(id);
                    request.setAttribute("searchResult", searchResult);
                }
            } else {
                // すべての商品を表示
                List<Product> productList = productDAO.getAllProducts();
                request.setAttribute("productList", productList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "データベースエラーが発生しました。");
        }

        request.getRequestDispatcher("product_list.jsp").forward(request, response);
    }
}
