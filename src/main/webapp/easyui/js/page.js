$(function(){

    //设置默认的分页参数
    if ($.fn.datagrid){
        $.fn.datagrid.defaults.pageSize = 20;//这里一定要用datagrid.defaults.pageSize，用pagination.defaults.pageSize一直不行
        $.fn.datagrid.defaults.pageList = [20,40,50,100];//这里一定要有，不然上面的也不起效
    }
});