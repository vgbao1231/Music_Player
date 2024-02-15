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
<link rel="stylesheet" href="<c:url value='/assets/login.css'/>">
<link rel="stylesheet" href="<c:url value='/assets/login.css'/>">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>
<div id="container">
    <div id="background" style="background: url('<c:url
            value='./assets/imgs/backgrounds/login.jpg'/>') center center / cover no-repeat;"></div>
    <div id="login-form">
        <form action="">
            <div id="login-title">Login</div>
            <div class="form-input">
                <input id="login-field" type="text" name="user" placeholder="Username">
            </div>
            <div class="form-input">
                <input id="password-field" type="password" name="password" placeholder="Password">
            </div>
            <div id="forgot-password">
                <a href="forgot_password">Forgot Password?</a>
            </div>
            <div id="login-button">
                <button type="submit">Login</button>
            </div>
            <div id="register">
                Don't have an account?
                <a href="register.jsp">Register</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>