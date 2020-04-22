layui.use(['form','layer','jquery'], function () {

    // 操作对象
    var form = layui.form;
    var $ = layui.jquery;
    form.on('submit(login)',function (data) {
        // console.log(data.field);
        $.ajax({
            url:'/login',
            data:data.field,
            type:'post',
            success:function (result) {
                var success = result.success;
                if (success){
                    location.href = "/consumer-info/index";
                }else{
                    layer.msg('登录名或密码错误');
                }
            }
            // success:function(result){
            //     var success = result.success;
            //     var message = result.message;
            //     if(success){
            //         var jumpPath = $.trim($('#jumpPath').val());
            //         var temp = document.createElement("form");
            //         temp.method = "GET";
            //         document.body.appendChild(temp);
            //         temp.submit();
            //     }
            // }
        })
        return false;  //禁用form的action地址
    })

});