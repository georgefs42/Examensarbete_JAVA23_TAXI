const baseUrl = "http://localhost:8080/drivers";

document.getElementById("driverForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const driverId = document.getElementById("driverId").value;
    const name = document.getElementById("name").value;
    const personalNumber = document.getElementById("personalNumber").value;
    const address = document.getElementById("address").value;
    const mobile = document.getElementById("mobile").value;
    const email = document.getElementById("email").value;

    const driverData = {
        name,
        personalNumber,
        address,
        mobile,
        email,
    };

    try {
        if (driverId) {
            // Update driver
            await fetch(`${baseUrl}/${driverId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(driverData),
            });
        } else {
            // Add new driver
            await fetch(baseUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(driverData),
            });
        }

        alert("Driver submitted successfully!");
        fetchDrivers();
        document.getElementById("driverForm").reset();
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to submit driver.");
    }
});

document.getElementById("fetchDrivers").addEventListener("click", fetchDrivers);

async function fetchDrivers() {
    try {
        const response = await fetch(baseUrl);
        const drivers = await response.json();

        const driverList = document.getElementById("driverList");
        driverList.innerHTML = "";

        drivers.forEach((driver) => {
            const li = document.createElement("li");
            li.textContent = `${driver.name} - ${driver.email}`;

            // Edit button
            const editBtn = document.createElement("button");
            editBtn.textContent = "Edit";
            editBtn.className = "edit-btn";
            editBtn.onclick = () => populateForm(driver);

            // Delete button
            const deleteBtn = document.createElement("button");
            deleteBtn.textContent = "Delete";
            deleteBtn.onclick = () => deleteDriver(driver.driverId);

            li.appendChild(editBtn);
            li.appendChild(deleteBtn);
            driverList.appendChild(li);
        });
    } catch (error) {
        console.error("Error fetching drivers:", error);
    }
}

function populateForm(driver) {
    // Populate the form with driver details
    document.getElementById("driverId").value = driver.driverId;
    document.getElementById("name").value = driver.name;
    document.getElementById("personalNumber").value = driver.personalNumber;
    document.getElementById("address").value = driver.address;
    document.getElementById("mobile").value = driver.mobile;
    document.getElementById("email").value = driver.email;

    // Optionally scroll to the form for better UX
    document.querySelector(".form-section").scrollIntoView({ behavior: "smooth" });
}

async function deleteDriver(driverId) {
    if (confirm("Are you sure you want to delete this driver?")) {
        try {
            await fetch(`${baseUrl}/${driverId}`, { method: "DELETE" });
            alert("Driver deleted successfully!");
            fetchDrivers();
        } catch (error) {
            console.error("Error deleting driver:", error);
        }
    }
}
