<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Player</title>
</head>
<link rel="stylesheet" href="<c:url value='/assets/style.css'/>">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="shortcut icon" href="#">

<body>
<div class="background"></div>
<div class="header">
    <div class="category">
        <div id="category-button"><i class="fa-solid fa-bars"></i></div>
        <div id="category-content"></div>
    </div>
    <div id="search-bar">BBBBBB</div>
    <div id="login">
        <ul>
            <li>Đăng ký</li>
            ||
            <li>Đăng nhập</li>
        </ul>
    </div>
</div>
<div class="player">
    <div class="container">
        <div class="dashboard">
            <!-- Header -->
            <header>
                <h4>Now playing:</h4>
                <h2 class="music-name">Gura songs</h2>
            </header>
            <!-- CD -->
            <div class="cd">
                <div class="music-img"></div>
            </div>
            <!-- Control -->
            <div class="controler">
                <div>
                    <i class="fa-solid fa-rotate-right"></i>
                </div>
                <div class="prev-btn">
                    <i class="fa-solid fa-backward-step"></i>
                </div>
                <div class="toggle-play">
                    <i class="fa-solid fa-pause icon-pause"></i> <i
                        class="fa-solid fa-play icon-play"></i>
                </div>
                <div class="next-btn">
                    <i class="fa-solid fa-forward-step"></i>
                </div>
                <div>
                    <i class="fa-solid fa-shuffle"></i>
                </div>
            </div>
            <!-- Time bar -->
            <input id="progress" class="progress" type="range" value="0"
                   step="1" min="0" max="100">
            <div class="current-time">00:00</div>
            <div class="duration">00:00</div>
            <!-- Audio -->
            <audio id="audio" src=""></audio>
        </div>

        <!-- Playlist -->
        <div class="music-list"></div>

    </div>
</div>
</body>
<script src="<c:url value='main.js'/>"></script>
</html>