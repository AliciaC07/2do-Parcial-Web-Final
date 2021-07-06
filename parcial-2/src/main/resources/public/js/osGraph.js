google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawOS);

function drawOS() {

    var data = new google.visualization.arrayToDataTable([
        ['OS', 'Total Clicks'],
        ['Windows', 48],
        ['Ubuntu', 30],
        ['Arch Linux', 10],
        ['OS X', 38]
    ]);
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

window.onresize = drawOS;