
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Player</title>
</head>
<link rel="stylesheet" href="<c:url value='/assets/main.css'/>">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<body>
<jsp:include page="header.jsp" />

<div id="content">
    <div class="background" style="background: url('<c:url
            value='./assets/imgs/backgrounds/home.jpg'/>') center center / cover no-repeat;"></div>
    <div class="home-container">
        <div class="playlist-section">
            <div class="playlist-section-name">Nổi Bật</div>
            <div class="playlist-section-list">
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album1.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album2.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album3.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album4.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album5.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
            </div>
        </div>
        <div class="songs-section">
            <div class="songs-section-name">Bài hát mới</div>
            <div class="songs-section-list">
                <%
                    for(int i=0;i<3;i++){
                %>
                <div class="list-songs">
                    <%
                        for (int j=1;j<=4;j++){
                            String imageURL = "./assets/imgs/songs/" + j + ".jpg";
                    %>
                    <div class="list-song-item">
                        <div class="song-item">
                            <div class="song-img" style="background-image: url('<c:url
                                    value='<%=imageURL%>'/>');"></div>
                            <div class="song-info">
                                <h5>Be Alright (Official Video)</h5>
                                <h6>Dean Lewis</h6>
                            </div>
                        </div>
                        <div class="song-item-option">
                            <i class="fa-solid fa-ellipsis-vertical"></i>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <div class="playlist-section">
            <div class="playlist-section-name">Nổi Bật</div>
            <div class="playlist-section-list">
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album1.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album2.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album3.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album4.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album5.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
            </div>
        </div>
        <div class="songs-section">
            <div class="songs-section-name">Bài hát mới</div>
            <div class="songs-section-list">
                <%
                    for(int i=0;i<3;i++){
                %>
                <div class="list-songs">
                    <%
                        for (int j=1;j<=4;j++){
                            String imageURL = "./assets/imgs/songs/" + j + ".jpg";
                    %>
                    <div class="list-song-item">
                        <div class="song-item">
                            <div class="song-img" style="background-image: url('<c:url
                                    value='<%=imageURL%>'/>');"></div>
                            <div class="song-info">
                                <h5>Be Alright (Official Video)</h5>
                                <h6>Dean Lewis</h6>
                            </div>
                        </div>
                        <div class="song-item-option">
                            <i class="fa-solid fa-ellipsis-vertical"></i>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <div class="playlist-section">
            <div class="playlist-section-name">Nổi Bật</div>
            <div class="playlist-section-list">
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album1.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album2.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album3.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album4.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
                <div class="playlist-section-album">
                    <div class="album-img" style="background: url('<c:url
                            value='./assets/imgs/albums/album5.jpg'/>') center center / cover no-repeat;"></div>
                    <div class="album-name">Nhạc trẻ gây nghiện</div>
                </div>
            </div>
        </div>
        <div class="songs-section">
            <div class="songs-section-name">Bài hát mới</div>
            <div class="songs-section-list">
                <%
                    for(int i=0;i<3;i++){
                %>
                <div class="list-songs">
                    <%
                        for (int j=1;j<=4;j++){
                            String imageURL = "./assets/imgs/songs/" + j + ".jpg";
                    %>
                    <div class="list-song-item">
                        <div class="song-item">
                            <div class="song-img" style="background-image: url('<c:url
                                    value='<%=imageURL%>'/>');"></div>
                            <div class="song-info">
                                <h5>Be Alright (Official Video)</h5>
                                <h6>Dean Lewis</h6>
                            </div>
                        </div>
                        <div class="song-item-option">
                            <i class="fa-solid fa-ellipsis-vertical"></i>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
</body>
<script src="<c:url value='/javascript/main.js'/>"></script>
</html>