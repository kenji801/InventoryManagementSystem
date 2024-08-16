<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>商品マスタ管理メイン</title>
</head>
<body>
    <h1>ようこそ <%= session.getAttribute("userID") %> さん</h1>
    <form action="ProductListServlet" method="get">
        <input type="submit" value="既存商品一覧表示">
    </form>
    <form action="LogoutServlet" method="post">
        <input type="submit" value="ログアウト">
    </form>
</body>
</html>
