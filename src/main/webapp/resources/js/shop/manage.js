$(function () {
    var shopId = getQueryString('shopId');

    var shopInfoUrl = '/admin/shop/getShopManagementInfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        }else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $("#shopInfo").attr('href', '/shopAdmin/operation?shopId=' + shopId);
        }
    });

});