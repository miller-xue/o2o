$(function () {
    let listUrl = '/shop/admin/product/category/list?shopId=4';
    let addUrl = '/shop/admin/product/category/batchAdd';
    let deleteUrl = '/shop/admin/product/category/delete';
    let categoryListTemplate = $("#categoryListTemplate").html();
    let categoryAddTemplate = $("#categoryAddTemplate").html();
    getList();
    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var list = data.list;
                handlerList(list);
            }else {
                $.toast('查询失败！' + data.stateInfo);
            }
        });
    }

    function handlerList(list) {
        Mustache.parse(categoryListTemplate);
        var rendered = Mustache.render(categoryListTemplate, {
            categoryList: list,
        });
        $('.category-wrap').html(rendered);
    }

    $("#new").click(function () {
        $('.category-wrap').append(categoryAddTemplate);
    })

    $("#submit").click(function (e) {
        let tempArr = $(".temp");
        if (tempArr.length == 0) {
            $.toast('请先新增！');
            e.stopPropagation();
            e.preventDefault();
            return;
        }
        let productCategoryList = tempArr.map(function (index,item) {
            let name = $(item).find('.category').val();
            let priority = $(item).find('.priority').val();
            if (name && priority) {
                return {name: name, priority: priority}
            }
        });
        if (productCategoryList.length == 0) {
            $.toast('请输入数据！');
            e.stopPropagation();
            e.preventDefault();
            return;
        }
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(productCategoryList),
            contentType:'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                    getList();
                }else {
                    $.toast('提交失败！');
                }
            }
        });
    });

    $('.category-wrap').on('click', '.row-product-category.temp .delete', function (e) {
        $(this).parent().parent().remove();
    });

    $('.category-wrap').on('click', '.row-product-category.now .delete', function (e) {
        var target = e.currentTarget;
        $.confirm('确定么？', function () {
            $.ajax({
                url: deleteUrl + '/' + target.dataset.id,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('删除成功!');
                        getList();
                    }else{
                        $.toast('删除失败！');
                    }
                }
            });
        });
    });
});