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
        console.log(data);
        let checkspan = document.createElement('span');
        if (data == 0) { //사용 가능한 이메일
            let oktext = document.createTextNode('사용 가능한 이메일입니다.');
            checkspan.appendChild(oktext);
        } else { // 이미 존재하는 이메일
            let alreadytext = document.createTextNode('이미 존재하는 이메일입니다.');
            checkspan.appendChild(alreadytext);
        }
        document.getElementById("check").appendChild(checkspan);
    }).catch(
        error => console.log(error)
    ).finally(
        () => console.log('finally')
    )
}


// $.ajax({
//     url: '/emailCheck', // Controller에서 요청 받을 주소
//     type: 'post', // POST 방식으로 전달
//     data: { email: email },
//     success: function(cnt) { // 컨트롤러에서 넘어온 cnt값을 받는다
//         console.log("AJAX success callback executed. cnt: ", cnt); // 디버깅용 로그 추가
//
//         if (cnt == 0) { // cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디
//             ('.email_ok').css("display", "inline-block");
//             ('.email_already').css("display", "none");
//         } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
//             ('.email_already').css("display", "inline-block");
//             ('.email_ok').css("display", "none");
//             alert("이메일을 다시 입력해주세요");
//             ('#email').val('');
//         }
//     },
//     error: function(xhr, status, error) {
//         console.error("AJAX error callback executed. Error: ", error); // 디버깅용 로그 추가
//         alert("에러입니다");
//     }
// });

