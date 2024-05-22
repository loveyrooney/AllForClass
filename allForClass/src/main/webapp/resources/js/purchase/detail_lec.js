let params;
let init = function(inits) {
    params = inits;
}

window.onload=function () {
    document.querySelector("#pay").onclick= async function requestPayment() {
        let prompt = window.prompt("연락처를 적어주세요 000-0000-0000");
        if( prompt!=null && prompt !==''){
            console.log(prompt);
            console.log(params);
            const response = await PortOne.requestPayment({
                storeId: "store-a684e3ce-b530-4ebf-ac94-380560c36dcf", // 고객사 storeId로 변경해주세요.
                channelKey: "channel-key-57f255e4-5b31-4460-a99f-2a0f44dddb8a", // 콘솔 결제 연동 화면에서 채널 연동 시 생성된 채널 키를 입력해주세요.
                paymentId: `${crypto.randomUUID()}`,
                orderName: document.getElementById("lname").value,
                totalAmount: Number.parseInt(document.getElementById("price").value),
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
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        paymentId: response.paymentId,
                        uid: params.uid,
                        lid: params.lid,
                    })
                }).then(res=>{
                    if(res.status !== 200)
                        throw new Error();
                    else
                        return res.json();
                }).then(data=>{
                    console.log(data);
                    alert(data.msg);
                }).catch(error=>{
                    console.error(error);
                });
            }
        }
    }

    document.querySelector("#refund").onclick= async function requestRefund() {
        if(window.confirm("정말 취소하시겠습니까?")){
            try{
                const response = await fetch(`/payment/cancel/${params.uid}`, {
                    method : 'get'
                    ,headers : {
                        'Content-Type': 'application/json'
                    }
                });
                const data = await response.json();
                console.log(data);
            } catch (error) {
                console.error(error);
            }
        }
    }
}


