google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawOS);

let osInfo = JSON.parse(document.getElementById('OS_graph').getAttribute('data-graph'));

function drawOS() {
    let arrayData =[];
    if(osInfo.length > 0){
        for (let i = 0; i < osInfo.length+1; i ++){
            if(i === 0)
                arrayData[i] = ['OS', 'Total Clicks'];
            else
                arrayData[i] = [osInfo[i-1].oS, osInfo[i-1].quantity]
        }
        
        var data = new google.visualization.arrayToDataTable(arrayData);
        var options = {
            hAxis: {
                title: 'OS',
            },
            vAxis: {
                title: 'Total Clicks'
            }
        };

        var chart = new google.visualization.ColumnChart(
            document.getElementById('OS_graph'));

        chart.draw(data, options);
    }

}

window.onresize = drawOS;