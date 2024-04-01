const $$ = document.querySelector.bind(document)

// Kích hoạt active cho sidebar item
const currentPage = window.location.pathname.split('/').pop(); // Xác định trang hiện tại
const sidebarItems = document.querySelectorAll(".sidebar-item")
sidebarItems.forEach(item => {
    if (item.id === currentPage) {
        item.classList.add('active');
    }
});
//Bật tắt sidebar
const menuBtn = $$('.menu-button')
menuBtn.addEventListener('click', function () {
    const sidebar = $$(".sidebar")
    const mainContent = $$(".content")
    if (sidebar.offsetWidth !== 0) {
        sidebar.style.width = 0
        mainContent.style.left = "9vw"
        mainContent.style.right = "9vw"
    } else {
        sidebar.style.width = "18vw"
        mainContent.style.left = "18vw"
        mainContent.style.right = 0
    }

})
// Bật tắt sidebar playlist
const sidebarPlaylist = $$('#playlists')
sidebarPlaylist.addEventListener('click', function () {
    const playlistContainer = $$('.playlist-container')
    if (playlistContainer.offsetHeight === 0) {
        playlistContainer.style.height = "24vh"
    } else {
        playlistContainer.style.height = "0"
    }
})
if (currentPage === "playlist") sidebarPlaylist.click()

//Gắn modal thêm playlist vào sidebar
const addPlaylistBtn = $$('#add-playlist')
addPlaylistBtn.addEventListener("click", () => {
    const formAddPlaylist = document.createElement("div")
    formAddPlaylist.classList.add("modal", "modal-add-playlist")
    formAddPlaylist.innerHTML = `
    <div class="modal-container__info">
        <form action="/user/addPlaylist" method="post">
            <div class="table-frame__name center m-10">Tạo playlist mới</div>
            <div class="song-info m-20">
                <div class="input-wrapper">
                    <label>
                        <input type="text" name="title" placeholder="Nhập tên playlist:">
                    </label>
                </div>
            </div>
            <div class="modal-btn">
                <button class="accept-btn" type="submit">Thêm</button>
            </div>
        </form>
    </div>
`
    $$('body').appendChild(formAddPlaylist)

    const modalAdd = $$(".modal-add-playlist")
    const modalContainer = $$(".modal-add-playlist .modal-container__info")
    // Thoát modal khi nhấn ra ngoài
    modalContainer.addEventListener("click", (event) => {
        event.stopPropagation()
    })
    modalAdd.addEventListener("click", () => {
        $$('body').removeChild(formAddPlaylist)
    })
})
