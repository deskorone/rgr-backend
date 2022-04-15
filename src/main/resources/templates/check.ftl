<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Чек</title>
</head>
<body>
<style>
    tr, th{
        font-family: "Roboto Light";
        padding: 10px 20px 10px 20px;
    }
    th{
        border-radius: 3px;
    }
    table{
        margin: 0 auto;
        width: auto;
        background: #9bf1bb;
        -webkit-box-shadow: 0px 11px 11px 1px rgba(34, 60, 80, 0.2);
        -moz-box-shadow: 0px 11px 11px 1px rgba(34, 60, 80, 0.2);
        box-shadow: 0px 11px 11px 1px rgba(34, 60, 80, 0.2);
    }
    .photo{
        max-width: 90px;
        max-height: 70px;
    }
    .cont{
        width: 100%;
    }
</style>
<div class="cont">
    <table>
        <tr><th>Фото</th><th>Имя</th><th>Цена</th></tr>
        <#list products as i>
            <tr><th><img style="border-radius: 10px" src="${i.photo}" class="photo"></th><th>${i.name}</th><th>${i.price}</th></tr>
        </#list>
        <tr><th></th><th>Сумма:</th><th>${sum}</th></tr>
    </table>
</div>
</body>
</html>
