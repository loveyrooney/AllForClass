let params;
let init = function (inits) {
    params = inits;
}

const today = new Date();
let year = today.getFullYear();
let month = ('0' + (today.getMonth() + 1)).slice(-2);
let day = ('0' + today.getDate()).slice(-2);
let hours = ('0' + today.getHours()).slice(-2);
let minutes = ('0' + today.getMinutes()).slice(-2);
// let seconds = ('0' + today.getSeconds()).slice(-2);

let dateString = year + '-' + month + '-' + day;
let timeString = hours + ':' + minutes;


// 비디오 업로드
const uploadVideo = function () {
    let lid = document.querySelector('#lid').value;
    let videopath = document.querySelector('#videopath').value;
    let role = document.querySelector('#role').value;
    let tid = document.querySelector('#tid').value;
    let sessionId = document.querySelector('#sessionId').value;

    let video = document.getElementById("vidPlayer");
    let form = document.getElementById('insertVideoForm'); // 폼 요소 가져오기
    let div_form = document.getElementById('video_upload');

    if ((role === 'teacher' && String(sessionId) === String(tid)) || role === 'admin') {
        if (videopath !== '') {
            let ele_vid = document.createElement('video');
            ele_vid.src = `/getVideo/${videopath}`;
            ele_vid.setAttribute("controls", "controls");
            video.appendChild(ele_vid);

            let ele_delbtn = document.createElement('button');
            ele_delbtn.textContent = '영상 삭제';
            ele_delbtn.onclick = function () {
                deleteVideo(videopath);
            };
            div_form.appendChild(ele_delbtn);
        } else {
            let div_title = document.getElementById('video_title');
            let ele_title = document.createElement('input');
            ele_title.type = "text";
            ele_title.name = "title";
            ele_title.id = "title";
            ele_title.placeholder = "영상 제목을 입력하세요.";
            div_title.appendChild(ele_title);

            let ele_input = document.createElement("input");
            ele_input.setAttribute('type', 'file');
            ele_input.setAttribute('name', 'vidfile');
            ele_input.setAttribute('id', 'vidfile');
            div_form.appendChild(ele_input);

            let ele_span = document.createElement("span");
            ele_span.className = "span_file";
            ele_span.textContent = "선택된 파일이 없습니다.";
            div_form.appendChild(ele_span);

            let ele_label = document.createElement("label");
            ele_label.className = "label_file_btn";
            ele_label.htmlFor = "vidfile";
            ele_label.textContent = "영상 선택";
            div_form.appendChild(ele_label);

            let ele_btn = document.createElement('button');
            ele_btn.setAttribute('type', 'submit');
            ele_btn.textContent = '영상 추가';
            div_form.appendChild(ele_btn);
        }
    } else {
        let startDate = params.startdate;
        let startTime = params.tsession.substring(6, 11); // ex 00 : 00
        let endTime = params.tsession.substring(12, 17);

        if (dateString === params.startdate && startTime <= timeString && timeString <= endTime) {
            let ele_vid = document.createElement('video');
            ele_vid.src = `/getVideo/${videopath}`;
            ele_vid.setAttribute("controls", "controls");
            video.appendChild(ele_vid);
        } else {
            let ele_span = document.createElement('span');
            ele_span.textContent = "오픈 날짜 " + startDate + " " + params.tsession.substring(6);
            div_form.appendChild(ele_span);
        }
    }

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(form);

        fetch('/insertvid', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                window.location.reload();
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
}

// 비디오 삭제
const deleteVideo = function (videopath) {
    let vid = document.querySelector('#vid').value;

    const confirm_check = confirm("정말로 삭제하시겠습니까?");

    if (confirm_check) {
        const data = {
            videopath: videopath
        };

        fetch(`/deletevideo/${vid}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete video');
            }
            window.location.reload();
            return response.json();
        }).then(data => {
            console.log(data);
        }).catch(error => {
            console.log('Error: ', error);
        }).finally(() => {
            console.log("delete video finally");
        });
    }
}

// 파일 업로드
const uploadFiles = function () {

    let role = document.querySelector('#role').value;
    let tid = document.querySelector('#tid').value;
    let sessionId = document.querySelector('#sessionId').value;
    let form = document.getElementById('fileUploadForm'); // 폼 요소 가져오기
    let div_form = document.getElementById('file_upload');

    // 강사(자신이 올린 강의) / 관리자일 경우에만 파일 추가 버튼 등을 생성
    if (('teacher' === String(role) && String(sessionId) === String(tid)) || 'admin' === String(role)) {
        let ele_input = document.createElement('input');
        ele_input.setAttribute('type', 'file');
        ele_input.setAttribute('name', 'files');
        ele_input.setAttribute('id', 'files');
        ele_input.setAttribute('multiple', 'multiple');
        div_form.appendChild(ele_input);

        let ele_span = document.createElement('span');
        ele_span.className = 'span_file';
        ele_span.textContent = '선택된 파일이 없습니다.';
        div_form.appendChild(ele_span);

        let ele_label = document.createElement('label');
        ele_label.className = 'label_file_btn';
        ele_label.setAttribute('for', 'files');
        ele_label.textContent = '강의 자료 선택';
        div_form.appendChild(ele_label);

        let ele_btn = document.createElement('button');
        ele_btn.setAttribute('type', 'submit');
        ele_btn.textContent = '강의 자료 추가';
        div_form.appendChild(ele_btn);
    }

    form.addEventListener('submit', function (event) {
        // 기본 동작 방지
        event.preventDefault();
        const formData = new FormData(form);

        fetch('/insertref', {
            method: 'POST',
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
        let replyList = document.getElementById('replyList');
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
    uploadVideo();

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

    let filesInput = document.getElementById('files');
    let vidfileInput = document.getElementById('vidfile');

    if (filesInput) {
        filesInput.addEventListener('change', customFileInput);
    }

    if (vidfileInput) {
        vidfileInput.addEventListener('change', customFileInput);
    }
}
