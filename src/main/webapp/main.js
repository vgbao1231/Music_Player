const $ = document.querySelector.bind(document)
    const category = $('#category-button')
    const background = $('.background')
    const musicName = $('.music-name')
    const cdThumb = $('.music-img')
    const audio = $('#audio')
    const player = $('.player')
    const timeBar = $('#progress')
    const playBtn = $('.toggle-play')
    const nextBtn = $('.next-btn')
    const prevBtn = $('.prev-btn')
    const shuffleBtn = $('.fa-shuffle')
    const repeatBtn = $('.fa-rotate-right')
    const playlist = $('.music-list')


    // Mở thanh danh mục
    let toggle = false
    category.addEventListener('click', function(){
        toggle = !toggle
        let categoryContent = $('#category-content')
        let content = $('#category-content div')
        toggle ? player.style.marginLeft= '40vh' : player.style.marginLeft= '0'
        toggle ? categoryContent.style.width = "40vh" : categoryContent.style.width = "0"

    })
    //Quay CD
    const cdThumbAnimation = cdThumb.animate([
        { transform: "rotate(360deg)" },
    ], {
        duration: 10000,
        iterations: Infinity
    })
    cdThumbAnimation.pause()


    const app = {
        currentIndex: 0,
        isPlaying: false,
        isShuffling: false,
        isRepeating: false,
        songs: [
            {
                name: 'Cupid',
                singer: 'FIFTY',
                path: './assets/songs/song1.mp3',
                img: './assets/imgs/1.jpg'
            },
            {
                name: 'Be Alright (Official Video)',
                singer: 'Dean Lewis',
                path: './assets/songs/song2.mp3',
                img: './assets/imgs/2.jpg'
            },
            {
                name: 'Dự báo thời tiết hôm nay mưa',
                singer: 'GREY D',
                path: './assets/songs/song3.mp3',
                img: './assets/imgs/3.jpg'
            },
            {
                name: 'Blue Tequila (Official Video)',
                singer: 'Táo',
                path: './assets/songs/song4.mp3',
                img: './assets/imgs/4.jpg'
            },
            {
                name: 'Tại vì em',
                singer: 'buitruonglinh',
                path: './assets/songs/song5.mp3',
                img: './assets/imgs/5.jpg'
            },

        ],

        defineProperties: function () {
            Object.defineProperty(this, 'currentSong', {
                get: function () {
                    return this.songs[this.currentIndex]
                }
            })
        },

        render: function () {
            const htmls = this.songs.map((song, index) => {
                return `
                <div class="song" data-index = "${index}">
                    <div class="thumbnail" style= "background-image: url(${song.img})"></div>
                    <div class="song-info">
                        <h2>${song.name}</h2>
                        <h4>${song.singer}</h4>
                    </div>
                </div>
                `
            })
            playlist.innerHTML = htmls.join('')
        },

        handleEvents: function () {
            const cd = $('.cd')
            const cdWidth = cd.offsetWidth
            window.onscroll = function () {
                console.log(window.scrollY);
                const scrollY = window.scrollY
                const newCdWidth = cdWidth - scrollY
                cd.style.width = newCdWidth > 0 ? newCdWidth + 'px' : 0
            }

            // Play/pause
            playBtn.onclick = function () {
                app.playSong()
            }

            //Quay CD
            const cdThumbAnimation = cdThumb.animate([
                { transform: "rotate(360deg)" },
            ], {
                duration: 10000,
                iterations: Infinity
            })
            cdThumbAnimation.pause()


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

            //Next song
            nextBtn.onclick = function () {
                if (app.isShuffling) {
                    app.randomSong()
                } else {
                    app.nextSong()
                }
                app.playSong()
                audio.play()
            }
            //Prev song
            prevBtn.onclick = function () {
                app.prevSong()
                app.playSong()
                audio.play()
            }

            //Shuffle song
            shuffleBtn.onclick = function () {
                app.isShuffling = !app.isShuffling
                shuffleBtn.classList.toggle('active')
            }
            //Repeat song
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
            playlist.onclick = function (e) {
                const songNode = e.target.closest('.song')
                app.currentIndex = songNode.dataset.index
                app.loadCurrentSong()
                app.playSong()
                audio.play()
            }
            
        },
        randomSong: function () {
            const newIndex = Math.floor(Math.random() * app.songs.length);
            do {
            } while (app.currentIndex === newIndex)
            app.currentIndex = newIndex
            app.loadCurrentSong()
        },

        playSong: function () {
            if (app.isPlaying) {
                audio.pause()
            } else {
                audio.play()
            }

            audio.onplay = function () {
                player.classList.add('playing')
                app.isPlaying = true
                cdThumbAnimation.play()
            }
            audio.onpause = function () {
                player.classList.remove('playing')
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
            musicName.innerText = this.currentSong.name
            cdThumb.style.backgroundImage = `url('${this.currentSong.img}')`
            audio.src = this.currentSong.path
        },
        loadProgress: function () {
            let currentTime = $('.current-time')
            let duration = $('.duration')
            if (audio.duration) {
                let currentMinute = Math.floor(audio.currentTime / 60)
                let currentSecond = Math.floor(audio.currentTime - currentMinute * 60)
                let durationMinute = Math.floor(audio.duration / 60)
                let durationSecond = Math.floor(audio.duration - durationMinute * 60)
                currentMinute = currentMinute < 10 ? "0" + currentMinute : currentMinute
                currentSecond = currentSecond < 10 ? "0" + currentSecond : currentSecond
                currentTime.innerText = currentMinute + ":" + currentSecond
                durationMinute = durationMinute < 10 ? "0" + durationMinute : durationMinute
                durationSecond = durationSecond < 10 ? "0" + durationSecond : durationSecond
                duration.innerText = durationMinute + ":" + durationSecond
                
            }
        },
        start: function () {
            this.defineProperties()
            this.render()
            this.handleEvents()
            this.loadCurrentSong()
        }
    }
    app.start()