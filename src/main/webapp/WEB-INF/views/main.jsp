<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Player</title>
</head>
<link rel="stylesheet" href="<c:url value='/assets/main.css'/>">
<link rel="stylesheet" href="<c:url value='/assets/home.css'/>">
<link rel="stylesheet" href="<c:url value='/assets/account.css'/>">
<link rel="stylesheet" href="<c:url value='/assets/playlist.css'/>">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>
<div id="header">
    <div id="category">
        <div id="category-button"><i class="fa-solid fa-bars"></i></div>
        <div id="category-container">
            <div class="category-element" id="category-home" data-index=0>
                <div id="home-icon"><i class="mr-10 fas fa-home"></i>Trang chủ</div>
            </div>
            <div class="category-element" id="category-account" data-index=1>
                <div id="acc-icon"><i class="mr-10 fas fa-user"></i>Tài khoản</div>
            </div>
            <div class="category-element" id="category-playlists" data-index=2>
                <div id="list-icon"><i class="mr-10 fa-solid fa-list-ul"></i>Danh sách phát</div>
            </div>
            <div class="playlist-container">
                <div class="playlist-item">
                    <span class="playlist-name">Playlist 111111222222233333333</span>
                    <span class="mr-20 playlist-option"><i class="fa-solid fa-ellipsis-vertical"></i></span>
                </div>
                <div class="playlist-item">
                    <span class="playlist-name">Playlist 2</span>
                    <span class="mr-20 playlist-option"><i class="fa-solid fa-ellipsis-vertical"></i></span>
                </div>
                <div class="playlist-item">
                    <span class="playlist-name">Playlist 3</span>
                    <span class="mr-20 playlist-option"><i class="fa-solid fa-ellipsis-vertical"></i></span>
                </div>
                <div>
                    <i class="fa-solid fa-plus"></i>
                    Thêm danh sách
                </div>
            </div>
        </div>
    </div>
    <div id="search-bar">
        <form action="" role="search">
            <input id="search-field" type="text" placeholder="Tìm kiếm bài hát...">
            <button id="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
        </form>
    </div>
    <div id="user">
        <button class="user-button" id="login"
                style="border-right: 1px solid rgba(255,255,255,0.1);">
            <a href="login">Đăng Nhập</a>
        </button>
        <button class="user-button" id="register">
            <a href="register">Đăng Ký</a>
        </button>
    </div>
</div>

<div id="content"></div>
</body>
<script src="<c:url value='/javascript/main.js'/>"></script>
<script src="<c:url value='/javascript/home.js'/>"></script>
<script src="<c:url value='/javascript/account.js'/>"></script>
<script src="<c:url value='/javascript/playlist.js'/>"></script>
</html>