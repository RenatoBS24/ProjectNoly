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


function getMenuData(idMenu){
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    fetch('/get-data-menu-by-id?id=' + idMenu, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        }
    }).then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById('nameProduct').value = data.name;
            let price = Number(data.price);
            document.getElementById('price').value = price.toFixed(2);
            document.getElementById('description').textContent = data.description;
            document.getElementById('category').value = data.idCategory;
            document.getElementById('id_menu').value = data.id;

            let list = data.menuIngredientDtoList
            let id_ingredients = [];
            list.forEach((item) => {
                id_ingredients.push(item.idIngredient);
            });
            document.querySelectorAll('input[type="checkbox"]').forEach((item) => {
                item.checked = false;
            });
            document.querySelectorAll('input[type="checkbox"]').forEach((item) => {
                if (id_ingredients.includes(Number(item.value))) {
                    item.checked = true;
                }
            });

        }).catch(error => {
        console.error('Error fetching menu data:', error);
    });
}

function showEditMenuModal(button){
    document.getElementById('editModal').classList.remove('hidden');
    document.getElementById('editModal').classList.add('editModal');
    getMenuData(button.getAttribute('data-id-menu'));
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


