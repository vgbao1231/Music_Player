// Gắn modal select playlist để thêm bài hát vào playlist
const addSongToPlaylistBtns = document.querySelectorAll('.song-item-option')
const modalSelect = $$(".modal-select-playlist")
const modalContainer = $$(".modal-select-playlist .modal-container__info")
addSongToPlaylistBtns.forEach(addBtn => {
    addBtn.addEventListener("click", (e)=>{
        $$(".modal-select-playlist input[name='songId']").value = addBtn.getAttribute("data-id")
        modalSelect.style.display = 'flex'
        // Thoát modal khi nhấn ra ngoài
        modalContainer.addEventListener("click", (event) => {
            event.stopPropagation()
        })
        modalSelect.addEventListener("click", () => {
            modalSelect.style.display = 'none'
        })
    })
})


