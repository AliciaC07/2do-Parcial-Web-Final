google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawBasic);

let info = JSON.parse(document.getElementById('chart_div').getAttribute('data-dates'));
console.log(info);
for(let i=0; i < info.length; i++){
    info[i].date = info[i].date.year+'-'+info[i].date.month+'-'+info[i].date.day;
}
console.log(info);
function drawBasic() {

    let arrayData=[];
    for(let i=0; i < info.length+1; i ++){
        if(i === 0){
            arrayData[i] = ['Date', 'Total Clicks']
        }else
            arrayData[i] = [info[i-1].date,info[i-1].quantity]
    }

    var data = new google.visualization.arrayToDataTable(arrayData);
    var options = {
        hAxis: {
            title: 'Day',
        },
        vAxis: {
            title: 'Total Clicks'
        }
    };

    var chart = new google.visualization.ColumnChart(
        document.getElementById('chart_div'));

    chart.draw(data, options);
}

window.onresize = drawBasic;