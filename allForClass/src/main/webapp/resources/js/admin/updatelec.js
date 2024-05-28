let params;
// jsp에서 넘겨준 값 받기
const init = function (data) {
    params = data;
}

document.addEventListener("DOMContentLoaded", function () {

    let startDate = document.getElementById('startdate');
    let timeSessions = document.querySelectorAll('input[name="timesession"]');
    let lecTimeCheckLabel = document.getElementById('lec_time_check');
    let updatelecbtn = document.getElementById('updatelec_btn');


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


    // 신청 가능 시간인지 체크
    function sendFormData(selectedDate, selectedSession) {
        fetch('/checklectime/' + params.tid, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
                , 'Accept': 'application/json'
            },
            body: JSON.stringify({
                startdate: selectedDate
                , timesession: selectedSession
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load checkLecTime list');
                }
                return response.json();
            })
            .then(data => {
                if (data == 0 || (selectedDate == params.startdate && selectedSession == params.timesession)) {
                    lecTimeCheckLabel.textContent = `등록 가능한 시간입니다.`;
                    lecTimeCheckLabel.style.color = 'green';
                    updatelecbtn.disabled = false;

                    // 초기화: else에서 설정된 스타일 제거
                    updatelecbtn.style.removeProperty('background');
                    updatelecbtn.style.removeProperty('border');
                    updatelecbtn.style.removeProperty('color');

                } else {
                    lecTimeCheckLabel.textContent = `해당 시간에 신청한 강의가 있습니다.`;
                    lecTimeCheckLabel.style.color = 'red';

                    updatelecbtn.disabled = true; // 수정 버튼 비활성화
                    updatelecbtn.style.background = '#F5F5F5';
                    updatelecbtn.style.border = '2px solid #F5F5F5';
                    updatelecbtn.style.color = 'gray';

                }
            })
            .catch(error => {
                console.error('Error:', error);
            }).finally(() => {
            console.log("sendFormData finally");
        });
    }


    // 승인 버튼 클릭 이벤트
    let confirmbtn = document.getElementById('confirm_btn');

    confirmbtn.addEventListener('click', function () {

        const userConfirmed = confirm('강의를 승인하시겠습니까?');

        // 사용자가 확인 버튼을 눌렀을 때
        if (userConfirmed) {

            console.log('강의 승인 확인');
            // 쿼리 문자열 생성
            const queryString = new URLSearchParams({ tid: params.tid }).toString();

            fetch(`/confirm/${params.lid}?${queryString}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
                .then(response => response.json())
                .then(data => {
                    if (data === 1) {
                        alert('강의 승인이 완료되었습니다.');
                        location.href = '/admin';
                    } else {
                        alert('강의 승인에 실패했습니다. 다시 시도해 주세요.');
                    }
                })
                .catch(error => {
                    console.error('강의 승인 중 오류 발생:', error);
                });
        } else {
            // 사용자가 취소 버튼을 눌렀을 때
            console.log('강의 승인을 취소했습니다.');
        }
    });


// 삭제 버튼 클릭 이벤트
    let deletelecbtn = document.getElementById('deletelec_btn');

    deletelecbtn.addEventListener('click', function () {

        const userConfirmed = confirm('강의를 삭제하시겠습니까?');

        // 사용자가 확인 버튼을 눌렀을 때
        if (userConfirmed) {

            console.log('강의 삭제 확인');

            fetch('/deletelec/' + params.lid, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
                .then(response => response.json())
                .then(data => {
                    if (data === 1) {
                        alert('강의 삭제가 완료되었습니다.');
                        location.href = '/admin';
                    } else {
                        alert('강의 삭제에 실패했습니다. 다시 시도해 주세요.');
                    }
                })
                .catch(error => {
                    console.error('강의 삭제 중 오류 발생:', error);
                });
        } else {
            // 사용자가 취소 버튼을 눌렀을 때
            console.log('강의 삭제를 취소했습니다.');
        }
    });


});