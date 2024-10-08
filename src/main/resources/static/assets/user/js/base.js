const $ = document.querySelector.bind(document)
const $$ = document.querySelectorAll.bind(document)

// Kích hoạt active cho sidebar item
const currentPage = window.location.pathname.split('/').pop(); // Xác định trang hiện tại
const sidebarItems = $$(".sidebar-item")
sidebarItems.forEach(item => {
    if (item.id === currentPage) {
        item.querySelector('a').classList.add('active');
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

//Bật tắt sidebar
const menuBtn = $('.menu-button')
menuBtn.addEventListener('click', function () {
    const sidebar = $(".sidebar")
    const mainContent = $(".main-content")
    if (sidebar.offsetWidth !== 0) {
        sidebar.style.width = "0"
        mainContent.style.left = "9vw"
        mainContent.style.right = "9vw"
    } else {
        sidebar.style.width = "18vw"
        mainContent.style.left = "18vw"
        mainContent.style.right = "0"
    }

})
// Bật tắt sidebar playlist
const sidebarPlaylist = $('#playlists')
sidebarPlaylist.addEventListener('click', function () {
    const playlistContainer = $('.playlist-container')
    if (playlistContainer.offsetHeight !== 0) {
        playlistContainer.style.height = "0"
    } else {
        playlistContainer.style.height = "38vh"
    }
})

//Bật modal thêm playlist
$('#add-playlist').addEventListener("click", () => {
    $(".modal-add-playlist").style.display = 'flex'
})
//Bật tính năng chỉnh sửa playlist
const updatePlaylistBtn = $$('.update-playlist__btn')
updatePlaylistBtn.forEach(btn => {
    btn.addEventListener("click", () => {
        const playlist = $(".playlist-item[data-id='" + btn.dataset.id + "'] a")
        const formPlaylist = $(".playlist-item[data-id='" + btn.dataset.id + "'] form")
        const inputPlaylist = playlist.querySelector(".playlist-name")
        //Ngăn không cho chuyển trang khi đang sửa
        playlist.addEventListener("click", (e) => {
            e.preventDefault()
        })
        playlist.addEventListener("keypress", (e) => {
            if (e.keyCode === 13) e.preventDefault()
        })
        //Cho phép chỉnh sửa playlist
        inputPlaylist.style.cursor = 'auto'
        const inputValue = inputPlaylist.value
        inputPlaylist.disabled = false
        inputPlaylist.style.pointerEvents = 'auto'
        inputPlaylist.focus()
        inputPlaylist.setSelectionRange(inputValue.length, inputValue.length)

        function submitForm() {
            if (inputPlaylist.value !== inputValue) formPlaylist.submit()
            else {
                inputPlaylist.disabled = true
                inputPlaylist.style.pointerEvents = 'none'
                playlist.addEventListener("click", () => {
                    window.location.href = "/user/playlist/" + btn.dataset.id
                })
            }
        }

        inputPlaylist.addEventListener("focusout", submitForm)
        inputPlaylist.addEventListener("keypress", (e) => {
            if (e.keyCode === 13) inputPlaylist.blur()
        })
    })
})
//Bật modal xác nhận xóa playlist
const deletePlaylistBtn = $$('.delete-playlist__btn')
deletePlaylistBtn.forEach(btn => {
    btn.addEventListener("click", () => {
        $(".modal-delete-playlist a").href = "/user/playlist/deletePlaylistId=" + btn.dataset.id
        $(".modal-delete-playlist").style.display = 'flex'
    })
})

// Bật menu tùy chỉnh playlist
const playlistOptionBtn = $$('.playlist-option');
const menus = $$('.menu-option');
playlistOptionBtn.forEach(optionBtn => {
    const menu = optionBtn.querySelector('.menu-option');
    // Hiển thị bảng menu khi nhấn nút option
    optionBtn.addEventListener('click', function (event) {
        menus.forEach(item => {
            item.style.display = 'none';
        })
        menu.style.display = 'block';
        event.stopPropagation()
    });

    // Ẩn bảng menu khi click ra ngoài
    menu.addEventListener("click", (event) => {
        event.stopPropagation()
    })
    document.addEventListener('click', function () {
        menu.style.display = 'none';
    });
})

//Tìm kiếm bài hát
const listAllSong = [...$$('.search-result .list-song-item')]

const searchField = $('.search-field')
let searchResult = $('.search-result')
if (listAllSong.length === 0)
    searchResult.innerHTML = `<span>Không tìm thấy bài hát</span>`;
const handleSearchingListEvent = e => {
    if (searchField.value === "") {
        searchResult.innerHTML = listAllSong.reduce((accumulator, elem) => accumulator + elem.outerHTML, "");
        return;
    }

    let searchingResult = listAllSong.reduce((accumulator, song) => {
        let currentCellElement = song.querySelector('.song-info span:first-child');
        let currentCellValue = currentCellElement.textContent.trim().toUpperCase();
        let isBeingFoundValue = currentCellValue.search(searchField.value.trim().toUpperCase()) !== -1;

        return accumulator + (isBeingFoundValue ? song.outerHTML : "");
    }, "");

    if (searchingResult === "")
        searchResult.innerHTML = `<span>Không tìm thấy bài hát</span>`;
    else {
        searchResult.innerHTML = searchingResult;
        handleAddSongToPlaylistEvent(searchResult.querySelectorAll('.song-option.add-to-playlist'))
    }
}

searchField.addEventListener("keyup", handleSearchingListEvent);

// Bật modal select playlist để thêm bài hát vào playlist
const handleAddSongToPlaylistEvent = (list) => {
    list.forEach(addBtn => {
        addBtn.addEventListener("click", (e) => {
            $(".modal-select-playlist input[name='songId']").value = addBtn.dataset.id
            $(".modal-select-playlist").style.display = 'flex'
        })
    })
}
const addSongToPlaylistBtns = $$('.song-option.add-to-playlist')
handleAddSongToPlaylistEvent(addSongToPlaylistBtns)

// Thông báo toast
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
        }, 3000)
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



