<!DOCTYPE html>
<html>
<head>

    <title>Add Expense</title>

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

        <label class="form-label">
            Transaction Type
        </label>

        <select
            name="transactionType"
            class="form-control">

            <option value="Debit">
                Debit
            </option>

            <option value="Credit">
                Credit
            </option>

        </select>

    </div>

    <div class="mb-3">

        <label class="form-label">
            Category
        </label>

        <select
            name="category"
            id="category"
            class="form-control"
            onchange="toggleOtherCategory()">

            <option value="Food">Food</option>

            <option value="Travel">Travel</option>

            <option value="Shopping">Shopping</option>

            <option value="Bills">Bills</option>

            <option value="Other">Other</option>

        </select>

    </div>

    <div
        class="mb-3"
        id="otherCategoryDiv"
        style="display:none;">

        <label class="form-label">
            Custom Category
        </label>

        <input
            type="text"
            name="customCategory"
            class="form-control"
            placeholder="Enter category">

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

</div>

<script>

function toggleOtherCategory() {

    let category =
        document.getElementById("category").value;

    let otherDiv =
        document.getElementById("otherCategoryDiv");

    if(category === "Other") {

        otherDiv.style.display = "block";

    } else {

        otherDiv.style.display = "none";
    }
}

</script>

</body>
</html>
