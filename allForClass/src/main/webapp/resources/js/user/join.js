

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
        //let checkspan = document.createElement('span');

        // 기존 span 요소를 찾고, 있으면 제거합니다.
        let existingSpan = document.getElementById('email-check-span');
        if (existingSpan) {
            document.getElementById("check").removeChild(existingSpan);
        }

        // 새로운 span 요소를 생성하고 ID를 부여합니다.
        let checkspan = document.createElement('span');
        checkspan.id = 'email-check-span';


        if (data == 0) { //사용 가능한 이메일
            let oktext = document.createTextNode('사용 가능한 이메일입니다.');
            checkspan.appendChild(oktext);
            checkspan.style.color= "green";

        } else { // 이미 존재하는 이메일
            let alreadytext = document.createTextNode('이미 존재하는 이메일입니다.');
            checkspan.appendChild(alreadytext);
            checkspan.style.color= "red";
        }
        document.getElementById("check").appendChild(checkspan);
    }).catch(
        error => console.log(error)
    ).finally(
        () => console.log('finally')
    )
}

