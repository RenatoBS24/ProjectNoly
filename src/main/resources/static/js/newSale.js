document.querySelectorAll('.checks label').forEach(label => {
    label.addEventListener('click', function() {
        document.querySelectorAll('.addProduct label').forEach(l => l.classList.remove('selected'));
        this.classList.add('selected');
    });
});
window.onload = function (){
    let id_table_pay = sessionStorage.getItem('id_table_pay');
    if(id_table_pay === null){
        id_table_pay = 1;
    }
    document.getElementById('toCartButton').setAttribute('table', id_table_pay);
}
window.addEventListener('beforeunload', function () {
    sessionStorage.removeItem('id_table_pay');
})
document.getElementById('toCartButton').addEventListener('click', function (){
   window.location.href = `/cart/${document.getElementById('toCartButton').getAttribute('table')}`;
})

function addToCart(id_table,id_product){
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    fetch(`/addToCart?id_table=${id_table}&id_product=${id_product}`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        }
    }).then(response => response.text())
        .then(data => {
            showToast(`Producto agregado a la mesa ${id_table}`, 3000);
        }).catch( error =>{
        console.log(error)
    })
}
function showToast(message, duration = 3000) {
    const toastContainer = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.textContent = message;
    toastContainer.appendChild(toast);
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);

    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toastContainer.removeChild(toast);
        }, 500);
    }, duration);
}


function showAddProductModal(){
    const modal = document.getElementById('addModal');
    modal.classList.remove('hidden')
    modal.classList.add('addProduct')
    document.querySelectorAll('.checks input[type="checkbox"]').forEach(input => {
        input.checked=false;
    });
    document.querySelectorAll('.checks label').forEach(label => {
        label.classList.remove('selected');
    });
}

function closeAddProductModal(){
    const modal = document.getElementById('addModal');
    modal.classList.add('hidden')
    modal.classList.remove('addProduct')
}

function getDataProduct(button){
    const idProduct = button.getAttribute('data-id');
    const idTable = sessionStorage.getItem('id_table_pay');
    if(idTable === null){
        const token = document.querySelector('meta[name="_csrf"]').content;
        const header = document.querySelector('meta[name="_csrf_header"]').content;
        fetch('/get-menu-by-id?id='+idProduct,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        }).then(response => response.json())
            .then(data => {
                document.getElementById('productName').textContent = data.nameProduct;
                document.getElementById('productPrice').textContent = 'S/ ' + Number(data.price).toFixed(2);
                document.getElementById('productId').value = data.id;
                document.getElementById('img').src = data.route;
                showAddProductModal();
            }).catch(error => {
            console.error('Error fetching product data:', error);
        });
    }else{
        addToCart(idTable, idProduct);
    }

}

function addProductToCart() {
    const id = document.getElementById('productId').value;
    const checkedInput = document.querySelector('.checks input[type="checkbox"]:checked');
    if (checkedInput) {
        const idTable = checkedInput.value;
        addToCart(idTable, id);
        closeAddProductModal();
    }else{
        showToast('Por favor, seleccione una mesa para agregar el producto.', 3000);
    }
}