//이메일 정규식
//let regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');
/*function emailCheck() {	// email 형식
    let email = $("#email").val();
    let emailRule = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    if (emailRule.test(email)) {
        return true;
    } else {
        return false;
    }
}*/

/* 이메일 중복체크 */
//이메일 중복체크가 되기 전엔 가입하기 버튼 비활성화
document.querySelectorAll('.join_btn')[0].disabled=true;
let emailvalue;
document.getElementById('emailCk').onclick = function () {
    emailvalue = document.getElementById('email').value;
    console.log("Email to check: ", emailvalue); // 디버깅용 로그 추가
    fetch("/emailCheck", {
        method: "POST"
        , headers: {
            'Content-Type': 'application/json',  // 요청을 JSON으로 보내기 위해 설정
        },
        body: JSON.stringify({ email: emailvalue})
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
            checkspan.style.color= "green";
            document.querySelectorAll('.join_btn')[0].disabled=false; //가입하기 버튼 활성화

        } else { // 이미 존재하는 이메일
            let alreadytext = document.createTextNode('이미 존재하는 이메일입니다.');
            checkspan.appendChild(alreadytext);
            checkspan.style.color= "red";
        }
        document.getElementById("check").appendChild(checkspan);
    }).catch(error => {
        console.log(error);
    }).finally(
        () => console.log('finally')
    )
}


