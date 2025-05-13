const tables = document.querySelectorAll('.containerTables div');
tables.forEach(table => {
    const infoElement = table.querySelector('.more-info');

    if (infoElement) {
        table.addEventListener('mouseenter', () => {
            infoElement.classList.remove('hidden');
            infoElement.style.display = 'flex';
            infoElement.style.opacity = '1';
        });
        table.addEventListener('mouseleave', () => {
            infoElement.classList.add('hidden');
            infoElement.style.opacity = '0';
            setTimeout(() => {
                if (infoElement.classList.contains('hidden')) {
                    infoElement.style.display = 'none';
                }
            }, 300);
        });
    }
});
window.onload = renderData;
async function getData() {
    try {
        const response = await fetch('/getAllTables');
        const data = await response.json();
        console.log(data);
        return data;
    } catch (error) {
        console.log('Error al obtener la data del servidor');
        return [];
    }
}
async function renderData(){
    let tables = await getData();
    console.log(tables)
    tables.forEach(table => {
        const moreInfo = document.getElementById(table.id_table);
        if (moreInfo) {
            let state = 'Libre'
            if (table.state === true){
                state = 'Ocupada'
            }
            moreInfo.innerHTML = `
                <p>Estado: ${state}</p>
                <p>Total: S/ ${table.total.toFixed(2)}</p>
                <div class="table-buttons">
                    <button class="btn-view" onclick="viewTable(${table.id_table})">Ver mesa</button>
                    <button class="btn-add" onclick="addProduct(${table.id_table})">Agregar</button>
                </div>
            `;
        }
    })
}