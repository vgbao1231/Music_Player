const modalSong = $('.modal-song')
const form = $(".modal-song form");
const modalName = $(".modal-song .table-frame__name")
//Thông tin phòng trong modal
const songId = $('.modal-song input[name="songId"]');
const songName = $('.modal-song input[name="songName"]');
const songArtist = $('.modal-song input[name="artist"]');
const songGenre = $('.modal-song select[name="genre.genreId"]');
const songAudio = $('.modal-song input[name="audio"]');
const audioInput = $('.audio-input')
const previewImage = $('.modal-song .preview-img img');
const songImg = $('.modal-song input[name="img"]');
const acceptBtn = $(".modal-song .accept-btn")

// Bật modal thêm bài hát
function openAddSongModal() {
    //Tùy chỉnh thành modal thêm bài hát
    form.action = "/admin/song/addSong"
    modalName.textContent = "Thêm bài hát mới"
    acceptBtn.textContent = "Thêm"
    //Làm trống các input để thêm bài hát
    songId.value = ''
    songName.value = ''
    songArtist.value = ''
    audioInput.style.display = 'block'
    songAudio.value = ''
    songImg.value = ''
    previewImage.src = ''
    //Bật modal
    modalSong.style.display = "flex"
}

// Bật modal chỉnh sửa bài hát
function openUpdateSongModal() {
    //Tùy chỉnh thành modal thêm bài hát
    form.action = "/admin/song/updateSong"
    modalName.textContent = "Chỉnh sửa thông tin bài hát"
    acceptBtn.textContent = "Lưu"
    //Gắn các thông tin hiện tại của bài hát vào các input
    const updateBtn = event.currentTarget;
    songId.value = updateBtn.dataset.id
    songName.value = updateBtn.dataset.songName
    songArtist.value = updateBtn.dataset.artist
    songGenre.value = updateBtn.dataset.genre
    audioInput.style.display = "none"
    songImg.value = ''
    previewImage.src = updateBtn.dataset.img
    //Bật modal
    modalSong.style.display = "flex"
}
customizeSortingListEvent()
customizeSearchingListEvent([...$$('.table-wrapper tbody tr')])