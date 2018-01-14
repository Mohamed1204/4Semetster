$( document ).ready(function() {
 // $("#textbox").hide();
 //  $("textbox").remove();
    
 $("#hideBtn").click(function(){
        console.log("button clicked");
        $("#textbox").hide();
    });

    $("#showBtn").click(function(){
        $("#textbox").show();
    });

    $("#toggle").click(function(){
        $("#textbox").toggle();
    });

$("#colorMeRed").on("click", function(){
    console.log("Color me RED")
});

$("#colorMeBlue").click(function(){
    console.log("Color me Blue")
});

$("#colorMeRed").on("mouseover", function(){
    $("#textbox").css("color", "red");
});

$("#colorMeBlue").on("mouseover", function(){
    $("#textbox").css("color", "blue");
});

$("#submit").click(function(){
    var textcolor = $("#colorme").val();
    $("#textbox").css("color", textcolor);
});

});