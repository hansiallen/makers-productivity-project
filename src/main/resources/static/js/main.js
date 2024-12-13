function initiateQRCodeInterface() {
    document.getElementById('nav-toggle-add-menu').checked = false;
    let popup = document.getElementById('popup-div')
    popup.innerHTML = '<button class="hide-popup-button" onclick="closeQRCodeInterface();"></button><div id="reader" class="cam-view"></div><div id="qr-code-area" class="qr-code-area"></div>';
    popup.classList.remove('hidden');
    generateQRCode();
    loadScannerLibrary();
}

function closeQRCodeInterface() {
    html5QrCode.stop().then((ignore) => {
      // QR Code scanning is stopped.
    }).catch((err) => {
      // Stop failed, handle it.
      alert("QR code scanner stop failed, please reload page");
    });
    let popup = document.getElementById('popup-div');
    popup.innerHTML = '';
    popup.classList.add('hidden');
}

var QRGenerateScriptLoaded = false;

function generateQRCode() {
    if (!QRScriptLoaded) {
        loadScript("/js/qr-generate.js");
    } else {
        fetchIdAndGenerateQRCode();
    }
}

function fetchIdAndGenerateQRCode() {

    // create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // set up the request
    xhr.open('GET', '/get-share-code');
    xhr.withCredentials = true;

    // handle the response
    xhr.onreadystatechange = function() {
        // parse the JSON response
        var response = JSON.parse(xhr.responseText);

        var qrcode = new QRCode("qr-code-area", {
            text: "https://networks.hansiallen.me/CAR/" + response,
            width: 200,
            height: 200,
            colorDark : "#000000",
            colorLight : "#ffffff",
            correctLevel : QRCode.CorrectLevel.M
        });
    }

    xhr.send();
}

QRScriptLoaded = false;

function loadScannerLibrary() {
    if (!QRScriptLoaded) {
        QRScriptLoaded = true;
        loadScript('https://unpkg.com/html5-qrcode');
    } else {
        showScannerInterface();
    }
}

var html5QrCode;

function showScannerInterface() {
    let height = innerHeight - 384;
    let width = Math.floor(innerWidth * 0.8) - 40;

    console.log(width);
    console.log(height);

    if (width < 432) {
        width = 432;
    }

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

    html5QrCode = new Html5Qrcode("reader");
    html5QrCode.start({ facingMode: "environment" }, config, onScanSuccess);
}

function onScanSuccess(decodedText, decodedResult) {
  // handle the scanned code as you like, for example:
  console.log(`Code matched = ${decodedText}`, decodedResult);
  console.log(decodedText.slice(35))
  addContact(decodedText.slice(35));
}

// Not currently in use, as from pre-built scanner (switched to custom)
function onScanFailure(error) {
  // handle scan failure, usually better to ignore and keep scanning.
  // for example:
  console.warn(`Code scan error = ${error}`);
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
    xhr.open('GET', '/contact/add/json/' + code);
    xhr.withCredentials = true;

    // handle the response
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // parse the JSON response
                var response = JSON.parse(xhr.responseText);

                // check if response is valid
                if (response.success) {
                    document.getElementById('popup-div').innerHTML += '<div class="success-div"><p>Successfully added contact! Redirecting...</p></div>';
                    console.log("valid!" + response.id);
                    html5QrCode.stop();
                    setTimeout(() => {
                        window.location.href = '/profile/' + response.id;
                    }, "3000");
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
        if (url == 'https://unpkg.com/html5-qrcode') {
            showScannerInterface();
        } else if (url == '/js/qr-generate.js') {
            fetchIdAndGenerateQRCode();
        }
    };

    script.onerror = function() {
        console.error(`Failed to load script from: ${url}`);
    };
}

