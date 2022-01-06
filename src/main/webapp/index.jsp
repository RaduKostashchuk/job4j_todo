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
            + "<%=request.getServletContext().getContextPath()%>"
    </script>
    <script src="js/index.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav justify-content-end">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout"><c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>
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
                    <th>Пользователь</th>
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