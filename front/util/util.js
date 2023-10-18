// // export const frontURL =
// //   "http://show-fan.s3-website.ap-northeast-2.amazonaws.com";
// // export const backURL =
//   "http://ec2-52-79-82-77.ap-northeast-2.compute.amazonaws.com:8080/showfan";
// export const frontURL = "http://127.0.0.1:5559";
// export const backURL = "http://127.0.0.1:8888/showfan";
export const frontURL = "http://192.168.1.112:5559";
export const backURL = "http://192.168.1.112:8888/showfan";
// api call(ajax) function
export const handleXhttps = (method, url, target) => {
  $.ajax({
    url: url,
    method: method,
    success: (responseText) => {
      target.html(responseText);
    },
  });
};

// carousel image slide - parameter(이미지 클래스들을 담고 있는 요소, 왼쪽 클릭 버튼, 오른쪽 클릭 버튼)
export const carouselSlider = (imageWrapper, prevArrow, nextArrow) => {
  $(imageWrapper).slick({
    dots: false,
    infinite: true,
    speed: 300,
    slidesToShow: 5,
    slidesToScroll: 5,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 4,
          slidesToScroll: 4,
        },
      },
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
        },
      },
    ],
    prevArrow: prevArrow,
    nextArrow: nextArrow,
  });
};
