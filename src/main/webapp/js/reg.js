function validate() {
    const email = $('#emailInput').val();
    const name = $('#nameInput').val();
    const password = $('#passwordInput').val();
    let alertMessage = "";
    if (email === "") {
        alertMessage = "Необходимо указать email\n";
    }
    if (name === "") {
        alertMessage += "Необходимо указать имя пользователя\n";
    }
    if (password === "") {
        alertMessage += "Необходимо указать пароль";
    }
    if (alertMessage !== "") {
        alert(alertMessage);
        return false;
    }
    return true;
}