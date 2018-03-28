window.onload = () => {
    if(window.location.pathname !== '/ERS/resolved.do') {
        window.location.replace('resolved.do');
    }
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");
    document.getElementById("getResolved").addEventListener("click", getAllResolved);
    getAllResolved();
};

function getAllResolved() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            presentReimbursements(data);
        }
    };
    xhr.open("GET", `resolved.do?fetch=resolved`)
    xhr.send();
}

function presentReimbursements(data) {
    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Could not get List</span>';
    }  
    else 
    {
        let resolvedList = document.getElementById("resolvedList");

        resolvedList.innerHTML = "";

        data.forEach((resolved) => {
            let resolvedNode = document.createElement("li");
            resolvedNode.className = "list-group-item"; 
            let resolvedNodeText = document.createTextNode(`Reimbursement ID: ${resolved.id}, Amount: ${resolved.amount}, Description: ${resolved.description}, First name: ${resolved.requester.firstName}, Last name: ${resolved.requester.lastName}, Manager ID: ${resolved.approver.id}, Status: ${resolved.status.status}, Type: ${resolved.type.type}`);
            resolvedNode.appendChild(resolvedNodeText);
            resolvedList.appendChild(resolvedNode);
        });
    }
};