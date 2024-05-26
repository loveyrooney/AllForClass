// 비디오 업로드


// 파일 업로드
const uploadFiles = function () {

    let role = document.querySelector('#role').value;
    let tid = document.querySelector('#tid').value;
    let sessionId = document.querySelector('#sessionId').value;
    let form = document.getElementById('fileUploadForm'); // 폼 요소 가져오기
    let div_form = document.getElementById('file_upload');

    // 강사(자신이 올린 강의) / 관리자일 경우에만 파일 추가 버튼 등을 생성
    if (('teacher' === String(role) && String(sessionId) === String(tid)) || 'admin' === String(role)) {
        let fileInput = document.createElement('input');
        fileInput.setAttribute('type', 'file');
        fileInput.setAttribute('name', 'files');
        fileInput.setAttribute('id', 'files');
        fileInput.setAttribute('multiple', 'multiple');
        div_form.appendChild(fileInput);

        let spanElement = document.createElement('span');
        spanElement.className = 'span_file';
        spanElement.textContent = '선택된 파일이 없습니다.';
        div_form.appendChild(spanElement);

        let labelElement = document.createElement('label');
        labelElement.className = 'label_file_btn';
        labelElement.setAttribute('for', 'files');
        labelElement.textContent = '강의 자료 선택';
        div_form.appendChild(labelElement);

        let buttonElement = document.createElement('button');
        buttonElement.setAttribute('type', 'submit');
        buttonElement.textContent = '강의 자료 추가';
        div_form.appendChild(buttonElement);
    }

    form.addEventListener('submit', function (event) {
        // 기본 동작 방지
        event.preventDefault();
        const formData = new FormData(form);

        fetch('/insertref', {
            method: 'POST',
            headers: {
                'Accept': 'multipart/form-data'
            },
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                window.location.reload();
                return response.formData;
            })
            .then(data => {
                console.log('Success:', data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

}

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
            if (item.urole === 1) {
                item.urole = 'student';
            } else if (item.urole === 2) {
                item.urole = 'teacher';
            } else {
                item.urole = 'admin';
            }

            let ele_li = document.createElement('li');
            let ele_txt1 = document.createTextNode("[" + item.urole + item.uid + "] " + item.content);
            ele_li.appendChild(ele_txt1);

            if (String(sessionId) === String(item.uid)) {
                let ele_delbtn = document.createElement('button');
                ele_delbtn.innerHTML = '삭제';
                ele_delbtn.id = 'delbtn' + item.rid;
                ele_delbtn.onclick = function () {
                    replydelete(item.rid);
                };
                ele_li.appendChild(ele_delbtn);
            }
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

    replylistjson();
    uploadFiles();

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
        } else {
            alert('내용을 입력하세요!');
        }
    }

    // 댓글 추가 엔터키 동작
    document.querySelector('#content').addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            document.querySelector('#append_btn').onclick();
        }
    });

    // 파일 인풋 커스텀
    function customFileInput(event) {
        let files = event.target.files;
        let fileNames = [];

        for (let i = 0; i < files.length; i++) {
            fileNames.push(files[i].name);
        }

        let fileListHtml = fileNames.join('<br>');
        let spanFileElement = event.target.parentElement.querySelector('.span_file');

        if (spanFileElement) {
            spanFileElement.innerHTML = fileListHtml || '선택된 파일이 없습니다.';
        }
    }

    document.getElementById('vidfile').addEventListener('change', customFileInput);
    document.getElementById('files').addEventListener('change', customFileInput);
}
