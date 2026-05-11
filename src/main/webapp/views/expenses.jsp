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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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

<div class="row mb-4">
    <div class="col-md-6">
        <h2 class="">All Expenses</h2>
    </div>
    <div class="col-md-6 text-end">
        <a href="${pageContext.request.contextPath}/views/addExpense.jsp"
           class="btn btn-success">
            <i class="bi bi-plus-circle"></i> Add New Expense
        </a>
    </div>
</div>

<div class="row mb-3">
    <div class="col-md-6">
        <form action="${pageContext.request.contextPath}/viewExpenses" method="get" class="d-flex">
            <input type="text" name="query" class="form-control me-2" placeholder="Search by title or category..." value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>">
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
    </div>
    <div class="col-md-6 text-end">
        <form action="${pageContext.request.contextPath}/filterExpenses"
              method="get"
              class="d-flex justify-content-end">

        <%
        List<String> categories =
            (List<String>) request.getAttribute("categories");
        %>

            <select name="category"
                    class="form-select w-50 me-2">

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
                    class="btn btn-outline-primary">
                Filter
            </button>

        </form>
    </div>
</div>

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
<div class="row mb-4">
    <div class="col-md-6">
        <div class="card shadow-sm h-100">
            <div class="card-header bg-white">
                <h5 class="mb-0">Category Breakdown</h5>
            </div>
            <div class="card-body">
                <div style="height: 300px;">
                    <canvas id="expenseChart"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card shadow-sm h-100">
            <div class="card-header bg-white">
                <h5 class="mb-0">Monthly Spending Trend</h5>
            </div>
            <div class="card-body">
                <div style="height: 300px;">
                    <canvas id="trendChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<textarea id="chartLabelsJson" hidden><%= chartLabelsBuilder.toString() %></textarea>
<textarea id="chartDataJson" hidden><%= chartDataBuilder.toString() %></textarea>

<%
    List<Object[]> trends = (List<Object[]>) request.getAttribute("trends");
    StringBuilder trendLabels = new StringBuilder("[");
    StringBuilder trendData = new StringBuilder("[");
    String[] monthNames = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    if (trends != null) {
        boolean first = true;
        for (Object[] row : trends) {
            if (!first) {
                trendLabels.append(",");
                trendData.append(",");
            }
            int monthIdx = (Integer) row[0];
            trendLabels.append("\"").append(monthNames[monthIdx]).append("\"");
            trendData.append(row[1]);
            first = false;
        }
    }
    trendLabels.append("]");
    trendData.append("]");
%>
<textarea id="trendLabelsJson" hidden><%= trendLabels.toString() %></textarea>
<textarea id="trendDataJson" hidden><%= trendData.toString() %></textarea>
<% } %>

<div class="row mb-4">

    <div class="col-md-3">

        <div class="card bg-success text-white shadow-sm h-100">

            <div class="card-body text-center">

                <h6 class="text-uppercase small">Total Credit</h6>

                <h3 class="mb-0">
                    ₹
                    <%= String.format("%.2f", request.getAttribute("totalCredit")) %>
                </h3>

            </div>

        </div>

    </div>

    <div class="col-md-3">

        <div class="card bg-danger text-white shadow-sm h-100">

            <div class="card-body text-center">

                <h6 class="text-uppercase small">Total Debit</h6>

                <h3 class="mb-0">
                    ₹
                    <%= String.format("%.2f", request.getAttribute("totalDebit")) %>
                </h3>

            </div>

        </div>

    </div>

    <div class="col-md-2">

        <div class="card bg-primary text-white shadow-sm h-100">

            <div class="card-body text-center">

                <h6 class="text-uppercase small">Balance</h6>

                <h3 class="mb-0">
                    ₹
                    <%= String.format("%.2f", request.getAttribute("balance")) %>
                </h3>

            </div>

        </div>

    </div>

    <div class="col-md-2">

        <div class="card bg-warning text-dark shadow-sm h-100">

            <div class="card-body text-center">

                <h6 class="text-uppercase small">Monthly Budget</h6>

                <h3 class="mb-0">
                    ₹
                    <%= String.format("%.2f", user.getBudget()) %>
                </h3>
                <button class="btn btn-sm btn-link text-dark p-0" data-bs-toggle="modal" data-bs-target="#budgetModal">Edit</button>

            </div>

        </div>

    </div>

    <div class="col-md-2">
        <% 
            double budgetRem = user.getBudget() - (Double)request.getAttribute("totalDebit");
            String remColor = budgetRem < 0 ? "bg-dark text-danger" : "bg-info text-white";
        %>
        <div class="card <%= remColor %> shadow-sm h-100">

            <div class="card-body text-center">

                <h6 class="text-uppercase small">Budget Left</h6>

                <h3 class="mb-0">
                    ₹
                    <%= String.format("%.2f", budgetRem) %>
                </h3>

            </div>

        </div>

    </div>

</div>

<!-- Overspending Alert -->
<% if (budgetRem < 0 && user.getBudget() > 0) { %>
<div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
  <strong><i class="bi bi-exclamation-triangle-fill"></i> Budget Alert!</strong> You have exceeded your monthly budget by ₹ <%= String.format("%.2f", Math.abs(budgetRem)) %>.
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<% } %>

<!-- Budget Modal -->
<div class="modal fade" id="budgetModal" tabindex="-1" aria-labelledby="budgetModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/updateBudget" method="post">
        <div class="modal-header">
          <h5 class="modal-title" id="budgetModalLabel">Set Monthly Budget</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="budget" class="form-label">Monthly Budget Limit (₹)</label>
            <input type="number" step="0.01" class="form-control" id="budget" name="budget" value="<%= user.getBudget() %>" required>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn-secondary btn" data-bs-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Save changes</button>
        </div>
      </form>
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

<div class="d-flex justify-content-between align-items-center mt-4 mb-3">
    <h4 class="mb-0">
    <%= showChart
    ? "Expense Records"
    : selectedCategory + " Expenses" %>
    </h4>
    <h5 class="text-muted">Total in View: ₹ <%= String.format("%.2f", total) %></h5>
</div>

<form action="${pageContext.request.contextPath}/viewExpenses" method="get" class="row mt-4 mb-3">
    <div class="col-md-3">
        <label class="small text-muted">From Date</label>
        <input type="date" name="fromDate" class="form-control" value="<%= request.getAttribute("fromDate") != null ? request.getAttribute("fromDate") : "" %>">
    </div>

    <div class="col-md-3">
        <label class="small text-muted">To Date</label>
        <input type="date" name="toDate" class="form-control" value="<%= request.getAttribute("toDate") != null ? request.getAttribute("toDate") : "" %>">
    </div>

    <div class="col-md-2 d-flex align-items-end">
        <button type="submit" class="btn btn-primary w-100">Filter Dates</button>
    </div>
    
    <div class="col-md-4 d-flex align-items-end justify-content-end">
        <%
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
            String firstDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
            String lastDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        %>
        <a href="${pageContext.request.contextPath}/exportPDF?fromDate=<%= firstDay %>&toDate=<%= lastDay %>" class="btn btn-dark me-2">
            <i class="bi bi-calendar-month"></i> Monthly PDF
        </a>
        <a href="${pageContext.request.contextPath}/exportCSV?fromDate=<%= request.getAttribute("fromDate") != null ? request.getAttribute("fromDate") : "" %>&toDate=<%= request.getAttribute("toDate") != null ? request.getAttribute("toDate") : "" %>" class="btn btn-outline-success me-2">
            <i class="bi bi-file-earmark-spreadsheet"></i> CSV
        </a>
        <a href="${pageContext.request.contextPath}/exportPDF?fromDate=<%= request.getAttribute("fromDate") != null ? request.getAttribute("fromDate") : "" %>&toDate=<%= request.getAttribute("toDate") != null ? request.getAttribute("toDate") : "" %>" class="btn btn-outline-danger">
            <i class="bi bi-file-earmark-pdf"></i> PDF
        </a>
    </div>
</form>

<div class="table-responsive">

<table class="table table-hover table-bordered bg-white shadow-sm">

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
        if (expenseList != null && !expenseList.isEmpty()) {

            for (Expense expense : expenseList) {
%>

<tr>

    <td><%= expense.getId() %></td>

    <td><%= expense.getTitle() %></td>

    <td><span class="<%= "Credit".equals(expense.getType()) ? "text-success" : "text-danger" %> font-weight-bold">
        ₹ <%= expense.getAmount() %>
    </span></td>

    <td><span class="badge bg-secondary"><%= expense.getCategory() %></span></td>

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
    } else {
%>
    <tr>
        <td colspan="7" class="text-center py-4 text-muted">No expenses found matching your criteria.</td>
    </tr>
<%
    }
%>

    </tbody>

</table>

</div>

<!-- Pagination -->
<%
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    String query = (String) request.getAttribute("searchQuery");
    if (query == null) query = "";
    
    if (totalPages != null && totalPages > 1) {
%>
<nav aria-label="Page navigation" class="mt-4">
  <ul class="pagination justify-content-center">
    <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
      <a class="page-link" href="?page=<%= currentPage - 1 %>&query=<%= query %>">Previous</a>
    </li>
    
    <% for (int i = 1; i <= totalPages; i++) { %>
    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
      <a class="page-link" href="?page=<%= i %>&query=<%= query %>"><%= i %></a>
    </li>
    <% } %>
    
    <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
      <a class="page-link" href="?page=<%= currentPage + 1 %>&query=<%= query %>">Next</a>
    </li>
  </ul>
</nav>
<% } %>

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
                data: data,
                backgroundColor: [
                    '#1e293b', '#2563eb', '#64748b', '#94a3b8', '#cbd5e1', '#e2e8f0'
                ],
                borderWidth: 2,
                borderColor: '#ffffff'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: { font: { family: 'Inter', size: 12 } }
                }
            }
        }
    });
}

const trendCanvas = document.getElementById('trendChart');
if (trendCanvas) {
    const labels = JSON.parse(document.getElementById('trendLabelsJson').value || '[]');
    const data = JSON.parse(document.getElementById('trendDataJson').value || '[]');

    new Chart(trendCanvas, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Monthly Spending',
                data: data,
                backgroundColor: '#2563eb',
                borderRadius: 8,
                barThickness: 30
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false }
            },
            scales: {
                x: {
                    grid: { display: false },
                    ticks: { font: { family: 'Inter' } }
                },
                y: {
                    beginAtZero: true,
                    grid: { color: '#f1f5f9' },
                    ticks: { 
                        font: { family: 'Inter' },
                        callback: function(value) { return '₹' + value; }
                    }
                }
            }
        }
    });
}

</script>

</body>
</html>
