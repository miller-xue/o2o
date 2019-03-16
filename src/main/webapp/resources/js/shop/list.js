
$(function () {
    var shopListTemplate = $("#shopListTemplate").html();
    var shopListUrl = "/admin/shop/list";
    getList();
    function getList() {
        $.ajax({
            url: shopListUrl,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handlerList(data.shopList);
                    handlerUser(data.user);
                }
            }
        });
    }

    function handlerUser(user) {
        $("#user-name").text(data.name);
    }

    function handlerList(shopList) {
        Mustache.parse(shopListTemplate);
        var rendered = Mustache.render(shopListTemplate, {
            shopList: shopList,
            shopStatus: function () {
                return shopStatus(this.enableStatus);
            },
            goShop: function () {
                return goShop(this.enableStatus, this.id);
            }
        });
        $('.shop-wrap').html(rendered);
    }

    function shopStatus(status) {
        if (status == 0) {
            return '审核中';
        }else if (status == -1) {
            return '店铺非法';
        }else {
            return '审核通过';
        }
    }

    function goShop(status, id) {
        if (status == 1) {
            return '<a href="/shopAdmin/manage?shopId='+ id + '">进入</a>'
        }
        return '';
    }
})