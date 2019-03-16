$(function () {
    let listUrl = '/shop/admin/product/category/list?shopId=4';
    let addUrl = '/shop/admin/product/category/add';
    let deleteUrl = '/shop/admin/product/category/remove';
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
});