document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.getElementById('sidebar');
    const toggleButton = document.getElementById('toggleButton');


    function toggleSidebar() {
        sidebar.classList.toggle('expanded');

        // Guardar el estado del sidebar en localStorage
        const isExpanded = sidebar.classList.contains('expanded');
        localStorage.setItem('sidebarExpanded', isExpanded);
    }


    toggleButton.addEventListener('click', toggleSidebar);


    const savedState = localStorage.getItem('sidebarExpanded');
    if (savedState === 'true') {
        sidebar.classList.add('expanded');
    }
});