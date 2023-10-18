import { handleXhttps } from "./util/util.js";

$(() => {
  handleXhttps("GET", "../header/index.html", $("header"));
  handleXhttps("GET", "../navigation/index.html", $("nav"));
  handleXhttps("GET", "../footer/index.html", $("footer"));

  handleXhttps("GET", "../main/index.html", $("main"));

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

  // 검색 시
  $("body").on("click", "#header-search-button", function (e) {
    const value = $("#header-search-input").val();
    location.href = `../search?q=${value}`;
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
});
