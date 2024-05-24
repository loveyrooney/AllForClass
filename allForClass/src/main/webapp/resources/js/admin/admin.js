window.onload=function () {
    const tabLinks = document.querySelectorAll('.tablink');
    const tabContents = document.querySelectorAll('.content');

    tabLinks.forEach((tabLink, index) => {
        tabLink.addEventListener('click', () => {
            tabContents.forEach((tabContent, i) => {
                if (i === index) {
                    tabContent.style.display = 'table';
                } else {
                    tabContent.style.display = 'none';
                }
            });

            tabLinks.forEach(link => {
                link.classList.remove('active');
            });
            tabLink.classList.add('active');
        });
    });

    tabContents[0].style.display = 'table';
    tabLinks[0].classList.add('active');
}