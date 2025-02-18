function showModalExit(){
    document.getElementById('LogOutModal').classList.remove('hidden');
    document.getElementById('LogOutModal').classList.add('exitModal');
}
function closeModalExit(){
    document.getElementById('LogOutModal').classList.remove('exitModal');
    document.getElementById('LogOutModal').classList.add('hidden');
}