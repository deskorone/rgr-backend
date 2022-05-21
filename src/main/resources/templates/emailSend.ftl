<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Confirmation</title>
</head>
<body>
<style>
    .cont {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 30px auto;
        width: 240px;
        height: 100px;
        font-family: "Roboto Light", sans-serif;
        font-size: 17px;
        background: #85C88A;
        border-radius: 10px;
        -webkit-box-shadow: 0 12px 13px 2px rgba(34, 60, 80, 0.27);
        -moz-box-shadow: 0 12px 13px 2px rgba(34, 60, 80, 0.27);
        box-shadow: 0 12px 13px 2px rgba(34, 60, 80, 0.27);
    }
</style>
<#if  send == true>
    <div class="cont"><p>Send</p></div>
<#else>
    <div class="cont"><p>Email no valide</p></div>
</#if>

</body>
</html>