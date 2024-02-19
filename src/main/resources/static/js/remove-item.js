function removeItem(itemId) {
    var confirmation = confirm("Are you sure you want to remove this item?");
    if (confirmation) {
        var removeUrl = '/item/remove/' + itemId;
        var xhr = new XMLHttpRequest();
        xhr.open('GET', removeUrl, true);
        xhr.onload = function () {
            setTimeout(function () {
                location.reload();
            }, 500);
        };
        xhr.send();
    }
}
