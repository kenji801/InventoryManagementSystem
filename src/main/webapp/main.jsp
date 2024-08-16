<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>商品マスタ管理メイン</title>
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
    </style>
</head>
<body>
    <h1>ようこそ <%= session.getAttribute("userID") %> さん</h1>
    <div class="container">
        <form action="ProductListServlet" method="get">
            <input type="submit" value="既存商品一覧表示">
        </form>
        <form action="add_product.jsp" method="get">
            <input type="submit" value="新規商品追加">
        </form>
        <form action="LogoutServlet" method="post">
            <input type="submit" value="ログアウト">
        </form>
    </div>
</body>
</html>
