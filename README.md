<div>
<h1><img src="https://github.com/loveyrooney/AllForClass/assets/113234712/10895696-ed6e-4b3b-8b52-90f13fa170e1" width=100> AllForClass</h1>
<h3>온라인 유료 시범강의 수강 서비스 (2024.05.14 ~2024.05-30) </h3>
</div>


  
<h2> 프로젝트 팀원 </h2>

|김보경 (25%)|김혜연 (25%)|송미라 (25%)|양세현 (25%)| 
|:---:|:---:|:---:|:---:|
|<img src="https://avatars.githubusercontent.com/ppodaejang" width="100" > <p>마이페이지,관리자페이지</p> |<img src="https://avatars.githubusercontent.com/loveyrooney" width="100" > <p>수강신청/결제, 이메일안내</p>|<img src="https://avatars.githubusercontent.com/mummyyyyy" width="100"> <p>회원가입/로그인, 메인페이지</p>| <img src="https://avatars.githubusercontent.com/ysh71034" width="100"> <p>강의실, 관리자페이지</p>|
|<a href="https://github.com/ppodaejang"><img src="https://img.shields.io/badge/GitHub-181717?style=plastic&logo=GitHub&logoColor=white"/></a>|<a href="https://github.com/loveyrooney"><img src="https://img.shields.io/badge/GitHub-181717?style=plastic&logo=GitHub&logoColor=white"/></a>|<a href="https://github.com/mummyyyyy"><img src="https://img.shields.io/badge/GitHub-181717?style=plastic&logo=GitHub&logoColor=white"/></a>|<a href="https://github.com/ysh71034"><img src="https://img.shields.io/badge/GitHub-181717?style=plastic&logo=GitHub&logoColor=white"/></a>|


<h2> 기술 스택 </h2>
<div>
  <h4> Front End </h4>
<img src="https://img.shields.io/badge/-jsp-orange"/>

<img src="https://img.shields.io/badge/-html-orange"/>

<img src="https://img.shields.io/badge/-css-blue"/>

<img src="https://img.shields.io/badge/-javascript-yellow"/> 
</div>
<div>
   <h4> Back End </h4>
<img src="https://img.shields.io/badge/Spring-339933?style=plastic&logo=spring&logoColor=white"/>

<img src="https://img.shields.io/badge/-mybatis-339933"/>

<img src="https://img.shields.io/badge/-MariaDB-darkblue"/>
 
<img src="https://img.shields.io/badge/-portOne API-blueviolet"/>

<img src="https://img.shields.io/badge/-FullCalendar-yellow"/>

<img src="https://img.shields.io/badge/-JavaMailSender-339933"/>

<img src="https://img.shields.io/badge/-Bcrypt-orange"/>
<h4> Version & Build </h4>
open JDK 11</br>
spring 5.3.15</br>
apache tomcat 9.89</br>
<p>AWS EC2(ubuntu) 및 RDS 사용, war 파일 톰캣 서버에 띄워 배포</p>
</div>

<h2>프로젝트 주요이슈</h2>
<h4>결제 및 수강신청 관련</h4>
<h6>⚠️문제 상황</h6>
<ol>
  <li>결제와 수강신청 플로우가 길다보니 경계가 모호해지는 문제</li>
  <li>실패하는 경우의 문제 </li>
</ol>

<h6>📌해결 과정</h6>
<ol>
  <li>
    <ul>
      <li>⚙️플로우 구조화  :  수강신청 가능여부 - 결제 - 결제확인 - 수강신청 - 응답</li>
      <li>클라이언트에서는 결제만 직접 api 접근하고, 결제확인 및 결제취소의 경우는 서버에서 api 요청으로 접근</li>
      <li>결제 관련 로직은 controller 메서드에서, 수강신청 관련 로직은 service 단에서 처리</li>
    </ul>
  </li>
  <br>
  <li>
    <ul>
      <li>결제 및 수강신청의 흐름에서 발생하는 예외들을 각각 커스텀 지정하여 알리도록 함.</li>
      <li>ex) 결제는 성공하였으나 결제 내역 확인 중 장애 발생한 경우의 메시지</li>
      <li>👉[결제확인불가] 수강신청에 실패하였습니다. 관리자에게 문의 바랍니다. </li>
    </ul>
  </li>
</ol>

<h4>강의실 관련</h4> 
<h6>⚠️문제 상황</h6>
<ol>
  <li>역할에 따라 접근 가능한 기능 분배의 문제</li>
</ol>
<h6>📌해결 과정</h6>
<ol>
  <li>
    <ul>
      <li>최초 시도 시에는 뷰 렌더링 할 때 전부 요소를 보낸 뒤 css hidden 처리를 통해 구현</li>
      <li>⚠️브라우저 개발자도구에서 벗길 수 있는 문제 발생</li>
    </ul>
  </li>
  <br>
  <li>
    <ul>
      <li>뷰 렌더시에는 강의 정보 관련 요소와 현재 접속자의 role 만 띄운다</li>
      <li>role에 따라 해당 기능과 관련된 요소를 fetch 요청을 통해 동적으로 생성</li>
    </ul>
  </li>
</ol>

<h2>프로젝트 주요기능</h2>

|기능| 설명|
|:---|:---|
|메인페이지|1. 개강일 임박 순으로 수강 오픈된 강의 조회<br>2. 상단 바에서 검색 및 로그인/회원가입/마이페이지 접근|
|수강신청/결제|1. 강의 상세정보 조회<br>2.현재 접속자의 role과 수강신청 여부에 따라 수강신청/취소 및 강의실 입장 버튼 표시 <br>3. 수강하기 버튼 누르면 현재 접속자의 스케줄 및 현재 강의의 정원초과 여부 확인 후 결제 api 연결<br>4. 결제 성공 시 알림 및 수강신청 성공 시 알림|
|회원가입/로그인|1. 이메일 중복 확인<br>2. 비밀번호 암호화 적용<br>3. 로그인 정보 확인 로직은 인터셉터 단에서 체크|
|강의실|1. 수강신청한 수강생, 해당 강의 강사, 관리자만 입장가능 <br>2. 수강생의 경우 댓글 자유롭게 이용, 강의 영상과 자료는 개강일 해당 타임세션에 활성화 <br>3. 강사 및 관리자의 경우 강의승인 후 영상, 자료, 댓글 자유롭게 이용 가능|
|마이페이지|[공통] <br> 1. 회원정보 수정 및 탈퇴 접근 <br>[수강생] <br> 2. 캘린더에서 수강신청한 강의 내역 조회 및 강의실 접근<br>[강사] <br> 3. 지난 강의, 예정된 강의, 승인 대기중 강의 리스트 조회 및 강의 등록 신청|
|관리자|1. 승인 대기중 강의 / 전체 강의 / 전체 회원 리스트 조회<br> 2. 승인 대기중 강의에서 강의 내용 수정 및 강의 승인 가능 <br>3. 회원 리스트에서 회원 수정 및 삭제 가능|

<h2>ErDiagram</h2>
<img style="width: 700px" src="https://github.com/loveyrooney/AllForClass/assets/113234712/898ad1ff-e473-4519-8a8f-6ff136dfc13a">

<h2>UseCase Diagram</h2>
<img style="width: 700px" src="https://github.com/loveyrooney/AllForClass/assets/113234712/fe443340-e1bd-4db9-8953-852ee983c484">
