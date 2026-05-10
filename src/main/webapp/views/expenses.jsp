<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.expense.model.Expense" %>

<!DOCTYPE html>
<html>
<head>

    <title>Expense List</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

</head>

<body class="container mt-5">

<h2 class="mb-4">All Expenses</h2>

<a href="${pageContext.request.contextPath}/views/addExpense.jsp"
   class="btn btn-success mb-3">

    Add New Expense
</a>

<table class="table table-bordered table-striped">

    <thead class="table-dark">

    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Amount</th>
        <th>Category</th>
        <th>Date</th>
        <th>Action</th>
    </tr>

    </thead>

    <tbody>

<%
    List<Expense> expenses =
            (List<Expense>) request.getAttribute("expenses");

    if (expenses != null) {

        for (Expense expense : expenses) {
%>

<tr>

    <td><%= expense.getId() %></td>

    <td><%= expense.getTitle() %></td>

    <td><%= expense.getAmount() %></td>

    <td><%= expense.getCategory() %></td>

    <td><%= expense.getExpenseDate() %></td>

    <td>

        <a href="${pageContext.request.contextPath}/deleteExpense?id=<%= expense.getId() %>"
           class="btn btn-danger btn-sm">

            Delete
        </a>

    </td>

</tr>

<%
        }
    }
%>

    </tbody>

</table>

</body>
</html>
