

$(function(){

    const appendAffair = function(data) {
        var affairCode = '<a href="#" class="affair-link" data-id="' +
            data.id + '">' + data.title + '</a><br>';
        $('#affair-list')
            .append('<div>' + affairCode + '</div>');
    };

    //Loading affairs on load page
//    $.get('/affairs/', function(response)
//    {
//        for(i in response) {
//            appendAffair(response[i]);
//        }
//    });


    //Show adding affair form
    $('#show-add-affair-form').click(function(){
        $('#affair-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#affair-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting affair
    $(document).on('click', '.affair-link', function(){
        var link = $(this);
        var affairId = link.data('id');
        console.log(affairId);
        $.ajax({
            method: "GET",
            url: '/affairs/' + affairId,
            success: function(response)
            {
                var code = '<span>Дата добавления: ' + response.addedDate + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Книга не найдена!');
                }
            }
        });
        return false;
    });

    //Adding book
    $('#save-affair').click(function()
    {
        var data = $('#affair-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/affairs/',
            data: data,
            success: function(response)
            {
                $('#affair-form').css('display', 'none');
                var affair = {};
                affair.id = response;
                var dataArray = $('#affair-form form').serializeArray();
                for(i in dataArray) {
                    affair[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendAffair(affair);
            }
        });
        return false;
    });

});


