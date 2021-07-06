google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawBrowser);

function drawBrowser() {

    var data = new google.visualization.arrayToDataTable([
        ['Browser', 'Total Clicks'],
        ['Firefox', 48],
        ['Chrome', 30],
        ['Safari', 10],
    ]);
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

window.onresize = drawBrowser;