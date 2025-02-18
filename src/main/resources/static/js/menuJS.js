document.getElementById('price').addEventListener('input', function (e) {
    this.value = this.value.replace(/[^0-9.]/g, '');
});

document.getElementById('new_price').addEventListener('input', function (e) {
    this.value = this.value.replace(/[^0-9.]/g, '');
});
document.getElementById('code').addEventListener('input', function (e) {
    this.value = this.value.replace(/[^0-9.]/g, '');
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

function showEditModal(button){
    document.getElementById('editModal').classList.remove('hidden');
    document.getElementById('editModal').classList.add('editModal');
    document.getElementById('nameProduct').value = button.getAttribute('data-nameProduct');
    document.getElementById('price').value = button.getAttribute('data-price');
    document.getElementById('description').textContent = button.getAttribute('data-description');
    document.getElementById('category').value = button.getAttribute('data-category');
    document.getElementById('id_menu').value = button.getAttribute('data-id-menu');

    let list = JSON.parse(button.getAttribute('data-ingredients'))
    let id_ingredients = [];
    list.forEach((item) =>{
        id_ingredients.push(item.ingredient);
    })
    document.querySelectorAll('input[type="checkbox"]').forEach((item) =>{
       item.checked = false;
    });
    document.querySelectorAll('input[type="checkbox"]').forEach((item) =>{
        if(id_ingredients.includes(Number(item.value))){
            item.checked = true;
        }
    });

    console.log(id_ingredients)

}
function closeEditModal(){
    document.getElementById('editModal').classList.remove('editModal');
    document.getElementById('editModal').classList.add('hidden');
}

function showDeleteModal(button){
    document.getElementById('deleteModal').classList.remove('hidden');
    document.getElementById('deleteModal').classList.add('deleteModal');
    document.getElementById('id').value = button.getAttribute('data-id-menu');
}

function closeDeleteModal(){
    document.getElementById('deleteModal').classList.remove('deleteModal');
    document.getElementById('deleteModal').classList.add('hidden');
}

function showAddModal(){
    document.getElementById('addModal').classList.remove('hidden');
    document.getElementById('addModal').classList.add('editModal');
    document.querySelectorAll('input[type="checkbox"]').forEach((item) =>{
        item.checked = false;
    });
}
function closeAddModal(){
    document.getElementById('addModal').classList.remove('editModal');
    document.getElementById('addModal').classList.add('hidden');
}


