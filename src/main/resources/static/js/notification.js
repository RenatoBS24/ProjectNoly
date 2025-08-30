// Funciones para manejar notificaciones
let currentFilter = 'all';

function filterNotifications(filter) {
    currentFilter = filter;

    // Actualizar tabs activos
    document.querySelectorAll('.filter-tab').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelector(`[data-filter="${filter}"]`).classList.add('active');

    // Filtrar notificaciones
    const notifications = document.querySelectorAll('.notification-item');
    let visibleCount = 0;

    notifications.forEach(notification => {
        const types = notification.dataset.type.split(' ');
        let shouldShow = false;

        switch(filter) {
            case 'all':
                shouldShow = true;
                break;
            case 'unread':
                shouldShow = notification.classList.contains('unread');
                break;
            case 'critical':
                shouldShow = types.includes('critical');
                break;
            case 'stock':
                shouldShow = types.includes('stock');
                break;
        }

        if (shouldShow) {
            notification.style.display = 'flex';
            visibleCount++;
        } else {
            notification.style.display = 'none';
        }
    });

    // Mostrar estado vacío si no hay notificaciones visibles
    const emptyState = document.getElementById('emptyState');
    const notificationsList = document.getElementById('notificationsList');

    if (visibleCount === 0) {
        notificationsList.style.display = 'none';
        emptyState.style.display = 'block';
    } else {
        notificationsList.style.display = 'block';
        emptyState.style.display = 'none';
    }
}

function markAsRead(notificationId) {
    const notification = document.querySelector(`[data-id="${notificationId}"]`);
    if (notification) {
        notification.classList.remove('unread');
        notification.classList.add('read');

        // Remover botón "Marcar leída"
        const markReadBtn = notification.querySelector('.mark-read');
        if (markReadBtn) {
            markReadBtn.remove();
        }

        updateCounts();

        // Aquí harías la llamada al backend
        // fetch(`/api/notifications/${notificationId}/mark-read`, { method: 'POST' });

        showToast('Notificación marcada como leída', 'success');
    }
}

function deleteNotification(notificationId) {
    const notification = document.querySelector(`[data-id="${notificationId}"]`);
    if (notification) {
        notification.style.animation = 'slideOut 0.3s ease-out forwards';

        setTimeout(() => {
            notification.remove();
            updateCounts();
            filterNotifications(currentFilter); // Re-aplicar filtro
        }, 300);

        // Aquí harías la llamada al backend
        // fetch(`/api/notifications/${notificationId}`, { method: 'DELETE' });

        showToast('Notificación eliminada', 'info');
    }
}

function markAllAsRead() {
    const unreadNotifications = document.querySelectorAll('.notification-item.unread');

    unreadNotifications.forEach(notification => {
        notification.classList.remove('unread');
        notification.classList.add('read');

        const markReadBtn = notification.querySelector('.mark-read');
        if (markReadBtn) {
            markReadBtn.remove();
        }
    });

    updateCounts();

    // Aquí harías la llamada al backend
    // fetch('/api/notifications/mark-all-read', { method: 'POST' });

    showToast(`${unreadNotifications.length} notificaciones marcadas como leídas`, 'success');
}

function deleteAllRead() {
    const readNotifications = document.querySelectorAll('.notification-item.read');

    if (readNotifications.length === 0) {
        showToast('No hay notificaciones leídas para eliminar', 'info');
        return;
    }

    readNotifications.forEach(notification => {
        notification.style.animation = 'slideOut 0.3s ease-out forwards';
        setTimeout(() => {
            notification.remove();
            updateCounts();
            filterNotifications(currentFilter);
        }, 300);
    });

    // Aquí harías la llamada al backend
    // fetch('/api/notifications/delete-read', { method: 'DELETE' });

    showToast(`${readNotifications.length} notificaciones eliminadas`, 'success');
}

function updateCounts() {
    const all = document.querySelectorAll('.notification-item').length;
    const unread = document.querySelectorAll('.notification-item.unread').length;
    const critical = document.querySelectorAll('.notification-item.critical').length;
    const stock = document.querySelectorAll('.notification-item[data-type*="stock"]').length;

    document.getElementById('count-all').textContent = all;
    document.getElementById('count-unread').textContent = unread;
    document.getElementById('count-critical').textContent = critical;
    document.getElementById('count-stock').textContent = stock;
}
const style = document.createElement('style');
style.textContent = `
    @keyframes slideOut {
        0% {
            opacity: 1;
            transform: translateX(0);
            max-height: 200px;
            margin-bottom: 15px;
        }
        100% {
            opacity: 0;
            transform: translateX(100%);
            max-height: 0;
            margin-bottom: 0;
            padding-top: 0;
            padding-bottom: 0;
        }
    }
`;
document.head.appendChild(style);
document.addEventListener('DOMContentLoaded', function() {
    updateCounts();
});