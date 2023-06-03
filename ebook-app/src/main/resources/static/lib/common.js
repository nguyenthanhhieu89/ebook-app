const MyAjax = function () {
    function doGet(url, doSuccess, doError) {
        $.ajax({
            type: "GET",
            url: url,
            success: (response) => {
                doSuccess && doSuccess(response);
            },
            error: (xhr, status, error) => {
                doError && doError(xhr, status, error);
            }
        });
    }

    function doPost(url, data, doSuccess, doError) {
        $.ajax({
            type: "POST",
            url: url,
            datatype: "json",
            contentType: "application/json; charset=utf-8",
            data: data,
            success: (response) => {
                doSuccess && doSuccess(response);
            },
            error: (xhr, status, error) => {
                doError && doError(xhr, status, error);
            }
        });
    }

    function doPut(url, data, doSuccess, doError) {
        $.ajax({
            type: "PUT",
            url: url,
            datatype: "json",
            contentType: "application/json; charset=utf-8",
            data: data,
            success: (response) => {
                doSuccess && doSuccess(response);
            },
            error: (xhr, status, error) => {
                doError && doError(xhr, status, error);
            }
        });
    }

    function doDelete(url, data, doSuccess, doError) {
        $.ajax({
            type: "DELETE",
            url: url,
            data: data,
            success: (response) => {
                doSuccess && doSuccess(response);
            },
            error: (xhr, status, error) => {
                doError && doError(xhr, status, error);
            }
        });
    }

    return {
        get: doGet,
        post: doPost,
        put: doPut,
        delete: doDelete,
    }
}();

function clickLogin() {
    window.location.href = window.location.origin + "/auth/login-page";
}

function clickSignUp() {
    window.location.href = window.location.origin + "/auth/register-page";
}

function clickBookLib() {
    window.location.href = window.location.origin + "/public/book-library-page";
}

function clickReadBook() {
    window.location.href = window.location.origin + "/public/book-info-page";
}