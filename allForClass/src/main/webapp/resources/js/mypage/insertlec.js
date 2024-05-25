let tid = '';
// jsp에서 넘겨준 값 받기
const init = function (data) {
    tid = data;
}

window.onload = function () {
    setPreview();
}


/**이미지 파일 미리보기*/
const setPreview = function (event) {
    let reader = new FileReader();
    reader.onload = function (event) {
        let img_container = document.getElementById('image_container');
        let green_image = document.getElementById('green_image');
        img_container.removeChild(green_image);

        let image = document.createElement("img");
        image.src = event.target.result;
        image.id = 'green_image';
        image.className='img-thumbnail';
        img_container.appendChild(image);
    }
    reader.readAsDataURL(event.target.files[0]);
}


document.addEventListener("DOMContentLoaded", function () {

    let startDate = document.getElementById('startdate');
    let timeSessions = document.querySelectorAll('input[name="timesession"]');
    let lecTimeCheckLabel = document.getElementById('lec_time_check');
    let insertlecbtn = document.getElementById('insertlec_btn');


    // 개강일 필드 이벤트 리스너 설정
    startDate.addEventListener('change', checkAndExecute);

    // 강의세션 라디오 버튼에 이벤트 리스너 설정
    timeSessions.forEach(function (timeSession) {
        timeSession.addEventListener('change', checkAndExecute);
    });

    // 두 입력 필드가 유효한 값을 가지고 있는지 확인, 조건을 충족하면 함수 실행
    function checkAndExecute(event) {

        // '개강일'과 '강의세션' 값이 있는지 확인
        let selectedDate = startDate.value;
        let selectedSession = document.querySelector('input[name="timesession"]:checked') ? document.querySelector('input[name="timesession"]:checked').value : null;

        // 두 값 모두 존재하는 경우 함수 실행
        if (selectedDate && selectedSession) {
            // 값 전달하기
            sendFormData(selectedDate, selectedSession);

        }
    }

    function sendFormData(selectedDate, selectedSession) {
        fetch('/checklectime/'+tid, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
                , 'Accept': 'application/json'
            },
            body: JSON.stringify({
                startdate: selectedDate
                ,timesession: selectedSession
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load checkLecTime list');
                }
                return response.json();
            })
            .then(data => {
                if(data == 0){
                    lecTimeCheckLabel.textContent = `신청 가능한 시간입니다.`;
                    lecTimeCheckLabel.style.color='green';
                    insertlecbtn.disabled=false;

                    // 초기화: else에서 설정된 스타일 제거
                    insertlecbtn.style.removeProperty('background');
                    insertlecbtn.style.removeProperty('border');
                    insertlecbtn.style.removeProperty('color');

                } else {
                    lecTimeCheckLabel.textContent = `해당 시간에 신청한 강의가 있습니다.`;
                    lecTimeCheckLabel.style.color='red';
                    insertlecbtn.disabled=true; // 등록 버튼 비활성화
                    insertlecbtn.style.background='#F5F5F5';
                    insertlecbtn.style.border='2px solid #F5F5F5';
                    insertlecbtn.style.color='gray';
                }
            })
            .catch(error => {
                console.error('Error:', error);
            }).finally(() => {
            console.log("sendFormData finally");
        });
    }



});
