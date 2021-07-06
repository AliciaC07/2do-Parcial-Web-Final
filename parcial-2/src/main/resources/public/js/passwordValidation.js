let form = document.getElementById("form");
let myEvent = function (event){
    ev.preventDefault();
    areEqual(ev, form);
}
form.addEventListener('submit', myEvent);

function areEqual(e, form){

    let password = document.getElementById("password").value;
    let passwordCheck = document.getElementById("passwordCheck").value;


    if(passwordCheck.normalize().trim() === password.normalize().trim()){
        form.removeEventlistener('submit', myEvent);
        form.submit;
    }else{
        alert("Password are not the same");
    }
}