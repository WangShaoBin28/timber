//JavaScript代码区域
layui.use('element', function(){
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    //监听导航点击
    element.on('nav(demo)', function(elem){
        //console.log(elem)
        layer.msg(elem.text());
    });
});
layui.use('table', function () {
    var table = layui.table
        , layer = layui.layer;
    table.render({
        elem: '#logTable',
        url: '/consumer-info/list',
        toolbar: 'default',
        defaultToolbar: ['filter'],
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        autoSort: false, //禁用前端自动排序。
        cellMinWidth: 80,
        even: true,
        size: 'lg',
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'consumerName', title: '客户名称', sort: true, align: 'center'},
            {field: 'tel', title: '客户电话', align: 'center'},
            {fixed: 'right', align: 'center', toolbar: '#barDemo'}
            // {field: 'id', title: 'ID',hide: true}
        ]],
        limits: [10, 20, 30],
        done: function (res, curr, count) {
            //客户表格渲染的时候不要toolbar的删除按钮
           // $(".layui-inline[lay-event='delete'] ").css("display",'none');
        },
        page: true,
        text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听表格排序问题
    table.on('sort(logTable)', function (obj) { //注：tool是工具条事件名，logTable是table原始容器的属性 lay-filter="对应的值"
        console.log(obj);
        var sortType = obj.type;
        if (!obj.type) {
            sortType = "desc"
        }
        //尽管我们的 table 自带排序功能，但并没有请求服务端。
        //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
        table.reload('logTable', { //testTable是表格容器id
            initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
            where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                sort: obj.field + ',' + sortType
            }
        });
    });

    //监听行工具事件
    table.on('tool(logTable)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'detail') {
            layer.open({
                id: 'info-open',
                type : 2,  //type1 的content是dom结构，  2是后台或者iframe的url
                title : ['客户信息详情','font-size:20px;font-weight:bold;margin-top:10px'],
                maxmin : true,
                // // offset: '80px',
                area : [ '600px', '500px' ],
                content : '/consumer-info/detail/'+ data.id                       // iframe的url
            });
        } else if (layEvent === 'del') {
            layer.confirm('确定要删除该客户吗？', function (index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    url: '/consumer-info/checkConsumerInfo/' + data.id,
                    type: 'POST',
                    success: function (result) {
                        if (result) {
                            $.ajax({
                                url: '/consumer-info/delete/' + data.id,
                                type: 'POST',
                                success: function (result2) {
                                    if (result2) {
                                        layer.msg("删除成功");
                                        window.location.href = "/consumer-info/index";
                                    } else {
                                        layer.msg("删除失败");
                                    }
                                }
                            })
                        } else {
                            layer.msg("该客户有销售登记，不能被删除！");
                        }
                    }
                });
            })
        } else if (layEvent === 'edit') {
            // layer.msg('编辑操作');
            window.location.href = "/consumer-info/to-update/" + obj.data.id;
        }
    });

    //监听头工具栏事件
    table.on('toolbar(logTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                window.location.href = "/consumer-info/to-insert";
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个');
                } else {
                    window.location.href = "/consumer-info/to-update/" + checkStatus.data[0].id;
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        }
        ;
    });
    var $ = layui.$, active = {
        reload: function () {
            var consumerNameInput = $('#consumerNameInput');
            var tel = $('#telInput');
            //执行重载
            table.reload('logTable', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    consumerName: consumerNameInput.val(),
                    tel: tel.val()
                }
            });
        }
    };

    $('.paltformTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


});

layui.use(['form', 'layedit'], function () {
    var $ = layui.jquery;
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate;

    //自定义验证规则
    form.verify({
        title: function (value) {
            if (value.length < 1) {
                return '不能为空！！！';
            }
        }
        , pass: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ]
        , textarea: function (value) {
            if (value.length > 1000) {
                return '输入的字数不能超过一千个字';
            }
        }
        , content: function (value) {
            layedit.sync(editIndex);
        }
    });

    //监听提交
    form.on('submit(consumer-form)', function (data) {
        $.ajax({
            url: '/consumer-info/save',
            data: data.field,
            type: 'POST',
            success: function (result) {
                if (result) {
                    layer.msg("提交成功");
                    window.location.href = "/consumer-info/index";
                } else {
                    layer.msg("提交失败");

                }
            }
        });
        return false;
    });
});