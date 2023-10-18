import { handleXhttps, backURL } from "../util/util.js";

$(() => {
  function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(";").shift();
  }

  //해당 쿠키값 얻어오기
  const loginCookie = getCookie("loginCookie");

  const $login = $("#login");
  const $logout = $("#logout");
  const $signup = $("#signup");
  const $mypage = $("#mypage");
  // $('#login')
  // $('#logout')
  // $('#signup')
  // $('#mypage')

  //쿠키값이 있다면 로그인 상태(로그아웃, 마이페이지만 보이게)
  if (loginCookie) {
    $login.hide();
    $signup.hide();
    $logout.show();
    $mypage.show();

    //쿠키값이 없다면 로그인X 상태(로그인, 회원가입 보이게)
  } else {
    $logout.hide();
    $mypage.hide();
    $login.show();
    $signup.show();
  }

  $logout.click((e) => {
    //로그아웃 alert에서 확인 눌렀을때
    if (confirm("로그아웃 하시겠어요?") == true) {
      $.ajax({
        xhrFields: {
          withCredentials: true,
        },
        url: backURL + "/logout",
        method: "get",
        success: (responseJSONObj) => {
          // responseJSONObj = JSON.parse(responseJSONObj);
          //로그아웃 성공시
          if (responseJSONObj.status == 1) {
            location.href = "../index.html";
          } else if (responseJSONObj.status == 0) {
            alert("쿠키 삭제 실패");
          } else {
            alert("알 수 없는 상태: " + responseJSONObj.status);
          }
          //로그아웃 실패시
        },
      });
      alert("로그아웃 되었습니다.");
    }
    //로그아웃 alert에서 취소 눌렀을때
    else {
      return;
    }
  });
});
