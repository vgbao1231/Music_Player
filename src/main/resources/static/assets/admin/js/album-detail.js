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
