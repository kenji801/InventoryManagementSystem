<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>新規ユーザー登録</title>
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
        input[type="password"] {
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
    </style>
</head>
<body>
    <h1>新規ユーザー登録</h1>
    <div class="container">
        <form action="RegisterServlet" method="post">
            <label for="username">ユーザー名:</label>
            <input type="text" id="username" name="username"><br><br>
            <label for="password">パスワード:</label>
            <input type="password" id="password" name="password"><br><br>
            <input type="checkbox" id="adminflag" name="adminflag" value="1">管理者権限
            <input type="submit" value="登録">
            
        </form>
        <form action="index.html" method="get">
            <input type="submit" value="ログイン画面に戻る">
        </form>
    </div>
</body>
</html>

