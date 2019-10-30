/* 
 * Automatically fills in form data
 */

var autofill = (function(){
    return{
        init: function() {
            $("#version").html( "jQuery Version: " + $().jquery );
        },
        autoFill: function(){
            var fullName = $("#fName").val()+" "+$("#lName").val();
            $("#fName").change(function(){
                $("#givenName").val(fullName);
            });
            $("#lName").change(function(){
                $("#givenName").val(fullName);
            });
        }
    };
}());
