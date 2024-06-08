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

// Áp dụng xác nhận xóa cho tất cả form xóa
$$('form').forEach( form => {
    if (form.action.includes('delete')){
        form.addEventListener("submit", e =>{
            if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") === false) e.preventDefault()
        })
    }
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

//Sắp xếp theo field
function customizeSortingListEvent() {
    const sortField = document.querySelectorAll('.main-content table thead th:not(:last-child)')
    let isAscending = true
    sortField.forEach((field, index) => {
        field.addEventListener("click", () => {
            //Thay đổi tăng dần hoặc giảm dần
            isAscending = !isAscending
            //Lấy ra tất cả các hàng
            const rows = [...document.querySelectorAll('.main-content tbody tr')];
            //Lấy ra các ô của field vừa click
            const cellsOfField = rows.map(row => {
                return row.querySelectorAll('td')[index]
            });
            let searchingDataFieldType = ""
            //Kiểm tra kiểu dữ liệu của cột để sắp xếp
            const date = cellsOfField[0].textContent.split('/');
            if (!isNaN(Date.parse(date[1] + '/' + date[0] + '/' + date[2]))) searchingDataFieldType = "Date";
            else if (!Number.isNaN(Number.parseInt(cellsOfField[0].textContent))) searchingDataFieldType = "Number";
            else searchingDataFieldType = "String";


            rows.sort((row1, row2) => {
                const a= row1.cells[index].textContent
                const b = row2.cells[index].textContent
                if (searchingDataFieldType === "Number")
                    return isAscending ? (+a - b) : (+b - a);
                else if (searchingDataFieldType === "Date") {
                    let [day, month, year] = a.split('/').map(Number);
                    const dateA = new Date(year, month - 1, day);
                    [day, month, year] = b.split('/').map(Number);
                    const dateB = new Date(year, month - 1, day);
                    return isAscending ? dateA - dateB : dateB - dateA;
                } else return isAscending ? a.localeCompare(b) : b.localeCompare(a);
            });

            // Sắp xếp lại các hàng trong bảng
            const tbody = document.querySelector('tbody');
            tbody.innerHTML = '';
            rows.forEach(row => tbody.appendChild(row));
        })
    })
}

//Tìm kiếm theo field
function customizeSearchingListEvent(plainTableRows) {
    const searchingInputTag = document.querySelector('#search-tool input');
    const selectField = document.querySelector('#search-tool select');
    let tableBody = document.querySelector('tbody');
    if (plainTableRows.length === 0)
        tableBody.innerHTML = `<tr><td colspan="10">Không tìm thấy dữ liệu</td></tr>`;
    const handleSearchingListEvent = e => {
        tableBody = document.querySelector('tbody');
        if (searchingInputTag.value === "") {
            tableBody.innerHTML = plainTableRows.reduce((accumulator, elem) => accumulator + elem.outerHTML, "");
            return;
        }

        let searchingResult = plainTableRows.reduce((accumulator, row) => {
            let currentCellElement = row.querySelectorAll('td')[selectField.value];
            let currentCellValue = currentCellElement.textContent.trim().toUpperCase();
            let isBeingFoundValue = currentCellValue.search(searchingInputTag.value.trim().toUpperCase()) !== -1;

            return accumulator + (isBeingFoundValue ? row.outerHTML : "");
        }, "");

        if (searchingResult === "")
            tableBody.innerHTML = `<tr><td colspan="10">Không tìm thấy dữ liệu</td></tr>`;
        else
            tableBody.innerHTML = searchingResult;
    }

    searchingInputTag.addEventListener("keyup", handleSearchingListEvent);
}
