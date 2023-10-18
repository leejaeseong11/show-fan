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
  $("body").on("click", "#mypage", function (e) {
    e.preventDefault();
    location.href = "mypage";
  });

  // 내 정보 수정 클릭 시
  $("body").on("click", "#modify", function (e) {
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

  const $inputEmail = $("input[name=email]"); //이메일 입력칸
  const $btCertifiedObj = $("form.signup>button[id=certify]"); //이메일 인증하기 버튼

  const $timerElement = $("#timer"); //이메일 인증 코드 타이머
  const $EmailChkObj = $("#ecodeinput"); //이메일 인증 코드 입력칸
  const $btEmailChkObj = $("#econdechk"); //이메일 인증 확인 버튼
  let emailchknum = 0; //이메일 인증의 성공여부를 저장하는 상태값(0-실패, 1-성공)

  const $pwdinput = $("#p"); //비밀번호 입력칸
  const $pwdinputchk = $("#p1"); //비밀번호 재확인칸
  let pwdchknum = 0; //비밀번호 인증의 일치여부를 저장하는 상태값(0-일치X, 1-일치)

  const $nickname = $("#n"); //닉네임 입력칸
  const $btDupchkObj = $("#dupchk"); //닉네임 중복확인 버튼
  let nickchknum = 0; //닉네임 사용가능 여부를 저장하는 상태값(0-사용불가, 1-사용가능)

  $EmailChkObj.hide();
  $btEmailChkObj.hide();

  let timerRunning = false; // 타이머가 실행 중인지 여부를 나타내는 상태값

  //이메일 인증하기 버튼 클릭했을 때
  $btCertifiedObj.click(() => {
    if (timerRunning) {
      return; // 타이머가 이미 실행 중이면 클릭을 무시
    }

    $EmailChkObj.show();
    $btEmailChkObj.show();
    let minutes = 1;
    let seconds = 0;

    //인증번호 타이머 함수
    function updateTimer() {
      if (minutes === 0 && seconds === 0) {
        $timerElement.text("시간 초과");
        $EmailChkObj.hide();
        $btEmailChkObj.hide();
        timerRunning = false; // 타이머 종료
        return;
      }

      if (seconds === 0) {
        minutes--;
        seconds = 59;
      } else {
        seconds--;
      }

      let formattedTime = `${String(minutes).padStart(2, "0")}:${String(
        seconds
      ).padStart(2, "0")}`;
      $timerElement.text(formattedTime);

      if (timerRunning) {
        setTimeout(updateTimer, 1000); // 1초마다 업데이트
        return; // 타이머가 종료되었으면 더 이상 실행하지 않음
      } else {
        return;
      }
    }

    timerRunning = true;
    updateTimer(); //타이머실행

    //sendmail로 입력한 이메일 값 전송
    $.ajax({
      xhrFields: {
        withCredentials: true,
      },
      url: backURL + "/sendmail",
      method: "POST",
      data: $inputEmail,
      success: (responseJSONObj) => {
        //메일 보내기 성공시
        if (responseJSONObj.status == 1) {
          //이메일 인증 확인 버튼 눌렀을 경우
          $btEmailChkObj.click((e) => {
            console.log(responseJSONObj.status);
            if (responseJSONObj.msg == $EmailChkObj.val()) {
              $("#emailchk").text("이메일 인증이 성공하였습니다.");
              emailchknum = 1;
              timerRunning = false; // 타이머 종료
            } else {
              $(".emailchk").text("이메일 인증이 실패하였습니다.");
              emailchknum = 0;
            }
          });
        }
        //메일 보내기 실패시
        else if (responseJSONObj.status == 0) {
          alert("메일 보내기 실패");
        } else {
          alert("알 수 없는 상태: " + responseJSONObj.status);
        }
        //로그아웃 실패시
      },
    });
  });

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

  // 이메일 알림 수신 동의 여부 가져오기(동의-1, 비동의-0)
  const emailChkValue = $('input[name="emailchk"]:checked').val();

  //폼객체 전송
  $("form").submit((e) => {
    e.preventDefault();

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
    const data = `email=${$inputEmail.val()}&pwd=${$pwdinput.val()}&nickname=${$nickname.val()}&emailr=${emailChkValue}&genre=${selectedGenres.join(
      ","
    )}`;
    if (emailchknum == 1 && pwdchknum == 1 && nickchknum == 1) {
      $.ajax({
        xhrFields: {
          withCredentials: true,
        },
        url: backURL + "/signup",
        method: "POST",
        data: data,
        success: (responseJSONObj) => {
          if (responseJSONObj.status == 1) {
            alert("회원가입 성공! 로그인 페이지로 이동합니다");
            localStorage.removeItem("savedId");
            location.href = "../login/index.html";
          } else if (responseJSONObj.status == 0) {
            alert("회원가입 실패");
            location.href = "../index.html";
          }
        },
      });
    } else {
      e.preventDefault();
    }
  });
});
