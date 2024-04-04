const $$ = document.querySelector.bind(document)

// Kích hoạt active cho sidebar item
const currentPage = window.location.pathname.split('/').pop(); // Xác định trang hiện tại
const sidebarItems = document.querySelectorAll(".sidebar-menu li")
sidebarItems.forEach(item => {
    if (item.id === currentPage) {
        item.classList.add('active');
    }
});

// Áp dụng thoát modal khi nhấn Không hoặc khi nhấn ra ngoài cho tất cả modal
const modals = document.querySelectorAll(".modal")
modals.forEach((modal) => {
    const modalContainer = modal.querySelector(".modal-container")
    modalContainer.addEventListener("click", (event) => {
        event.stopPropagation()
    })
    modal.addEventListener("click", () => {
        modal.style.display = "none"
    })
})


// Bật thông báo xác nhận đăng xuất
function openLogoutModal() {
    //Bật modal
    $$('.modal-logout').style.display = "flex"
}


//
// // Search theo field
// function getSelectedField() {
//     const field = document.querySelector("#search-tool select")
//     const input = document.querySelector("#search-tool input")
//     switch (Number(field.value)) {
//         case 1:
//             input.name = "song"
//             break;
//         case 2:
//             input.name = "artist"
//             break;
//         default:
//     }
// }
