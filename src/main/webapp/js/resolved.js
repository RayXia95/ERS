window.onload = () => {
    if(window.location.pathname !== '/ERS/pending.do') {
        window.location.replace('pending.do');
    }
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");
    document.getElementById("getPending").addEventListener("click", getAllPending);
    getAllPending();
};

function getAllPending() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            presentCustomer(data);
        }
    };
    xhr.open("GET", `pending.do?fetch=pending`)
    xhr.send();
}

function presentCustomer(data) {
    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Could not get List</span>';
    }  
    else 
    {
        let pendingList = document.getElementById("pendingList");

        pendingList.innerHTML = "";

        data.forEach((pending) => {
            let pendingNode = document.createElement("li");
            pendingNode.className = "list-group-item"; 
            let pendingNodeText = document.createTextNode(`Reimbursement ID: ${pending.id}, Amount: ${pending.amount}, Description: ${pending.description}, First name: ${pending.requester.firstName}, Last name: ${pending.requester.lastName}, Manager ID: ${pending.approver.id}, Status: ${pending.status.status}, Type: ${pending.type.type}`);
            pendingNode.appendChild(pendingNodeText);
            pendingList.appendChild(pendingNode);
        });
    }
};