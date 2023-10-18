document.querySelector(".classic").addEventListener("click", classic);
document.querySelector(".festival").addEventListener("click", festival);
document.querySelector(".concert").addEventListener("click", concert);
document.querySelector(".show").addEventListener("click", show);
document.querySelector(".musical").addEventListener("click", musical);
document.querySelector(".all").addEventListener("click", all);

function classic() {
  console.log("clasic");
  window.localStorage.setItem("show_genre", 3);
  location.href = "../genre";
}
function festival() {
  window.localStorage.setItem("show_genre", 5);
  location.href = "../genre";
}
function musical() {
  window.localStorage.setItem("show_genre", 2);
  location.href = "../genre";
}
function concert() {
  window.localStorage.setItem("show_genre", 4);
  location.href = "../genre";
}
function show() {
  window.localStorage.setItem("show_genre", 1);
  location.href = "../genre";
}
function all() {
  location.href = "../index.html";
}
