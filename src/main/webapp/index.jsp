<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/data_handler.js"></script>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<label>Name:</label>
<input id="input_name"><br><br>
<label>Number:</label>
<input id="input_picked_number"><br><br>
<button type="button" onclick="sendContestantPick()">Submit</button>
</body>
</html>