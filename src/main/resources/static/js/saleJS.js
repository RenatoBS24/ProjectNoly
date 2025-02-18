document.addEventListener('DOMContentLoaded', function() {
    const payMethodFilter = document.getElementById('pay-method');
    const employeeFilter = document.getElementById('employee');
    const dateFilter = document.getElementById('date');
    const sumElement = document.getElementById('sum');

    function filterTable() {
        const payMethod = payMethodFilter.value.toLowerCase();
        const employeeId = employeeFilter.value;
        const selectedDate = dateFilter.value;
        const rows = document.querySelectorAll('tbody tr');
        let totalSum = 0;

        rows.forEach(row => {
            const payMethodCell = row.cells[2].textContent.toLowerCase();
            const employeeIdCell = row.cells[6].textContent;
            const dateCell = row.cells[1].textContent.split(' ')[0];
            const totalCell = row.cells[4].textContent.replace('S/ ', '');
            const totalValue = parseFloat(totalCell);

            let showRow = true;

            if (payMethod !== 'todos' && payMethod !== payMethodCell) {
                showRow = false;
            }

            if (employeeId !== '0' && employeeId !== employeeIdCell) {
                showRow = false;
            }

            if (selectedDate && selectedDate !== dateCell.split('-').reverse().join('-')) {
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