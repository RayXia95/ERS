window.onload = () => {
    if(window.location.pathname !== '/ERS/resolve.do') {
        window.location.replace('resolve.do');
    }
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");
    document.getElementById("getPending").addEventListener("click", () => {
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let data = JSON.parse(xhr.responseText);
                presentReimbursements(data);
            }
        };
        xhr.open("GET", `pending.do?fetch=pending`)
        xhr.send();
    });

};

function presentReimbursements(data) {
    if(data.message) {
        document.getElementById("pendingMessage").innerHTML = '<span class="label label-danger label-center">Could not get List</span>';
    }  
    else 
    {
        let pendingList = document.getElementById("pendingList");
        pendingList.innerHTML = "";
        let buttonCounter = 1;

        data.forEach((pending) => {
            
            let pendingNode = document.createElement("li");
            let approveNode = document.createElement("button");

            approveNode.id = `approve${buttonCounter}`;
            buttonCounter++;
            approveNode.className = "btn btn-sm btn-success btn-block btn-list";
            pendingNode.className = "list-group-item"; 

            let approveNodeText = document.createTextNode("Approve");
            let pendingNodeText = document.createTextNode(`Reimbursement ID: ${pending.id}, Amount: ${pending.amount}, Description: ${pending.description}, First name: ${pending.requester.firstName}, Last name: ${pending.requester.lastName}, Manager ID: ${pending.approver.id}, Status: ${pending.status.status}, Type: ${pending.type.type}`);
            
            approveNode.appendChild(approveNodeText);

            pendingNode.appendChild(pendingNodeText);
            pendingList.appendChild(pendingNode);
            pendingList.appendChild(approveNode);
        });
    }
};
