<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー一覧</title>
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
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        form {
            display: inline-block;
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
        .sort-buttons, .search-bar {
            text-align: center;
            margin-bottom: 20px;
        }
        .edit-form {
            display: none;
        }
    </style>
    
</head>
<body>
    <h1>ユーザー一覧</h1>
   
    <table>
        <tr>
        	<th>ユーザーID</th>
            <th>ユーザー名</th>
            <th>管理者権限</th>
            <th>操作</th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
            	<td>${user.userid}</td>
                <td>${user.username}</td>
                <td> <c:choose>
        <c:when test="${user.adminflag == 1}">
            &#10003; <!-- チェックマークを表示 -->
        </c:when>
        <c:otherwise>
            &nbsp; <!-- 空白を表示 -->
        </c:otherwise>
    </c:choose></td>
                <td>
                    <!-- 商品削除フォーム -->
                    <form action="DeleteUserServlet" method="post" style="display:inline;">
                        <input type="hidden" name="userid" value="${user.userid}">
                        <input type="submit" value="削除">
                    </form>
                    <!-- 編集ボタン -->
                    <button onclick="toggleEditForm('${user.userid}')">編集</button>
                    <!-- 商品編集フォーム -->
                    <div id="edit-form-${user.userid}" class="edit-form">
                        <form action="EditUserServlet" method="post">
                            <input type="hidden" name="userid" value="${user.userid}">
                            <label for="name-${user.userid}">ユーザー名</label>
                            <input type="text" id="name-${user.userid}" name="username" value="${user.username}" required><br><br>

                            <label for="admin-${user.userid}">管理者権限</label>
                            <input type="checkbox" id="admin-${user.userid}" name="adminflag" value="1" ${user.adminflag == 1 ? 'checked' : ''}><br><br>


                            <input type="submit" value="更新">
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form action="register.jsp" method="get">
            <input type="submit" value="新規ユーザー登録">
        </form>
</body>
<script>
        // 編集フォームを表示/非表示にする関数
        function toggleEditForm(id) {
            var form = document.getElementById('edit-form-' + id);
            form.style.display = (form.style.display === 'none') ? 'block' : 'none';
        }
    </script>
</html>