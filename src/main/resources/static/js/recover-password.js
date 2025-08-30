function togglePassword(inputId) {
    const passwordInput = document.getElementById(inputId);
    const toggleIcon = passwordInput.parentElement.querySelector('.toggle-password');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
    } else {
        passwordInput.type = 'password';
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('.recover-form');
    const inputs = document.querySelectorAll('.form-input');
    const passwordInput = document.getElementById('password');
    const submitButton = document.querySelector('.recover-button');
    const buttonText = document.querySelector('.button-text');
    const buttonLoader = document.querySelector('.button-loader');
    addCommonStyles();
    setupInputEvents(inputs);
    setupInputFilters();
    setupBackgroundEffects();
    setupTouchEvents();
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            checkPasswordStrength(this.value);
        });
    }
    createParticles(8, 3);
    animateExistingMessages();
    setupRecoverValidations();
    form.addEventListener('submit', function(e) {
        buttonText.style.display = 'none';
        buttonLoader.style.display = 'block';
        submitButton.disabled = true;

        let allValid = true;
        let firstInvalidField = null;

        inputs.forEach(input => {
            const isValid = validateInput(input);
            if (!isValid || input.value.length === 0) {
                allValid = false;
                if (!firstInvalidField) {
                    firstInvalidField = input;
                }
            }
        });
        if (passwordInput && checkPasswordStrength(passwordInput.value) < 50) {
            showCustomMessage('La contraseña debe ser más fuerte', 'error');
            allValid = false;
        }

        if (!allValid) {
            e.preventDefault();
            buttonText.style.display = 'block';
            buttonLoader.style.display = 'none';
            submitButton.disabled = false;

            if (firstInvalidField) {
                firstInvalidField.scrollIntoView({ behavior: 'smooth', block: 'center' });
                firstInvalidField.focus();
            }

            showCustomMessage('Por favor completa todos los campos correctamente', 'error');
        }
    });
    setTimeout(() => {
        const title = document.querySelector('.recover-title');
        if (title) {
            const originalText = title.textContent;
            typeWriter(title, originalText, 80);
        }
    }, 1500);
});
function setupRecoverValidations() {
    const inputs = document.querySelectorAll('.form-input');

    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            const isValid = validateInput(this);

            if (!isValid && this.value.length > 0) {
                let errorMessage = '';

                switch (this.id) {
                    case 'username':
                        errorMessage = 'Usuario debe tener entre 3-20 caracteres, solo letras, números y guiones bajos';
                        break;
                    case 'password':
                        errorMessage = 'La nueva contraseña debe tener al menos 6 caracteres';
                        break;
                    case 'code':
                        errorMessage = 'Código dinámico debe tener al menos 4 caracteres';
                        break;
                    default:
                        errorMessage = 'Campo requerido';
                }

                showFieldError(this, errorMessage);
            } else {
                clearFieldError(this);
            }
        });
    });
}
function typeWriter(element, text, speed = 100) {
    element.textContent = '';
    let i = 0;

    function type() {
        if (i < text.length) {
            element.textContent += text.charAt(i);
            i++;
            setTimeout(type, speed);
        }
    }

    type();
}
resetButtonState('.recover-button', '.button-text', '.button-loader');
