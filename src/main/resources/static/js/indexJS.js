function renderPieChart(){
    fetch('data',{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
    .then(data => {
        const total = data.total;
        const percentages = data.percentages;
        updatePieChart(total, percentages);
    }).catch(error => console.error('Error:', error));
}
window.onload = renderPieChart;

function updatePieChart(total, percentages) {
    const pieChart = document.getElementById('pieChart');
    const colors = ['#00ff00', '#800080FF', '#00BFFFFF', '#FF0000FF'];
    let gradient = 'conic-gradient(';

    let start = 0;
    percentages.forEach((percentage, index) => {
        const end = start + (percentage / total) * 100;
        gradient += `${colors[index]} ${start}% ${end}%, `;
        start = end;
    });

    gradient = gradient.slice(0, -2) + ')';
    pieChart.style.background = gradient;
}