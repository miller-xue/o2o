
$(function () {
    var productListTemplate = $("#productListTemplate").html();
    var productListUrl = "/shop/admin/product/getListByShop";
    getList();
    function getList() {
        $.ajax({
            url: productListUrl,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handlerList(data.productList);
                }else {
                    $.toast('查询失败！' + data.msg);
                }
            }
        });
    }

    function statusName(status) {
        if (status == 0) {
            return "下架";
        }else {
            return "上架";
        }
    }
    function contraryStatus(status) {
        if (status == 0) {
            return "1";
        }else {
            return "0";
        }
    }

    function handlerList(productList) {
        Mustache.parse(productListTemplate);
        let rendered = Mustache.render(productListTemplate, {
            productList: productList,
            'statusName': function () {
                return statusName(this.enableStatus)
            },
            contraryStatus: function () {
                return contraryStatus(this.enableStatus)
            }
        });
        $('.product-wrap').html(rendered);
        bindOperation();
    }

    function bindOperation() {
        $(".product-wrap").on('click','a',function (e) {
            let target = $(e.currentTarget);
            if(target.hasClass('edit')){
                window.location.href = '/shopAdmin/product/operation?productId=' + e.currentTarget.dataset.id;
            }else if(target.hasClass('status')){
                changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
            }else if(target.hasClass('preview')){

            }
        })
    }

    function changeItemStatus(id, status) {
        $.confirm('确定么?',function () {
            $.ajax({
                url: '/shop/admin/product/modify',
                type: 'POST',
                data: {'productStr': JSON.stringify({id: id, enableStatus: status}),
                    statusChange: true} ,
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('提交失败！' + data.msg);
                    }
                    $("#captcha_img").click();
                }
            });
        })
    }

})