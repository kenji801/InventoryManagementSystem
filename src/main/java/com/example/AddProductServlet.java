package com.example;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            ProductDAO productDAO = new ProductDAO();

            // 新しい商品を追加する
            String name = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            String stockStr = request.getParameter("stock");

            // LocalDateをjava.sql.Dateに変換
            LocalDate localDate = LocalDate.now();
            Date updatedDate = Date.valueOf(localDate);

            // 価格と在庫数のバリデーション
            int price, stock;
            try {
                price = Integer.parseInt(priceStr);
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "価格と在庫数は数値で入力してください。");
                request.getRequestDispatcher("add_product.jsp").forward(request, response);
                return;
            }

            Product product = new Product(stock, stockStr, stock, stockStr, stockStr, updatedDate, stock);
            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);
            product.setDescription(description);
            product.setStock(stock);
            product.setUpdatedDate(updatedDate);

            productDAO.addProduct(product);

            System.out.println("商品追加成功: " + product.getName());

            // 成功メッセージを設定して同じページに戻る
            request.setAttribute("successMessage", "登録に成功しました！");
            request.getRequestDispatcher("add_product.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while adding the product.");
            request.getRequestDispatcher("add_product.jsp").forward(request, response);
        }
    }
}
