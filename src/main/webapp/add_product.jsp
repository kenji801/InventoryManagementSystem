<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>新規商品追加</title>
    <style>
        body {
            background-color: #f0f8ff; /* ライトブルーの背景色 */
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 400px;
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 8px;
        }
        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
            box-sizing: border-box;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <h1>新規商品追加</h1>
    <div class="container">
        <!-- エラーメッセージの表示 -->
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        <form action="AddProductServlet" method="post">
            <label for="name">商品名:</label>
            <input type="text" id="name" name="name" required><br><br>

            <label for="price">価格(数値として入力):</label>
            <input type="text" id="price" name="price" required><br><br>

            <label for="category">カテゴリー:</label>
            <input type="text" id="category" name="category" required><br><br>

            <label for="description">説明:</label>
            <textarea id="description" name="description"></textarea><br><br>

            <input type="submit" value="追加">
        </form>
        <form action="ProductListServlet" method="get">
            <input type="submit" value="商品一覧に戻る">
        </form>
    </div>
</body>
</html>

