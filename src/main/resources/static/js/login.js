document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const loader = document.querySelector('.hidden');

    form.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(form);
        loader.classList.add('loader');
        fetch('/validateLogin', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                }
            })
            .catch(error => {
                console.error('Error:', error);
                window.location.href = '/login';
            })
            .finally(() => {
                // Ocultar loader solo si hay error
                loader.style.display = 'none';
                form.style.opacity = '1';
            });
    });
});