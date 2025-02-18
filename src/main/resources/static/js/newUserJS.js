document.addEventListener('DOMContentLoaded', (event) => {
    const numericFields = ['dni', 'phone', 'code'];

    numericFields.forEach(fieldId => {
        const field = document.getElementById(fieldId);
        field.addEventListener('input', (e) => {
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
        });
    });
});