/* 
 * A script to handle submitting form data to server
 */

var submit = (function(){
    return{
        postForm: function(){
            $("registerForm").submit(function(event){
                event.preventDefault();
                var url = $(this).attr("action");
                var data = $(this).serialize();
                
                $.post( url, data, function(response) {
                        $("#server-results").html(response);
                }); // need to verify
            });
        }
    };
}());