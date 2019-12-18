/* Javascript Document */

$(document).ready(function () {

        $(function () {
            $('[data-toggle="popover"]').popover()
        });

        $( "#bookSearch" ).autocomplete({
            minLength: 0,
            source: function( request, response ) {
                var term = request.term;
                $.getJSON( "/book/bookList", request, function( data, status, xhr ) {

                    hideShowCard(data);
                    response(

                        $.map( data, function( item ) {
                            return {
                                value: item.title ,
                                isbn: item.isbn,
                                id:item.id
                            }
                        })
                    );
                });
            },
            select:function (event,ui) {
                $(".bookList").hide();
                $(".bookList[id=" + ui.item.isbn + "]").show();
            }

        });
});
function hideShowCard(data) {
    $(".bookList").removeClass("bookListHide").removeClass("bookListShow");
    $(".bookList").addClass("bookListHide");

    $.each(data,function (index,item) {
        $(".bookList[id=" + item.isbn + "]").removeClass("bookListHide").addClass("bookListShow");
    });

    $(".bookListHide").hide();
    $(".bookListShow").show();

}