<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>商品一覧</title>
    <style>
        body {
            background-color: #f0f8ff;
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
            position: relative;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        a {
            color: white;
            text-decoration: none;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        a:hover {
            background-color: #45a049;
        }
        .search-bar {
            text-align: center;
            margin-bottom: 20px;
        }
        .edit-form {
            display: none;
        }
    </style>
    <script>
        // 編集フォームを表示/非表示にする関数
        function toggleEditForm(id) {
            var form = document.getElementById('edit-form-' + id);
            form.style.display = (form.style.display === 'none') ? 'block' : 'none';
        }
    </script>
</head>
<body>
    <h1>商品一覧</h1>
    <div class="search-bar">
        <!-- 商品IDで検索するフォーム -->
        <form action="ProductListServlet" method="get">
            <input type="hidden" name="action" value="search">
            <input type="text" name="id" placeholder="商品IDで検索">
            <input type="submit" value="検索">
        </form>
    </div>
    <!-- 検索結果の表示 -->
    <c:if test="${not empty searchResult}">
        <h2>検索結果</h2>
        <p>ID: ${searchResult.id}</p>
        <p>商品名: ${searchResult.name}</p>
        <p>価格: <fmt:formatNumber value="${searchResult.price}" type="number" /> 円</p>
        <p>カテゴリー: ${searchResult.category}</p>
        <p>説明: ${searchResult.description}</p>
        <p>在庫数: ${searchResult.stock}</p> <!-- 在庫数の表示 -->
        <p>更新日: ${searchResult.updatedDate}</p>
    </c:if>
    <table>
        <tr>
            <th><a href="?action=sort&sortBy=id&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">ID</a></th>
            <th><a href="?action=sort&sortBy=name&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">商品名</a></th>
            <th><a href="?action=sort&sortBy=price&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">価格</a></th>
            <th><a href="?action=sort&sortBy=category&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">カテゴリー</a></th>
            <th><a href="?action=sort&sortBy=description&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">説明</a></th>
            <th><a href="?action=sort&sortBy=stock&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">在庫数</a></th>
            <th><a href="?action=sort&sortBy=updated_date&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}">更新日</a></th>
            <th>操作</th>
        </tr>
        <!-- 商品リストの表示 -->
        <c:forEach var="product" items="${productList}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td><fmt:formatNumber value="${product.price}" type="number" /> 円</td>
                <td>${product.category}</td>
                <td>${product.description}</td>
                <td>${product.stock}</td> <!-- 在庫数の表示 -->
                <td>${product.updatedDate}</td>
                <td>
                    <!-- 商品削除フォーム -->
                    <form action="DeleteProductServlet" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${product.id}">
                        <input type="submit" value="削除">
                    </form>
                    <!-- 編集ボタン -->
                    <button onclick="toggleEditForm(${product.id})">編集</button>
                    <!-- 商品編集フォーム -->
                    <div id="edit-form-${product.id}" class="edit-form">
                        <form action="EditProductServlet" method="post">
                            <input type="hidden" name="id" value="${product.id}">
                            <label for="name-${product.id}">商品名:</label>
                            <input type="text" id="name-${product.id}" name="name" value="${product.name}" required><br><br>

                            <label for="price-${product.id}">価格:</label>
                            <input type="text" id="price-${product.id}" name="price" value="${product.price}" required><br><br>

                            <label for="category-${product.id}">カテゴリー:</label>
                            <input type="text" id="category-${product.id}" name="category" value="${product.category}" required><br><br>

                            <label for="description-${product.id}">説明:</label>
                            <textarea id="description-${product.id}" name="description">${product.description}</textarea><br><br>

                            <label for="stock-${product.id}">在庫数:</label> <!-- 在庫数の編集フォーム -->
                            <input type="text" id="stock-${product.id}" name="stock" value="${product.stock}" required><br><br>

                            <input type="submit" value="更新">
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="main.jsp">メイン画面に戻る</a>
</body>
</html>