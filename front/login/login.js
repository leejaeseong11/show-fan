import { handleXhttps, backURL } from "../util/util.js";


$(() => {

  handleXhttps("GET", "../header/index.html", $("header"));
  handleXhttps("GET", "../navigation/index.html", $("nav"));
  handleXhttps("GET", "../footer/index.html", $("footer"));

  // 회원가입 클릭 시
  $("body").on("click", '#signup', function (e) {
    e.preventDefault();
    location.href = "../signup/index.html";
    // handleXhttps("GET", "../signup/index.html", $("main"));
  });

  // 로그인 클릭 시
  $("body").on("click", '#login', function (e) {
    e.preventDefault();
    location.href = "../login/index.html";
    // handleXhttps("GET", "../login/index.html", $("main"));
  });

  // if 마이페이지 link clicked
  $("body").on("click", '#mypage', function (e) {
    e.preventDefault();
    location.href = "mypage";
  });

  // 내 정보 수정 클릭 시
  $("body").on("click", '#modify', function (e) {
    e.preventDefault();
    location.href = "../modify/index.html";
    // handleXhttps("GET", "../modify/index.html", $("main"));
  });

  // 검색 버튼 클릭 시
  $("body").on("click", "#header-search-button", function (e) {
    // e.preventDefault();
    const value = $("#header-search-input").val();
    location.href = `../search/index.html?q=${value}`;
  });

  // 검색 입력 후 엔터
  $("body").on("keydown", "#header-search-input", function (e) {
    if (e.key == "Enter" || e.keyCode == "13") {
      const value = $("#header-search-input").val();
      location.href = `../search/index.html?q=${value}`;
    }
  });

  // 캘린더 조회 시
  $("body").on("click", "#navigation-calendar-button", function (e) {
    location.href = "../calendar/index.html";
  });

  // 장르 전체 선택
  $("#genreAll").click((e) => {
    if ($("#genreAll").is(":checked")) {
      $("input[name=genre]").prop("checked", true);
    } else {
      $("input[name=genre]").prop("checked", false);
    }
  });


  const savedId = localStorage.getItem('savedId');

  //로컬 스토리지에 저장한 아이디 값이 있으면
  if (savedId != null) {
    //아이디입력란에 자동 입력
    $('input[name=email]').val(savedId);
    //아이디 저장 체크박스 체크
    $('input:checkbox[id=saveemail]').attr('checked', true);
  }

  //----form객체에서 submit이벤트가 발생했을 때
  $('form').submit((e) => {
    e.preventDefault();
    //아이디 저장 체크시
    if ($('#saveemail').prop('checked')) {
      localStorage.setItem('savedId', $('input[name=email]').val());
    } else {
      localStorage.removeItem('savedId');
    }

    const idValue = $('input[name=email]').val(); //입력한 아이디 저장
    const pwdValue = $('input[name=pwd]').val(); //입력한 비밀번호 저장
    const autoValue = $('#autologin').is(':checked'); //자동로그인 체크 여부 저장

    const data = `email=${idValue}&pwd=${pwdValue}&autoLogin=${autoValue}`;

    $.ajax({
      xhrFields: {
        withCredentials: true,
      },
      url: backURL + '/login',
      method: 'POST',
      data: data,
      success: (responseJSONObj) => {
        if (responseJSONObj.status == 0) {
          //로그인실패인 경우
          alert("로그인 실패!")
          // alert("로그인 실패: " + responseJSONObj.status)
          alert(responseJSONObj.msg);
        } else if (responseJSONObj.status == 1) {
          //로그인성공인 경우
          alert("로그인 성공! 메인 페이지로 이동합니다")
          // alert("로그인 성공: " + responseJSONObj.status)
          location.href = '../index.html';
        }
        console.log(responseJSONObj); // 응답을 콘솔에 출력합니다.
      },
      error: (jqXHR, textStatus) => {
        //( jqXHR jqXHR, String textStatus, String errorThrown )
        // alert(textStatus) //error
        // console.log(jqXHR)
        alert(jqXHR.readyState + ':' + jqXHR.status + ':' + jqXHR.statusText);
      }, //로그인이 fail났을때(네트워크,url의 문제등) 실패는 아님
    });
    e.preventDefault();
  });
  //----form객체에서 submit이벤트가 발생했을 때 할 일 END----
});