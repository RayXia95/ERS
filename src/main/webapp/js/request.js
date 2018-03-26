window.onload = () => {
    /** **/
    // Redirect user to the right URL if he comes from somewhere else
    if(window.location.pathname !== '/ERS/request.do') {
        window.location.replace('request.do');
    }

    // Login Event Listener
    document.getElementById("submit").addEventListener("click", () => {
        let amount = document.getElementById('amount').value;
        let description = document.getElementById.getElementById('description').value;
        let type_id;
        let type;
        

        // AJAX logic
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            // If the request is DONE(4), and everything is OK
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Getting JSON from response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                //Call login response processing
                login(data);
            }
        };
        xhr.open("POST", `login.do?username=${username}&password=${password}`)

        //Sending our request
        xhr.send();
    });
};