// Bật thông báo xác nhận đăng xuất
function openLogoutModal() {
    const modalLogout = document.querySelector(".modal-logout")
    const modalContainer = document.querySelector(".modal-logout .modal-container")
    const refuseBtn = document.querySelector(".modal-logout .refuse-btn")
    //Bật modal
    modalLogout.style.display = "flex"
    // Thoát modal khi nhấn Không hoặc khi nhấn ra ngoài
    refuseBtn.addEventListener("click", (event) => {
        modalLogout.style.display = "none"
    })
    modalContainer.addEventListener("click", (event) => {
        event.stopPropagation()
    })
    modalLogout.addEventListener("click", (event) => {
        modalLogout.style.display = "none"
    })
}