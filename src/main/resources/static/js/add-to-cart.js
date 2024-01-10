function removeItem(itemId) {
    alert('Item has been added to your cart');

//    if (confirmation) {
        var removeUrl = '/add-to-cart/' + itemId + ;

        var xhr = new XMLHttpRequest();
        xhr.open('GET', removeUrl, true);
        xhr.onload = function () {
            setTimeout(function () {
                location.reload();
            }, 500);
        };
        xhr.send();
//    }
}