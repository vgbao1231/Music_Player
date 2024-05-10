// Hiện thời lượng bài hát
const duration = $$(".song-duration")
duration.forEach((item)=>{
    let audio = new Audio();
    audio.src = item.dataset.audio;
    audio.preload = 'metadata'; // Chỉ tải thông tin metadata (bao gồm thời lượng)
    audio.onloadedmetadata = function() {
        let duration = audio.duration;
        let minutes = Math.floor(duration / 60);
        let seconds = Math.floor(duration % 60);
        minutes = minutes < 10 ? "0" + minutes : minutes
        seconds = seconds < 10 ? "0" + seconds : seconds
        item.innerHTML = minutes + ":" + seconds;
    };
})

// Bật modal select playlist để thêm bài hát vào playlist
const addSongToPlaylistBtns = $$('.song-option')
addSongToPlaylistBtns.forEach(addBtn => {
    addBtn.addEventListener("click", (e)=>{
        $(".modal-select-playlist input[name='songId']").value = addBtn.dataset.id
        $(".modal-select-playlist").style.display = 'flex'
    })
})