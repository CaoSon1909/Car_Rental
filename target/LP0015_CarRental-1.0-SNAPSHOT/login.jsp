<%-- 
    Document   : login
    Created on : Feb 21, 2021, 9:26:47 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <!--bootstrap 3.3.5 cdn-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <!--google sign in-->
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="456680102690-r3kqa2gg84malj794to5kdtjpfb0qc99.apps.googleusercontent.com">
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="main">Car Rental Service - Login Page</a>
                </div>
            </div>
        </nav>
        <c:if test="${not empty param.error}">
            <p style="color: red;">            
                Wrong username or password
            </p>
        </c:if>
        <form action="login" method="POST" onsubmit="return submitUserForm();">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="email" type="text" class="form-control" name="txtEmail" placeholder="Email" style="width: 50%;" />
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" class="form-control" name="txtPassword" placeholder="Password" style="width: 50%;">
            </div>
            <br/>
            <div class="g-recaptcha" data-sitekey="6Lc0mmgaAAAAABP7h209DXi4pvHw8p-SliUp9Qbw" data-callback="verifyCaptcha"></div>
            <div id="g-recaptcha-error"></div>
            <button type="text" class="btn btn-success">Login</button>
        </form>
        <br/>
        <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
        <!--Google Signin-->
        <script type="text/javascript">
            function onSignIn(googleUser) {
                // Useful data for your client-side scripts:
                var profile = googleUser.getBasicProfile();
                var email = profile.getEmail();
                var name = profile.getName();
                var request = new XMLHttpRequest();
                var url = "google-signin?btAction=Google&email=" + email + "&name=" + name;
                request.open("GET", url, true);
                request.onreadystatechange = function () {
                    if (request.readyState === 4) {
                        if (request.status === 200) {
                            console.log("ok");
                            let redirectURL = request.responseText;
                            console.log(redirectURL);
                            window.location.replace(redirectURL);
                        }
                    }
                }
                request.send();
                gapi.auth2.getAuthInstance().disconnect();


            }
            function submitUserForm() {
                var response = grecaptcha.getResponse();
                if (response.length == 0) {
                    document.getElementById('g-recaptcha-error').innerHTML = '<span style="color:red;">Please complete ReCAPTCHA!!!</span>';
                    return false;
                }
                return true;
            }

            function verifyCaptcha() {
                document.getElementById('g-recaptcha-error').innerHTML = '';
            }
        </script>
        <a href="register-page">Register</a>
        <a href="main">Back</a>
    </body>
</html>
