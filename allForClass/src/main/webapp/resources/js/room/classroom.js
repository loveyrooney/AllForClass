let params;
let init = function (inits) {
    params = inits;
}

// 현재 시간 (강의 오픈 시간 계산)
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

    let video = document.getElementById("vidPlayer");
    let form = document.getElementById('insertVideoForm'); // 폼 요소 가져오기
    let div_form = document.getElementById('video_upload');

    if ((params.role === 'teacher' && String(params.sessionId) === String(params.tid)) || params.role === 'admin') {
        if (params.videopath !== '') {
            let ele_vid = document.createElement('video');
            ele_vid.src = `/getVideo/${params.videopath}`;
            ele_vid.setAttribute("controls", "controls");
            video.appendChild(ele_vid);

            let ele_delbtn = document.createElement('button');
            ele_delbtn.textContent = '영상 삭제';
            ele_delbtn.id = 'video_delbtn';
            ele_delbtn.onclick = function () {
                deleteVideo(params.videopath);
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
        let startTime = params.tsession.substring(6, 11); // ex 00:00
        let endTime = params.tsession.substring(12, 17);

        // 형식이 같으면 연산 가능
        if (dateString === params.startdate && startTime <= timeString && timeString <= endTime) {
            let ele_vid = document.createElement('video');
            ele_vid.src = `/getVideo/${params.videopath}`;
            ele_vid.setAttribute("controls", "controls");
            video.appendChild(ele_vid);
        } else {
            let ele_img = document.createElement('img');
            ele_img.src = '/resources/images/default.png';
            ele_img.alt = '기본 이미지';
            video.appendChild(ele_img);

            let ele_span = document.createElement('span');
            ele_span.id = "span_info";
            ele_span.textContent = "오픈 날짜 " + startDate + " " + params.tsession.substring(6);
            div_form.appendChild(ele_span);

        }
    }

    form.addEventListener('submit', function (event) {
        // 기본 동작 방지
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

    const confirm_check = confirm("정말로 삭제하시겠습니까?");

    if (confirm_check) {
        const data = {
            videopath: videopath
        };

        fetch(`/deletevideo/${params.vid}`, {
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

    let form = document.getElementById('fileUploadForm');
    let div_form = document.getElementById('file_upload');

    // 강사(자신이 올린 강의) / 관리자일 경우에만 파일 추가 버튼 생성
    if (('teacher' === String(params.role) && String(params.sessionId) === String(params.tid)) || 'admin' === String(params.role)) {
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

// 파일 리스트
const loadFileList = function () {
    fetch('/getFileList')
        .then(response => response.json())
        .then(files => {
            const fileListContainer = document.getElementById('fileListContainer');
            fileListContainer.innerHTML = '';

            if (files.length > 0) {
                const ele_ul = document.createElement('ul');
                ele_ul.id = 'reflist';

                files.forEach(file => {

                    // 파일이름 디코드
                    const decodedFileName = decodeURIComponent(file.fileName);
                    // uuid 제거
                    const fileName = decodedFileName.substring(37);
                    const ele_li = document.createElement('li');

                    // 권한자만 삭제 / 영상 오픈 시간과 관계없이
                    if (('teacher' === String(params.role) && String(params.sessionId) === String(params.tid)) || 'admin' === String(params.role)) {
                        const checkbox = document.createElement('input');
                        checkbox.type = 'checkbox';
                        checkbox.value = file.fileName;
                        checkbox.className = 'file-checkbox';
                        ele_li.appendChild(checkbox);

                        const ele_a = document.createElement('a');
                        ele_a.href = `/download/${file.fileName}`;
                        ele_a.textContent = fileName;
                        ele_li.appendChild(ele_a);
                    } else {
                        // 수강생은 오픈 시간에만 다운로드 가능
                        let startTime = params.tsession.substring(6, 11); // ex 00:00
                        let endTime = params.tsession.substring(12, 17);
                        if (dateString === params.startdate && startTime <= timeString && timeString <= endTime) {
                            const ele_a = document.createElement('a');
                            ele_a.href = `/download/${file.fileName}`;
                            ele_a.textContent = fileName;
                            ele_li.appendChild(ele_a);
                        } else {
                            const ele_li2 = document.createElement('li');
                            ele_li2.textContent = fileName;
                            ele_ul.appendChild(ele_li2);
                        }
                    }
                    ele_ul.appendChild(ele_li);
                });
                fileListContainer.appendChild(ele_ul);

                if (('teacher' === String(params.role) && String(params.sessionId) === String(params.tid)) || 'admin' === String(params.role)) {
                    const deleteBtn = document.createElement('button');
                    deleteBtn.id = 'deleteFilesBtn';
                    deleteBtn.type = 'button';
                    deleteBtn.textContent = '삭제';
                    const div_deleteBtn = document.getElementById('deleteBtn');
                    div_deleteBtn.appendChild(deleteBtn);

                    document.getElementById("deleteFilesBtn").onclick = function () {
                        const checkboxes = document.querySelectorAll('.file-checkbox:checked');
                        const filesToDelete = Array.from(checkboxes).map(checkbox => checkbox.value);

                        fetch(`/deleteFiles`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({files: filesToDelete})
                        })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('Network response was not ok');
                                }
                                window.location.reload();
                            })
                            .catch(error => {
                                console.error('Error:', error);
                            });

                    };

                } else {
                    const div_deleteBtn = document.getElementById('deleteBtn');
                    let ele_span = document.createElement('span');
                    ele_span.textContent = '오픈시간이 아닙니다.'
                    div_deleteBtn.appendChild(ele_span);
                }
            } else {
                fileListContainer.textContent = '자료가 없습니다.';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// 댓글 리스트
const replylistjson = function () {

    fetch('/replylist/' + params.lid, {
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

            if (String(params.sessionId) === String(item.uid) || params.role === 'admin') {
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
        // console.log(data);
        replylistjson();
    }).catch(error => {
        console.log('Error: ', error);
    }).finally(() => {
        console.log("delete reply finally");
    });

}

window.onload = function () {

    replylistjson();
    uploadFiles();
    uploadVideo();
    loadFileList();

    // 댓글 추가
    document.querySelector('#append_btn').onclick = function () {
        let content = document.querySelector('#content').value;

        if (String(content) !== '') {
            let r = {
                'content': content,
                'lid': params.lid,
                'uid': params.sessionId
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
                // console.log(data);
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
