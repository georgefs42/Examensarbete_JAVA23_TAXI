// Function to get the report data from the backend
function getReport() {
    const driverId = document.getElementById("driverId").value;
    const periodFrom = document.getElementById("periodFrom").value;
    const periodTo = document.getElementById("periodTo").value;

    if (!driverId || !periodFrom || !periodTo) {
        alert("Please provide all inputs.");
        return;
    }

    // Construct the URL with query parameters
    const url = `http://localhost:8080/monthly-rapport?driverId=${driverId}&periodFrom=${periodFrom}&periodTo=${periodTo}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                displayReport(data);
            } else {
                alert("No data found for the given parameters.");
            }
        })
        .catch(error => {
            console.error("Error fetching data:", error);
            alert("Error fetching data. Please try again.");
        });
}

// Function to display the report data in the table
function displayReport(data) {
    const reportTable = document.getElementById("reportTable").getElementsByTagName('tbody')[0];
    reportTable.innerHTML = ''; // Clear any existing data

    data.forEach(rapport => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${rapport.driverId}</td>
            <td>${rapport.name}</td>
            <td>${rapport.personalNumber}</td>
            <td>${rapport.totalProfit}</td>
            <td>${rapport.periodFrom}</td>
            <td>${rapport.periodTo}</td>
        `;

        reportTable.appendChild(row);
    });

    // Display the report container and the download button
    document.getElementById("reportContainer").style.display = "block";
    document.getElementById("downloadBtn").style.display = "inline-block";
}

// Function to download the report as a PDF
function downloadPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    doc.text("Driver Monthly Report", 14, 10);

    const table = document.getElementById("reportTable");
    const rows = table.getElementsByTagName('tr');
    const rowData = [];

    // Loop through table rows and extract data for the PDF
    for (let i = 1; i < rows.length; i++) { // Start at 1 to skip table header
        const cells = rows[i].getElementsByTagName('td');
        rowData.push([
            cells[0].textContent,   // Driver ID
            cells[1].textContent,   // Name
            cells[2].textContent,   // Personal Number
            cells[3].textContent,   // Total Profit
            cells[4].textContent,   // Period From
            cells[5].textContent    // Period To
        ]);
    }

    // Use autoTable to add the data in a table format in the PDF
    doc.autoTable({
        head: [['Driver ID', 'Name', 'Personal Number', 'Total Profit', 'Period From', 'Period To']],
        body: rowData,
        startY: 20,  // Position the table below the title
    });

    // Save the generated PDF file
    doc.save('Driver_Monthly_Report.pdf');
}
