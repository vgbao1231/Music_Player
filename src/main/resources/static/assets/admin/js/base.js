const $ = document.querySelector.bind(document)
const $$ = document.querySelectorAll.bind(document)

// Kích hoạt active cho sidebar item
const currentPage = window.location.pathname.split('/').pop(); // Xác định trang hiện tại
const sidebarItems = $$(".sidebar-menu li")
sidebarItems.forEach(item => {
    if (item.id === currentPage) {
        item.classList.add('active');
    }
});

// Áp dụng thoát modal khi nhấn Không hoặc khi nhấn ra ngoài cho tất cả modal
const modals = $$(".modal")
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
    $('.modal-logout').style.display = "flex"
}

//Hiển thị ảnh xem trước
const inputImgs = $$('.input-wrapper input[name="img"]');

inputImgs.forEach(inputImg => {
    const previewImage = inputImg.parentNode.querySelector(".preview-img img")
    inputImg.addEventListener('change', function () {
        // Kiểm tra xem có ảnh nào được chọn không
        if (this.files && this.files[0]) {
            let reader = new FileReader();
            // Thực hiện khi đọc tệp hoàn tất
            reader.onload = function (e) {
                // Thiết lập đường dẫn của hình ảnh được chọn vào thuộc tính src của thẻ img
                previewImage.src = e.target.result;
            }
            // Đọc tệp hình ảnh
            reader.readAsDataURL(this.files[0]);
        } else previewImage.src = "";
    });
})

