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
    const form = document.querySelector('.register-form');
    const inputs = document.querySelectorAll('.form-input');
    const selects = document.querySelectorAll('.form-select');
    const passwordInput = document.getElementById('password');
    const submitButton = document.querySelector('.register-button');
    const buttonText = document.querySelector('.button-text');
    const buttonLoader = document.querySelector('.button-loader');

    // Agregar estilos comunes
    addCommonStyles();

    // Configurar eventos de entrada
    setupInputEvents(inputs, selects);

    // Configurar filtros específicos
    setupInputFilters();

    // Configurar efectos de fondo
    setupBackgroundEffects();

    // Configurar eventos táctiles
    setupTouchEvents();

    // Event listener específico para verificación de fortaleza de contraseña
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            checkPasswordStrength(this.value);
        });
    }

    // Crear partículas
    createParticles(8, 3);

    // Animar mensajes existentes
    animateExistingMessages();

    // Configurar validaciones específicas para registro
    setupRegisterValidations();

    // Manejo del formulario
    form.addEventListener('submit', function(e) {
        buttonText.style.display = 'none';
        buttonLoader.style.display = 'block';
        submitButton.disabled = true;

        let allValid = true;
        let firstInvalidField = null;

        // Validar inputs
        inputs.forEach(input => {
            const isValid = validateInput(input);
            if (!isValid || input.value.length === 0) {
                allValid = false;
                if (!firstInvalidField) {
                    firstInvalidField = input;
                }
            }
        });

        // Validar selects
        selects.forEach(select => {
            const isValid = validateInput(select);
            if (!isValid || select.value === '') {
                allValid = false;
                if (!firstInvalidField) {
                    firstInvalidField = select;
                }
            }
        });

        // Validación específica de fortaleza de contraseña
        if (passwordInput && checkPasswordStrength(passwordInput.value) < 50) {
            showCustomMessage('La contraseña debe ser más fuerte', 'error');
            allValid = false;
        }

        // Validación específica de teléfono que empiece con 9
        const phoneInput = document.getElementById('phone');
        if (phoneInput && !phoneInput.value.startsWith('9')) {
            showCustomMessage('El número de celular debe empezar con 9', 'error');
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

    // Efecto de escritura en el título (igual que en login.js)
    setTimeout(() => {
        const title = document.querySelector('.register-title');
        if (title) {
            const originalText = title.textContent;
            typeWriter(title, originalText, 80);
        }
    }, 1500);
});

// Función para configurar validaciones específicas de registro
function setupRegisterValidations() {
    const inputs = document.querySelectorAll('.form-input');
    const selects = document.querySelectorAll('.form-select');

    [...inputs, ...selects].forEach(element => {
        element.addEventListener('blur', function() {
            const isValid = validateInput(this);

            if (!isValid && this.value.length > 0) {
                let errorMessage = '';

                switch (this.id) {
                    case 'username':
                        errorMessage = 'Usuario debe tener entre 3-20 caracteres, solo letras, números y guiones bajos';
                        break;
                    case 'password':
                        errorMessage = 'La contraseña debe tener al menos 6 caracteres';
                        break;
                    case 'employeeName':
                    case 'employeeLastName':
                        errorMessage = 'Solo se permiten letras y espacios, mínimo 2 caracteres';
                        break;
                    case 'dni':
                        errorMessage = 'DNI debe tener exactamente 8 dígitos';
                        break;
                    case 'phone':
                        errorMessage = 'Celular debe tener 9 dígitos y empezar con 9';
                        break;
                    case 'userType':
                        errorMessage = 'Debe seleccionar un rol';
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

    // Validación especial para teléfono
    const phoneInput = document.getElementById('phone');
    if (phoneInput) {
        phoneInput.addEventListener('blur', function() {
            if (this.value.length > 0 && !this.value.startsWith('9')) {
                showFieldError(this, 'El número debe empezar con 9');
            }
        });
    }
}

// Función de efecto typewriter (igual que en login.js)
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

// Resetear estado del botón
resetButtonState('.register-button', '.button-text', '.button-loader');
