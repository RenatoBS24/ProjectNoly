document.getElementById('search').addEventListener('input', function() {
    const searchTerm = this.value.toLowerCase();
    const products = document.querySelectorAll('.product');

    products.forEach(product => {
        const title = product.querySelector('.title-product').textContent.toLowerCase();
        if (title.includes(searchTerm)) {
            product.style.display = '';
        } else {
            product.style.display = 'none';
        }
    });
});