//Thao tác nghe nhạc
const background = $('.background')
const musicName = $('.music-name')
const cdThumb = $('.music-img')
const audio = $('#audio')
const playerContainer = $('.player-container')
const timeBar = $('#progress')
const playBtn = $('.toggle-play')
const nextBtn = $('.next-btn')
const prevBtn = $('.prev-btn')
const shuffleBtn = $('.fa-shuffle')
const repeatBtn = $('.fa-rotate-right')
const musicList = $('.music-list')

//Quay CD
const cdThumbAnimation = cdThumb.animate([
    {transform: "rotate(360deg)"},
], {
    duration: 10000,
    iterations: Infinity
})
cdThumbAnimation.pause()

// Lấy ra thông tin từng bài hát rồi thêm vào mảng các bài hát (songList)
const songList = []
$$(".song").forEach(song => {
    const songInfo = {
        songName: song.dataset.song_name,
        artist: song.dataset.artist,
        audio: song.dataset.audio,
        img: song.dataset.img
    }
    songList.push(songInfo)
})
const app = {
    currentIndex: musicList.dataset.current_song,
    isPlaying: false,
    isShuffling: false,
    isRepeating: false,
    songs: songList,

    defineProperties: function () {
        Object.defineProperty(this, 'currentSong', {
            get: function () {
                return this.songs[this.currentIndex]
            }
        })
    },

    handleEvents: function () {

        // Play/pause
        playBtn.onclick = function () {
            app.playSong()
        }

        // Tiến độ nhạc
        audio.ontimeupdate = function () {
            if (audio.duration) {
                timeBar.value = Math.floor(audio.currentTime / audio.duration * 100)
            }
            app.loadProgress()
        }

        //Xử lý tua nhạc
        timeBar.oninput = function (e) {
            audio.currentTime = audio.duration * e.target.value / 100
        }

        //Chạy bài hát sau đó
        nextBtn.onclick = function () {
            app.isShuffling ? app.randomSong() : app.nextSong()
            app.playSong()
            audio.play()
        }
        //Chạy bài hát trước đó
        prevBtn.onclick = function () {
            app.isShuffling ? app.randomSong() : app.prevSong()
            app.playSong()
            audio.play()
        }

        //Xáo bài hát
        shuffleBtn.onclick = function () {
            app.isShuffling = !app.isShuffling
            shuffleBtn.classList.toggle('active')
        }
        //Lặp lại bài hát
        repeatBtn.onclick = function () {
            app.isRepeating = !app.isRepeating
            repeatBtn.classList.toggle('active')
            audio.loop = app.isRepeating;
        }

        //Chuyển bài hát khi hết nhạc
        audio.onended = function () {
            if (!app.isRepeating) {
                nextBtn.click()
            }
        }

        //Chọn bài hát
        musicList.onclick = function (e) {
            const deleteSongOption = e.target.closest('.song-option.delete-song')
            const addToPlaylistOption = e.target.closest('.song-option.add-to-playlist')
            if (deleteSongOption) {
                // Truyền @PathVariable playlistId và songId cho controller xử lý
                $(".modal-delete-song a").href = "/user/playlist/"+currentPage+"/deleteSongId=" + deleteSongOption.dataset.id
                $(".modal-delete-song").style.display = 'flex'
            } else if (addToPlaylistOption){
                // Truyền @PathVariable playlistId và songId cho controller xử lý
                $(".modal-select-playlist input[name='songId']").value = addToPlaylistOption.dataset.id
                $(".modal-select-playlist").style.display = 'flex'
            } else {
                app.currentIndex = e.target.closest('.song').dataset.index
                app.loadCurrentSong()
                app.playSong()
                audio.play()
            }
        }
    },
    randomSong: function () {
        let newIndex = Math.floor(Math.random() * app.songs.length);
        while (app.currentIndex === newIndex) {
            newIndex = Math.floor(Math.random() * app.songs.length);
        }
        app.currentIndex = newIndex
        app.loadCurrentSong()
    },

    playSong: function () {
        app.isPlaying ? audio.pause() : audio.play()

        audio.onplay = function () {
            playerContainer.classList.add('playing')
            app.isPlaying = true
            cdThumbAnimation.play()
        }
        audio.onpause = function () {
            playerContainer.classList.remove('playing')
            app.isPlaying = false
            cdThumbAnimation.pause()
        }
    },

    nextSong: function () {
        app.currentIndex++
        if (app.currentIndex >= app.songs.length) {
            app.currentIndex = 0
        }
        this.loadCurrentSong()
    },
    prevSong: function () {
        if (app.currentIndex > 0) {
            app.currentIndex--
        }
        this.loadCurrentSong()
    },
    loadCurrentSong: function () {
        background.style.background = `url('${this.currentSong.img}') no-repeat center/ cover`
        musicName.innerText = this.currentSong.songName
        cdThumb.style.backgroundImage = `url('${this.currentSong.img}')`
        audio.src = this.currentSong.audio
        audio.onloadedmetadata = function() {
            let duration = audio.duration;
            let minutes = Math.floor(duration / 60);
            let seconds = Math.floor(duration % 60);
            minutes = minutes < 10 ? "0" + minutes : minutes
            seconds = seconds < 10 ? "0" + seconds : seconds
            $('.duration').innerText = minutes + ":" + seconds;
        };
    },
    loadProgress: function () {
        let currentTime = $('.current-time')
        if (audio.duration) {
            let currentMinute = Math.floor(audio.currentTime / 60)
            let currentSecond = Math.floor(audio.currentTime - currentMinute * 60)
            currentMinute = currentMinute < 10 ? "0" + currentMinute : currentMinute
            currentSecond = currentSecond < 10 ? "0" + currentSecond : currentSecond
            currentTime.innerText = currentMinute + ":" + currentSecond
        }
    },
    start: function () {
        this.defineProperties()
        this.handleEvents()
        this.loadCurrentSong()
    }
}
app.start()