$('#sendbutton').click(function () {
    //发送数据
	var data='{"paste":'+$('#message')[0].value+'}';
	console.log(data);
    $.ajax({
        url:'/',
        type:'POST',
        dataType:'JSON',
        contentType: 'application/json',
        data:JSON.parse(JSON.stringify(data)),
        success:function (data,status) {
            
        }
    });
});