<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Список задач</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
            integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
            integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>

    <script>
        let context = "http://" + "<%=request.getServerName()%>"
            + ":" + "<%=request.getServerPort()%>"
            + "<%=request.getServletContext().getContextPath()%>";
        function validate() {
            const description = $('#descriptionInput').val();
            if (description === "") {
                alert("Заполните описание задачи");
                return false;
            }
            return true;
        }
        function sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }
        function setDone() {
            let target = event.target;
            let id = target.id;
            $.ajax({
                type: 'GET',
                url: context + '/done' + '?id=' + id
            }).done(async function () {
                await sleep(500);
                drawTable();
            })
        }
        function getTasks() {
            return $.ajax({
                type: 'GET',
                url: context + '/show',
                dataType: 'json'
            })
        }
        function compare(a, b) {
            return a.created.localeCompare(b.created);
        }
        function drawTable() {
                window.onload = getTasks().done(function (data) {
                    let checkBox = document.getElementById("allCheck");
                    data.sort(compare);
                    let tbody = document.getElementById("mainTableBody");
                    while (tbody.firstChild) {
                        tbody.removeChild(tbody.firstChild);
                    }
                    for(let task of data) {
                        if (!checkBox.checked && task.done) {
                            continue;
                        }
                        let tr = document.createElement("tr");
                        let td1 = document.createElement("td");
                        let td2 = document.createElement("td");
                        let td3 = document.createElement("td");
                        let td4 = document.createElement("td");
                        let td5 = document.createElement("td");
                        td1.innerText = task.id;
                        td2.innerText = task.description;
                        const date = new Date(task.created);
                        const options = { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric',
                            hour: 'numeric', minute: 'numeric'};
                        td3.innerText = date.toLocaleDateString("ru-RU", options);
                        td4.innerHTML = '<span class="badge rounded-pill bg-warning text-dark">В процессе</span>';
                        if (task.done === true) {
                            td4.innerHTML = '<span class="badge rounded-pill bg-success text-dark">Выполнена</span>';
                        }
                        let input = document.createElement("input");
                        input.type = "checkbox";
                        input.className = "form-check-input m-1 align-middle";
                        input.id = task.id;
                        input.onchange = setDone;
                        let label = document.createElement("label");
                        label.className = "form-check-label m-1 align-middle";
                        label.htmlFor = input.id;
                        label.innerText = "Выполнить задачу";
                        if (task.done) {
                            input.checked = true;
                            label.innerText = "Возобновить задачу";
                        }
                        td5.append(input);
                        td5.append(label);
                        tr.append(td1);
                        tr.append(td2);
                        tr.append(td3);
                        tr.append(td4);
                        tr.append(td5);
                        tbody.append(tr);
                }
            })
        }
        drawTable();
        setInterval(drawTable, 10000);
    </script>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <p class="h5">Добавить задачу</p>
        </div>
        <div class="card-body form-group">
            <form action="<%=request.getContextPath()%>/add" method="post" onsubmit="return validate()">
                <div class="row">
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="descriptionInput" name="description" placeholder="Описание">
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <div class="row justify-content-between">
                <div class="col-4">
                    <p class="h5">Список задач</p>
                </div>
                 <div class="col-4">
                    <input class="form-check-input" type="checkbox" id="allCheck" onchange="drawTable()">
                    <label class="form-check-label" for="allCheck">
                        Показать все
                    </label>
                </div>
            </div>
        </div>

        <div class="card-body">
            <table class="table table-bordered">
                <thead class="table-light">
                <tr>
                    <th>Номер</th>
                    <th>Описание</th>
                    <th>Создана</th>
                    <th>Статус</th>
                    <th>Выполнить/Возобновить</th>
                </tr>
                </thead>
            <tbody id="mainTableBody">
            </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>