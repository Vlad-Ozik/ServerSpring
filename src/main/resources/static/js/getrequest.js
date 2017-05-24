$( document ).ready(function() {

    // GET REQUEST
    $("#getBtn").click(function(event){
        event.preventDefault();
        ajaxGetU();
    });

    $("#getMessBt").click(function (event) {
        event.preventDefault();
        ajaxGetAllM();
    });

    // DO GET
    function ajaxGetU(){
        $.ajax({
            type : "GET",
            url : "http://localhost:8081/allUsers",
            success: function(result){
                    $('#getResultDiv .ulUsers li').remove();
                    $.each(result, function(i, customer){
                        var customer = customer.username + "<br\>";

                        $('#getResultDiv .ulUsers').append('<li id='+(i+1)+' class="navli" >' +
                            '<h4 class="list-group-item"><a href="#">'+customer+'</a></h4></li>')
                    });
                $('.navli').click(function () {
                    ajaxGetMesByUsername($(this).text());
                })
                    console.log("Success: ", result);
            },
            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }

    function ajaxGetAllM() {
        $.ajax({
                type: "GET",
                url: "http://localhost:8081/getAllMessages",
            success: function (result) {
                    $('#getResM .ulMess li').remove();
                    $.each(result,function(i,mess){
                        var message ="UserTo:" + mess.userOne + "| UserFrom:"+mess.userTwo  + "| Message:"+mess.message + "<br\>";
                        $('#getResM .ulMess').append('<li id='+(i+1)+'>' +
                            '<h4 class="list-group-item"><a href="#"> '+message+'</a></h4></li>')
                    })

            }
            }
        )
    }

    function ajaxGetMesByUsername(username) {
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/findConversationForUserOne/"+ username,
            success: function (result) {
                $('#getResM .ulMess li').remove();
                $.each(result,function(i,mess){
                    var message ="UserTo:" + mess.userOne + "| UserFrom:"+mess.userTwo  + "| Message:"+mess.message + "<br\>";
                    $('#getResM .ulMess').append('<li id='+(i+1)+'>' +
                        '<h4 class="list-group-item"><a href="#"> '+message+'</a></h4></li>')
                })
            }
        })
    }
});


