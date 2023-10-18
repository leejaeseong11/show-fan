import { handleXhttps, backURL, frontURL } from "../util/util.js";

$(() => {
  const queryStr = location.search.substring(1); // q=value
  const value = queryStr.substring(2);
  let cPage = 1;
  let showCnt;
  let showList = [];

  // 검색어 저장
  $("#header-search-input").attr("value", decodeURI(value));

  handleXhttps("GET", "../navigation/index.html", $("nav"));
  handleXhttps("GET", "../footer/index.html", $("footer"));

  // 회원가입 클릭 시
  $("body").on("click", '.header-menu-link[href="signup"]', function (e) {
    e.preventDefault();
    location.href = "../signup/index.html";
  });

  // 로그인 클릭 시
  $("body").on("click", '.header-menu-link[href="login"]', function (e) {
    e.preventDefault();
    location.href = "../login/index.html";
  });

  // if 마이페이지 link clicked
  $("body").on("click", '.header-menu-link[href="mypage"]', function (e) {
    e.preventDefault();
    location.href = "../mypage/index.html";
  });

  // 내 정보 수정 클릭 시
  $("body").on("click", '.mypage-main-text[href="modify.html"]', function (e) {
    e.preventDefault();
    handleXhttps("GET", "../html/modify.html", $("main"));
  });

  // 검색 버튼 클릭 시
  $("body").on("click", "#header-search-button", function (e) {
    // e.preventDefault();
    const value = $("#header-search-input").val();
    location.href = `index.html?q=${value}`;
  });

  // 검색 입력 후 엔터
  $("body").on("keydown", "#header-search-input", function (e) {
    if (e.key == "Enter" || e.keyCode == "13") {
      const value = $("#header-search-input").val();
      location.href = `index.html?q=${value}`;
    }
  });

  // 캘린더 조회 시
  $("body").on("click", "#navigation-calendar-button", function (e) {
    location.href = "../calendar/index.html";
  });

  // 체크박스 클릭
  $(".form-check-input").click((e) => {
    showListFilterHandler(showList);
  });

  // 전체 선택 체크박스
  $("#genreAll").click((e) => {
    allChkHandler($("#genreAll"), "genre");
  });

  $("#statusAll").click((e) => {
    allChkHandler($("#statusAll"), "status");
  });

  $("#localAll").click((e) => {
    allChkHandler($("#localAll"), "local");
  });

  $(document).ready(function () {
    $("#btn_top").hide();

    // 스크롤 시 효과 설정
    $(window).scroll(function () {
      // 스크롤 시
      if ($(this).scrollTop() > 1000) {
        // 스크롤탑이 '1000'보다 클 때
        $("#btn_top").fadeIn(); // 버튼이 fadein되며 출력
      } else {
        // 반대로 '1000'보다 작을 때
        $("#btn_top").fadeOut(); // 버튼이 fadeout되며 제거
      }
    });

    $("#btn_top").click(function () {
      $("html,body").scrollTop(0);
    });
  });

  // 공연 데이터 호출
  ajaxHandler(cPage);

  // 공연 데이터 호출
  function ajaxHandler(page) {
    const data = `p=${page}`;

    $.ajax({
      url: `${backURL}/search?${queryStr}`,
      method: "GET",
      data: data,
      success: (responseJSONObj) => {
        showCnt = responseJSONObj.showCnt;

        if (showList.length == 0) {
          showList = responseJSONObj.show;
        } else {
          $(responseJSONObj.show).each((index, e) => {
            showList.push(e);
          });
        }

        if (showList.length <= 20) {
          showListHandler(showCnt, showList);
        } else {
          showListFilterHandler(showList);
        }
      },
      error: (xhr, textStatus) => {
        alert("err: " + xhr.status);
      },
    });
  }

  // 필터 전체 체크
  function allChkHandler($allChk, name) {
    if ($allChk.is(":checked")) {
      $(`input[name=${name}]`).prop("checked", true);
    } else {
      $(`input[name=${name}]`).prop("checked", false);
    }
    showListFilterHandler(showList);
  }

  // show list 체크 필터
  function showListFilterHandler(showList) {
    var showFilterList = showList;

    // show list 장르체크 필터
    $("input:checkbox[name=genre]").each((index, e) => {
      if (!$(e).is(":checked")) {
        showFilterList = showFilterList.filter(function (s) {
          return s.genreId != $(e).val();
        });
      }
    });

    // show list 상태체크 필터
    $("input:checkbox[name=status]").each((index, e) => {
      if (!$(e).is(":checked")) {
        showFilterList = showFilterList.filter(function (s) {
          return s.showStatus != $(e).val();
        });
      }
    });

    // show list 지역체크 필터
    $("input:checkbox[name=local]").each((index, e) => {
      if (!$(e).is(":checked")) {
        showFilterList = showFilterList.filter(function (s) {
          return s.showAddress.indexOf($(e).val()) == -1;
        });
      }
    });

    // 필터된 show list 렌더링
    $("#search-result-container").html(
      // showListHandler(showCnt, showFilterList)
      addList(showFilterList)
    );
  }

  // show list handler
  function showListHandler(showCnt, showList) {
    if (showCnt == 0) {
      $("#search-result-count > b").html(`검색 결과 (${showCnt})`);
      $("#showCardList").html(
        '<div id="search-result-count" class="container text-right"><b>검색된 공연이 없습니다.</b></div>'
      );
    } else {
      $("#search-result-count > b").html(`검색 결과 (${showCnt})`);

      const $originShow = $("div.col").first();
      $originShow.siblings().remove();
      $originShow.show();

      $(showList).each((index, s) => {
        const $copyShow = $originShow.clone();

        const showId = s.showId;
        const showImage = s.showPoster;
        const genreId = s.genreId;
        const showName = s.showName;
        const showVenues = s.showVenues;
        const showStartDay = s.showStartDay;
        const showEndDay = s.showEndDay;
        const showAddress = s.showAddress;
        const showStatus = s.showStatus;
        const reviewCnt = s.reviewCnt;
        const gradeAvg = s.gradeAvg;

        $("input:checkbox[name=genre]").each((index, e) => {
          if (genreId == $(e).val()) {
            $(e).prop("checked", true);
          }
        });

        $("input:checkbox[name=status]").each((index, e) => {
          if (showStatus == $(e).val()) {
            $(e).prop("checked", true);
          }
        });

        $("input:checkbox[name=local]").each((index, e) => {
          if (showAddress.indexOf($(e).val()) != -1) {
            $(e).prop("checked", true);
          }
        });

        $copyShow
          .find("a")
          .attr("href", `${frontURL}/show-detail/index.html?showId=${showId}`);
        $copyShow.find("img").attr("src", showImage).attr("title", showName);

        if (showStatus == "공연완료") {
          $copyShow.find(".status > b").html("공연종료");
        } else {
          $copyShow.find(".status > b").html(showStatus);
        }

        $copyShow.find(".card-title").html(showName);
        $copyShow.find("#period").html(showStartDay + " ~ " + showEndDay);
        $copyShow.find("#venues").html(showVenues);
        $copyShow
          .find("#grade")
          .html(
            "<i id='show-review-star-icon' class='fa-solid fa-star'></i> " +
              gradeAvg +
              " (" +
              reviewCnt +
              ")"
          );

        $("#showCardList").append($copyShow);
      });

      $originShow.hide();
    }
  }

  // 무한 스크롤
  window.onscroll = function (e) {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
      ajaxHandler(++cPage);
    }
  };

  // show list 추가
  function addList(addList) {
    const $originShow = $("div.col").first();
    $originShow.siblings().remove();
    $originShow.show();

    $(addList).each((index, s) => {
      const $copyShow = $originShow.clone();

      const showId = s.showId;
      const showImage = s.showPoster;
      const showName = s.showName;
      const showVenues = s.showVenues;
      const showStartDay = s.showStartDay;
      const showEndDay = s.showEndDay;
      const showStatus = s.showStatus;
      const reviewCnt = s.reviewCnt;
      const gradeAvg = s.gradeAvg;

      $copyShow
        .find("a")
        .attr("href", `../show-detail/index.html?showId=${showId}`);
      $copyShow.find("img").attr("src", showImage).attr("title", showName);

      if (showStatus == "공연완료") {
        $copyShow.find(".status > b").html("공연종료");
      } else {
        $copyShow.find(".status > b").html(showStatus);
      }

      $copyShow.find(".card-title").html(showName);
      $copyShow.find("#period").html(showStartDay + " ~ " + showEndDay);
      $copyShow.find("#venues").html(showVenues);
      $copyShow
        .find("#grade")
        .html(
          "<i id='show-review-star-icon' class='fa-solid fa-star'></i>  " +
            gradeAvg +
            " (" +
            reviewCnt +
            ")"
        );

      $("#showCardList").append($copyShow);
    });

    $originShow.hide();
  }
});
