<!DOCTYPE html>
<html>
<head>

    <title>Add Expense</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

</head>

<body class="container mt-5">

<h2>Add Expense</h2>

<form action="${pageContext.request.contextPath}/saveExpense" method="post">

    <div class="mb-3">
        <label>Title</label>

        <input type="text"
               name="title"
               class="form-control"
               required>
    </div>

    <div class="mb-3">
        <label>Amount</label>

        <input type="number"
               step="0.01"
               name="amount"
               class="form-control"
               required>
    </div>

    <div class="mb-3">

        <label>Category</label>

        <select name="category"
                class="form-control">

            <option>Food</option>
            <option>Travel</option>
            <option>Shopping</option>
            <option>Bills</option>

        </select>
    </div>

    <div class="mb-3">

        <label>Date</label>

        <input type="date"
               name="expenseDate"
               class="form-control"
               required>
    </div>

    <button type="submit"
            class="btn btn-primary">

        Save Expense
    </button>

    <a href="${pageContext.request.contextPath}/viewExpenses"
       class="btn btn-dark">

        View Expenses
    </a>

</form>

</body>
</html>
