let params;
let init = function (inits) {
    params = inits;
}

window.onload = function () {
    if (document.querySelector("#pay")) {
        document.querySelector("#pay").onclick = async function requestPayment() {
            let prompt = window.prompt("연락처를 적어주세요 000-0000-0000");
            if (prompt != null && prompt !== '') {
                console.log(prompt);
                console.log(params);
                const response = await PortOne.requestPayment({
                    storeId: params.storeId, // 고객사 storeId로 변경해주세요.
                    channelKey: params.channelKey, // 콘솔 결제 연동 화면에서 채널 연동 시 생성된 채널 키를 입력해주세요.
                    paymentId: `${crypto.randomUUID()}`,
                    orderName: document.getElementById("lname").textContent,
                    totalAmount: Number.parseInt(document.getElementById("price").textContent),
                    currency: "CURRENCY_KRW",
                    payMethod: "CARD",
                    customer: {
                        fullName: params.fullName,
                        phoneNumber: prompt,
                        email: params.email,
                    },
                });
                if (response.code != null) {
                    console.log(response.message);
                } else {
                    console.log(response);
                    const notified = await fetch(`/payment/complete`, {
                        method: "POST",
                        headers: {"Content-Type": "application/json"},
                        body: JSON.stringify({
                            paymentId: response.paymentId,
                            uid: params.p_uid,
                            lid: params.p_lid,
                        })
                    }).then(res => {
                        if (res.status !== 200)
                            throw new Error();
                        else
                            return res.json();
                    }).then(d => {
                        console.log(d);
                        alert(d.msg);
                    }).catch(error => {
                        console.error(error);
                    });
                }
            }
        }
    }

    if (document.querySelector("#refund")) {
        document.querySelector("#refund").onclick = async function requestRefund() {
            if (window.confirm("정말 취소하시겠습니까?")) {
                try {
                    const response = await fetch(`/payment/cancel/${params.p_pid}`, {
                        method: 'get'
                        , headers: {
                            'Content-Type': 'application/json'
                        }
                    });
                    const data = await response.json();
                    console.log(data);
                    location.href='/detail_lec/'+params.p_lid;
                } catch (error) {
                    console.error(error);
                }
            }
        }
    }

}


