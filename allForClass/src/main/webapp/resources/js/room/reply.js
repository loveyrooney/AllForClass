const listjson = function (){
    fetch('/replylist/'+lid,{
        method: 'GET'
        , headers : {
            'Accept' : 'application/json'
        }
    })
}

window.onload = function (){
    document.querySelector('#append').onclick = function (){
        let content = document.querySelector('#content');
        let lid = document.querySelector('#lid');
        let lcode = lid.value;

        let r={
            'content' : content.value
            , 'lid' : lcode
        }
        content.value = '';

        fetch('/replyinsert',{
            method : 'POST'
            , headers : {
                'ContentType' : 'application/json;charset=utf-8'
                , 'Accept' : 'application/json'
            }
            , body : JSON.stringify(r)
        }).then({

        })

    }
}