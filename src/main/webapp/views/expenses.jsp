<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.expense.model.Expense" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>

    <title>Expense List</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

</head>

<body>

<%
// block direct access and get logged-in user
if (session == null || session.getAttribute("user") == null) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
}
com.expense.model.User user = (com.expense.model.User) session.getAttribute("user");
%>
<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <span class="navbar-brand mb-0 h1">
            Daily Expense Manager
        </span>
        <div class="d-flex">
            <span class="text-white me-3"><%= user != null ? user.getEmail() : "" %></span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Logout</a>
        </div>
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

<%
List<String> categories =
    (List<String>) request.getAttribute("categories");
%>

    <select name="category"
            class="form-select w-25 me-2">

        <option value=""
            ${selectedCategory == null || selectedCategory == ''
            ? 'selected' : ''}>
            All Categories
        </option>

<%
if (categories != null) {
    for(String cat : categories) {
%>

        <option value="<%= cat %>"
            <%= cat.equals(request.getParameter("category"))
            || cat.equals(request.getAttribute("selectedCategory"))
            ? "selected"
            : "" %>>

            <%= cat %>

        </option>

<%
    }
}
%>

    </select>

    <button type="submit"
            class="btn btn-primary">
        Filter
    </button>

</form>

<%
String selectedCategory =
    request.getParameter("category");

if (selectedCategory == null) {
    selectedCategory =
        (String) request.getAttribute("selectedCategory");
}

boolean showChart =
    selectedCategory == null
    || selectedCategory.trim().equals("");

Map<String, Double> categoryTotals =
        (Map<String, Double>) request.getAttribute("categoryTotals");

StringBuilder chartLabelsBuilder = new StringBuilder("[");
StringBuilder chartDataBuilder = new StringBuilder("[");

if (categoryTotals != null) {
    boolean first = true;

    for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
        if (!first) {
            chartLabelsBuilder.append(",");
            chartDataBuilder.append(",");
        }

        String safeLabel = entry.getKey()
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");

        chartLabelsBuilder.append("\"")
                .append(safeLabel)
                .append("\"");

        chartDataBuilder.append(entry.getValue());
        first = false;
    }
}

chartLabelsBuilder.append("]");
chartDataBuilder.append("]");
%>

<% if(showChart) { %>
<h3 class="mt-4">Expense Analytics</h3>

<div style="width: 450px; height: 450px; margin:auto;">

    <canvas id="expenseChart"></canvas>

</div>

<textarea id="chartLabelsJson" hidden><%= chartLabelsBuilder.toString() %></textarea>
<textarea id="chartDataJson" hidden><%= chartDataBuilder.toString() %></textarea>
<% } %>

<div class="row mb-4">

    <div class="col-md-4">

        <div class="card bg-success text-white">

            <div class="card-body">

                <h5>Total Credit</h5>

                <h3>
                    ₹
                    <%= String.format("%.2f", request.getAttribute("totalCredit")) %>
                </h3>

            </div>

        </div>

    </div>

    <div class="col-md-4">

        <div class="card bg-danger text-white">

            <div class="card-body">

                <h5>Total Debit</h5>

                <h3>
                    ₹
                    <%= String.format("%.2f", request.getAttribute("totalDebit")) %>
                </h3>

            </div>

        </div>

    </div>

    <div class="col-md-4">

        <div class="card bg-primary text-white">

            <div class="card-body">

                <h5>Balance</h5>

                <h3>
                    ₹
                    <%= String.format("%.2f", request.getAttribute("balance")) %>
                </h3>

            </div>

        </div>

    </div>

    <div class="col-md-3">

        <div class="card bg-warning text-dark">

            <div class="card-body">

                <h5>Monthly Savings</h5>

                <h3>
                    ₹
                    <%= String.format("%.2f", request.getAttribute("monthlySavings")) %>
                </h3>

            </div>

        </div>

    </div>

</div>

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

<h4 class="mt-4 mb-3">
<%= showChart
? "All Expenses"
: selectedCategory + " Expenses" %>
</h4>

<div class="row mt-4 mb-3">

    <div class="col-md-3">
        <input type="date" name="fromDate" class="form-control">
    </div>

    <div class="col-md-3">
        <input type="date" name="toDate" class="form-control">
    </div>

    <div class="col-md-2">
        <a href="${pageContext.request.contextPath}/exportCSV" class="btn btn-success w-100">
            Download CSV
        </a>
    </div>

    <div class="col-md-2">
        <a href="${pageContext.request.contextPath}/exportPDF" class="btn btn-danger w-100">
            Download PDF
        </a>
    </div>

</div>

<div class="table-responsive mt-4">

<table class="table table-bordered table-striped">

    <thead class="table-dark">

    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Amount</th>
        <th>Category</th>
        <th>Type</th>
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

    <td><%= expense.getType() %></td>

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

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
const chartCanvas = document.getElementById('expenseChart');

if (chartCanvas) {
    const labelsJson = document.getElementById('chartLabelsJson');
    const dataJson = document.getElementById('chartDataJson');

    const labels = JSON.parse(labelsJson ? labelsJson.value : '[]');
    const data = JSON.parse(dataJson ? dataJson.value : '[]');

    new Chart(chartCanvas, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: data
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

</script>

</body>
</html>
