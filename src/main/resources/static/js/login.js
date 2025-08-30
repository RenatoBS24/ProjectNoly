function togglePassword() {
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.querySelector('.toggle-password');

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
    const form = document.querySelector('.login-form');
    const inputs = document.querySelectorAll('.form-input');
    const submitButton = document.querySelector('.login-button');
    const buttonText = document.querySelector('.button-text');
    const buttonLoader = document.querySelector('.button-loader');
    addCommonStyles();
    setupInputEvents(inputs);
    setupBackgroundEffects();
    setupTouchEvents();
    createParticles(10, 4);
    animateExistingMessages();
    form.addEventListener('submit', function(e) {
        buttonText.style.display = 'none';
        buttonLoader.style.display = 'block';
        submitButton.disabled = true;

        let allValid = true;
        inputs.forEach(input => {
            if (!input.validity.valid || input.value.length === 0) {
                allValid = false;
            }
        });

        if (!allValid) {
            e.preventDefault();
            buttonText.style.display = 'block';
            buttonLoader.style.display = 'none';
            submitButton.disabled = false;
            showCustomMessage('Por favor completa todos los campos correctamente', 'error');
        }
    });
    setTimeout(() => {
        const title = document.querySelector('.login-title');
        if (title) {
            const originalText = title.textContent;
            typeWriter(title, originalText, 80);
        }
    }, 1500);
});
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
resetButtonState('.login-button', '.button-text', '.button-loader');
