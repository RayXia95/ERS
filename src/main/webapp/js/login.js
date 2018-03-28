window.onload = () => {
    if(window.location.pathname !== '/ERS/login.do') {
        window.location.replace('login.do');
    }

    document.getElementById("login").addEventListener("click", () => {
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let data = JSON.parse(xhr.responseText);
                login(data);
            }
        };
        xhr.open("POST", `login.do?username=${username}&password=${password}`)
        xhr.send();
    });
};

function login(data) {
    if(data.message) {
        document.getElementById("loginMessage").innerHTML = '<span class="label label-danger label-center">Wrong credentials.</span>';
    }  
    else 
    {
        sessionStorage.setItem("employeeId", data.id);
        sessionStorage.setItem("employeeUsername", data.username);
        window.location.replace("home.do");
    }
};