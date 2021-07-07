google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawBrowser);
let browserInfo = JSON.parse(document.getElementById('browser_graph').getAttribute('data-graph'));

function drawBrowser() {

    let arrayData =[];
    if(browserInfo.length > 0){
        for (let i =0; i < browserInfo.length+1;i++){
            if(i === 0 ){
                arrayData[i] = ['Browser', 'Total Clicks'];
            }else{
                arrayData[i] = [browserInfo[i-1].browser, browserInfo[i-1].quantity]
            }
        }

        var data = new google.visualization.arrayToDataTable(arrayData);
        var options = {
            hAxis: {
                title: 'Browser',
            },
            vAxis: {
                title: 'Total Clicks'
            }
        };

        var chart = new google.visualization.ColumnChart(
            document.getElementById('browser_graph'));

        chart.draw(data, options);
    }

}

window.onresize = drawBrowser;