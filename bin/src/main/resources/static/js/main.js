$('#sendbutton').click(function () {
    console.log("send1!");
    //发送数据
    $.ajax({
        url:'/',
        method:'POST',
        data:new Json,
        success:function (data,status) {
            
        }
    });
});