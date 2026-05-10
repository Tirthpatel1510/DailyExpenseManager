<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.expense.model.Expense" %>

<%
Expense expense =
(Expense) request.getAttribute("expense");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Expense</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <span class="navbar-brand mb-0 h1">
            Daily Expense Manager
        </span>
    </div>
</nav>

<div class="container mt-5">

<h2>Edit Expense</h2>

<form action="${pageContext.request.contextPath}/updateExpense"
      method="post">

    <div class="mb-3">
        <input type="hidden"
               name="id"
               value="<%= expense.getId() %>">
    </div>

    <div class="mb-3">
        <label>Title</label>
        <input type="text"
               name="title"
               class="form-control"
               value="<%= expense.getTitle() %>"
               required>
    </div>

    <div class="mb-3">
        <label>Amount</label>
        <input type="number"
               step="0.01"
               name="amount"
               class="form-control"
               value="<%= expense.getAmount() %>"
               required>
    </div>

    <div class="mb-3">
        <label>Category</label>
        <input type="text"
               name="category"
               class="form-control"
               value="<%= expense.getCategory() %>"
               required>
    </div>

    <button type="submit"
            class="btn btn-success">
        Update Expense
    </button>

    <a href="${pageContext.request.contextPath}/viewExpenses"
       class="btn btn-secondary">
        Cancel
    </a>

</form>

</div>

</body>
</html>
