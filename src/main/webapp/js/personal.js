window.onload = () => {
    if(window.location.pathname !== '/ERS/personal.do') {
        window.location.replace('personal.do');
    }
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let data = JSON.parse(xhr.responseText);
                presentEmployeeInfo(data);
            }
        }
        xhr.open("GET", `personal.do?fetch=INFO`)
        xhr.send();
};

function presentEmployeeInfo(data) {
    if(data.message)
    {
        console.log("Whatever");
    }
    else
    {
        let personalList = document.getElementById("personalList");

        personalList.innerHTML = "";
    
        let personalNode = document.createElement("li");
        personalNode.className = "list-group-item"; 
        let personalNodeText = document.createTextNode(`Employee ID :${data.id},
        First Name: ${data.firstName},
        Last Name: ${data.lastName},
        Email: ${data.email}
        Employment Role: ${data.employeeRole.type}`);
        personalNode.appendChild(personalNodeText);
        personalList.appendChild(personalNode);
    }
};