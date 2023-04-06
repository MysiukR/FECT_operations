var count = 0;
var currentGroup = "Понеділок";
$(document).ready(function() {
var settings = {
          "url": "http://localhost:8080/api/schedule/items",
          "method": "GET",
          "headers": {
              "accept": "application/json",
              "Access-Control-Allow-Origin": 'http://localhost:8080'
          }
      }



  $.ajax(settings).done(function (data) {
    console.log(data);
	const obj = JSON.parse(JSON.stringify((data)));
    console.log(obj.length);
	count = obj.length;
	//$(".table").append("<thead> <tr> <th>"+obj[0].groupName+" </th> <th>"+obj[0].dayOfWeek+"</th> <th>"+obj[0].dayOfWeek+
	//"</th> <th>"+obj[0].dayOfWeek+"</th> <th>"+obj[0].dayOfWeek+"</th> <th class='last'>"+obj[0].dayOfWeek+"</th> </tr> </thead>");
	$(".table").append("<thead> <tr class='scheduleHead'><th></th>");
	 for (var i=0; i<obj.length; i++) {
	   $(".scheduleHead").append("<th>"+obj[i].groupName+"</th>");
	 }
	$(".table").append("</tr></thead>");
	$(".table").append("<tbody class='scheduleBody'>");
	var caf = 0;
	for (var i=0; i<obj.length; i++) {
    //console.log(obj[i].groupName);
    if(obj[i].groupName != currentGroup) {

    }

	 //if(i!=j) { //avoid duplicates in row
	  for (var j=0; j<obj[i].perDayDto.length; j++) {
	   $(".scheduleBody").append("<tr id='lessonDetails"+i+j+"'>");
       caf = obj[i].perDayDto[j].dayOfWeek;
       console.log(caf);
	   $("#lessonDetails"+i+j).append("<td class='day'>"+caf+"</td>");
	   $("#lessonDetails"+i+j).append("<td class='active'> <h4>"+obj[i].perDayDto[j].subjectName+"</h4> <p>"
	   +obj[i].perDayDto[j].teacher+" "+obj[i].perDayDto[j].roomName+ "</p> <div class='hover'> <h4>"+
	   obj[i].perDayDto[j].subjectName+"</h4> <p>"+obj[i].perDayDto[j].lessonNumber+
	   "</p> <span>"+obj[i].perDayDto[j].teacher+" "+obj[i].perDayDto[j].roomName+"</span> </div> </td> ");


	   $(".scheduleBody").append("</tr>");
	   console.log(i + " j:" + j);
	   }

      currentGroup= obj[i].groupName;
      //j++;
	}
	$(".table").append("</tbody>");
});

});

//search
function loadDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {

    if (this.readyState == 4 && this.status == 200 && this.responseText == "[]") {
      $(".mb-0").html("Вибачте, вказаний навичок не знайдено: " + document.getElementById("txtSearch").value);
      for (var i=0; i< count; i++) {
		  if (document.contains(document.getElementById('teacher'+i))) {
			document.getElementById('teacher'+i).remove();
		  }
	  }
    } else if (this.readyState == 4 && this.status == 200){
		$(".u-text-1").html("Співробітники");
		  for (var i=0; i< count; i++) {
			if (document.contains(document.getElementById('teacher'+i))) {
				document.getElementById('teacher'+i).remove();
			}
	      }
		const filterObj = JSON.parse(this.responseText);
		console.log(filterObj);
		for (var i=0; i< filterObj.length; i++) {
		$(".u-repeater-1").append("<div class='u-container-style u-list-item u-repeater-item u-white u-list-item-1' style='height:60px' id='teacher"+i+"'> <div class='u-container-layout u-similar-container u-valign-bottom-md u-container-layout-1'> <img alt='' class='u-expanded-width u-image u-image-default u-image-1' data-image-width='626' data-image-height='417' src='"+filterObj[i].image+"'> <div class='u-align-center u-container-style u-group u-white u-group-1'> <div class='u-container-layout u-valign-top u-container-layout-2'> <h4 class='u-text u-text-2 append-value"+i+"'></h4> <p class='u-text u-text-3 append-position"+i+"'></p> <div class='u-social-icons u-spacing-10 u-social-icons-1'> <a class='u-social-url' title='facebook' target='_blank' href='https://facebook.com/name'><span class='u-icon u-icon-circle u-social-facebook u-social-icon u-text-black u-icon-1'><svg class='u-svg-link' preserveAspectRatio='xMidYMin slice' viewBox='0 0 112 112' style=''><use xlink:href='#svg-20a0'></use></svg><svg class='u-svg-content' viewBox='0 0 112 112' x='0' y='0' id='svg-20a0'><path fill='currentColor' d='M75.5,28.8H65.4c-1.5,0-4,0.9-4,4.3v9.4h13.9l-1.5,15.8H61.4v45.1H42.8V58.3h-8.8V42.4h8.8V32.2 c0-7.4,3.4-18.8,18.8-18.8h13.8v15.4H75.5z'></path></svg></span> </a> <a class='u-social-url' title='twitter' target='_blank' href='https://twitter.com/name'><span class='u-icon u-icon-circle u-social-icon u-social-twitter u-text-black u-icon-2'><svg class='u-svg-link' preserveAspectRatio='xMidYMin slice' viewBox='0 0 112 112' style=''><use xlink:href='#svg-7f7d'></use></svg><svg class='u-svg-content' viewBox='0 0 112 112' x='0' y='0' id='svg-7f7d'><path fill='currentColor' d='M92.2,38.2c0,0.8,0,1.6,0,2.3c0,24.3-18.6,52.4-52.6,52.4c-10.6,0.1-20.2-2.9-28.5-8.2 c1.4,0.2,2.9,0.2,4.4,0.2c8.7,0,16.7-2.9,23-7.9c-8.1-0.2-14.9-5.5-17.3-12.8c1.1,0.2,2.4,0.2,3.4,0.2c1.6,0,3.3-0.2,4.8-0.7 c-8.4-1.6-14.9-9.2-14.9-18c0-0.2,0-0.2,0-0.2c2.5,1.4,5.4,2.2,8.4,2.3c-5-3.3-8.3-8.9-8.3-15.4c0-3.4,1-6.5,2.5-9.2 c9.1,11.1,22.7,18.5,38,19.2c-0.2-1.4-0.4-2.8-0.4-4.3c0.1-10,8.3-18.2,18.5-18.2c5.4,0,10.1,2.2,13.5,5.7c4.3-0.8,8.1-2.3,11.7-4.5 c-1.4,4.3-4.3,7.9-8.1,10.1c3.7-0.4,7.3-1.4,10.6-2.9C98.9,32.3,95.7,35.5,92.2,38.2z'></path></svg></span> </a> <a class='u-social-url' title='instagram' target='_blank' href='https://www.instagram.com/name'><span class='u-icon u-icon-circle u-social-icon u-social-instagram u-text-black u-icon-3'><svg class='u-svg-link' preserveAspectRatio='xMidYMin slice' viewBox='0 0 112 112' style=''><use xlink:href='#svg-70f6'></use></svg><svg class='u-svg-content' viewBox='0 0 112 112' x='0' y='0' id='svg-70f6'><path fill='currentColor' d='M55.9,32.9c-12.8,0-23.2,10.4-23.2,23.2s10.4,23.2,23.2,23.2s23.2-10.4,23.2-23.2S68.7,32.9,55.9,32.9z M55.9,69.4c-7.4,0-13.3-6-13.3-13.3c-0.1-7.4,6-13.3,13.3-13.3s13.3,6,13.3,13.3C69.3,63.5,63.3,69.4,55.9,69.4z'></path><path fill='#FFFFFF' d='M79.7,26.8c-3,0-5.4,2.5-5.4,5.4s2.5,5.4,5.4,5.4c3,0,5.4-2.5,5.4-5.4S82.7,26.8,79.7,26.8z'></path><path fill='currentColor' d='M78.2,11H33.5C21,11,10.8,21.3,10.8,33.7v44.7c0,12.6,10.2,22.8,22.7,22.8h44.7c12.6,0,22.7-10.2,22.7-22.7 V33.7C100.8,21.1,90.6,11,78.2,11z M91,78.4c0,7.1-5.8,12.8-12.8,12.8H33.5c-7.1,0-12.8-5.8-12.8-12.8V33.7 c0-7.1,5.8-12.8,12.8-12.8h44.7c7.1,0,12.8,5.8,12.8,12.8V78.4z'></path></svg></span> </a> </div> </div> </div> </div> </div>");
		$(".append-value"+i).append(filterObj[i].teacherName);
		$(".append-position"+i).append(filterObj[i].position);
	}
	}
  };
  xhttp.open("GET", "http://localhost:8080/api/schedule/skills/search?query="+document.getElementById("txtSearch").value, true);
  xhttp.send();

}