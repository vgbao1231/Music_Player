// Bật modal select song để thêm bài hát vào album
const addSongToAlbumBtn = $('.add-btn')
addSongToAlbumBtn.addEventListener("click", (e)=>{
    $(".modal-select-song").style.display = 'flex'
})