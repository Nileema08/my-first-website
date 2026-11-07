let expenses = JSON.parse(localStorage.getItem("expenses")) || [];

const expenseName = document.getElementById("expenseName");
const expenseAmount = document.getElementById("expenseAmount");
const expenseCategory = document.getElementById("expenseCategory");
const addExpense = document.getElementById("addExpense");
const expenseList = document.getElementById("expenseList");
const totalAmount = document.getElementById("totalAmount");

// Add Expense
addExpense.addEventListener("click", function () {

    const name = expenseName.value.trim();
    const amount = Number(expenseAmount.value);
    const category = expenseCategory.value;

    if (name === "" || expenseAmount.value === "" ||
        !Number.isFinite(amount) || amount <= 0 || category === "") {
        alert("Please enter valid expense details!");
        return;
    }

    const expense = {
        id: Date.now(),
        name: name,
        amount: amount,
        category: category
    };

    expenses.push(expense);

    saveExpenses();
    displayExpenses();

    expenseName.value = "";
    expenseAmount.value = "";
    expenseCategory.value = "";
});

// Save Expenses
function saveExpenses() {
    localStorage.setItem("expenses", JSON.stringify(expenses));
}

// Display Expenses
function displayExpenses() {

    expenseList.innerHTML = "";

    let total = 0;

    expenses.forEach(function (expense) {

        total += expense.amount;

        const li = document.createElement("li");

        const details = document.createElement("span");
        details.textContent =
            `${expense.name} (${expense.category}) - ₹${expense.amount}`;

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.className = "delete-btn";

        deleteButton.addEventListener("click", function () {
            deleteExpense(expense.id);
        });

        li.appendChild(details);
        li.appendChild(deleteButton);

        expenseList.appendChild(li);
    });

    totalAmount.textContent = "₹" + total.toLocaleString("en-IN");
}

// Delete Expense
function deleteExpense(id) {
    expenses = expenses.filter(function (expense) {
        return expense.id !== id;
    });

    saveExpenses();
    displayExpenses();
}

// Show saved expenses when page opens
displayExpenses();
