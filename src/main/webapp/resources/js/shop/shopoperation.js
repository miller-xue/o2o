$(function () {

    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';
    getShopInitInfo();
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id=">' + item.id + '">'
                    + item.name + '</option>';
                });

                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id=">' + item.id + '">'
                        + item.name + '</option>';
                });
                $("#category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
        });
        $("#submit").click(function () {
            var shop = {};
            shop.name = $("#name").val();
            shop.addr = $("#addr").val();
            shop.phone = $("#phone").val();
            shop.desc = $("#desc").val();
            shop.category = {id: $('#category option:selected').val()}
            shop.area = {id: $('#area option:selected').val()}
            var shopImg = $("#img")[0].files[0];

            var formData = new FormData();
            formData.append('shopImg', shopImg);
            formData.append('shopStr', JSON.stringify(shop));
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
                }
            })

        });
    }

});