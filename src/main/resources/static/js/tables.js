let allProducts = [];
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
                    <button class="btn-add" data-id=${table.id_table} onclick="showProductModal(this)">Agregar</button>
                </div>
            `;
        }
    })
}

function viewTable(id_table){
    window.location.href = `/cart/${id_table}`;
    sessionStorage.removeItem("id_table");
    sessionStorage.setItem('id_table', id_table);
}
function addProduct(id_table){
    window.location.href = '/newSale';
    sessionStorage.removeItem("id_table_pay");
    sessionStorage.setItem('id_table_pay', id_table);
}
function showProductModal(button) {
    document.getElementById('productModal').classList.remove('hidden');
    console.log(button.getAttribute('data-id'));
    document.getElementById('idTable').value = button.getAttribute('data-id');
    loadAllProducts();
    document.getElementById('productSearchInput').focus();
}
function closeProductModal() {
    document.getElementById('productModal').classList.add('hidden');
    document.getElementById('productSearchInput').value = '';
}
function loadAllProducts() {
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    fetch('/get-menu-to-cart',{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        }
    })
        .then(response => response.json())
        .then(products => {
            allProducts = products;
            renderProducts(products);
        })
        .catch(error => {
            console.error('Error loading products:', error);
            showNoProducts();
        });
}
function renderProducts(products) {
    const productList = document.getElementById('productList');

    if (products.length === 0) {
        showNoProducts();
        return;
    }

    productList.innerHTML = products.map(product => `
        <div class="product-item" style="animation-delay: ${Math.random() * 0.3}s">
            <img src="${product.image || '/img/default-product.jpg'}" 
                 alt="${product.name}" 
                 class="product-image">
            <div class="product-info">
                <div class="product-name">${product.name}</div>
            </div>
            <div class="product-price">S/${product.price.toFixed(2)}</div>
            <button class="product-add-btn" data-product-id=${product.id} onclick="addProductToOrder(this)">
                Agregar
            </button>
        </div>
    `).join('');
}
function showNoProducts() {
    const productList = document.getElementById('productList');
    productList.innerHTML = `
        <div class="no-products">
            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                <path d="M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.37 2.37 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0M1.5 8.5A.5.5 0 0 1 2 9v6h1v-5a1 1 0 0 1 1-1h3a1 1 0 0 1 1 1v5h6V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5M4 15h3v-5H4zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1zm3 0h-2v3h2z"/>
            </svg>
            <p>No se encontraron productos</p>
        </div>
    `;
}

function addProductToOrder(button){
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    let tableId = document.getElementById('idTable').value;
    let productId = button.getAttribute('data-product-id');
    const productToCart ={
        idTable: tableId,
        idProduct: productId
    }
    fetch('/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(productToCart)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showSuccessToast(`Producto agregado a la mesa ${tableId}`);
        } else {
            showErrorToast('No se pudo agregar el producto a la mesa');
        }
    })
    .catch(error => {
        console.error('Error al agregar el producto:', error);
        showErrorToast('Error de conexión. Inténtalo de nuevo');
    });
}
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('productSearchInput');
    if (searchInput) {
        searchInput.addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase().trim();

            if (searchTerm === '') {
                renderProducts(allProducts);
            } else {
                const filteredProducts = allProducts.filter(product =>
                    product.name.toLowerCase().includes(searchTerm) ||
                    (product.description && product.description.toLowerCase().includes(searchTerm))
                );
                renderProducts(filteredProducts);
            }
        });
    }
});

document.addEventListener('click', function(e) {
    const modal = document.getElementById('productModal');
    if (e.target === modal) {
        closeProductModal();
    }
});
