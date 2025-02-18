function showDeleteModal(button){
    document.getElementById('deleteModal').classList.remove('hidden');
    document.getElementById('deleteModal').classList.add('deleteModal');
    document.getElementById('id').value = button.getAttribute('data-id-ingredient');
}

function closeDeleteModal(){
    document.getElementById('deleteModal').classList.remove('deleteModal');
    document.getElementById('deleteModal').classList.add('hidden');
}

function showEditModal(button){
    document.getElementById('editModal').classList.remove('hidden');
    document.getElementById('editModal').classList.add('editModal');
    document.getElementById('name-ingredient').value = button.getAttribute('data-name');
    let price = Number(button.getAttribute('data-price'));
    document.getElementById('price').value = price.toFixed(2);
    document.getElementById('id_ingredient').value = button.getAttribute('data-id-ingredient');
    document.getElementById('stock').value = button.getAttribute('data-stock');
    document.getElementById('img').src = '/'+button.getAttribute('data-img');
}
function closeEditModal(){
    document.getElementById('editModal').classList.remove('editModal');
    document.getElementById('editModal').classList.add('hidden');
}
function showAddModal(){
    document.getElementById('addModal').classList.remove('hidden');
    document.getElementById('addModal').classList.add('editModal');
}
function closeAddModal(){
    document.getElementById('addModal').classList.remove('editModal');
    document.getElementById('addModal').classList.add('hidden');
}