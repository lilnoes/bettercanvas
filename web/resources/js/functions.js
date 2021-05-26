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
  elt.style.display = elt.style.display == "none" ? "block" : "none";
}
