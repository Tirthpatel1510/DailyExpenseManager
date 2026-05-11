<!DOCTYPE html>
<html>
<head>

    <title>Login</title>

    <link href=
"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

</head>

<body class="container mt-5">

<h2 class="mb-4">Login</h2>

<form action="${pageContext.request.contextPath}/login"
      method="post">

<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>
<p style="color:red;">
    <%= error %>
</p>
<%
}
%>

    <div class="mb-3">

        <label>Email</label>

        <input type="email"
               name="email"
               class="form-control"
               required>
    </div>

    <div class="mb-3">

        <label>Password</label>

        <input type="password"
               name="password"
               class="form-control"
               required>
    </div>

    <button type="submit"
            class="btn btn-primary">

        Login
    </button>

    <a href="register.jsp"
class="btn btn-dark w-100 mt-2">

Create Account

</a>

</form>

</body>
</html>
