import { backURL } from "../util/util.js";

$(() => {
  function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(";").shift();
  }

  const loginCookie = getCookie("loginCookie");

  let memberId;
  $.ajax({
    url: `${backURL}/selectreview`,
    method: "GET",
    data: `showId=${window.location.search.replace("?showId=", "")}`,
    success: (responseJSONObj) => {
      displayReviews(responseJSONObj);
    },
    error: (e) => {
      console.log(e)
    }
  });

  $.ajax({
    url: backURL + "/member",
    method: "GET",
    data: `email=${loginCookie}`,
    success: (memberResponseText) => {
      if (memberResponseText != null) memberId = memberResponseText.memberId;
    },
  });
  //--모달 시작--
  // 모달 요소 가져오기
  var modal = document.getElementById("myModal");

  // 모달 닫기 버튼 가져오기
  var closeModalBtn = document.getElementById("closeModalBtn");

  // 모달 등록 버튼 가져오기
  var submitbutton = document.getElementById("submitbutton");

  // 모달 닫기 버튼 클릭 시 모달을 숨김
  closeModalBtn.onclick = function () {
    modal.style.display = "none";
  };

  // 모달 영역 외부 클릭 시 모달을 숨김
  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };
  // 모달 등록 버튼 클릭 시 모달을 숨김
  submitbutton.onclick = function () {
    modal.style.display = "none";
    if (loginCookie == null) {
      alert("로그인 후 리뷰 쓰기가 가능합니다");
      return;
    }
    $.ajax({
      method: "GET",
      url:
        backURL +
        `/insertreview?seatId=${$("#seat").val()}&reviewContent=${$(
          "#reviewContent"
        ).val()}&reviewGrade=${$(
          "input[name='reviewGrade']:checked"
        ).val()}&showId=${window.location.search.replace(
          "?showId=",
          ""
        )}&memberId=${memberId}`,
      success: (responseJSONObj) => {
        alert(responseJSONObj.msg);
        location.reload();
      },
      error: (xhr, status, error) => {
        alert(xhr.responseText);
      },
    });
  };
  // 모달을 숨김
  var modal = document.getElementById("myModal");
  modal.style.display = "none";
  //--모달 끝--
});

var reviews = [];
var replies = [];
// 리뷰 데이터를 화면에 출력하는 함수
function displayReviews(reviews) {
  for (var i = 0; i < reviews.length; i++) {
    const $reviewContainer = $(
      '<div class="showdetail-review-container"></div>'
    );
    $reviewContainer.append(
      `<img src= ${reviews[i].memberImage} alt="member-profile"/>`
    );
    $reviewContainer.append(`<div>${reviews[i].reviewCreatedAt}</div>`);
    $reviewContainer.append(`<div>${reviews[i].memberNickname}</div>`);
    $reviewContainer.append(`<div>${reviews[i].reviewGrade}</div>`);
    $reviewContainer.append(`<div>${reviews[i].seatName}</div>`);
    $reviewContainer.append(`<div>${reviews[i].reviewContent}</div>`);
    $reviewContainer.append(`<div>_____________________________</div>`);
    $("#review-list").append($reviewContainer);
  }
}
function displayReplies(replies) {
  for (var i = 0; i < replies.length; i++) {
    const $reviewContainer = $(
      '<div class="showdetail-reply-container"></div>'
    );
    const $replyContainer = $('<div class="showdetail-reply-container"></div>');
    $replyContainer.append(`<div>${replies[i].replyContent}</div>`);
    $("#showdetail-reply").append($replyContainer);
  }
}
// 리뷰 쓰기
$("#create-review").click(() => {
  initPopup();
  // 리뷰 쓰기 버튼 클릭 시 모달을 보이게 설정
  var modal = document.getElementById("myModal");
  modal.style.display = "block";
  let showId = window.location.search.replace("?showId=", "");
  $.ajax({
    url: backURL + "/showdetail",
    method: "GET",
    data: `showId=${showId}`,
    success: (showReviewResponse) => {
      console.log(showReviewResponse)
      //seatid, name, price다 가져옴
      let seatName = {};
      showReviewResponse.forEach((showReview) => {
        if (seatName[showReview.seatId] == undefined) {
          seatName[showReview.seatId] =
            showReview.seatName +
            " (" +
            showReview.seatPrice.toLocaleString() +
            "원)";
        }
      });
      Object.keys(seatName).forEach((seatId) => {
        if (!String(seatName[seatId].includes('undefined'))) {
          $("#seat").append(
            `<option value=${seatId}>${seatName[seatId]}</option>`
          );
        } else {
          $("#seat").append(
            `<option value=${seatId}>${seatName[seatId]}</option>`
          );
        }
      });
      $(".review-form > h2").text(showReviewResponse[0].showName);
    },
  });
});

function initPopup() {
  $(".review-form > h2").empty();
  $("#seat").empty();
  $("#reviewContent").empty();
}
//----------리뷰 end---
