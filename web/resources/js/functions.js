function loadData() {
  const data = {
    labels: ["quiz 1", "quiz 2", "midterm"],
    datasets: [
      {
        label: "Grades",
        data: [10, 20, 15],
        borderColor: "rgb(75, 192, 192)",
        pointBorderWidth: 3,
        pointBackgroundColor: "red",
        pointBorderColor: "red",
      },
    ],
  };
  const options = { scales: { y: { min: 0, max: 100 } } };
  const config = { type: "line", data: data, options: options };
  const chart = new Chart(document.getElementById("gradesChart"), config);
}

function toggle(selector) {
  const elt = document.getElementById(selector);
  console.log("found", elt);
  elt.classList.toggle("hidden");
}

function scrollToBottom() {
  const elt = document.getElementById("allSms");
  elt.scrollTop = elt.scrollHeight;
}

function closeGrades() {
  const elts = document.getElementsByClassName("newgrades");
  for (let elt of elts) elt.style.display = "none";
}

function openGrades() {
  const elts = document.getElementsByClassName("newgrades");
  for (let elt of elts) elt.style.display = "block";
}

function closeQuiz() {
  const elts = document.getElementsByClassName("newquiz");
  for (let elt of elts) elt.style.display = "none";
}

function openQuiz() {
  const elts = document.getElementsByClassName("newquiz");
  for (let elt of elts) elt.style.display = "block";
}

function showUpload() {
  const elt = document.getElementById("registerform:picture");
  elt.click();
}