// Bật modal select playlist để thêm bài hát vào playlist
const addSongToPlaylistBtns = $$('.song-item-option')
addSongToPlaylistBtns.forEach(addBtn => {
    addBtn.addEventListener("click", (e)=>{
        $(".modal-select-playlist input[name='songId']").value = addBtn.dataset.id
        $(".modal-select-playlist").style.display = 'flex'
    })
})


