// Funciones de validación comunes
function validateInput(input) {
    const container = input.closest('.input-container');
    const icon = container.querySelector('.input-icon');
    let isValid = false;

    switch (input.id) {
        case 'username':
            isValid = /^[a-zA-Z0-9_]{3,20}$/.test(input.value);
            break;
        case 'password':
            isValid = input.value.length >= 6;
            break;
        case 'employeeName':
        case 'employeeLastName':
            isValid = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]{2,}$/.test(input.value);
            break;
        case 'dni':
            isValid = /^[0-9]{8}$/.test(input.value);
            break;
        case 'phone':
            isValid = /^9[0-9]{8}$/.test(input.value);
            break;
        case 'userType':
            isValid = input.value !== '';
            break;
        case 'code':
            isValid = input.value.length >= 4;
            break;
        default:
            isValid = input.value.length > 0;
    }

    if (isValid) {
        container.classList.add('valid');
        container.classList.remove('invalid');
        if (icon) icon.style.color = '#27ae60';
    } else if (input.value.length > 0) {
        container.classList.add('invalid');
        container.classList.remove('valid');
        if (icon) icon.style.color = '#e74c3c';
    } else {
        container.classList.remove('valid', 'invalid');
        if (icon) icon.style.color = '#e5b322';
    }

    return isValid;
}

// Función para mostrar errores de campo
function showFieldError(input, message) {
    const container = input.closest('.input-container') || input.closest('.input-group');
    let errorDiv = container.querySelector('.field-error');

    if (!errorDiv) {
        errorDiv = document.createElement('div');
        errorDiv.className = 'field-error';
        errorDiv.style.cssText = `
            color: #e74c3c;
            font-size: 12px;
            margin-top: 5px;
            padding-left: 5px;
            display: flex;
            align-items: center;
            gap: 5px;
        `;
        container.appendChild(errorDiv);
    }

    errorDiv.innerHTML = `<i class="fas fa-exclamation-circle"></i><span>${message}</span>`;
}

// Función para limpiar errores de campo
function clearFieldError(input) {
    const container = input.closest('.input-container') || input.closest('.input-group');
    const errorDiv = container.querySelector('.field-error');
    if (errorDiv) {
        errorDiv.remove();
    }
}

// Función para verificar fortaleza de contraseña
function checkPasswordStrength(password) {
    const strengthBar = document.querySelector('.strength-fill');
    const strengthText = document.querySelector('.strength-text');

    if (!strengthBar || !strengthText) return 0;

    let strength = 0;
    let feedback = 'Muy débil';

    if (password.length >= 6) strength += 20;
    if (password.length >= 8) strength += 15;
    if (/[A-Z]/.test(password)) strength += 15;
    if (/[a-z]/.test(password)) strength += 15;
    if (/[0-9]/.test(password)) strength += 15;
    if (/[^A-Za-z0-9]/.test(password)) strength += 20;

    strengthBar.style.width = strength + '%';

    if (strength < 40) {
        strengthBar.style.background = '#e74c3c';
        feedback = 'Muy débil';
        strengthText.style.color = '#e74c3c';
    } else if (strength < 60) {
        strengthBar.style.background = '#f39c12';
        feedback = 'Débil';
        strengthText.style.color = '#f39c12';
    } else if (strength < 80) {
        strengthBar.style.background = '#f1c40f';
        feedback = 'Buena';
        strengthText.style.color = '#f1c40f';
    } else {
        strengthBar.style.background = '#27ae60';
        feedback = 'Excelente';
        strengthText.style.color = '#27ae60';
    }

    strengthText.textContent = feedback;
    return strength;
}

// Función para crear efecto ripple
function createRipple(element) {
    const container = element.closest('.input-container');
    if (!container) return;

    const ripple = document.createElement('div');
    ripple.classList.add('ripple');
    ripple.style.cssText = `
        position: absolute;
        border-radius: 50%;
        background: rgba(229, 179, 34, 0.3);
        transform: scale(0);
        animation: ripple-animation 0.6s linear;
        pointer-events: none;
        width: 20px;
        height: 20px;
        left: 20px;
        top: 50%;
        margin-top: -10px;
    `;

    container.appendChild(ripple);

    setTimeout(() => {
        ripple.remove();
    }, 600);
}

// Función para mostrar mensajes personalizados
function showCustomMessage(message, type = 'error') {
    let messageDiv = document.querySelector('.custom-message');

    if (!messageDiv) {
        messageDiv = document.createElement('div');
        messageDiv.className = 'custom-message';

        const form = document.querySelector('form');
        const button = form.querySelector('button[type="submit"]');
        button.parentNode.insertBefore(messageDiv, button);
    }

    const colors = {
        error: 'linear-gradient(135deg, #ff6b6b, #ff5252)',
        success: 'linear-gradient(135deg, #2ecc71, #27ae60)',
        warning: 'linear-gradient(135deg, #f39c12, #e67e22)'
    };

    const icons = {
        error: 'fas fa-exclamation-triangle',
        success: 'fas fa-check-circle',
        warning: 'fas fa-exclamation-circle'
    };

    messageDiv.style.cssText = `
        background: ${colors[type]};
        color: white;
        padding: 12px 15px;
        border-radius: 10px;
        margin-bottom: 20px;
        display: flex;
        align-items: center;
        gap: 10px;
        font-size: 14px;
        animation: slideDown 0.3s ease;
        opacity: 0;
        transform: translateY(-10px);
        transition: all 0.3s ease;
    `;

    messageDiv.innerHTML = `<i class="${icons[type]}"></i><span>${message}</span>`;

    setTimeout(() => {
        messageDiv.style.opacity = '1';
        messageDiv.style.transform = 'translateY(0)';
    }, 10);

    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.style.opacity = '0';
            messageDiv.style.transform = 'translateY(-10px)';
            setTimeout(() => {
                if (messageDiv.parentNode) {
                    messageDiv.remove();
                }
            }, 300);
        }
    }, 5000);
}

// Función para resetear estado del botón
function resetButtonState(buttonSelector, textSelector, loaderSelector) {
    window.addEventListener('load', function() {
        const submitButton = document.querySelector(buttonSelector);
        const buttonText = document.querySelector(textSelector);
        const buttonLoader = document.querySelector(loaderSelector);

        if (submitButton && buttonText && buttonLoader) {
            buttonText.style.display = 'block';
            buttonLoader.style.display = 'none';
            submitButton.disabled = false;
        }
    });
}

// Funciones de configuración comunes
function addCommonStyles() {
    const style = document.createElement('style');
    style.textContent = `
        @keyframes ripple-animation {
            to {
                transform: scale(4);
                opacity: 0;
            }
        }
        
        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            75% { transform: translateX(5px); }
        }
        
        @keyframes float {
            0%, 100% {
                transform: translateY(0) rotate(0deg);
                opacity: 0.6;
            }
            50% {
                transform: translateY(-20px) rotate(180deg);
                opacity: 1;
            }
        }
        
        .field-error {
            animation: fadeIn 0.3s ease;
        }
        
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-5px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .input-container.focused .form-input,
        .input-container.focused .form-select {
            transform: translateY(-1px);
        }

        .input-container.valid .form-input,
        .input-container.valid .form-select {
            border-color: #27ae60;
            box-shadow: 0 0 0 3px rgba(39, 174, 96, 0.1);
        }

        .input-container.invalid .form-input,
        .input-container.invalid .form-select {
            border-color: #e74c3c;
            box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.1);
            animation: shake 0.3s ease-in-out;
        }
    `;
    document.head.appendChild(style);
}

function setupInputEvents(inputs, selects = []) {
    [...inputs, ...selects].forEach(element => {
        element.addEventListener('focus', function() {
            this.closest('.input-container').classList.add('focused');
            if (this.tagName === 'INPUT') {
                createRipple(this);
            }
        });

        element.addEventListener('blur', function() {
            this.closest('.input-container').classList.remove('focused');
            validateInput(this);
        });

        element.addEventListener('input', function() {
            validateInput(this);
            clearFieldError(this);

            const errorMessage = document.querySelector('.error-message');
            const successMessage = document.querySelector('.success-message');
            if (errorMessage) errorMessage.style.opacity = '0.5';
            if (successMessage) successMessage.style.opacity = '0.5';
        });

        element.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                const container = this.closest('.input-container');
                container.style.transform = 'scale(0.98)';
                setTimeout(() => {
                    container.style.transform = 'scale(1)';
                }, 100);
            }
        });
    });
}

function setupInputFilters() {
    const dniInput = document.getElementById('dni');
    const phoneInput = document.getElementById('phone');

    if (dniInput) {
        dniInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '').slice(0, 8);
        });
    }

    if (phoneInput) {
        phoneInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '').slice(0, 9);
        });
    }
}

function setupBackgroundEffects() {
    document.addEventListener('mousemove', function(e) {
        const background = document.querySelector('.background-blur');
        if (background) {
            const x = (e.clientX / window.innerWidth) * 6;
            const y = (e.clientY / window.innerHeight) * 6;
            background.style.transform = `scale(1.1) translate(${x}px, ${y}px)`;
        }
    });
}

function setupTouchEvents() {
    document.addEventListener('touchstart', function() {
        const meta = document.querySelector('meta[name="viewport"]');
        if (meta) {
            meta.setAttribute('content', 'width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no');
        }
    });

    document.addEventListener('touchend', function() {
        setTimeout(() => {
            const meta = document.querySelector('meta[name="viewport"]');
            if (meta) {
                meta.setAttribute('content', 'width=device-width, initial-scale=1.0');
            }
        }, 500);
    });
}

function createParticles(count = 10, size = 4) {
    const container = document.querySelector('.container');
    if (!container) return;

    for (let i = 0; i < count; i++) {
        const particle = document.createElement('div');
        particle.style.cssText = `
            position: absolute;
            width: ${size}px;
            height: ${size}px;
            background: rgba(229, 179, 34, 0.6);
            border-radius: 50%;
            pointer-events: none;
            animation: float ${5 + Math.random() * 5}s ease-in-out infinite;
            left: ${Math.random() * 100}%;
            top: ${Math.random() * 100}%;
            animation-delay: ${Math.random() * 5}s;
        `;
        container.appendChild(particle);
    }
}

function animateExistingMessages() {
    const existingError = document.querySelector('.error-message');
    const existingSuccess = document.querySelector('.success-message');

    [existingError, existingSuccess].forEach(msg => {
        if (msg) {
            msg.style.opacity = '0';
            msg.style.transform = 'translateY(-10px)';

            setTimeout(() => {
                msg.style.transition = 'all 0.3s ease';
                msg.style.opacity = '1';
                msg.style.transform = 'translateY(0)';
            }, 100);
        }
    });
}
