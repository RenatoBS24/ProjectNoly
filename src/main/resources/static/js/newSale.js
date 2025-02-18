document.querySelectorAll('.checks label').forEach(label => {
    label.addEventListener('click', function() {
        document.querySelectorAll('.addProduct label').forEach(l => l.classList.remove('selected'));
        this.classList.add('selected');
    });
});
function showModal(button){
    document.getElementById('addModal').classList.remove("hidden");
    document.getElementById('addModal').classList.add("addProduct");
    document.getElementById('price') .textContent = 'S/ '+Number(button.getAttribute('data-price')).toFixed(2);
    document.getElementById('price_product').value = button.getAttribute('data-price');
    document.getElementById('name_product').textContent = button.getAttribute('data-name');
    document.getElementById('id_product').value = button.getAttribute('data-id');
    document.getElementById('img').src = '/'+button.getAttribute('data-img');
    document.getElementById('route').value=button.getAttribute('data-img');
    document.querySelectorAll('.checks input[type="checkbox"]').forEach(input => {
        input.checked=false;
    });
    document.querySelectorAll('.checks label').forEach(label => {
        label.classList.remove('selected');
    });
}
function closeModal(){
    document.getElementById('addModal').classList.add("hidden");
    document.getElementById('addModal').classList.remove("addProduct");
}
function addProduct(){
    let id = document.getElementById('id_product').value;
    let name = document.getElementById('name_product').textContent;
    let price = document.getElementById('price_product').value;
    let route = document.getElementById('route').value;
    // codigo que recorra todos los checkbox
    let table_id = 0;
    document.querySelectorAll('.checks input[type="checkbox"]').forEach(input => {
        if(input.checked){
            table_id = input.value;
        }
    });
    console.log(table_id)
    addToCart(id,name,price,table_id,route);
}
function addToCart(id,name,price,table_id,route){
    let id_product = id
    let name_product = name
    let price_product = price
    let id_table = table_id
    const product ={
        id_product: id_product,
        name: name_product,
        price: price_product,
        subtotal : price_product,
        quantity: 1,
        route: route
    }
    console.log(product)
    fetch(`/addToCart?id_table=${id_table}`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    }).then(response => response.text())
        .then(data => {
            closeModal();
            showToast('Producto agregado al carrito');
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