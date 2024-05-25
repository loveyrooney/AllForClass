let tid = '';
// jsp에서 넘겨준 값 받기
const init = function (data) {
    tid = data;
}

document.addEventListener("DOMContentLoaded", function () {

    let startDate = document.getElementById('startdate');
    let timeSessions = document.querySelectorAll('input[name="timesession"]');
    let lecTimeCheckLabel = document.getElementById('lec_time_check');
    let confirmbtn = document.getElementById('confirm_btn');

/*    document.getElementById('deletelec_btn').onclick = function (){
        location.href='../deletelec/'+document.getElementById('lid').value;
    }*/


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
                    lecTimeCheckLabel.textContent = `등록 가능한 시간입니다.`;
                    lecTimeCheckLabel.style.color='green';
                    confirmbtn.disabled=false;

                    // 초기화: else에서 설정된 스타일 제거
                    confirmbtn.style.removeProperty('background');
                    confirmbtn.style.removeProperty('border');
                    confirmbtn.style.removeProperty('color');

                } else {
                    lecTimeCheckLabel.textContent = `해당 시간에 신청한 강의가 있습니다.`;
                    lecTimeCheckLabel.style.color='red';
                    confirmbtn.disabled=true; // 등록 버튼 비활성화
                    confirmbtn.style.background='#F5F5F5';
                    confirmbtn.style.border='2px solid #F5F5F5';
                    confirmbtn.style.color='gray';
                }
            })
            .catch(error => {
                console.error('Error:', error);
            }).finally(() => {
            console.log("sendFormData finally");
        });
    }

});