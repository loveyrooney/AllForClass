let uid = '';
const init = function (data) {  // jsp에서 넘겨준 값 받기
    uid = data;
}

document.addEventListener('DOMContentLoaded', function () {

    fetch("/cal_list/" + uid, {
        method: 'GET'
        , headers: {
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (!response.ok) {
            throw new Error('not calendar load');
        }
        return response.json();
    }).then((data) => {

        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth'
            , events: data
            , eventClick: function(info) {
                // 이벤트 클릭 시 수강신청 페이지로 이동
                if (info.event.url) {
                    location.href = info.event.url;
                }
            }
        });

        calendar.render();

    }).catch(error => console.log(error))
        .finally(() => {
            console.log('calendar finally')
        });

});

window.onload = function () {

    //강사 마이페이지 지난 강의 리스트
    document.getElementById("pastlec").onclick=function (){

        fetch('/pastmylec/' + uid, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        }).then((response) => {
            if (!response.ok) {
                throw new Error('Failed to load mylec list');
            }
            return response.json();
        }).then((data) => {

            data.forEach(item => {

                let ele_tr = document.createElement('tr');
                let ele_td1 = document.createElement('td');
                let ele_td2 = document.createElement('td');
                let ele_td3 = document.createElement('td');
                let ele_td4 = document.createElement('td');
                let ele_td5 = document.createElement('td');

                let ele_txt1 = document.createTextNode(item.subject);
                let ele_txt2 = document.createTextNode(item.lname);
                let ele_txt3 = document.createTextNode(item.price);
                let ele_txt4 = document.createTextNode(item.startdate);
                let ele_txt5 = document.createTextNode(item.timesession);

                ele_td1.appendChild(ele_txt1);
                ele_td2.appendChild(ele_txt2);
                ele_td3.appendChild(ele_txt3);
                ele_td4.appendChild(ele_txt4);
                ele_td5.appendChild(ele_txt5);

                ele_tr.appendChild(ele_td1);
                ele_tr.appendChild(ele_td2);
                ele_tr.appendChild(ele_td3);
                ele_tr.appendChild(ele_td4);
                ele_tr.appendChild(ele_td5);

                document.getElementById('myleclist').appendChild(ele_tr);
            });

        }).catch(error => {
            console.log('Error: ', error);
        }).finally(() => {
            console.log("mylec list finally");
        });
    }
}
