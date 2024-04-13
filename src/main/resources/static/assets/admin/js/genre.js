// Bật modal chỉnh sửa thể loại
const genreId = $('.modal-genre input[name="genre_id"]');
const genreName = $('.modal-genre input[name="genre_name"]');
const genreImg = $('.modal-genre input[name="img"]');
const previewImage = $('.modal-genre .preview-img img');
function openUpdateGenreModal() {
    //Gắn các thông tin hiện tại của bài hát vào các input
    const updateBtn = event.currentTarget;
    genreId.value = updateBtn.dataset.id
    genreName.value = updateBtn.dataset.genre_name
    previewImage.src = updateBtn.dataset.img
    genreImg.value = ''
    //Bật modal
    $(".modal-genre").style.display = "flex"
}