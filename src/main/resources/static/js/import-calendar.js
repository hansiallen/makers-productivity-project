function processICSToARR(source) {
    let calObject = convert(source);
    let calEvents = calObject['VCALENDAR']['0']['VEVENT'];
    let calArr = processEvents(calEvents);
    calArrExcluded = [];
    for (let i = 0; i < calArr.length; i++) {
        if (calArr[i].length == 5) {
            calArrExcluded.push(calArr[i]);
        }
    }
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