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

            // LocalDateをjava.sql.Dateに変換
            LocalDate localDate = LocalDate.now();
            Date updatedDate = Date.valueOf(localDate);

            // 価格のバリデーション
            int price;
            try {
                price = Integer.parseInt(priceStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "価格は数値で入力してください。");
                request.getRequestDispatcher("add_product.jsp").forward(request, response);
                return;
            }

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);
            product.setDescription(description);
            product.setUpdatedDate(updatedDate);

            productDAO.addProduct(product);

            System.out.println("商品追加成功: " + product.getName());

            // 商品リストを取得してコンソールに出力
            System.out.println("Updated product list:");
            for (Product p : productDAO.getAllProducts()) {
                System.out.println(p.getName() + " - " + p.getPrice());
            }

            // リダイレクトして更新された商品リストを表示する
            response.sendRedirect("ProductListServlet");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input format for price.");
            request.getRequestDispatcher("add_product.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while adding the product.");
            request.getRequestDispatcher("add_product.jsp").forward(request, response);
        }
    }
}
