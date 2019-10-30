/* 
 * Automatically fills in form data
 */

var autofill = (function(){
    return{
        init: function() {
            $("version").html( "jQuery Version: " + $().jquery );
        },
        autoFill: function(){
            $("fName").change(function(){
                $("givenName").val($("fName").val());
            });
            $("lName").change(function(){
                $("givenName").val($("fName").val()+""+$("lName").val());
            });
        }
    };
}());
