const $$ = document.querySelector.bind(document)

// Bật thông báo xác nhận đăng xuất
function openLogoutModal() {
    const modalLogout = $$(".modal-logout")
    const modalContainer = $$(".modal-logout .modal-container")
    const refuseBtn = $$(".modal-logout .refuse-btn")
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
// Bật modal thêm bài hát
function openAddSongModal() {
    const modalAdd = $$(".modal-add")
    const modalContainer = $$(".modal-add .modal-container__info")
    //Bật modal
    modalAdd.style.display = "flex"
    // Thoát modal khi nhấn ra ngoài
    modalContainer.addEventListener("click", (event) => {
        event.stopPropagation()
    })
    modalAdd.addEventListener("click", (event) => {
        modalAdd.style.display = "none"
    })
}
// Bật modal chỉnh sửa bài hát
const updateBtns = document.querySelectorAll(".update-btn")
updateBtns.forEach(updateBtn =>{
    updateBtn.addEventListener("click",()=>{
        //Lấy ra thông tin bài hát cần chỉnh sửa
        const songInfo = {
            songId: updateBtn.getAttribute("data-id"),
            songTitle:updateBtn.getAttribute("data-title"),
            songArtist:updateBtn.getAttribute("data-artist"),
            songGenre:updateBtn.getAttribute("data-genre"),
            songImg:updateBtn.getAttribute("data-img")
        }
        $$(".modal-update input[name='id']").value = songInfo.songId
        $$(".modal-update input[name='title']").value = songInfo.songTitle
        $$(".modal-update input[name='artist']").value = songInfo.songArtist
        $$(".modal-update select").value = songInfo.songGenre
        $$(".modal-update .current-img img").src = songInfo.songImg

        const modalUpdate = $$(".modal-update")
        const modalContainer = $$(".modal-update .modal-container__info")
        //Bật modal
        modalUpdate.style.display = "flex"
        // Thoát modal khi nhấn ra ngoài
        modalContainer.addEventListener("click", (event) => {
            event.stopPropagation()
        })
        modalUpdate.addEventListener("click", (event) => {
            modalUpdate.style.display = "none"
        })
    })
})
// Search theo field
function getSelectedField() {
    const field = document.querySelector("#search-tool select")
    const input = document.querySelector("#search-tool input")
    switch (Number(field.value)) {
        case 1:
            input.name = "song"
            break;
        case 2:
            input.name = "artist"
            break;
        default:
    }
}
