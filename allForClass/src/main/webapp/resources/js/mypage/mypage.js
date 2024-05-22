var calendar;
// 클릭 시 캘린더에 이벤트 추가
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth'
        , events : [
            {title:"09:00-12:00\n[국어] 고등1국어 홍길동ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇdddddddddㅇㅇ"
                , start : "2024-05-21"
                , color : 'pink'}
        ]
    });
    calendar.render();
});