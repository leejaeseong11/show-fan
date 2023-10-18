import { handleXhttps, backURL } from "../util/util.js";

$(() => {
  handleXhttps("GET", "../header/index.html", $("header"));
  handleXhttps("GET", "../navigation/index.html", $("nav"));
  handleXhttps("GET", "../footer/index.html", $("footer"));

  // 회원가입 클릭 시
  $("body").on("click", "#signup", function (e) {
    e.preventDefault();
    location.href = "../signup/index.html";
    // handleXhttps("GET", "../signup/index.html", $("main"));
  });

  // 로그인 클릭 시
  $("body").on("click", "#login", function (e) {
    e.preventDefault();
    location.href = "../login/index.html";
    // handleXhttps("GET", "../login/index.html", $("main"));
  });

  // if 마이페이지 link clicked
  $('body').on('click', '#mypage', function (e) {
    e.preventDefault();
    location.href = '../mypage/index.html';
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


  function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
  }

  //해당 쿠키값 얻어오기
  const loginCookie = getCookie('loginCookie');
  console.log(loginCookie);

  if (loginCookie) {
    const emailElement = document.getElementById('e');
    emailElement.textContent = loginCookie;
  }


  const $pwdinput = $("#p"); //비밀번호 입력칸
  const $pwdinputchk = $("#p1"); //비밀번호 재확인칸
  let pwdchknum = 0; //비밀번호 인증의 일치여부를 저장하는 상태값(0-일치X, 1-일치)

  const $nickname = $("#n"); //닉네임 입력칸
  const $btDupchkObj = $("#dupchk"); //닉네임 중복확인 버튼
  let nickchknum = 0; //닉네임 사용가능 여부를 저장하는 상태값(0-사용불가, 1-사용가능)


  //닉네임 중복확인 버튼 눌렀을 경우
  $btDupchkObj.click(() => {
    const data = `nickname=${$nickname.val()}`;

    $.ajax({
      xhrFields: {
        withCredentials: true,
      },
      url: backURL + "/nicknamedupchk",
      method: "POST",
      data: data,
      success: (responseJSONObj) => {
        if (responseJSONObj.status == 1) {
          nickchknum = 1;
          $("#nickchk").text("사용가능한 닉네임 입니다");
        } else if (responseJSONObj.status == 0) {
          nickchknum = 0;
          $("#nickchk").text("중복된 닉네임 입니다");
        }
      },
    });
  });

  // 비밀번호 일치 여부 확인하는 함수
  function checkPasswordMatch() {
    if ($pwdinput.val() == $pwdinputchk.val()) {
      pwdchknum = 1;
      $("#pwdchk").text("비밀번호가 일치합니다");
    } else {
      pwdchknum = 0;
      $("#pwdchk").text("비밀번호가 일치하지 않습니다");
    }
  }
  $pwdinput.on("keyup", checkPasswordMatch);
  $pwdinputchk.on("keyup", checkPasswordMatch);

  
  //폼객체 전송
  $("form").submit((e) => {
    e.preventDefault();
    // 이메일 알림 수신 동의 여부 가져오기(동의-1, 비동의-0)
    const emailChkValue = $('input[name="emailchk"]:checked').val();
    
    // 선호 장르 가져오기
    const selectedGenres = [];
    $('input[name="genre"]:checked').each(function () {
      selectedGenres.push($(this).val());
    });
    // 아무것도 체크하지 않았을 경우
    if (selectedGenres.length === 0) {
      selectedGenres.push("1", "2", "3", "4", "5");
    }

    //전송할 데이터 (이메일, 비밀번호, 닉네임, 이메일 알람 수신 동의 여부, 선호 장르)
    const data = `email=${loginCookie}&pwd=${$pwdinput.val()}&nickname=${$nickname.val()}&emailr=${emailChkValue}&genre=${selectedGenres.join(
      ","
    )}`;
    if (pwdchknum == 1 && nickchknum == 1) {
      $.ajax({
        xhrFields: {
          withCredentials: true,
        },
        url: backURL + "/modify",
        method: "POST",
        data: data,
        success: (responseJSONObj) => {
          if (responseJSONObj.status == 1) {
            alert("정보 수정 성공! 메인 페이지로 이동합니다");
            location.href = "../index.html";
          } else if (responseJSONObj.status == 0) {
            alert("정보 수정 실패");
            location.href = "../index.html";
          }
        },
      });
    } else {
      e.preventDefault();
    }
  });

});
