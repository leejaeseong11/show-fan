import { backURL } from '../../util/util.js';

const $login = $('#login');
const $logout = $('#logout');
const $signup = $('#signup');
const $mypage = $('#mypage');
document.querySelector('.classic').addEventListener('click', classic);
document.querySelector('.festival').addEventListener('click', festival);
document.querySelector('.concert').addEventListener('click', concert);
document.querySelector('.show').addEventListener('click', show);
document.querySelector('.musical').addEventListener('click', musical);
document.querySelector('.all').addEventListener('click', all);

function classic() {
  console.log('clasic');
  window.localStorage.setItem('show_genre', 3);
  location.href = '../genre';
}
function festival() {
  window.localStorage.setItem('show_genre', 5);
  location.href = '../genre';
}
function musical() {
  window.localStorage.setItem('show_genre', 2);
  location.href = '../genre';
}
function concert() {
  window.localStorage.setItem('show_genre', 4);
  location.href = '../genre';
}
function show() {
  window.localStorage.setItem('show_genre', 1);
  location.href = '../genre';
}
function all() {
  location.href = '../index.html';
}

$(() => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${'loginCookie'}=`);
  let loginCookie = null;
  if (parts.length === 2) {
    loginCookie = parts.pop().split(';').shift();
  }
  if (!loginCookie) {
    alert('로그인 후 이용 가능합니다.');
    history.back();
  }
  const $login = $('#login');
  const $logout = $('#logout');
  const $signup = $('#signup');
  const $mypage = $('#mypage');

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
    if (confirm('로그아웃 하시겠어요?') == true) {
      $.ajax({
        xhrFields: {
          withCredentials: true,
        },
        url: backURL + '/logout',
        method: 'get',
        success: (responseJSONObj) => {
          // responseJSONObj = JSON.parse(responseJSONObj);
          //로그아웃 성공시
          if (responseJSONObj.status == 1) {
            location.href = '../index.html';
          } else if (responseJSONObj.status == 0) {
            alert('쿠키 삭제 실패');
          } else {
            alert('알 수 없는 상태: ' + responseJSONObj.status);
          }
          //로그아웃 실패시
        },
      });
      alert('로그아웃 되었습니다.');
    }
    //로그아웃 alert에서 취소 눌렀을때
    else {
      return;
    }
  });

  $.ajax({
    url: backURL + '/memberreview',
    method: 'GET',
    data: `memberId=${window.localStorage.getItem('memberId')}`,
    success: (myReviewResponse) => {
      if (myReviewResponse === '') {
        $('#mypage-myreview-review-containers')
          .prev()
          .after('<p>작성된 리뷰가 없습니다</p>');
      } else {
        let evaluationCount = myReviewResponse.length;
        let genreEvalutionCount = {
          1: 0,
          2: 0,
          3: 0,
          4: 0,
          5: 0,
        };
        const $mypageReviewContainers = $('#mypage-myreview-review-containers');

        myReviewResponse.forEach((myReview, index) => {
          genreEvalutionCount[myReview.genreId] += 1;
          const $mypageReviewContainer = $(`
                <div class="mypage-myreview-review-container"></div>
                `);
          $mypageReviewContainer.append(`
                <img
                  src="${myReview.showPoster}"
                  alt="review-show-post"
                  class="mypage-myreview-review-post"
                  onclick="location.href='/show-detail/index.html?showId=${
                    myReview.showId
                  }'"}
                />
                <div class="mypage-myreview-review-info">
                  <div id="mypage-myreview-review-show-info">
                    <div>${myReview.showName}</div>
                    <i class="fa-solid fa-slash mypage-myreview-review-line-icon"></i>
                    <div>${myReview.reviewCreatedAt}</div>
                    <i class="fa-solid fa-slash mypage-myreview-review-line-icon"></i>
                    <div>${
                      myReview.seatName
                    }(${myReview.seatPrice.toLocaleString()}원)</div>
                    <i class="fa-solid fa-slash mypage-myreview-review-line-icon"></i>
                    <div>${myReview.showVenues}</div>
                  </div>
                  <div class="mypage-myreview-review-evaluation-info">
                    <i id="mypage-myreview-review-star-icon" class="fa-solid fa-star"></i>
                    <div>${myReview.reviewGrade}</div>
                    <i class="fa-solid fa-slash mypage-myreview-review-line-icon"></i>
                    <div id="mypage-review-content">
                      ${myReview.reviewContent}
                    </div>
                  </div>
                </div>
                <div id="mypage-myreview-review-button">
                  <a id="mypage-myreview-review-edit-${
                    myReview.reviewId
                  }" href="#">수정</a>
                  <i class="fa-solid fa-slash mypage-myreview-review-line-icon"></i>
                  <a id="mypage-myreview-review-delete-${
                    myReview.reviewId
                  }" href="#">삭제</a>
                </div>
              `);

          $mypageReviewContainers.append($mypageReviewContainer);
          $mypageReviewContainers.append(`
                <div class="mypage-myreview-review-line"></div>
              `);
          $(`#mypage-myreview-review-delete-${myReview.reviewId}`).click(
            (e) => {
              if (confirm('삭제하시겠습니까?')) {
                $.ajax({
                  url: backURL + '/deletereview',
                  method: 'GET',
                  data: `reviewId=${myReview.reviewId}`,
                  success: () => {
                    alert('삭제되었습니다');
                    location.reload();
                  },
                });
              }
            }
          );
          $(`#mypage-myreview-review-edit-${myReview.reviewId}`).click((e) => {
            e.preventDefault();
            $('.popup').removeClass('hidden');
            $.ajax({
              url: backURL + '/review',
              method: 'GET',
              data: `reviewId=${myReview.reviewId}`,
              success: (reviewResponse) => {
                let reviewGrade = Number(reviewResponse.reviewGrade) * 2;
                $(`#rating${reviewGrade}`).click();
                $('.pop-detail').text(reviewResponse.reviewContent);
                $('.popup').attr('id', reviewResponse.reviewId);

                $.ajax({
                  url: backURL + '/showdetail',
                  method: 'GET',
                  data: `showId=${myReview.showId}`,
                  success: (showReviewResponse) => {
                    let seatName = {};
                    showReviewResponse.forEach((showReview) => {
                      if (seatName[showReview.seatId] == undefined) {
                        seatName[showReview.seatId] =
                          showReview.seatName +
                          ' (' +
                          showReview.seatPrice.toLocaleString() +
                          '원)';
                      }
                    });

                    $('.pop-title').text(showReviewResponse[0].showName);
                    Object.keys(seatName).forEach((seatId) => {
                      $('#popup-seat-list').append(
                        `<option value=${seatId}>${seatName[seatId]}</option>`
                      );
                      $('#popup-seat-list').val(reviewResponse.seatId);
                    });
                  },
                });
                $('.pop-title').text(reviewResponse.r);
              },
            });
          });
        });

        $('#mypage-myreview-evalution-count-value').text(evaluationCount);
        for (let i = 1; i <= 5; i++) {
          $(`#mypage-myreview-evalution-count-value-${i}`).text(
            genreEvalutionCount[i]
          );
        }

        $('#mypage-myreview-review-info').append(
          `<div class="mypage-myreview-review-show-info">`
        );
      }
    },
  });

  $('#close-btn').click(() => {
    initPopup();
  });

  $('#popup-regist-button').click(() => {
    $.ajax({
      url:
        backURL +
        `/updatereview?seatId=${$('#popup-seat-list').val()}&reviewId=${$(
          '.popup'
        ).attr('id')}&reviewContent=${$('.pop-detail').val()}&reviewGrade=${$(
          "input[name='reviewGrade']:checked"
        ).val()}`,
      method: 'GET',
      success: () => {
        alert('수정되었습니다');
        location.reload();
      },
    });
    initPopup();
  });

  // 검색 버튼 클릭 시
  $('body').on('click', '#header-search-button', function (e) {
    // e.preventDefault();
    const value = $('#header-search-input').val();
    location.href = `../../search/index.html?q=${value}`;
  });

  // 검색 입력 후 엔터
  $('body').on('keydown', '#header-search-input', function (e) {
    if (e.key == 'Enter' || e.keyCode == '13') {
      const value = $('#header-search-input').val();
      location.href = `../../search/index.html?q=${value}`;
    }
  });
});

function initPopup() {
  $('.pop-title').empty();
  $('#popup-seat-list').empty();
  $('.popup').addClass('hidden');
}
