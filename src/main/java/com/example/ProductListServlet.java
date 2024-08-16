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
        try {
            ProductDAO productDAO = new ProductDAO();
            String action = request.getParameter("action");
            List<Product> productList;

            if ("search".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    Product product = productDAO.getProductById(id);
                    request.setAttribute("searchResult", product);
                }
                productList = productDAO.getAllProducts();
            } else if ("sort".equals(action)) {
                String sortOrder = request.getParameter("sortOrder");
                productList = productDAO.getAllProductsSorted(sortOrder);
            } else {
                productList = productDAO.getAllProducts();
            }

            request.setAttribute("productList", productList);
            request.getRequestDispatcher("product_list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
