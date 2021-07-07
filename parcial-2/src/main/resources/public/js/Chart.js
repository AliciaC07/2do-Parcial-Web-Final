google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawBasic);

function drawBasic() {

    var data = new google.visualization.arrayToDataTable([
        ['Date', 'Total Clicks'],
        ['Past yesterday', 48],
        ['Yesterday', 30],
        ['Today', 10]
    ]);
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