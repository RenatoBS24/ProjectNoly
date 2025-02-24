document.addEventListener('DOMContentLoaded', function() {
    const payMethodFilter = document.getElementById('pay-method');
    const employeeFilter = document.getElementById('employee');
    const dateFilter = document.getElementById('date');
    const monthsFilter = document.getElementById('months');
    const sumElement = document.getElementById('sum');

    function filterTable() {
        const payMethod = payMethodFilter.value.toLowerCase();
        const employeeId = employeeFilter.value;
        const selectedDate = dateFilter.value;
        const selectedMonths = monthsFilter.value;
        const rows = document.querySelectorAll('tbody tr');
        let totalSum = 0;

        const today = new Date();
        let startDate = null;

        if (selectedMonths !== '0') {
            switch(selectedMonths) {
                case '1': // Este mes
                    startDate = new Date(today.getFullYear(), today.getMonth(), 1);
                    break;
                case '2': // Mes pasado
                    startDate = new Date(today.getFullYear(), today.getMonth() - 1, 1);
                    today.setMonth(today.getMonth() - 1);
                    today.setDate(0);
                    break;
                case '3': // Últimos 3 meses
                    startDate = new Date(today.getFullYear(), today.getMonth() - 3, 1);
                    break;
                case '4': // Últimos 6 meses
                    startDate = new Date(today.getFullYear(), today.getMonth() - 6, 1);
                    break;
                case '5': // Este año
                    startDate = new Date(today.getFullYear(), 0, 1);
                    break;
            }
        }

        rows.forEach(row => {
            const payMethodCell = row.cells[2].textContent.toLowerCase();
            const employeeIdCell = row.cells[6].textContent;
            const dateCell = row.cells[1].textContent.split(' ')[0];
            const totalCell = row.cells[4].textContent.replace('S/ ', '');
            const totalValue = parseFloat(totalCell);
            const rowDate = new Date(dateCell.split('-').reverse().join('-'));

            let showRow = true;

            if (payMethod !== 'todos' && payMethod !== payMethodCell) {
                showRow = false;
            }

            if (employeeId !== '0' && employeeId !== employeeIdCell) {
                showRow = false;
            }

            if (selectedMonths !== '0') {
                if (rowDate < startDate || rowDate > today) {
                    showRow = false;
                }
            } else if (selectedDate && selectedDate !== dateCell.split('-').reverse().join('-')) {
                showRow = false;
            }

            if (showRow) {
                totalSum += totalValue;
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });

        sumElement.textContent = 'S/ ' + totalSum.toFixed(2);
    }

    payMethodFilter.addEventListener('change', filterTable);
    employeeFilter.addEventListener('change', filterTable);
    dateFilter.addEventListener('input', filterTable);
    monthsFilter.addEventListener('change', function() {
        if (this.value !== '0') {
            dateFilter.value = '';
        }
        filterTable();
    });
    filterTable();
});

function showDeleteModal(button){
    document.getElementById('deleteModal').classList.remove('hidden');
    document.getElementById('deleteModal').classList.add('deleteModal');
    document.getElementById('id').value = button.getAttribute('data-id');
}

function closeDeleteModal(){
    document.getElementById('deleteModal').classList.remove('deleteModal');
    document.getElementById('deleteModal').classList.add('hidden');
}