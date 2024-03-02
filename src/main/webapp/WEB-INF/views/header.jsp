<div id="header">
    <jsp:include page="sidebar.jsp" />
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