/*console.log("User Email:", userEmail);*/


/** 이메일 중복체크 */
//이메일 중복체크가 되기 전엔 가입하기 버튼 비활성화
document.querySelectorAll('.updateuser_btn')[0].disabled = true;
let emailvalue;
document.getElementById('emailCk').onclick = function () {
    emailvalue = document.getElementById('email').value;
    console.log("Email to check: ", emailvalue); // 디버깅용 로그 추가
    fetch("/emailCheck", {
        method   : "POST"
        , headers: {
            'Content-Type': 'application/json',  // 요청을 JSON으로 보내기 위해 설정
        },
        body     : JSON.stringify({email: emailvalue})
    }).then(response => {
        if (!response.ok)
            throw new Error('noooo');
        return response.json();
    }).then((data) => {
        // 중복체크 버튼 초기화
        // 기존 span 요소를 찾고, 있으면 제거
        let existingSpan = document.getElementById('email-check-span');
        if (existingSpan) {
            document.getElementById("check").removeChild(existingSpan);
        }
        // 새로운 span 요소를 생성하고 ID 부여
        let checkspan = document.createElement('span');
        checkspan.id = 'email-check-span';

        if (data == 0) { //사용 가능한 이메일
            let oktext = document.createTextNode('사용 가능한 이메일입니다.');
            checkspan.appendChild(oktext);
            checkspan.style.color = "green";
            document.querySelectorAll('.updateuser_btn')[0].disabled = false; //수정하기 버튼 활성화

        } else { // 이미 존재하는 이메일
            if (emailvalue == userEmail) { //입력값과 기존 이메일이 같을 때
                let oktext = document.createTextNode('현재 사용중인 이메일 입니다.');
                checkspan.appendChild(oktext);
                checkspan.style.color = "green";
                document.querySelectorAll('.updateuser_btn')[0].disabled = false; //수정하기 버튼 활성화
            } else {
                let alreadytext = document.createTextNode('이미 존재하는 이메일입니다.');
                checkspan.appendChild(alreadytext);
                checkspan.style.color = "red";
            }
        }
        document.getElementById("check").appendChild(checkspan);
    }).catch(error => {
        console.log(error);
    }).finally(
        () => console.log('finally')
    )
}


const deleteUserBtn = document.getElementById('deleteuser_btn');

// 버튼 클릭 이벤트 리스너 추가
deleteUserBtn.addEventListener('click', function() {

    let uid = document.getElementById('uid').value;
    const userConfirmed = confirm('회원 탈퇴하시겠습니까?');

    // 사용자가 확인 버튼을 눌렀을 때
    if (userConfirmed) {

        fetch('/deleteuser/'+uid, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data ===1) {
                    alert('회원 탈퇴가 완료되었습니다.');
                    location.href = '/logout';
                } else {
                    alert('회원 탈퇴에 실패했습니다. 다시 시도해 주세요.');
                }
            })
            .catch(error => {
                console.error('회원 탈퇴 중 오류 발생:', error);
                alert('회원 탈퇴 중 오류가 발생했습니다.');
            });
    } else {
        // 사용자가 취소 버튼을 눌렀을 때
        console.log('회원 탈퇴를 취소했습니다.');
    }
});
