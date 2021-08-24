window.addEventListener('offline', StatusOn_Off);
window.addEventListener('online', StatusOn_Off);
let webSocket;

function StatusOn_Off(ev){
    const status = navigator.onLine ? "Online" : "Offline";
    if(status === "Offline"){
        urlList();
        webSocket.close();
    }else
        sendData();
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

function sendData(){
    webSocket = new WebSocket("ws://"+location.host+"/connectServer");
    webSocket.onopen = function (ev){
        console.log("ws abierto");
    }
    webSocket.onmessage = function (ev){
        if (dbs){
            let data = database.result.transaction(["url"],"readwrite");
            let url = data.objectStore("url");
            let listUrl = [];

            if(ev.data === "1"){
                url.openCursor().onsuccess = function (ev){
                    let cursor = ev.target.result;
                    if (cursor){
                        listUrl.push(cursor.value);
                        cursor.continue();
                    }
                }
                data.oncomplete = function (){
                    console.log(listUrl, 'el areglo que se manda', listUrl.length);
                    webSocket.send(JSON.stringify(listUrl));
                }
            }else if(ev.data === "2"){
                url.openCursor().onsuccess = function (ev){
                    let cursor = ev.target.result;
                    if(cursor){
                        if(cursor.value.inServer === false){
                            let update = cursor.value;
                            update.inServer = true;
                            let request = cursor.update(update);
                            request.onsuccess = function (){}
                            //request.oncomplete = function (){
                                cursor.continue();
                            //}
                        }
                    }
                }
                data.oncomplete = function (){
                    console.log("updated inServer from server");
                }
            }
        }
    }
    webSocket.onclose = function (ev){
        console.log("se ha cerrado el websocket")
    }
}