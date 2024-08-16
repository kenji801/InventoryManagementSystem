<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品一覧</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h1>商品一覧</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>商品名</th>
            <th>価格</th>
            <th>カテゴリー</th>
            <th>説明</th>
            <th>更新日</th>
        </tr>
        <c:forEach var="product" items="${productList}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.category}</td>
                <td>${product.description}</td>
                <td>${product.updateDate}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="main.jsp">メイン画面に戻る</a>
</body>
</html>
