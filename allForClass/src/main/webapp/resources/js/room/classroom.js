// 댓글 리스트
const replylistjson = function () {
    let lid = document.querySelector('#rlid').value;
    let sessionId = document.querySelector('#sessionId').value;

    fetch('/replylist/' + lid, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (!response.ok) {
            throw new Error('Failed to load reply list');
        }
        return response.json();
    }).then((data) => {
        const replyList = document.getElementById('replyList');
        replyList.innerHTML = '';

        data.forEach(item => {
            // console.log(item);
            // console.log(sessionId);
            // console.log(item.uid);
            let ele_li = document.createElement('li');
            let ele_txt1 = document.createTextNode(item.content);
            ele_li.appendChild(ele_txt1);

            if (String(sessionId) === String(item.uid)) {
                // console.log(sessionId);
                // console.log(item.uid);
                let ele_delbtn = document.createElement('button');
                ele_delbtn.innerHTML = '삭제';
                ele_delbtn.id = 'delbtn' + item.rid;
                ele_delbtn.onclick = function () {
                    replydelete(item.rid);
                };
                ele_li.appendChild(ele_delbtn);
            }
            // else{
            //     console.log("sessionId: "+sessionId);
            //     console.log("uid: "+item.uid);
            //     console.log('not match')
            // }
            replyList.appendChild(ele_li);
        });
    }).catch(error => {
        console.log('Error: ', error);
    }).finally(() => {
        console.log("reply list finally");
    });
}

// 댓글 삭제
const replydelete = function (rid) {

    fetch('/replydelete/' + rid, {
        method: 'POST',
        headers: {
            'Accept': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('Failed to delete reply');
        }
        return response.json();
    }).then(data => {
        console.log(data);
        replylistjson();
    }).catch(error => {
        console.log('Error: ', error);
    }).finally(() => {
        console.log("delete reply finally");
        // console.log(rid);
    });

}

window.onload = function () {
    // 파일 업로드
    const uploaderCheck = function () {
        let role = document.querySelector('#role').value;
        let tid = document.querySelector('#tid').value;
        let sessionId = document.querySelector('#sessionId').value;

        //  강사(자신이 올린 강의) / 관리자
        if (('teacher' === String(role) && String(sessionId) === String(tid)) || 'admin' === String(role)) {
            console.log('you can upload');
            let ele_visible = document.getElementById('file_upload');
            ele_visible.style.visibility = 'visible';
        } else {
            console.log('your id: ' + sessionId);
        }
    }
    uploaderCheck();

    replylistjson();
    // 댓글 추가
    document.querySelector('#append_btn').onclick = function () {
        let content = document.querySelector('#content').value;
        let lid = document.querySelector('#rlid').value;
        let sessionId = document.querySelector('#sessionId').value;

        if (String(content) !== '') {
            let r = {
                'content': content,
                'lid': lid,
                'uid': sessionId
            };
            document.querySelector('#content').value = '';

            fetch('/replyinsert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(r)
            }).then(response => {
                if (!response.ok) {
                    if (response.status === 403) {
                        console.log('403 - Forbidden');
                    } else {
                        throw new Error('Failed to save reply');
                    }
                }
                return response.json();
            }).then(data => {
                console.log(data);
                replylistjson();
            }).catch(error => {
                console.log('Error: ', error);
            }).finally(() => {
                console.log("reply insert finally");
            });
        }else{
            alert('내용을 입력하세요!');
        }

    }

    // 파일 버튼 커스텀
    document.getElementById('files').addEventListener('change', function () {
        let files = this.files;
        let fileNames = [];

        for (let i = 0; i < files.length; i++) {
            fileNames.push(files[i].name);
        }

        let fileListHtml = fileNames.join('<br>');
        let spanFileElement = this.parentElement.querySelector('.span_file');

        if (spanFileElement) {
            spanFileElement.innerHTML = fileListHtml || '선택된 파일이 없습니다.';
        }
    });
}
