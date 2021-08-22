const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB
const database = indexedDB.open("shortly", 1);
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
        console.log(urlsStored);
    }

}
const allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
const usingSplit = allowedString.split('');
const base = usingSplit.length;
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
    var request = url.put({
        originalUrl: document.querySelector("#url").value
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