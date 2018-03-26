window.onload = () => {
    /** **/

    // Register Event Listener
    document.getElementById("submit").addEventListener("click", () => {
        let password = document.getElementById("password").value;
        let repeatPassword = document.getElementById("repeatPassword").value;

        if(password !== repeatPassword)
        {
            document.getElementById("registerationMessage").innerHTML = '<span class="label label-danger label-center">Password mismatch.</span>';
            return;
        }

        // get the rest of the fields
        let username = document.getElementById("username").value;
        let firstName = document.getElementById("firstName").value;
        let lastName = document.getElementById("lastName").value;
        

        // AJAX logic
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            // If the request is DONE(4), and everything is OK
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Getting JSON from response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                //Call login response processing
                register(data);
            }
        };
        xhr.open("POST",`register.do?firstName=${firstName}&lastName=${lastName}&username=${username}&password=${password}`);

        //Sending our request
        xhr.send();
    });
};

function disableAllComponents() {
    document.getElementById("firstName").setAttribute("disabled","disabled");
    document.getElementById("lastName").setAttribute("disabled","disabled");
    document.getElementById("username").setAttribute("disabled","disabled");
    document.getElementById("password").setAttribute("disabled","disabled");
    document.getElementById("repeatPassword").setAttribute("disabled","disabled");
    document.getElementById("submit").setAttribute("disabled","disabled");
}

function register(data) {
    // If message is a member of the JSON, it was AUTHENICATED FAILED
    if(data.message === "REGISTRATION SUCCESSFUL") {
        disableAllComponents();

        document.getElementById("registerationMessage").innerHTML = '<span class="label label-success label-center">Registration Successful.</span>';

        setTimeout(() => {window.location.replace("login.do"); }, 2000);
        
    }  
    else 
    {
        document.getElementById("registerationMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
};