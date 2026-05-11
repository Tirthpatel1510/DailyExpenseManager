<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

<title>Register</title>

<link href=
"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
rel="stylesheet">

</head>

<body>

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-md-5">

<div class="card shadow p-4">

<h2 class="text-center mb-4">

Create Account

</h2>

<form action="../register"
method="post">

<div class="mb-3">

<label class="form-label">

Username

</label>

<input type="text"
name="username"
class="form-control"
required>

</div>

<div class="mb-3">

<label class="form-label">

Email

</label>

<input type="email"
name="email"
class="form-control"
required>

</div>

<div class="mb-3">

<label class="form-label">

Password

</label>

<input type="password"
name="password"
class="form-control"
required>

</div>

<button type="submit"
class="btn btn-success w-100">

Register

</button>

<div class="text-center mt-3">

<a href="login.jsp">

Already have account?

Login

</a>

</div>

</form>

</div>

</div>

</div>

</div>

</body>
</html>
