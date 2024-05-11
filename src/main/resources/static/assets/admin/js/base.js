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

// Thống báo toast
const toastContainer = $("#toast")
if (toastContainer){
    toast({
        message: toastContainer.dataset.message,
        type: toastContainer.dataset.type
    })
}
function toast({ message, type }) {
    const icons = {
        success: '<i class="fa-solid fa-circle-check"></i>',
        error: '<i class="fa-solid fa-circle-exclamation"></i>'
    }
    if (toastContainer) {
        const toast = document.createElement("div")
        toast.classList.add('toast', `toast__${type}`, 'center')
        toast.innerHTML = `
            <div class="toast__icon">
                ${icons[type]}
            </div>
            <div class="toast__body">
                <h3 class="toast__title">${type === "success" ? "Thành công" : "Thất bại"}</h3>
                <p class="toast__message">${message}</p>
            </div>
            <div class="toast__close">
                <i class="fa-solid fa-xmark" style="color: gray;"></i>
            </div>
        `
        toastContainer.appendChild(toast)
        // Auto close
        const autoCloseId = setTimeout(function () {
            toastContainer.removeChild(toast)
        }, 30000)
        // Close when click
        toast.onclick = function () {
            // toast.style.opacity = 0
            setTimeout(function () {
                toastContainer.removeChild(toast)
            }, 300)
            clearTimeout(autoCloseId)
        }
    }
}

