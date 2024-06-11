// Bật modal select song để thêm bài hát vào album
$('.add-btn').addEventListener("click", (e)=>{
    $(".modal-select-song").style.display = 'flex'
})

//Hiện input chỉnh sửa tên album
const albumName = $(".album-name span")
const albumNameInput = $(".album-name input[name='albumName']")
$('.album-name i').addEventListener("click", (e)=>{
    albumName.style.display = 'none'
    albumNameInput.style.display = 'inline-block'
    albumNameInput.setSelectionRange(albumNameInput.value.length,albumNameInput.value.length)
    albumNameInput.focus()

    //Kiểm tra nếu có thay đổi thì submit ko thì thôi
    const inputValue = albumNameInput.value
    albumNameInput.addEventListener('blur',()=>{
        if(inputValue !== albumNameInput.value) $(".album-name").submit()
        else {
            albumNameInput.style.display = 'none'
            albumName.style.display = 'inline'
        }
    })
})

//Tìm kiếm bài hát
const listAllSong = [...$$('.modal .song-btn')]

const searchField = $('.search-field')
let searchResult = $('.list-song')
if (listAllSong.length === 0)
    searchResult.innerHTML = `<span>Không tìm thấy bài hát</span>`;
const handleSearchingListEvent = e => {
    if (searchField.value === "") {
        searchResult.innerHTML = listAllSong.reduce((accumulator, elem) => accumulator + elem.outerHTML, "");
        return;
    }

    let searchingResult = listAllSong.reduce((accumulator, song) => {
        let currentCellElement = song.querySelector('span');
        let currentCellValue = currentCellElement.textContent.trim().toUpperCase();
        let isBeingFoundValue = currentCellValue.search(searchField.value.trim().toUpperCase()) !== -1;

        return accumulator + (isBeingFoundValue ? song.outerHTML : "");
    }, "");

    if (searchingResult === "") searchResult.innerHTML = `<span>Không tìm thấy bài hát</span>`;
    else searchResult.innerHTML = searchingResult;
}

searchField.addEventListener("keyup", handleSearchingListEvent);
