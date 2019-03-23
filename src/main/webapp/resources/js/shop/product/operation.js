$(function () {
    let productId = getQueryString('productId');
    let categoryUrl = "/shop/admin/product/category/list";
    let infoUrl = '/shop/admin/product/' + productId;
    let saveUrl = '/shop/admin/product/add';
    let isEdit = false;
    if (productId) {
        getInfo(productId);
        isEdit = true;
        saveUrl = '/shop/admin/product/modify';
    }else {
        getCategory();
    }

    /**
     * 获取商品详情，并且数据填写页面
     * @param id
     */
    function getInfo(id) {
        $.getJSON(infoUrl, function (data) {
            if (data.success) {
                let product = data.product;
                $("#name").val(product.name);
                $("#desc").val(product.desc);
                $("#priority").val(product.priority);
                $("#normalPrice").val(product.normalPrice);
                $("#promotionPrice").val(product.promotionPrice);
                let tempHtml;
                data.productCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.id + '"' +
                    product.productCategory.id == item.id ? 'selected' : ''
                        + '>'
                        + item.name + '</option>';
                });
                $("#productCategory").html(tempHtml);
            }else {
                $.toast('获取商品详情失败！' + data.msg);

            }
        });
    }

    /**
     * 获取店铺商品分类
     */
    function getCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.success) {
                let tempHtml = '';
                data.list.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.id + '">'
                        + item.name + '</option>';
                });
                $("#productCategory").html(tempHtml);
            }else {
                $.toast('初始化失败！' + data.msg);

            }
        });
    }


    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">')
        }
    });


    $("#submit").click(function () {
        let verifyCodeActual = $("#captcha").val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return;
        }
        let product = {
            name: $("#name").val(),
            desc: $("#desc").val(),
            priority: $("#priority").val(),
            normalPrice: $("#normalPrice").val(),
            promotionPrice: $("#promotionPrice").val(),
            name: $("#name").val(),
            productCategory:{id: $('#productCategory option').not(function(){ return !this.selected }).attr('data-id')}
        }
        if(isEdit) product.id = productId;
        let thumbnail = $("#small-img")[0].files[0];
        let formData = new FormData();
        formData.append('thumbnail', thumbnail);
        formData.append('productStr', JSON.stringify(product));

        $('.detail-img').map(function (index, item) {
            if ($('.detail-img')[index].files.length > 0) {
                formData.append('productImg', $('.detail-img')[index].files[0]);
            }
        });
        formData.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            url: saveUrl,
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
        })
    });
});