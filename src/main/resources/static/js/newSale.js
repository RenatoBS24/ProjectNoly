document.querySelectorAll('.checks label').forEach(label => {
    label.addEventListener('click', function() {
        document.querySelectorAll('.addProduct label').forEach(l => l.classList.remove('selected'));
        this.classList.add('selected');
    });
});
function add(button){
    let id_table = sessionStorage.getItem("id_table_pay");
    if(id_table === null){
        id_table = 1;
    }
    //sessionStorage.removeItem("id_table_pay"); -> lo estoy comentando por que no se si es necesario
    let id_product = button.getAttribute('data-id');
    addToCart(id_table,id_product)

}
window.onload = function (){
    let id_table_pay = sessionStorage.getItem('id_table_pay');
    if(id_table_pay === null){
        id_table_pay = 1;
    }
    document.getElementById('toCartButton').setAttribute('table', id_table_pay);
}
document.getElementById('toCartButton').addEventListener('click', function (){
   window.location.href = `/cart/${document.getElementById('toCartButton').getAttribute('table')}`;
})

function addToCart(id_table,id_product){
    fetch(`/addToCart?id_table=${id_table}&id_product=${id_product}`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
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