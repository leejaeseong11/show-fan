import { backURL, handleXhttps } from "../util/util.js";

$(() => {
  function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(";").shift();
  }

  handleXhttps("GET", "../header/index.html", $("header"));
  handleXhttps("GET", "../navigation/index.html", $("nav"));
  handleXhttps("GET", "../footer/index.html", $("footer"));
  let showId;
  let memberId;

  // showId = window.localStorage.getItem("param");
  showId = location.search.substring(location.search.indexOf("=") + 1);

  $.ajax({
    type: "get",
    url: backURL + "/showdetail?showId=" + showId,
    dataType: "json",
    success: function (data) {

      if (data[0].genreId == 5) {
        $(".show_type").text("콘서트");
      } else if (data[0].genreId == 4) {
        $(".show_type").text("클래식");
      } else if (data[0].genreId == 3) {
        $(".show_type").text("축제");
      } else if (data[0].genreId == 2) {
        $(".show_type").text("연극");
      } else if (data[0].genreId == 1) {
        $(".show_type").text("뮤지컬");
      }
      $(".show_info_detail > img").attr("src", data[0].showPoster);
      $(".show_name").text(data[0].showName);
      $(".show_open").text(data[0].showStatus);

      $(".show_start_day").text("공연일시 : " + data[0].showStartDay);
      $(".show_time").text("공연시간 : " + data[0].showTime);
      $(".show_place").text("공연장소 : " + data[0].showVenues);
      $(".show_age").text("관람가능연령 : " + data[0].showAge + "세 이상");
      $(".show_price").text("가격 : " + data[0].seatPrice + "원 부터");
      $(".show_run_time").text("런타임 : " + data[0].showRuntime);
      $(".info_photo_img").attr("src", data[0].showImage1);
      let total = 0;
      $.each(data, function (key, value) {
        if (total == 3 || value.artistId == null) {
          return false;
        }

        let div1 = document.createElement("div");
        div1.setAttribute("class", "actor_detail" + key + " actor_detail");
        document.querySelector(".actor").append(div1);

        let img1 = document.createElement("img");
        img1.setAttribute("class", "actor_detail_img" + key);

        let span1 = document.createElement("span");
        span1.setAttribute("class", "actor_detail_span" + key);

        document.querySelector(".actor_detail" + key).append(img1);
        $(".actor_detail_img" + key).attr("src", "../images/profile.jpg");
        document.querySelector(".actor_detail" + key).append(span1);
        $(".actor_detail_span" + key).text(value.artistName);
        total += 1;
      });
    },
    error: function () {
      console.log("통신에러");
    },
  });
  //--

  const loginCookie = getCookie("loginCookie");

  if (!loginCookie) {
    $("#cancel_heart").hide();
    $("#heart").hide();
  }
  $.ajax({
    url: backURL + "/member",
    method: "GET",
    data: `email=${loginCookie}`,
    success: (memberResponseText) => {
      if(memberResponseText != null) {
        memberId = memberResponseText.memberId;
        $.ajax({
          type: "get",
          url: backURL + `/myshow?memberId=${memberId}`, //찜하기 되어있는지 확인하는 url
          dataType: "json",
          success: function (data) {
            const isMyShow = data.filter((d) => d.showId == showId).length == 1;
            if (loginCookie) {
              if (isMyShow) {
                $("#heart").hide();
              } else {
                $("#cancel_heart").hide();
              }
            } else {
              $("#cancel_heart").hide();
              $("#heart").hide();
            }
          },
        });
      }
    },

    error: function () {
      $("#cancel_heart").hide();
      $("#heart").hide();
    },
  });

  // 찜하기 버튼 클릭 시
  $("#heart").click(() => {
    $.ajax({
      url: `${backURL}/myshow?showId=${showId}&memberId=${memberId}`,
      method: "POST",
      success: () => {
        alert("추가되었습니다.");
        location.reload();
      },
    });
  });

  // 찜하기 취소 버튼 클릭 시
  $("#cancel_heart").click(() => {
    $.ajax({
      url: `${backURL}/myshow?showId=${showId}&memberId=${memberId}`,
      method: "DELETE",
      success: () => {
        alert("삭제되었습니다.");
        location.reload();
      },
    });
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
});
