$(function () {
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;

    var initUrl = '/shop/admin/getShopInit';
    var registerShopUrl = '/shop/admin/register';
    var shopInfoUrl = '/shop/admin/shop/'+ shopId;
    var editShopUrl = '/shop/admin/modify';


    if (!isEdit) {
        getShopInitInfo();
    }else {
        getShopInfo();
    }

    function getShopInfo() {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $("#id").val(shop.id);
                $("#name").val(shop.name);
                $("#addr").val(shop.addr);
                $("#phone").val(shop.phone);
                $("#desc").val(shop.desc);
                var shopCategory = '<option data-id="' + shop.shopCategory.id + '" selected>'
                    + shop.shopCategory.name + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item) {
                    var selected = item.id == shop.area.id? 'selected' : '';
                    tempAreaHtml += '<option data-id="' + item.id + '" ' + selected + '>'
                        + item.name + '</option>';
                });
                $("#shopCategory").html(shopCategory);
                $("#shopCategory").attr('disabled', 'disabled');
                $("#area").html(tempAreaHtml);
            }else {
                getShopInitInfo();
            }
        });
    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.id + '">'
                    + item.name + '</option>';
                });

                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.id + '">'
                        + item.name + '</option>';
                });
                $("#shopCategory").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
        });
    }

    /**
     * 异步上传文件，使用FormData对象
     */
    $("#submit").click(function () {
        var shop = {};
        if (isEdit) {
            shop.id = shopId;
        }
        shop.name = $("#name").val();
        shop.addr = $("#addr").val();
        shop.phone = $("#phone").val();
        shop.desc = $("#desc").val();
        shop.shopCategory = {id: $('#shopCategory option').not(function(){ return !this.selected }).attr('data-id')}
        shop.area = {id: $('#area option').not(function(){ return !this.selected }).attr('data-id')}

        var shopImg = $("#img")[0].files[0];

        var formData = new FormData();
        formData.append('shopImgFile', shopImg);
        formData.append('shop', JSON.stringify(shop));

        var verifyCodeActual = $("#captcha").val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: isEdit ? editShopUrl : registerShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功')
                }else {
                    $.toast('提交失败！' + data.msg);
                }
                $("#captcha_img").click();
            }
        });

    });

});