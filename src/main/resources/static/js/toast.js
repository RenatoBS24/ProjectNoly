function showToast(message, type = 'default', duration = 3500) {
    const toastContainer = document.getElementById('toast-container');
    const toast = document.createElement('div');

    const icons = {
        success: '✓',
        error: '✗',
        info: 'ℹ',
        default: '🍽️'
    };

    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span class="toast-icon">${icons[type] || icons.default}</span>
        <span>${message}</span>
    `;

    toastContainer.appendChild(toast);
    setTimeout(() => {
        toast.classList.add('show');
    }, 50);

    setTimeout(() => {
        hideToast(toast);
    }, duration);

    toast.addEventListener('click', () => {
        hideToast(toast);
    });
}

function hideToast(toast) {
    if (toast && toast.parentNode) {
        toast.classList.add('hiding');
        toast.classList.remove('show');

        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 300);
    }
}
function showSuccessToast(message, duration) {
    showToast(message, 'success', duration);
}

function showErrorToast(message, duration) {
    showToast(message, 'error', duration);
}

function showInfoToast(message, duration) {
    showToast(message, 'info', duration);
}