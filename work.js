// script.js

const video = document.getElementById('video');
const canvas = document.getElementById('canvas');
const captureButton = document.getElementById('capture');
const sendButton = document.getElementById('send');

// Start the camera
navigator.mediaDevices.getUserMedia({ video: true })
  .then((stream) => {
    video.srcObject = stream;
  })
  .catch((err) => {
    console.error("Error accessing the camera: ", err);
  });

// Capture the photo
captureButton.addEventListener('click', () => {
  const context = canvas.getContext('2d');
  canvas.width = video.videoWidth;
  canvas.height = video.videoHeight;
  context.drawImage(video, 0, 0, canvas.width, canvas.height);
  sendButton.style.display = 'inline-block';
});

// Send the photo
sendButton.addEventListener('click', () => {
  canvas.toBlob((blob) => {
    const formData = new FormData();
    formData.append('photo', blob, 'photo.png');

    fetch('http://localhost:8081/api/camera', {
      method: 'POST',
      body: formData,
    })
    .then((response) => {
      if (response.ok) {
        alert('Photo sent successfully!');
      } else {
        alert('Failed to send photo.');
      }
    })
    .catch((error) => {
      console.error('Error sending the photo: ', error);
    });
  }, 'image/png');
});

document.getElementById("compareButton").addEventListener("click", () => {
    // Show a loading message
    const resultDiv = document.getElementById("result");
    resultDiv.textContent = "Comparing images, please wait...";

    // Send a GET request to the compare endpoint
    fetch("http://localhost:8081/api/compare", {
      method:'GET',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server responded with status: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            // Display the response from the server
            resultDiv.textContent = data;
        })
        .catch(error => {
            // Handle any errors
            console.error("Error:", error);
            resultDiv.textContent = "An error occurred while comparing images.";
        });
});


// Upload system starts here

document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const fileInput = document.getElementById('fileInput');
    const formData = new FormData();

    // Append the selected file to the FormData object
    formData.append('file', fileInput.files[0]);

    // Send the file to the server using fetch
    fetch('http://localhost:8081/api/upload', { // Adjust the URL as necessary
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text(); // Assuming the server responds with text
    })
    .then(data => {
        document.getElementById('responseMessage').innerText = data; // Display success message
    })
    .catch((error) => {
        console.error('Error:', error);
        document.getElementById('responseMessage').innerText = 'Error uploading file: ' + error.message; // Display error message
    });

});

// Attendance marking system starts here


        // Function to mark attendance
        function markAttendance() {
            const studentId = document.getElementById('markStudentId').value;

            fetch('http://localhost:8081/api/mark?studentId=' + studentId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                alert("Attendance marked for student ID: " + data.studentId);
            })
            .catch(error => {
                alert("Error: " + error);
            });
        }

        // Function to get attendance status
        function getAttendance() {
            const studentId = document.getElementById('statusStudentId').value;

            fetch('http://localhost:8081/api/status?studentId=' + studentId,{
              method: 'GET',
            })
            .then(response => response.json())
            .then(data => {
                if (data) {
                    alert("Student ID: " + data.studentId + " attended on: " + data.presentDate);
                } else {
                    alert("No attendance record found for student ID: " + studentId);
                }
            })
            .catch(error => {
                alert("Error: " + error);
            });
        }
