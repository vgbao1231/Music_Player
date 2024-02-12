const $$ = document.querySelector.bind(document)
const category = $$('#category')
const categoryBtn = $$('#category-button')
const categoryPlaylist = $$('#category-playlists')
const content = $$('#content')
// Mở thanh danh mục
let openCategoryToggle = false
categoryBtn.addEventListener('click', function () {
    openCategoryToggle = !openCategoryToggle
    let categoryContainer = $$('#category-container')
    openCategoryToggle ? content.style.marginLeft = '40vh' : content.style.marginLeft = '0'
    openCategoryToggle ? categoryContainer.style.width = "40vh" : categoryContainer.style.width = "0"
    openCategoryToggle ? category.style.width = "70vh" : category.style.width = "30vh"

})
// Mở danh sách phát
let openPlaylistToggle = false
categoryPlaylist.addEventListener('click', function () {
    openPlaylistToggle = !openPlaylistToggle
    let playlistContainer = $$('.playlist-container')
    openPlaylistToggle ? playlistContainer.style.height = "24vh" : playlistContainer.style.height = "0"
})


$(document).ready(function() {
    loadContent('home');
    // Xử lý khi người dùng click vào home
    $("#category-home").click(function() {
        loadContent('home');
    });

    // Xử lý khi người dùng click vào account
    $('#category-account').click(function() {
        loadContent('account');
    });

    // Hàm để tải nội dung của mục A hoặc mục B thông qua AJAX
    function loadContent(section) {
        $.ajax({
            url: '/' + section, // Đường dẫn tới endpoint Spring MVC để lấy nội dung
            success: function(data) {
                $('#content').html(data); // Cập nhật nội dung của phần content
            },
            error: function() {
                console.error('Lỗi khi tải nội dung.');
            }
        });
    }
});