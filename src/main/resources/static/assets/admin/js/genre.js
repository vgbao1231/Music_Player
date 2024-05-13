// Bật modal chỉnh sửa thể loại
const genreId = $('.modal-genre input[name="genreId"]');
const genreName = $('.modal-genre input[name="genreName"]');
const genreImg = $('.modal-genre input[name="genreImg"]');
const imgInput = $('.modal-genre input[name="img"]');
const previewImage = $('.modal-genre .preview-img img');
function openUpdateGenreModal() {
    //Gắn các thông tin hiện tại của bài hát vào các input
    const updateBtn = event.currentTarget;
    genreId.value = updateBtn.dataset.id
    genreImg.value = updateBtn.dataset.img
    genreName.value = updateBtn.dataset.genreName
    previewImage.src = updateBtn.dataset.img
    imgInput.value = ''
    //Bật modal
    $(".modal-genre").style.display = "flex"
}