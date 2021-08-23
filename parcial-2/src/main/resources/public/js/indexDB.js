const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB
const database = indexedDB.open("shortly", 1);
const allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
let usingSplit = "";
usingSplit = allowedString.split('');
const base = usingSplit.length;
const keyUrl = new Map();
const urlKey = new Map();

database.onupgradeneeded = function (ev){
    console.log("Creating database structure");

    active = database.result;
    const urls = active.createObjectStore("url", {keyPath:'id', autoIncrement: true});
    urls.createIndex('by_index', 'index', {unique: true});
};
database.onsuccess = function (ev){
    console.log("Loading data");
};

database.onerror = function (ev){
    console.log('Occurred an error processing:'+ ev.target.errorCode);
};
function urlList(){
    var data = database.result.transaction(["url"]);
    var url = data.objectStore("url");
    var cont =0;
    var urlsStored = [];

    url.openCursor().onsuccess = function (ev){
        var cursor = ev.target.result;
        if (cursor){
            cont++;
            urlsStored.push(cursor.value);
            cursor.continue();
        }else {
            console.log("Quantity restored:" + cont);
        }
    };
    data.oncomplete = function (){
        //mostrar tabla
        buildTable(urlsStored)
        document.getElementById("size").innerText = urlsStored.length.toString()
        document.getElementById("totMonth").innerText = addedThisMonth(urlsStored).toString()
        console.log(urlsStored);

        //document.getElementById("car-urls").innerHTML = prue;

    }

}
function addedThisMonth(urlsList){
    let cont = 0;
    for (let key in urlsList){
        if (new Date(urlsList[key].dateAdded).getMonth() === new Date().getMonth()){
            cont++;
        }
    }
    return cont;
}
function buildTable(urlsList){
    let prue = "<h5 class=\"card-title titleFont text-muted\" id=\"prue\">Urls</h5>\n";
    for (var id in urlsList){
        if (urlsList[id].inServer){
            prue += "<div class=\"card mt-3\">\n" +
                "                                    <div class=\"card-body\">\n" +
                "                                        <div class=\"row\">\n" +
                "                                            <p class=\"url-card-title text-muted col-md-9\">Original Link<br>\n" +
                "<input hidden=\"true\" id=\"identi"+urlsList[id].id+"\" value=\""+urlsList[id].id+"\">"+
                "                                                </p>\n" +
                "                                            <p class=\"col-md url-font text-muted text-end\">Clicks not avaible while offline</p>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"row\">\n" +
                "                                            <p class=\"url-card-title text-muted\">Shortened url<br><a class=\"url-font\">shortly.traki-tech.games/"+urlsList[id].cuttedUrl+"</a></p>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"accordion w-25\">\n" +
                "                                            <div class=\"accordion-item\">\n" +
                "                                                <p class=\"accordion-header url-card-title text-muted\">\n" +
                "                                                    <button class=\"accordion-button collapsed text-muted\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapse"+id+"\" aria-expanded=\"true\" aria-controls=\"collapseOne\">\n" +
                "                                                        QR Code\n" +
                "                                                    </button>\n" +
                "                                                </p>\n" +
                "                                                <div id=\"collapse"+id+"\" class=\"accordion-collapse collapse collapse\" aria-labelledby=\"headingOne\" data-bs-parent=\"#accordionExample\">\n" +
                "                                                    <div class=\"accordion-body text-center\">\n" +
                "                                                        <p>Not avaible while offline</p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </div>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"card-footer text-end\">\n" +
                "                                        <a class=\"btn btn-sm btn-primary\" href='../html/offline.html'>Info</a>\n" +
                "                                        \n" +
                "                                    </div>\n" +
                "                                </div>"
        }else {
            prue += "<div class=\"card mt-3\">\n" +
                "                                    <div class=\"card-body\">\n" +
                "                                        <div class=\"row\">\n" +
                "                                            <p class=\"url-card-title text-muted col-md-9\">Original Link<br>\n" +
                "<input hidden=\"true\" id=\"identi"+urlsList[id].id+"\" value=\""+urlsList[id].id+"\">"+
                "                                                </p>\n" +
                "                                            <p class=\"col-md url-font text-muted text-end\">Clicks not avaible while offline</p>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"row\">\n" +
                "                                            <p class=\"url-card-title text-muted\">Shortened url<br><a class=\"url-font\">shortly.traki-tech.games/"+urlsList[id].cuttedUrl+"</a></p>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"accordion w-25\">\n" +
                "                                            <div class=\"accordion-item\">\n" +
                "                                                <p class=\"accordion-header url-card-title text-muted\">\n" +
                "                                                    <button class=\"accordion-button collapsed text-muted\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapse"+id+"\" aria-expanded=\"true\" aria-controls=\"collapseOne\">\n" +
                "                                                        QR Code\n" +
                "                                                    </button>\n" +
                "                                                </p>\n" +
                "                                                <div id=\"collapse"+id+"\" class=\"accordion-collapse collapse collapse\" aria-labelledby=\"headingOne\" data-bs-parent=\"#accordionExample\">\n" +
                "                                                    <div class=\"accordion-body text-center\">\n" +
                "                                                        <p>Not avaible while offline</p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </div>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"card-footer text-end\">\n" +
                "                                        <a class=\"btn btn-sm btn-primary\" href='../html/offline.html'>Info</a>\n" +
                "                                        <a class=\"btn btn-sm btn-danger\" onclick='deleteUrl()'>Delete</a>\n" +
                "                                    </div>\n" +
                "                                </div>"
        }

    }
    document.getElementById("car-urls").innerHTML = prue;


}
function addUrl(){
    var dbActive = database.result;
    var transaction = dbActive.transaction(["url"], "readwrite");

    transaction.onerror = function (ev){
        alert(request.error.name+ ' ----->'+request.error.message);
    };
    transaction.oncomplete = function (ev){
        alert("Url added");
        urlList();
    }
    var url = transaction.objectStore("url");
    var shortened = encodeUrl(document.querySelector("#url").value);
    var dateadded = new Date().getFullYear().toString()+'-'+(new Date().getMonth()+1).toString()+'-' +new Date().getDate().toString()
    console.log(document.getElementById("navbarDropdownMenuLink").text)
    let user = document.getElementById("navbarDropdownMenuLink").text;
    user = user.replace(/\r?\n|\r/g, " ");
    console.log(user);
    let server = navigator.onLine;
    var request = url.put({
        originalUrl: document.querySelector("#url").value,
        user: user.trim(),
        cuttedUrl: shortened,
        original: decodeUrl(shortened),
        dateAdded: dateadded,
        inServer: server

    });
    request.onerror = function (ev) {
        var mensaje = "Error: "+ev.target.errorCode;
        console.error(mensaje);
        alert(mensaje)
    };
    request.onsuccess = function (ev) {
        console.log("Datos Procesado con exito");
        document.querySelector("#url").value = "";
        console.log(url);
    };


}
function deleteUrl(){
    let id = document.getElementById("identi").value;
    console.log(id);
    const data = database.result.transaction(["url"], "readwrite");
    const url = data.objectStore("url");

    url.delete(parseInt(id)).onsuccess = function (ev){
        console.log("deleted ...")
        urlList();
    };

}
function confirmUrl(originalUrl){
    if (originalUrl.substring(0, 7) === "http://")
        originalUrl = originalUrl.substring(7);

    if (originalUrl.substring(0, 8) === "https://")
        originalUrl = originalUrl.substring(8);

    if (originalUrl.charAt(originalUrl.length - 1) === '/')
        originalUrl = originalUrl.substring(0, originalUrl.length - 1);
    return originalUrl;

}

function encodeUrl(originalUrl){
    let urlCuted;
    originalUrl = confirmUrl(originalUrl);
    if (urlKey.has(originalUrl)){
        urlCuted = keyUrl.get(originalUrl);
    }else {
        urlCuted = setKey(originalUrl);
    }
    console.log("urlcut:"+urlCuted);
    return urlCuted;
}
function decodeUrl(cuttedUrl){
    let longUrl;
    const key = cuttedUrl.substring(cuttedUrl.lastIndexOf("/")+1);
    longUrl = keyUrl.get(key);
    return longUrl;
}

function setKey(originalUrl){
    let key = "";
    key = ramChar();
    keyUrl.set(key, originalUrl);
    urlKey.set(originalUrl, key);
    return key;

}
function ramChar(){
    let ramString="";
    for (let i = 0; i < 8; i++){
        console.log(usingSplit[Math.floor(Math.random() * base)])
        let num = usingSplit[Math.floor(Math.random() * base)]
        console.log(typeof num);
        ramString += num
    }
    console.log('ram :'+ramString);
    return ramString.toString();
}