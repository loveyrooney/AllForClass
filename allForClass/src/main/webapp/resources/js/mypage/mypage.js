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
            throw new Error('not load');
        }
        return response.json();
    }).then((data) => {
        console.log(data);  // 콘솔에 출력

        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            events: data
        });

        calendar.render();

    }).catch(error => console.log(error))
        .finally(() => {
            console.log('finally')
        });


});