window.onload = () => {
    // if(window.location.pathname !== '/ERS/request.do') {
    //     window.location.replace('request.do');
    // }
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");

    document.getElementById("submit").addEventListener("click", () => {
        let amount = document.getElementById('amount').value;
        let description = document.getElementById('description').value;

        let typeElement = document.getElementById('type');
        let type = typeElement.options[typeElement.selectedIndex].text;

        let managerId = document.getElementById('manager');
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let data = JSON.parse(xhr.responseText);
                request(data);
            }
        };
        xhr.open("POST", `request.do?amount=${amount}&description=${description}&typeId=${typeElement.selectedIndex}&type=${type}&manager=${managerId.selectedIndex}`);
        xhr.send();
    });
};

function request(data) {
    if(data.message === "SUBMISSION SUCCESSFUL") {
        document.getElementById("submitMessage").innerHTML = '<span class="label label-success label-center">Submit Success!</span>';
    }  
    else 
    {
        document.getElementById("submitMessage").innerHTML = '<span class="label label-danger label-center">Unable to submit</span>';
    }
};