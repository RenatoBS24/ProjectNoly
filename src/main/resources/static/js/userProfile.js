document.addEventListener('DOMContentLoaded', function() {
    const changePhotoBtn = document.getElementById('changePhotoBtn');
    const profileImageInput = document.getElementById('profileImageInput');
    const profileAvatar = document.querySelector('.profile-avatar');
    const toggleButton = document.getElementById('toggleButton');
    const sidebar = document.querySelector('.sidebar');
    const main = document.querySelector('.main-content');
    const passwordModal = document.getElementById('passwordModal');
    const openPasswordModalBtn = document.getElementById('openPasswordModalBtn');
    const closePasswordModal = document.getElementById('closePasswordModal');
    const cancelPasswordBtn = document.getElementById('cancelPasswordBtn');
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');
    const passwordStrength = document.getElementById('passwordStrength');
    const passwordOld = document.getElementById('passwordOld');
    const passwordMatch = document.getElementById('passwordMatch');
    const savePasswordBtn = document.getElementById('savePasswordBtn');
    const updateForm = document.getElementById('profileForm');
    const updateData = document.getElementById('update-data');

    updateForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData ={
            id_user: document.getElementById('id_user').value,
            username: document.getElementById('username').value,
            dni: document.getElementById('dni').value,
            phone: document.getElementById('phone').value
        }
        console.log("El metodo llego hasta aqui xd")
        fetch("/update-data",{
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                window.location.href = '/user-profile';
            } else {
                updateData.textContent = 'Error al actualizar los datos.';
            }
        })
            .catch(error => console.error('Error:', error));
    })
    if (changePhotoBtn && profileImageInput && profileAvatar) {
        changePhotoBtn.addEventListener('click', function() {
            profileImageInput.click();
        });

        profileImageInput.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    profileAvatar.src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });
    }
    if (toggleButton && sidebar && main) {
        toggleButton.addEventListener('click', function() {
            sidebar.classList.toggle('expanded');
            if (sidebar.classList.contains('expanded')) {
                main.style.marginLeft = '160px';
                main.style.width = 'calc(100% - 160px)';
            } else {
                main.style.marginLeft = '84px';
                main.style.width = 'calc(100% - 84px)';
            }
        });
    }

    if (openPasswordModalBtn && passwordModal) {
        openPasswordModalBtn.addEventListener('click', function() {
            passwordModal.classList.remove('hidden');
            passwordModal.classList.add('passwordModal');
        });
    }

    if (closePasswordModal && passwordModal) {
        closePasswordModal.addEventListener('click', function() {
            passwordModal.classList.remove('passwordModal');
            passwordModal.classList.add('hidden');
        });
    }

    if (cancelPasswordBtn && passwordModal) {
        cancelPasswordBtn.addEventListener('click', function() {
            passwordModal.classList.remove('passwordModal');
            passwordModal.classList.add('hidden');
        });
    }

    if (newPassword && passwordStrength) {
        newPassword.addEventListener('input', function() {
            validatePasswordStrength();
        });
    }

    if (confirmPassword && passwordMatch) {
        confirmPassword.addEventListener('input', function() {
            validatePasswordMatch();
        });
    }
    function validatePasswordStrength() {
        const password = newPassword.value;
        if (password.length < 8) {
            passwordStrength.textContent = 'La contraseña debe tener al menos 8 caracteres.';
            return false;
        } else if (!/[A-Z]/.test(password)) {
            passwordStrength.textContent = 'La contraseña debe contener al menos una letra mayúscula.';
            return false;
        } else if (!/[a-z]/.test(password)) {
            passwordStrength.textContent = 'La contraseña debe contener al menos una letra minúscula.';
            return false;
        } else if (!/[0-9]/.test(password)) {
            passwordStrength.textContent = 'La contraseña debe contener al menos un número.';
            return false;
        } else {
            passwordStrength.textContent = '';
            return true;
        }
    }
    function validatePasswordMatch() {
        if (newPassword.value !== confirmPassword.value) {
            passwordMatch.textContent = 'Las contraseñas no coinciden.';
            return false;
        } else {
            passwordMatch.textContent = '';
            return true;
        }
    }
    if (savePasswordBtn) {
        savePasswordBtn.addEventListener('click',  async function() {
            const currentPassword = document.getElementById('currentPassword').value;
            let isValid = await validateOldPassword(currentPassword);
            console.log(isValid);
            if (isValid === false) {
                return;
            }
            if (!currentPassword) {
                passwordOld.textContent = 'La contraseña actual es obligatoria.';
                return;
            }
            if (validatePasswordStrength() && validatePasswordMatch() && isValid) {
                console.log('Contraseña válida');
                let newPasswordValue = newPassword.value;
                let userID = document.getElementById('userID').value;
                fetch(`/updatePassword?userID=${userID}&password=${newPasswordValue}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                }).then(response => {
                    if (response.ok) {
                        window.location.href = '/user-profile';
                    } else {
                        throw new Error('Error al actualizar la contraseña.');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Error al actualizar la contraseña.');
                })

            }
        });
    }
});

async function validateOldPassword(passwordOld) {
    let userID = document.getElementById('userID').value;
    console.log(userID);
    console.log(passwordOld);
    try {
        const response = await fetch(`/validateOldPassword?userID=${userID}&password=${passwordOld}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        if (!response.ok) {
            document.getElementById('passwordOld').textContent = 'Error al validar la contraseña actual.';
            return false;
        }

        const data = await response.json();
        console.log(data);
        if (data) {
            document.getElementById('passwordOld').textContent = '';
            return true;
        } else {
            document.getElementById('passwordOld').textContent = 'Contraseña actual incorrecta';
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('passwordOld').textContent = 'Error al validar la contraseña actual.';
        return false;
    }
}

