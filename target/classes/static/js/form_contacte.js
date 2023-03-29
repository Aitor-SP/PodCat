$(document).ready(function () {

    var emailreg = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;

    $("#botoenviar").click(function (){
        $(".error").remove();
        if( $("#name").val() == "" ){
            $("#name").focus().after("<span class='error'>Afegeix el seu nom</span>");
            return false;
        }else if( $("#email").val() == "" || !emailreg.test($("#email").val()) ) {
            $("#email").focus().after("<span class='error'>Afegeix un email correcte</span>");
            return false;
        }else if( $("#asunto").val() == ""){
            $("#asunto").focus().after("<span class='error'>Afegeix un assumpte</span>");
            return false;
        }else if( $("#message").val() == "" ) {
            $("#message").focus().after("<span class='error'>Afegeix un missatge</span>");
            return false;
        }else {

                $(".frmhide").css("display","none");


        }
    });

});

