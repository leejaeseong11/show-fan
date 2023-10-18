import { handleXhttps, backURL } from "../util/util.js";
$(() => {
  let cpage = 1;

  handleXhttps("GET", "../header/index.html", $("header"));
  handleXhttps("GET", "../navigation/index.html", $("nav"));
  handleXhttps("GET", "../footer/index.html", $("footer"));

  ajaxHandler(cpage);

  function ajaxHandler(cpage) {
    $.ajax({
      type: "get",
      url:
        `${backURL}/showconcept?genreId=` +
        window.localStorage.getItem("show_genre") +
        `&p=${cpage}`,
      dataType: "json",
      success: function (data) {
        $.each(data, function (key, value) {
          console.log(data);
          console.log(key);
          console.log(value);

          let div1 = document.createElement("div");
          let div2 = document.createElement("div");

          div1.setAttribute("class", "col " + key);
          div2.setAttribute("class", "card h-100 " + key);
          div2.setAttribute("id", value.showId);
          document.getElementById("genre-show-container").append(div1);
          let img1 = document.createElement("img");
          img1.setAttribute("class", "card-img-top img" + key);

          let div3 = document.createElement("div");
          div3.setAttribute("class", "card-body " + key);

          // document.querySelector(".q" + key)[key].append(div3);

          div2.append(div3);

          let h = document.createElement("span");
          h.setAttribute("class", "card-title card_title" + key);

          // document.querySelector(".a" + key).append(h);
          // document.querySelector(".q" + key).append(img1);
          div1.append(div2);
          div2.append(img1);
          $(".img" + key).attr("src", value.showPoster);
          div2.append(div3);
          div3.append(h);
          $(".card_title" + key).text(value.showName);
          // document.querySelector(".b" + key).append(div2);

          if ($(".card").length) {
            console.log("oo");
          } else {
            console.log("xx");
          }
        });
      },
    });
  }

  function addShowList(cpage) {
    $.ajax({
      type: "get",
      url:
        `${backURL}/showconcept?genreId=` +
        window.localStorage.getItem("show_genre") +
        `&p=${cpage}`,
      dataType: "json",
      success: function (data) {
        $.each(data, function (key, value) {
          console.log(data);
          let div1 = document.createElement("div");
          let div2 = document.createElement("div");

          div1.setAttribute("class", "col " + key);
          div2.setAttribute("class", "card h-100 " + key);
          div2.setAttribute("id", value.showId);
          document.getElementById("genre-show-container").append(div1);
          let img1 = document.createElement("img");
          img1.setAttribute("class", "card-img-top img" + key);

          let div3 = document.createElement("div");
          div3.setAttribute("class", "card-body " + key);

          div2.append(div3);

          let h = document.createElement("span");
          h.setAttribute("class", "card-title card_title" + key);

          div1.append(div2);
          div2.append(img1);
          $(".img" + key).attr("src", value.showPoster);
          div2.append(div3);
          div3.append(h);
          $(".card_title" + key).text(value.showName);
        });
      },
    });
  }

  //$(".card").click((e) => {
  $("#genre-show-container").on("click", "div.col>div.card", (e) => {
    let showId = $(e.currentTarget).attr("id");
    window.localStorage.setItem("param", showId);
    location.href = "../show-detail/index.html?showId=" + showId;
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

  // 무한 스크롤
  window.onscroll = function (e) {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
      console.log("무한 스크롤 데이터 호출 필요");
      addShowList(++cpage);
    }
  };

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
});
