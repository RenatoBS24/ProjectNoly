window.onload =function initView(){
    let id_table = sessionStorage.getItem('id_table');
    if(id_table === null){
        if(sessionStorage.getItem("id_table_pay") === null){
            id_table = 1;
        }else{
            id_table = sessionStorage.getItem("id_table_pay");
        }
    }
    document.getElementById('id_table').value = id_table;
    sessionStorage.removeItem('id_table');
    const name = 'table'+id_table;
    document.querySelectorAll('.container-cart').forEach(function (element) {
        element.classList.remove('container-cart');
        element.classList.add('hidden');
    })
    document.getElementById('tables').value = id_table;
    document.getElementById(name).classList.remove('hidden');
    document.getElementById(name).classList.add('container-cart');
}

document.getElementById('pay').addEventListener('change', function (){
    let id = document.getElementById('tables').value;
    updateTotal(Number(id));
});
function showCategory(button){
    document.querySelectorAll('.container-product').forEach(function (element) {
        element.classList.add('hidden');
        element.classList.remove('container-product')
    })
    const category = button.getAttribute('data-category')
    document.getElementById(category).classList.remove('hidden');
    document.getElementById(category).classList.add('container-product');
}


document.getElementById('tables').addEventListener('change', function() {
    let value = document.getElementById('tables').value;
    console.log(value)
    hideAllTables();
    showTable(value);
    updateTotal(Number(value));
});

function hideAllTables() {
    for (let i = 1; i <= 6; i++) {
        document.getElementById('table' + i).classList.remove('container-cart');
        document.getElementById('table' + i).classList.add('hidden');
    }
}

function showTable(value) {
    let table = document.getElementById('table' + value);
    document.getElementById('id_table').value = value;
    if (table) {
        table.classList.remove('hidden');
        table.classList.add('container-cart');
    }
}
function increment(button){
    let id = button.getAttribute('data-id')
    let id_table = document.getElementById('tables').value;
    const product ={
        id_product : id
    }
    fetch(`/increment?id_table=${id_table}`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    }).then(response => response.json())
        .then(data => {
            document.getElementById('subtotal'+id).textContent ='S/'+ data.subtotal.toFixed(2);
            document.getElementById('quantity'+id).textContent = data.quantity;
            updateTotal(Number(id_table));
        }).then( error =>{
        console.log(error)
    })

}

function decrement(button){
    let id = button.getAttribute('data-id')
    let id_table = document.getElementById('tables').value;
    const product ={
        id_product : id
    }
    fetch(`/decrement?id_table=${id_table}`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    }).then(response => response.json())
        .then(data => {
            document.getElementById('subtotal'+id).textContent ='S/'+ data.subtotal.toFixed(2);
            document.getElementById('quantity'+id).textContent = data.quantity;
            updateTotal(Number(id_table));
        }).then( error =>{
        console.log(error)
    })
}
function removeProduct(button) {
    let id = button.getAttribute('data-id');
    let id_table = document.getElementById('tables').value;
    const product = { id_product: id };
    sessionStorage.setItem("id_table",id_table)
    fetch(`/removeProduct?id_table=${id_table}`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    })
        .then(response => response.text())
        .then(data => {
            window.location.href = `/cart/${id_table}`;
        })
        .catch(error => {
            console.log("error:", error);
        });
}

function updateTotal(id_table){
    fetch(`/updateTotal?id_table=${id_table}`,{
        method: "GET",
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then(data => {
            let total = data;
            const payMethod = document.getElementById('pay').value;
            if (payMethod === 'tarjeta') {
                total *= 1.05;
            }
            document.getElementById('total').textContent = 'S/' + total.toFixed(2);
        }).then( error =>{
        console.log(error)
    })
}
document.getElementById('valid-sale').addEventListener('change', function() {
    if (this.checked) {
        document.getElementById('total').textContent = 'S/0.00';
    } else {
        let id = document.getElementById('tables').value;
        updateTotal(Number(id));
    }
});


