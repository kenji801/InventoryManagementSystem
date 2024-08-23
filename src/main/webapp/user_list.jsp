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
    
    <div>
    <label for="searchId">社員番号:</label>
    <input type="text" id="searchId" onkeyup="filterTable()">
    <label for="searchName">ユーザー名:</label>
    <input type="text" id="searchName" onkeyup="filterTable()">
    <label for="searchEmail">メールアドレス:</label>
    <input type="text" id="searchEmail" onkeyup="filterTable()">
      <label for="searchAdmin">管理者権限:</label>
    <select id="searchAdmin" onchange="filterTable()">
        <option value="all">全て</option>
        <option value="true">あり</option>
        <option value="false">なし</option>
    </select>
</div>
   <br>
    <table id="userTable">
        <tr>
        	<th onclick="sortTable(0)">社員番号</th>
            <th onclick="sortTable(1)">ユーザー名</th>
            <th onclick="sortTable(2)">メールアドレス</th>
            <th onclick="sortTable(3)">管理者権限</th>
            <th onclick="sortTable(4)">登録日</th>
            <th onclick="sortTable(5)">更新日</th>
            <th onclick="sortTable(6)">最終ログイン日</th>
            <th onclick="sortTable(7)">状態</th>
            <th>操作</th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
            	<td>${user.userid}</td>
                <td>${user.username}</td>
                <td>${user.mail}</td>
                
                <td> <c:choose>
        <c:when test="${user.adminflag == 1}">
            &#10003; <!-- チェックマークを表示 -->
        </c:when>
        <c:otherwise>
            &nbsp; <!-- 空白を表示 -->
        </c:otherwise>
    </c:choose></td>
   				<td>${user.registered_date}</td>
                <td>${user.updated_date}</td>
                <td>${user.last_login_date}</td>
                <td> <c:choose>
        <c:when test="${user.deleteflag == true}">
           削除済み
        </c:when>
        <c:otherwise>
            &nbsp; <!-- 空白を表示 -->
        </c:otherwise>
    </c:choose></td>
                <td>
                    <!-- 商品削除フォーム -->
                   <c:choose>
    <c:when test="${user.deleteflag == true}">
        <form action="ReleaseUserServlet" method="post" style="display:inline;">
            <input type="hidden" name="userid" value="${user.userid}">
            <input type="submit" value="削除解除">
        </form>
    </c:when>
    <c:otherwise>
        <form action="DeleteUserServlet" method="post" style="display:inline;">
            <input type="hidden" name="userid" value="${user.userid}">
            <input type="submit" value="削除">
        </form>
    </c:otherwise>
</c:choose>
                    <!-- 編集ボタン -->
                    <button onclick="toggleEditForm('${user.userid}')">編集</button>
                    <!-- 商品編集フォーム -->
                    <div id="edit-form-${user.userid}" class="edit-form">
                        <form action="EditUserServlet" method="post">
                            <input type="hidden" name="userid" value="${user.userid}">
                            <label for="name-${user.userid}">ユーザー名</label>
                            <input type="text" id="name-${user.userid}" name="username" value="${user.username}" required><br><br>
                            
                            <label for="name-${user.userid}">メールアドレス</label>
                            <input type="text" id="mail-${user.userid}" name="mail" value="${user.mail}" required><br><br>

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

        function sortTable(n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("userTable");
            switching = true;
            // ソートの方向は、昇順に設定
            dir = "asc";
            // 切り替えが終わるまでループ
            while (switching) {
                switching = false;
                rows = table.rows;
                // テーブルのすべての行をループ
                for (i = 1; i < (rows.length - 1); i++) {
                    shouldSwitch = false;
                    // 現在の行と次の行を比較
                    x = rows[i].getElementsByTagName("TD")[n];
                    y = rows[i + 1].getElementsByTagName("TD")[n];
                    if (dir == "asc") {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            // 昇順の場合、順序が間違っていたら切り替え
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir == "desc") {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                            // 降順の場合、順序が間違っていたら切り替え
                            shouldSwitch = true;
                            break;
                        }
                    }
                }
                if (shouldSwitch) {
                    // 切り替えを実行
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    // 切り替えが完了したらカウントアップ
                    switchcount++;
                } else {
                    // 切り替えが一度も行われなかった場合、ソートの方向を変更
                    if (switchcount == 0 && dir == "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
        }

        function filterTable() {
            // 入力された検索条件を取得
            var idInput = document.getElementById("searchId").value.toLowerCase();
            var nameInput = document.getElementById("searchName").value.toLowerCase();
            var emailInput = document.getElementById("searchEmail").value.toLowerCase();
            var adminSelect = document.getElementById("searchAdmin").value; // プルダウンの選択値を取得
            
            // テーブルの行を取得
            var table = document.getElementById("userTable");
            var tr = table.getElementsByTagName("tr");

            // テーブルの各行をループ
            for (var i = 1; i < tr.length; i++) {
                var tdId = tr[i].getElementsByTagName("td")[0]; // 社員番号
                var tdName = tr[i].getElementsByTagName("td")[1]; // ユーザー名
                var tdEmail = tr[i].getElementsByTagName("td")[2]; // メールアドレス
                var tdAdmin = tr[i].getElementsByTagName("td")[3]; // 管理者権限

                if (tdId && tdName && tdEmail && tdAdmin) {
                    var idText = tdId.textContent || tdId.innerText;
                    var nameText = tdName.textContent || tdName.innerText;
                    var emailText = tdEmail.textContent || tdEmail.innerText;
                    var adminText = tdAdmin.textContent || tdAdmin.innerText;

                    // 検索条件に一致するかチェック
                    var showRow = idText.toLowerCase().indexOf(idInput) > -1 &&
                                  nameText.toLowerCase().indexOf(nameInput) > -1 &&
                                  emailText.toLowerCase().indexOf(emailInput) > -1;
                    
                    // プルダウンメニューの選択に基づいてフィルタリング
                    if (adminSelect === "true" && !adminText.includes('✓')) {
                        showRow = false; // "あり" を選択し、✓がない場合は非表示
                    } else if (adminSelect === "false" && adminText.includes('✓')) {
                        showRow = false; // "なし" を選択し、✓がある場合は非表示
                    }

                    // 行を表示または非表示
                    tr[i].style.display = showRow ? "" : "none";
                }       
            }
        }
    </script>
    
    
</html>