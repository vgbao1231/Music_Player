<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="background"></div>
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

