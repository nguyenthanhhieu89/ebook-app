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

    function doPatch(url, data, doSuccess, doError) {
        $.ajax({
            type: "PATCH",
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
    function toastShowNotification(text,icon) {
            $.toast({
                text : text && text,
                showHideTransition: 'fade',
                position : 'top-right',
                icon : icon ? icon : "success" ,
                loader : false
            })
    }
    function getBookDetail(id) {
        window.location.href = window.location.origin + `/book-page/${id}`
    }

    return {
        get: doGet,
        post: doPost,
        put: doPut,
        delete: doDelete,
        patch: doPatch,
        notify : toastShowNotification,
        getBookDetail : getBookDetail
    }
}();

