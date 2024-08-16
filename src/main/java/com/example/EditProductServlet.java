package com.example;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // リクエストパラメータからデータを取得
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            int stock = Integer.parseInt(request.getParameter("stock")); // 在庫数を取得

            LocalDate localDate = LocalDate.now();
            Date updatedDate = Date.valueOf(localDate);

            // 商品オブジェクトを作成
            Product product = new Product(id, name, price, category, description, updatedDate, stock);
            ProductDAO productDAO = new ProductDAO();
            productDAO.updateProduct(product);

            // 更新後に商品一覧ページにリダイレクト
            response.sendRedirect("ProductListServlet");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "商品情報の更新に失敗しました。");
            request.getRequestDispatcher("product_list.jsp").forward(request, response);
        }
    }
}
