window.addEventListener('offline', StatusOn_Off);

function StatusOn_Off(ev){
    const status = navigator.onLine ? "Online" : "Offline";
    changeHtml(status);
}
function changeHtml(status){

    switch (status){
        case "Online":
            document.getElementById("saveBrowser").setAttribute("hidden", "true");
            document.getElementById("shortUrl").setAttribute("action", "/shortener/register/reg-url");
            document.getElementById("shortUrl").setAttribute("method", "post");
            break;

        case "Offline":
            document.getElementById("button-addon2").setAttribute("hidden", "true");
            break;


    }

}