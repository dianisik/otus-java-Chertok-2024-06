<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Список клиентов</title>
    <style>
        body {
            font-family: sans-serif;
            padding: 20px;
        }

        table {
            border-collapse: collapse;
            border: 1px solid gray;
            width: 100%;
        }

        th, td, td {
            border: 1px solid gray;
            padding: 5px;
        }

        thead {
            background-color: lightgray;
            font-weight: bold;
        }
        button {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<table>
    <thead>
    <tr>
        <td>ID</td>
        <td>ИМЯ</td>
        <td>АДРЕС</td>
        <td>ТЕЛЕФОНЫ</td>
    </tr>
    </thead>
    <#list clients as client>
    <tr>
        <td>${client.id}</td>
        <td>${client.name}</td>
        <td>${client.address.street}</td>
        <td><ul>
            <#list client.phones as phone>
                <li>${phone.number}</li>
            </#list>
            </ul></td>
    </tr>
    </#list>
</table>
<a href="/client/new"><button>Add client</button></a>
</body>
</html>