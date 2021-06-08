function loadData() {
  const labels = []
  const points = []
  const _points = document.getElementsByClassName("point");
  for(let point of _points){
    labels.push(point.getAttribute("name"));
    points.push(parseFloat(point.getAttribute("points")));
  }
  const data = {
    labels: labels,
    datasets: [
      {
        label: "Grades",
        data: points,
        borderColor: "rgb(75, 192, 192)",
        pointBorderWidth: 3,
        pointBackgroundColor: "red",
        pointBorderColor: "red",
      },
    ],
  };
  console.log(data);
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

function showImage() {
  console.log("yuup");
  const elt = document.getElementById("registerform:picture");
  if (elt.files.length < 1) return;
  const target = document.getElementById("registerform:uploaded");
  const file = elt.files[0];
  target.src = URL.createObjectURL(file);
}

function hideNewCourse() {
  const elt = document.getElementById("newcourse");
  elt.style.display = "none";
}
function hideNewCourseAjax(evt) {
  if (evt.status == "success") hideNewCourse();
  console.log(evt);
}

function showNewCourse() {
  const elt = document.getElementById("newcourse");
  elt.style.display = "block";
}

function showAllowStudents() {
  const elt = document.getElementById("allowstudents");
  elt.style.display = "block";
}

function hideAllowStudents() {
  const elt = document.getElementById("allowstudents");
  elt.style.display = "none";
}

function hideStudent(evt, elt) {
  if (evt.status != "success") return false;
  evt.source.parentElement.parentElement.style.display = "none";
  return false;
}
function hideElement(id) {
  document.getElementById(id).style.display = "none";
}

function hideElementAjax(evt, id) {
  if (evt.status != "success") return false;
  hideElement(id);
  return false;
}
function showElement(id) {
  document.getElementById(id).style.display = "block";
}
function joinedClass(evt) {
  if (evt.status != "success") return;
  evt.source.value = "Waiting for approval...";
  evt.source.disabled = true;
}

//<<<<<<< HEAD

function changePassWord() {
    const changePassword = document.getElementsById("changepassword");
    changePassword.style.display = "block";
}
//=======
function setRecipient(elt) {
  window.elt = elt;
  console.log("clicked ", elt);
  const value1 = elt.getElementsByClassName("name").item(0).innerText;
  const value2 = elt.getElementsByClassName("course").item(0).innerText;
  const id = elt.getElementsByClassName("id").item(0).innerText;
  const type = elt.getElementsByClassName("type").item(0).innerText;
  document.getElementById("smsTopName1").innerText = value1;
  document.getElementById("smsTopName2").innerText = value2;
  document.getElementById("toID").value = id;
  document.getElementById("toID1").value = id;

  document.getElementById("type").value = type;
  document.getElementById("type1").value = type;
  document.getElementById("loadContent").click();
}

function addMessage() {
  const message = document.getElementById("inputText").value;
  if(message.length <=0) return;
  const div = document.createElement("div");
  div.innerHTML = `<div>
  <div class="row float-right"
      style="margin-top:10px; width: 50%; background-color: rgba(217, 218, 219, 0.664); border-radius: 20px; padding: 7px;">
      <div class="col-md-8">
          <span>${message}</span>
      </div>
      <div class="col-md-4">
          <span class="float-end date" style="margin-top:12px;"><small
                  style="font-size: 10px; color: black; ">26-05-2021</small></span>

      </div>

  </div>
  <div class="clear-right"></div>
</div>`;
  document.getElementById("allSms").appendChild(div);
  document.getElementById("inputText").value = "";
  document.getElementById("inputText").focus();
}
//>>>>>>> e3d75df6a599e283305aa45fadb1c12c8958300c

function sendGrades(elt){
  console.log("here here");
  window.elt = elt;
  const btn = document.getElementById("sendGrades1");
  document.getElementById("studentID").value = elt.getAttribute("student");
  document.getElementById("studentPoints").value = elt.value;
  document.getElementById("studentModel").value = elt.getAttribute("model");
  btn.click();
}

function updateTotalPoints(){
  let count = 0;
  let sum1 = 0;
  let sum2 = 0;
  const points = document.getElementsByClassName("points");
  const ranges = document.getElementsByClassName("range");
  for(let point of points){sum1 += parseFloat(point.innerText); count += 1}
  for(let range of ranges){sum2 += parseFloat(range.innerText);}
  sum1 /= count;
  sum2 /= count;
  document.getElementById("totalPoints").innerText = sum1;
  document.getElementById("totalRange").innerText = sum2;
}

function openFile(){
  const btn = document.getElementById("file");
  btn.click();
}

function changePicture(evt){
  console.log(evt);
  // if(evt.status != "success") return true;
  const elt = document.getElementById("pic1");
  const file = document.getElementById("file");
  if(file.files.length <= 0) return;
  elt.src = window.URL.createObjectURL(file.files[0]);

}
