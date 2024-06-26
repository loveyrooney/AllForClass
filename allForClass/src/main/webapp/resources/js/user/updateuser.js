/** 이메일 중복체크 */
let updatebtn = document.getElementById('updateuser_btn');

// 이메일 인풋이 변경 되었을 때
let emailinput = document.getElementById('email');
emailinput.addEventListener('change', function () {

    let existingSpan2 = document.getElementById('update-check-span');
    if (existingSpan2) {
        document.getElementById("update_check").removeChild(existingSpan2);
    }

    updatebtn.disabled = true;  // input이 변경되면 수정 버튼 비활성화
    updatebtn.style.background = '#F5F5F5';
    updatebtn.style.border = '2px solid #F5F5F5';
    updatebtn.style.color = 'gray';

    // 새로운 span 요소를 생성하고 ID 부여
    let updatespan = document.createElement('span');
    updatespan.id = 'update-check-span';

    let updatetext = document.createTextNode('이메일 중복체크를 해주세요.');

    updatespan.appendChild(updatetext);
    updatespan.style.color = "red";

    document.getElementById("update_check").appendChild(updatespan);

});


let emailvalue;
document.getElementById('emailCk').onclick = function () {

    let existingSpan2 = document.getElementById('update-check-span');
    if (existingSpan2) {
        document.getElementById("update_check").removeChild(existingSpan2);
    }
    emailvalue = document.getElementById('email').value;
    console.log("Email to check: ", emailvalue); // 디버깅용 로그 추가
    fetch("/emailCheck", {
        method: "POST"
        , headers: {
            'Content-Type': 'application/json',  // 요청을 JSON으로 보내기 위해 설정
        },
        body: JSON.stringify({email: emailvalue})
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

        let checktext;

        if (emailvalue == "" || emailvalue == null || (emailvalue != userEmail && data != 0)) {
            if(emailvalue == "" || emailvalue == null){  //값이 없을 때
                checktext = document.createTextNode('이메일 주소를 입력해주세요.');
            } else if (emailvalue != userEmail && data != 0) { //중복값으로 사용 불가능한 이메일

                checktext = document.createTextNode('이미 존재하는 이메일입니다.');
            }
                checkspan.appendChild(checktext);
                checkspan.style.color = "red";

                updatebtn.disabled = true; // 등록 버튼 비활성화
                updatebtn.style.background = '#F5F5F5';
                updatebtn.style.border = '2px solid #F5F5F5';
                updatebtn.style.color = 'gray';

                // 메일 입력창 활성화
                emailinput.readOnly = false;
                emailinput.style.removeProperty('background');

        } else {

            if (emailvalue == userEmail) { //입력값과 기존 이메일이 같을 때
                checktext = document.createTextNode('현재 사용중인 이메일 입니다.');
            } else {
                checktext = document.createTextNode('사용 가능한 이메일입니다.'); //사용가능
            }

            emailinput.readOnly = true; // 다른 텍스트로 입력 못하도록 입력창 비활성화
            emailinput.style.background = '#F5F5F5';
            checkspan.appendChild(checktext);
            checkspan.style.color = "green";

            updatebtn.disabled = false; //수정하기 버튼 활성화

            // 초기화: else에서 설정된 스타일 제거
            updatebtn.style.removeProperty('background');
            updatebtn.style.removeProperty('border');
            updatebtn.style.removeProperty('color');
        }

        document.getElementById("check").appendChild(checkspan);

    }).catch(error => {
        console.log(error);
    }).finally(
        () => console.log('finally')
    )
}


// 삭제 버튼 클릭 이벤트 리스너 추가
const deleteUserBtn = document.getElementById('deleteuser_btn');

deleteUserBtn.addEventListener('click', function () {

    let uid = document.getElementById('uid').value;
    const userConfirmed = confirm('회원 탈퇴하시겠습니까?');

    // 사용자가 확인 버튼을 눌렀을 때
    if (userConfirmed) {

        fetch('/deleteuser/' + uid, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data === 1) {
                    alert('회원 탈퇴가 완료되었습니다.');
                    if (role == 'admin') {
                        location.href = '/admin';
                    } else {
                        location.href = '/logout';
                    }

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


// 취소 버튼
let updateresetbtn = document.getElementById('updatereset_btn');

updateresetbtn.addEventListener('click', function () {
    // 메일 입력창 활성화
    emailinput.readOnly = false;
    emailinput.style.removeProperty('background');

    //수정하기 버튼 활성화
    updatebtn.disabled = false;
    // 설정된 스타일 제거
    updatebtn.style.removeProperty('background');
    updatebtn.style.removeProperty('border');
    updatebtn.style.removeProperty('color');

    // 기존 span 요소를 찾고, 있으면 제거
    let existingSpan = document.getElementById('email-check-span');
    if (existingSpan) {
        document.getElementById("check").removeChild(existingSpan);
    }
});