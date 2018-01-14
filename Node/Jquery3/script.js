$(document).ready(function(){
    
    $("#studentName, #studentMsg").focus(function(){
        $(this).css("background", "blue")
    });

    $("#studentName, #studentMsg").blur(function(){
       $(this).css("background", "#fff"); 
    });
    
    $("#sendMsg").click(function(){
        var studentName = $("#studentName").val();
        var studentMsg = $("#studentMsg").val();

        var msg = "<p class='msg'>"+studentName + ": "+ studentMsg + "</p>";
        
        $("#messageBox").append(msg);
     });

     var colors = ["yellow", "pink", "red", "green"];
     var number = 0;
     // on the button of #changeBorderColor
     // run the function
     // that will change the border color of all message

     $("#changeBorderColor").click(function(){
         changeColor();
     });

     //create hte ChangeColors functions -- loop array and change color
     var changeColor = function(){
         console.log(colors[number]);
        $(".msg").css("border-color", colors[number]); 
        if(number == colors.length-1){
            number = 0;
        }else{
            number++;   
        }
        
     }
});