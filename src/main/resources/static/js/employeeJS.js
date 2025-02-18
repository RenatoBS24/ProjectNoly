function showEditModal(button){
    document.getElementById('modalEdit').classList.remove('hidden');
    document.getElementById('modalEdit').classList.add('modal');
    document.getElementById('id_employee').value = button.getAttribute('data-id');
    document.getElementById('name').value = button.getAttribute('data-name');
    document.getElementById('lastname').value = button.getAttribute('data-lastname');
    document.getElementById('dni').value = button.getAttribute('data-dni');
    document.getElementById('phone').value = button.getAttribute('data-phone');
}
function closeEditModal(){
    document.getElementById('modalEdit').classList.remove('modal');
    document.getElementById('modalEdit').classList.add('hidden');
}
function showDeleteModal(button){
    document.getElementById('modalDelete').classList.remove('hidden');
    document.getElementById('modalDelete').classList.add('deleteModal')
    document.getElementById('id').value = button.getAttribute('data-id')
}
function closeDeleteModal(){
    document.getElementById('modalDelete').classList.remove('deleteModal')
    document.getElementById('modalDelete').classList.add('hidden');
}

function showAddModal(){
    document.getElementById('addEmployee').classList.remove('hidden');
    document.getElementById('addEmployee').classList.add('modal');
}
function closeAddModal(){
    document.getElementById('addEmployee').classList.remove('modal');
    document.getElementById('addEmployee').classList.add('hidden');
}

document.getElementById('phone').addEventListener('input', function (e) {
    this.value = this.value.replace(/[^0-9]/g, '');
});

document.getElementById('dni').addEventListener('input', function (e) {
    this.value = this.value.replace(/[^0-9]/g, '');
});