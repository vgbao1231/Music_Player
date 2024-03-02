<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div class="background" style="background: url('<c:url
        value='./assets/imgs/backgrounds/home.jpg'/>') center center / cover no-repeat;"></div>
<div class="genres-container">
    <div class="genres-section">
        <div class="genres-section-name">Nổi Bật</div>
        <div class="genres-section-list">
            <%
                for (int i = 0; i < 12; i++) {
            %>
            <div class="genres-section-item">
                <div class="genres-img" style="background: url('<c:url
                        value='./assets/imgs/albums/album1.jpg'/>') center center / cover no-repeat;">
                    <div class="genres-name">KHÚC NHẠC VUI</div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>

