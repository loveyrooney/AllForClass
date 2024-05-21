const listjson = function () {
    let lid = document.querySelector('#rlid').value
    fetch('/replylist/' + lid, {
        method: 'GET'
        , headers: {
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (!response.ok)
            throw new Error('not load');
        return response.json();
    }).then((data) => {
        console.log("data: "+data);
        data.forEach(item => {
            console.log(item)
            let ele_li = document.createElement('li')
            let ele_txt1 = document.createTextNode(item.content)
            // let ele_txt2 = document.createTextNode(item.writedate)

            ele_li.appendChild(ele_txt1)
            // ele_li.appendChild(ele_txt2)

            document.getElementById('replyList').appendChild(ele_li);
        })

    }).catch(error => console.log(error))
        .finally(() => {
            console.log("finally")
        })
}

// uid 로그인 세션에서 나중에 처리
window.onload = function () {
    listjson()
    document.querySelector('#append').onclick = function () {
        let content = document.querySelector('#content');
        let lid = document.querySelector('#rlid').value

        let r = {
            'content' : content.value
            , 'lid' : lid
            , 'uid' : 3
        }
        content.value = '';

        fetch('/replyinsert', {
            method: 'POST'
            , headers: {
                'Content-Type': 'application/json;charset=utf-8'
                , 'Accept': 'application/json'
            }
            , body: JSON.stringify(r)
        }).then(response => {
            if (response.status === 200)
                console.log('saved');
            else if (response.status === 403)
                console.log('403-error');
            // console.log('...', response);
            // return response.text();
            return response.json();
        }).then(data => {
            console.log(data);
        }).catch(error => {
            console.log(error);
        }).finally(() => {
            console.log("finally")
        });
    }
}