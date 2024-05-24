let uid = '';
// jsp에서 넘겨준 값 받기
const init = function (data) {
    uid = data;
}


/*// 페이징 설정
let currPage = 1;
let pageSize = 10;
let blockSize= 5;

const page_container = document.getElementById('page_container');
const prevButton = document.getElementById('prev_button');
const nextButton = document.getElementById('next_button');*/



// 수강생 캘린더
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
            , eventClick: function (info) {
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



/**예정된 강의 목록*/
const confirmlec = function () {
    fetch('/confirmedmylec/' + uid, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (!response.ok) {
            throw new Error('Failed to load confirmmylec list');
        }
        return response.json();
    }).then((data) => {

        addLectureList(data);

    }).catch(error => {
        console.log('Error: ', error);
    }).finally(() => {
        console.log("confirmmylec list finally");
    });
}


/**지난강의 목록*/
const pastlec = function () {
    fetch('/pastmylec/' + uid, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (!response.ok) {
            throw new Error('Failed to load pastmylec list');
        }
        return response.json();
    }).then((data) => {

        addLectureList(data);

    }).catch(error => {
        console.log('Error: ', error);
    }).finally(() => {
        console.log("pastmylec list finally");
    });
}


/**승인 대기 강의*/
const waitlec = function () {
    fetch('/waitmylec/' + uid, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (!response.ok) {
            throw new Error('Failed to load waitmylec list');
        }
        return response.json();
    }).then((data) => {

        addLectureList(data);

    }).catch(error => {
        console.log('Error: ', error);
    }).finally(() => {
        console.log("waitmylec list finally");
    });

}

/**json 데이터를 jsp 리스트에 추가하는 메서드*/
function addLectureList(json){

    /*// 리스트를 해당 페이지 블럭 영역만큼 자르기
    let startRow = (currPage - 1) * pageSize;
    let endRow = startRow+pageSize;
    const Pagelist = json.slice(startRow, endRow);*/

    // 자식 노드가 있는지 확인
    if (myleclist.hasChildNodes()) {
        // 모든 자식 노드를 삭제한다.
        myleclist.replaceChildren();
    }

    json.forEach(item => {

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

        // 리스트 추가
        myleclist.appendChild(ele_tr);
    });

}

/*
/!**지난강의 페이징처리*!/
const makepage = function (data) {
    let totalCount = data.length;
    let totalPage = Math.ceil(totalCount / pageSize);
    let startBlock = ((currPage - 1) / blockSize) * blockSize + 1;

    let endBlock = startBlock + blockSize - 1;
    if (endBlock > totalPage){
        endBlock = totalPage;
    }

    let next = startBlock + 1;
    let prev = endBlock - 1;

    // 페이지 숫자
    for(let i = startBlock; i < endBlock; i++){
        console.log("i------------"+i);
        let pageBtn = document.createElement('button');
        pageBtn.textContent = i;
        page_container.appendChild(pageBtn);
    }
}*/



window.onload = function () {
    // 강사 강의 목록 영역
    let myleclist = document.getElementById('myleclist');

    // 예정된 강의 목록이 초기 화면
    confirmlec();

    //강사 마이페이지 지난 강의 리스트
    document.getElementById("pastlec").onclick = function () {
        pastlec();
    }

    //예정된 강의 리스트
    document.getElementById("confirmlec").onclick = function () {
        confirmlec();
    }

    //승인 대기 강의 리스트
    document.getElementById("waitlec").onclick = function () {
        waitlec();
    }

}
