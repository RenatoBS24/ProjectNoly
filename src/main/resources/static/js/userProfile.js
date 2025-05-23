document.addEventListener('DOMContentLoaded', function() {
    // ================== Element References ==================
    // Botón y elementos para cambiar foto
    const changePhotoBtn = document.getElementById('changePhotoBtn');
    const profileImageInput = document.getElementById('profileImageInput');
    const profileAvatar = document.querySelector('.profile-avatar');

    // Modal de contraseña
    const passwordModal = document.getElementById('passwordModal');
    const openPasswordModalBtn = document.getElementById('openPasswordModalBtn');
    const closePasswordModal = document.getElementById('closePasswordModal');
    const cancelPasswordBtn = document.getElementById('cancelPasswordBtn');

    // Campos y mensajes de contraseña
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');
    const passwordStrength = document.getElementById('passwordStrength');
    const passwordOld = document.getElementById('passwordOld');
    const passwordMatch = document.getElementById('passwordMatch');
    const savePasswordBtn = document.getElementById('savePasswordBtn');

    // Formulario de perfil
    const updateForm = document.getElementById('profileForm');
    const updateData = document.getElementById('update-data');

    // CSRF
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;

    // ================== Event Bindings ==================
    bindProfileForm();
    bindPhotoUpload();
    bindPasswordModal();
    bindPasswordValidation();
    bindSavePassword();

    // ================== Function Definitions ==================

    // ---- 1. Actualización de datos de usuario ----
    function bindProfileForm() {
        if (!updateForm) return;
        updateForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = {
                id_user: document.getElementById('id_user').value,
                username: document.getElementById('username').value,
                dni: document.getElementById('dni').value,
                phone: document.getElementById('phone').value
            };
            fetch("/user/update-data", {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                },
                body: JSON.stringify(formData)
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/login';
                    } else {
                        updateData.textContent = 'Error al actualizar los datos.';
                    }
                })
                .catch(error => {
                    updateData.textContent = 'Error al actualizar los datos.';
                    console.error('Error:', error);
                });
        });
    }

    // ---- 2. Cambio de foto de perfil ----
    function bindPhotoUpload() {
        if (!(changePhotoBtn && profileImageInput && profileAvatar)) return;

        changePhotoBtn.addEventListener('click', function() {
            profileImageInput.click();
        });

        profileImageInput.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    profileAvatar.src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        });
    }
    // ---- 4. Modal de contraseña ----
    function bindPasswordModal() {
        if (openPasswordModalBtn && passwordModal) {
            openPasswordModalBtn.addEventListener('click', function() {
                passwordModal.classList.remove('hidden');
                passwordModal.classList.add('passwordModal');
            });
        }
        if (closePasswordModal && passwordModal) {
            closePasswordModal.addEventListener('click', closeModalPassword);
        }
        if (cancelPasswordBtn && passwordModal) {
            cancelPasswordBtn.addEventListener('click', closeModalPassword);
        }

        function closeModalPassword() {
            passwordModal.classList.remove('passwordModal');
            passwordModal.classList.add('hidden');
        }
    }

    // ---- 5. Validación de contraseñas ----
    function bindPasswordValidation() {
        if (newPassword && passwordStrength) {
            newPassword.addEventListener('input', validatePasswordStrength);
        }
        if (confirmPassword && passwordMatch) {
            confirmPassword.addEventListener('input', validatePasswordMatch);
        }
    }

    function validatePasswordStrength() {
        const password = newPassword.value;
        if (password.length < 8) {
            passwordStrength.textContent = 'La contraseña debe tener al menos 8 caracteres.';
            return false;
        }
        if (!/[A-Z]/.test(password)) {
            passwordStrength.textContent = 'La contraseña debe contener al menos una letra mayúscula.';
            return false;
        }
        if (!/[a-z]/.test(password)) {
            passwordStrength.textContent = 'La contraseña debe contener al menos una letra minúscula.';
            return false;
        }
        if (!/[0-9]/.test(password)) {
            passwordStrength.textContent = 'La contraseña debe contener al menos un número.';
            return false;
        }
        passwordStrength.textContent = '';
        return true;
    }

    function validatePasswordMatch() {
        if (newPassword.value !== confirmPassword.value) {
            passwordMatch.textContent = 'Las contraseñas no coinciden.';
            return false;
        }
        passwordMatch.textContent = '';
        return true;
    }

    // ---- 6. Guardar nueva contraseña ----
    function bindSavePassword() {
        if (!savePasswordBtn) return;
        savePasswordBtn.addEventListener('click', async function() {
            const currentPassword = document.getElementById('currentPassword').value;
            if (!currentPassword) {
                passwordOld.textContent = 'La contraseña actual es obligatoria.';
                return;
            }
            let isValid = await validateOldPassword(currentPassword);
            if (!isValid) return;

            if (validatePasswordStrength() && validatePasswordMatch()) {
                const newPasswordValue = newPassword.value;
                const userID = document.getElementById('userID').value;
                fetch(`/user/updatePassword?userID=${userID}&password=${encodeURIComponent(newPasswordValue)}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        [header]: token
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/user/user-profile';
                        } else {
                            throw new Error('Error al actualizar la contraseña.');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Error al actualizar la contraseña.');
                    });
            }
        });
    }
});

// ================== Función global: validar contraseña actual ==================
async function validateOldPassword(passwordOld) {
    const userID = document.getElementById('userID').value;
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const passwordOldMsg = document.getElementById('passwordOld');
    try {
        const response = await fetch(`/user/validateOldPassword?userID=${userID}&password=${encodeURIComponent(passwordOld)}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        });

        if (!response.ok) {
            passwordOldMsg.textContent = 'Error al validar la contraseña actual.';
            return false;
        }

        const data = await response.json();
        if (data) {
            passwordOldMsg.textContent = '';
            return true;
        } else {
            passwordOldMsg.textContent = 'Contraseña actual incorrecta';
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        passwordOldMsg.textContent = 'Error al validar la contraseña actual.';
        return false;
    }
}

