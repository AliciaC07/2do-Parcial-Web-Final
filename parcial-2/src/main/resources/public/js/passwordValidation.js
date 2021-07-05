
let form = document.getElementById("form");
form.addEventListener('submit', (ev)=>{
   ev.preventDefault();
   areEqual(ev);
});

function areEqual(e){

    let password = document.getElementById("password").value;
    let passwordCheck = document.getElementById("passwordCheck").value;

    if(passwordCheck.normalize().trim() === password.normalize().trim()){
        e.unbind('submit').submit();
    }
}