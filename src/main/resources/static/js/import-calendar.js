var calendarArrayFinal = '';

function processICSToARR(source) {
    let calObject = convert(source);
    let calEvents = calObject['VCALENDAR']['0']['VEVENT'];
    let calArr = processEvents(calEvents);
    calArrExcluded = [];
    for (let i = 0; i < calArr.length; i++) {
        if (calArr[i].length == 5) {
            calArrExcluded.push(calArr[i]);
        } else {
            console.log("Excluded 1 event");
        }
    }
    console.log(calArrExcluded);
    calendarArrayFinal = calArrExcluded;
    return calArrExcluded;
}

function processEvents(events) {
    eventsArr = [];
    for (let i = 0; i < events.length; i++) {
        let event = [];
        let eventObject = events[i];
        if (eventObject.hasOwnProperty('DTSTART;VALUE=DATE')) {
            event.push(formatDate(eventObject['DTSTART;VALUE=DATE']));
            event.push('00:00:00');
            event.push('23:59:59');
        } else {
            if (eventObject.hasOwnProperty('DTSTART')) {
                event.push(formatFullDTDate(eventObject['DTSTART']));
                event.push(formatTime(eventObject['DTSTART']));
                event.push(formatTime(eventObject['DTEND']));
            } else {
                console.log("discarded")
            }
        }
        if (eventObject.hasOwnProperty('SUMMARY')) {
            event.push(eventObject['SUMMARY']);
        } else {
            event.push('(No Title)');
        }
        if (eventObject.hasOwnProperty('DESCRIPTION')) {
            event.push(eventObject['DESCRIPTION']);
        } else {
            event.push('No description given...');
        }
        eventsArr.push(event);
    }
    return eventsArr;
}

function formatDate(dtstart) {
  // Extract the year, month, and day from the YYYYMMDD string
  const year = dtstart.substring(0, 4);
  const month = dtstart.substring(4, 6);
  const day = dtstart.substring(6, 8);

  // Format as d-m-YYYY (remove leading zeros from day and month)
  return `${parseInt(day)}-${parseInt(month)}-${year}`;
}

// Function to extract the date from a DTSTART value (in YYYYMMDD format)
function formatFullDTDate(dtstart) {
  const date = dtstart.substring(0, 8); // Extract YYYYMMDD
  const year = date.substring(0, 4);
  const month = date.substring(4, 6);
  const day = date.substring(6, 8);

  // Return the date in d-m-YYYY format (day-month-year)
  return `${parseInt(day)}-${parseInt(month)}-${year}`;
}

// Function to extract the time from a DTSTART or DTEND value (in HHMMSS format)
function formatTime(dtvalue) {
  const time = dtvalue.substring(9, 15); // Extract HHMMSS

  const hours = time.substring(0, 2);
  const minutes = time.substring(2, 4);
  const seconds = time.substring(4, 6);

  // Return time in HH:mm:ss format
  return `${hours}:${minutes}:${seconds}`;
}

function sendEventsToAPI(events) {
    for (var i = 0; i < calendarArrayFinal.length; i++) {
        var event = calendarArrayFinal[i];
        var dateStr = event[0];
        var startTimeStr = event[1];
        var endTimeStr = event[2];
        var title = event[3];
        var description = event[4];

        var dateParts = dateStr.split('-');
        var day = parseInt(dateParts[0]);
        var month = parseInt(dateParts[1]) - 1;
        var year = parseInt(dateParts[2]);
        var date = new Date(year, month, day);

        var startTimeParts = startTimeStr.split(':');
        var startHours = parseInt(startTimeParts[0]);
        var startMinutes = parseInt(startTimeParts[1]);
        var startTime = new Date(date);
        startTime.setHours(startHours, startMinutes, 0, 0);

        var endTimeParts = endTimeStr.split(':');
        var endHours = parseInt(endTimeParts[0]);
        var endMinutes = parseInt(endTimeParts[1]);
        var endTime = new Date(date);
        endTime.setHours(endHours, endMinutes, 0, 0);

        var payload = "title=" + encodeURIComponent(title) +
                      "&description=" + encodeURIComponent(description) +
                      "&date=" + encodeURIComponent(date.toISOString().split('T')[0]) +
                      "&startTime=" + encodeURIComponent(startTime.toISOString().split('T')[1].split('.')[0]) +
                      "&endTime=" + encodeURIComponent(endTime.toISOString().split('T')[1].split('.')[0]);

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/event/create", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function() {
            if (xhr.status === 200) {
                console.log("Event created successfully: " + title);
            } else {
                console.error("Failed to create event: " + title);
            }
        };

        xhr.onerror = function() {
            console.error("Error while sending event to API");
        };

        xhr.send(payload);
    }
    alert('Successfully imported calendar events from file!');
}

function setICSFormContents(arr) {
    document.getElementById('ics-array-text-box').value = arr;
    document.getElementById('ics-form').classList.remove('hidden');
}