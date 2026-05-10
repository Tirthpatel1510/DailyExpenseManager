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

<body>

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <span class="navbar-brand mb-0 h1">
            Daily Expense Manager
        </span>
    </div>
</nav>

<div class="container mt-5">

<h2 class="mb-4">All Expenses</h2>

<a href="${pageContext.request.contextPath}/views/addExpense.jsp"
   class="btn btn-success mb-3">

    Add New Expense
</a>

<form action="${pageContext.request.contextPath}/filterExpenses"
            method="get"
            class="d-flex mb-3">

        <select name="category"
                        class="form-select w-25 me-2">

        <option value="">All Categories</option>
        <option value="Food">Food</option>
        <option value="Travel">Travel</option>
        <option value="Shopping">Shopping</option>
        <option value="Bills">Bills</option>

    </select>

    <button type="submit"
            class="btn btn-primary">
        Filter
    </button>

</form>

<h3 class="mt-4">Expense Analytics</h3>

<canvas id="expenseChart"
        width="400"
        height="150">
</canvas>

<%
    double total = 0;
    List<Expense> expenseList =
            (List<Expense>) request.getAttribute("expenseList");

    if (expenseList == null) {
        expenseList = (List<Expense>) request.getAttribute("expenses");
    }

    if (expenseList != null) {
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
    }
%>

<h4 class="mb-3">Total Expense: ₹ <%= String.format("%.2f", total) %></h4>

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
        if (expenseList != null) {

            for (Expense expense : expenseList) {
%>

<tr>

    <td><%= expense.getId() %></td>

    <td><%= expense.getTitle() %></td>

    <td><%= expense.getAmount() %></td>

    <td><%= expense.getCategory() %></td>

    <td><%= expense.getExpenseDate() %></td>

    <td>

        <a href="${pageContext.request.contextPath}/editExpense?id=<%= expense.getId() %>"
           class="btn btn-warning btn-sm">

            Edit
        </a>

        <a href="${pageContext.request.contextPath}/deleteExpense?id=<%= expense.getId() %>"
           class="btn btn-danger btn-sm"
           onclick="return confirm('Are you sure?')">

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

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>

const ctx =
document.getElementById('expenseChart');

new Chart(ctx, {

    type: 'pie',

    data: {

        labels: ['Food', 'Travel', 'Shopping', 'Bills'],

        datasets: [{

            label: 'Expenses',

            data: [250, 150, 100, 50],

            borderWidth: 1

        }]
    }
});

</script>

</body>
</html>
