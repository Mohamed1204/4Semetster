$(document).ready(function(){
    // $("button").click(function(e){
    //     console.log(e);
    //     console.log(e.type);
    //     console.log(e.bubble);
    // });

    $(document).on("mousemove", function(e){
        console.log("X coord: " +e.clientX + "Y coord: " +e.clientY);
        $("coords").html("X coord: " +e.clientX + "Y coord: " +e.clientY);
    });

});