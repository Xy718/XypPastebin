$('#sendbutton').click(function () {
    //发送数据
	var data='{"paste":"'+window.btoa($('#message')[0].value)+'"}';
	
	console.log(data);
	console.log(JSON.parse(JSON.stringify(data)));
    $.ajax({
        url:'/',
        type:'POST',
        dataType:'JSON',
        contentType: 'application/json',
        data:JSON.parse(JSON.stringify(data)),
        success:function (data,status) {
            if(data.code==0){
                window.location.href='p/'+data.data;
            }
        }
    });
});