window.onload = () => {
    // Login Event Listener
    document.getElementById("getCustomer").addEventListener("click", getAllCustomer);

    getAllCustomer();

};
function getAllCustomer() {
    // AJAX logic
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        // If the request is DONE(4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Getting JSON from response body
            let data = JSON.parse(xhr.responseText);
            console.log(data);
    
            // Present the data to the user
            presentCustomer(data);
        }
    };
    xhr.open("GET", `getAll.do?fetch=LIST`)
    //Sending our request
    xhr.send();
}


function presentCustomer(data) {
    // If message is a member of the JSON, something went wrong
    if(data.message) {
        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Wrong credentials.</span>';
    }  
    else 
    {
        // We present all data to the customers

        // Clean customer list
        let customerList = document.getElementById("customerList");

        customerList.innerHTML = "";

        data.forEach((customer) => {
            // Creating node of <li>
            let customerNode = document.createElement("li");
            // Add class for styling <li class="something">
            customerNode.className = "list-group-item"; 

            //Creating innerHTML object, adding customer name to it.
            // <li class="something">[create this object]</li>
            let customerNodeText = document.createTextNode(`${customer.lastName}, ${customer.firstName}`);
            //Append innerHTML to the customerNode
            // // <li class="something">ku, bob</li>
            customerNode.appendChild(customerNodeText);

            //finally appended
            customerList.appendChild(customerNode);
        });
    }
};