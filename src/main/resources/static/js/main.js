function initiateQRCodeInterface() {
    let popup = document.getElementById('popup-div')
    popup.innerHTML = '<div class="hide-popup-button"></div><div id="reader" class="cam-view"></div><div id="qr-code-area" class="qr-code-area"></div>';
    popup.classList.remove('hidden');
    generateQRCode();
    showScannerInterface();
}

function closeQRCodeInterface() {
    let popup = document.getElementById('popup-div');
    popup.innerHTML = '';
    popup.classList.add('hidden');
}

function generateQRCode() {

}

function showScannerInterface() {
    loadScript('https://unpkg.com/html5-qrcode');

    const height = innerHeight;
    const width = innerWidth;

    if (height > width) {
        smallestDimension = width;
    } else {
        smallestDimension = height;
    }

    aspectRatio = width/height;

    if (navigator.userAgent.match(/Android/i) || navigator.userAgent.match(/webOS/i) || navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/BlackBerry/i) || navigator.userAgent.match(/Windows Phone/i)) {
      aspectRatio = height / width;
    }

    const config = { fps: 2, aspectRatio: aspectRatio, qrbox: { width: smallestDimension * 0.8, height: smallestDimension * 0.8 } };


}

function addContactThroughCode() {
    addContact(prompt("Enter the code to add the contact!"));
}

function addContact(code) {
    // check if ID is an integer
    if (!/^[0-9]+$/.test(code)) {
        console.log('Invalid contact ID');
        alert("invalid contact ID");
        return;
    }

    // create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // set up the request
    xhr.open('GET', '/contact/add/' + code);
    xhr.withCredentials = true;

    // handle the response
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // parse the JSON response
                var response = JSON.parse(xhr.responseText);

                // check if response is valid
                if (response.success) {
                    console.log("valid!" + response.id);
                    window.location.href = '/profile/' + response.id;
                } else {
                    // handle invalid response
                  	if (response.error) {
                  	alert(response.error);
                  	console.log(response.error);
                    } else {
                      alert("Profile ID not found.");
                    }
                }
            } else {
                // handle error response
                alert("server error " + xhr.status);
            }
        }
    };

    // Set up an error handler (in case something goes wrong with the request)
    xhr.onerror = function() {
        alert('Network error or request failed');
    };

    // send the request
    xhr.send();
}

function loadScript(url) {
    var script = document.createElement('script');
    script.src = url;
    script.type = 'text/javascript';
    script.async = true;  // Optional: Make sure the script loads asynchronously
    document.head.appendChild(script);

    // Optionally, handle script loading completion
    script.onload = function() {
        console.log(`Script loaded successfully from: ${url}`);
    };

    script.onerror = function() {
        console.error(`Failed to load script from: ${url}`);
    };
}

