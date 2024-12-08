const apiBaseUrl = 'http://localhost:8080/monthly-rapport';  // Adjust if needed

// Get all reports and display in a styled table
function getAllReports() {
    fetch(apiBaseUrl)
        .then(response => response.json())
        .then(data => {
            const reportsDiv = document.getElementById('all-reports');
            if (data && data.length > 0) {
                let tableHtml = `
                    <table>
                        <thead>
                            <tr>
                                <th>Driver ID</th>
                                <th>Name</th>
                                <th>Personal Number</th>
                                <th>Total Profit</th>
                                <th>Period From</th>
                                <th>Period To</th>
                            </tr>
                        </thead>
                        <tbody>`;

                data.forEach(report => {
                    tableHtml += `
                        <tr>
                            <td>${report.driverId}</td>
                            <td>${report.name || "N/A"}</td>
                            <td>${report.personalNumber || "N/A"}</td>
                            <td>${report.totalProfit}</td>
                            <td>${report.periodFrom}</td>
                            <td>${report.periodTo}</td>
                        </tr>`;
                });

                tableHtml += `
                    </tbody>
                </table>`;

                reportsDiv.innerHTML = tableHtml;
            } else {
                reportsDiv.innerHTML = '<p>No reports found.</p>';
            }
        })
        .catch(error => {
            console.error('Error fetching reports:', error);
        });
}

// Get report by driver ID and display it in a table
function getReportById() {
    const driverId = document.getElementById('driverIdGet').value;
    fetch(`${apiBaseUrl}/${driverId}`)
        .then(response => response.json())
        .then(data => {
            const reportDiv = document.getElementById('single-report');
            if (data) {
                reportDiv.innerHTML = `
                    <table>
                        <thead>
                            <tr>
                                <th>Driver ID</th>
                                <th>Name</th>
                                <th>Personal Number</th>
                                <th>Total Profit</th>
                                <th>Period From</th>
                                <th>Period To</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${data.driverId}</td>
                                <td>${data.name || "N/A"}</td>
                                <td>${data.personalNumber || "N/A"}</td>
                                <td>${data.totalProfit}</td>
                                <td>${data.periodFrom}</td>
                                <td>${data.periodTo}</td>
                            </tr>
                        </tbody>
                    </table>
                `;
            } else {
                reportDiv.innerHTML = 'No report found for this driver ID.';
            }
        })
        .catch(error => {
            console.error('Error fetching report by ID:', error);
        });
}

// Create new report
function createReport() {
    const driverId = document.getElementById('driverIdCreate').value;
    const periodFrom = document.getElementById('periodFrom').value;
    const periodTo = document.getElementById('periodTo').value;

    // Validate input
    if (!driverId || !periodFrom || !periodTo) {
        alert('Please fill in all fields!');
        return;
    }

    // Create the query string with the necessary parameters
    const queryString = `?driverId=${driverId}&periodFrom=${periodFrom}&periodTo=${periodTo}`;

    fetch(apiBaseUrl + queryString, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (response.status === 201) {
                return response.json();
            } else {
                throw new Error('Failed to create report');
            }
        })
        .then(data => {
            const statusDiv = document.getElementById('create-status');
            statusDiv.innerHTML = `<div class="success">Created Report: ${JSON.stringify(data, null, 2)}</div>`;
        })
        .catch(error => {
            console.error('Error creating report:', error);
            const statusDiv = document.getElementById('create-status');
            statusDiv.innerHTML = '<div class="error">Failed to create report. Please check input data.</div>';
        });
}

// Delete report
function deleteReport() {
    const driverId = document.getElementById('driverIdDelete').value;

    fetch(`${apiBaseUrl}/${driverId}`, {
        method: 'DELETE',
    })
        .then(response => {
            const statusDiv = document.getElementById('delete-status');
            if (response.ok) {
                statusDiv.innerHTML = '<div class="success">Successfully deleted the report.</div>';
            } else {
                statusDiv.innerHTML = '<div class="error">Failed to delete report. Report not found.</div>';
            }
        })
        .catch(error => {
            console.error('Error deleting report:', error);
        });
}
