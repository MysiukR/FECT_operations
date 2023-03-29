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
	document.getElementById("schedule").innerHTML = obj[0].dayOfWeek + "<br>" + obj[0]
	.subjectName;
});

});