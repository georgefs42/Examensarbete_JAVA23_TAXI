const API_BASE_URL = "http://localhost:8080/api/income";

document.getElementById("incomeForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const name = document.getElementById("name").value;
    const date = document.getElementById("date").value;
    const dailyIncome = parseFloat(document.getElementById("dailyIncome").value);

    if (!name || !date || isNaN(dailyIncome)) {
        alert("Please fill out all fields correctly.");
        return;
    }

    const dailyIncomeAfterVAT = dailyIncome - dailyIncome * 0.06;
    const dailyDriverProfit = dailyIncomeAfterVAT * 0.45;

    document.getElementById("afterVAT").textContent = dailyIncomeAfterVAT.toFixed(2);
    document.getElementById("driverProfit").textContent = dailyDriverProfit.toFixed(2);

    const income = { name, date, dailyIncome };
    await fetch(API_BASE_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(income),
    });
    alert("Income saved successfully!");
});

document.getElementById("generateReport").addEventListener("click", async () => {
    const year = new Date().getFullYear();
    const month = new Date().getMonth() + 1;

    const response = await fetch(`${API_BASE_URL}/monthly?year=${year}&month=${month}`);
    const monthlyData = await response.json();

    generatePDFReport(monthlyData, year, month);
});

function generatePDFReport(data, year, month) {
    const doc = new jsPDF(); // Initialize the jsPDF instance

    // Add title
    doc.setFontSize(16);
    doc.text(`Monthly Driver Income Report - ${month}/${year}`, 10, 10);

    // Table header
    let y = 20;
    doc.setFontSize(12);
    doc.text("Date", 10, y);
    doc.text("Name", 50, y);
    doc.text("Profit", 150, y);
    y += 10;

    // Data rows
    let totalProfit = 0;
    data.forEach((entry) => {
        const dailyIncomeAfterVAT = entry.dailyIncome - entry.dailyIncome * 0.06;
        const dailyDriverProfit = dailyIncomeAfterVAT * 0.45;
        totalProfit += dailyDriverProfit;

        doc.text(entry.date, 10, y);
        doc.text(entry.name, 50, y);
        doc.text(dailyDriverProfit.toFixed(2), 150, y);
        y += 10;

        if (y > 280) { // Create a new page if content exceeds page length
            doc.addPage();
            y = 10;
        }
    });

    // Add total profit
    y += 10;
    doc.setFontSize(14);
    doc.text(`Total Monthly Profit: ${totalProfit.toFixed(2)}`, 10, y);

    // Save the PDF
    doc.save(`Monthly_Report_${month}_${year}.pdf`);
}
