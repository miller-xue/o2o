$(function () {

    var initUrl = '/shop/admin/getshopinitinfo';
    var registerShopUrl = '/shop/admin/register';
    getShopInitInfo();
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
        $("#submit").click(function () {
            var shop = {};
            shop.name = $("#name").val();
            shop.addr = $("#addr").val();
            shop.phone = $("#phone").val();
            shop.desc = $("#desc").val();

            shop.shopCategory = {id: $('#shopCategory option').not(function(){ return !this.selected }).attr('data-id')}
            shop.area = {id: $('#area option').not(function(){ return !this.selected }).attr('data-id')}
            var shopImg = $("#img")[0].files[0];

            var formData = new FormData();
            formData.append('shopImg', shopImg);
            formData.append('shopStr', JSON.stringify(shop));
            var verifyCodeActual = $("#captcha").val();
            if (!verifyCodeActual) {
                $.toast("请输入验证码！");
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            $.ajax({
                url: registerShopUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast('提交成功')
                    }else {
                        $.toast('提交失败！' + data.errMsg);
                    }
                    $("#captcha_img").click();
                }
            });

        });
    }

});