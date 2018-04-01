window.onload = () => {
    if(window.location.pathname !== '/ERS/viewAll.do') {
        window.location.replace('viewAll.do');
    }
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");

    document.getElementById("getEmployeeInfo").addEventListener("click", () => {
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let data = JSON.parse(xhr.responseText);
                presentEmployee(data);
            }
        };
        xhr.open("POST", `viewAll.do?fetch=Employees`)
        xhr.send();
    });
};

function presentEmployee(data) {
    if(data.message) {
        document.getElementById("specificMessage").innerHTML = '<span class="label label-danger label-center">Could not get List</span>';
    }  
    else 
    {
        let employeeList = document.getElementById("employeeList");
        employeeList.innerHTML = "";

        data.forEach((employee) => {
            let employeeNode = document.createElement("li");
            employeeNode.className = "list-group-item"; 
            let employeeNodeText = document.createTextNode(`Employee ID: ${employee.id}, First Name: ${employee.firstName}, Last Name: ${employee.lastName}, Username: ${employee.username}, Email: ${employee.email}, Role: ${employee.employeeRole.type}`);
            employeeNode.appendChild(employeeNodeText);
            employeeList.appendChild(employeeNode);
        });
    }
}