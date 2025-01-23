Face Detection and Attendance System

Overview

This project implements a Face Detection and Attendance System using Java, Spring Boot, and OpenCV. The application allows users to upload images, capture images from a camera, compare faces using histogram-based image matching, and mark student attendance. It also includes REST APIs to perform these operations.

Features

Mark Attendance:

Endpoint to save student attendance details based on their ID and the current date.

Check Attendance Status:

Retrieve a student's attendance details by their ID.

Image Upload:

Upload images to a server for use in face comparison.

Capture Images:

Save images captured from a camera.

Face Comparison:

Compare the captured image against the uploaded images to identify matching faces.

Histogram-Based Face Comparison Algorithm:

Uses OpenCV to process and compare images based on their histograms in HSV color space.

Prerequisites

Java 8 or later

Maven (for dependency management)

OpenCV (configured and added to the project)

Spring Boot

Project Structure

Face_detection
└── src
    └── main
        └── java
            └── com.Face_detection.Face_detection
                └── face_controller.java
                └── Model
                    └── Student.java
                └── Repository
                    └── StudentRepo.java
        └── resources
            └── application.properties

API Endpoints

1. Mark Attendance

URL: /api/mark

Method: POST

Parameters:

studentId (String)

Description: Saves the attendance of a student with their ID and the current date.

2. Check Attendance Status

URL: /api/status

Method: GET

Parameters:

studentId (String)

Description: Retrieves attendance details for a student using their ID.

3. Upload Image

URL: /api/upload

Method: POST

Parameters:

file (MultipartFile)

Description: Uploads an image to the server for face comparison.

4. Capture Image

URL: /api/camera

Method: POST

Parameters:

photo (MultipartFile)

Description: Saves a captured image from a camera to the server.

5. Compare Faces

URL: /api/compare

Method: GET

Description: Compares the captured image with all uploaded images to identify a match. Returns the best match if found.

Technologies Used

Java: Core programming language.

Spring Boot: Backend framework for REST API implementation.

OpenCV: For image processing and histogram-based face comparison.

Maven: Build and dependency management.

File Locations

Base Path for Uploaded Images: C:/Users/Darshan G N/Documents/project_images/

Captured Image Path: C:/Users/Darshan G N/IdeaProjects/Face_detection (1)/Face_detection/src/main/java/com/Face_detection/Face_detection/image1.jpg

Setup Instructions

Clone the Repository:

git clone <repository-url>

Install Dependencies:
Ensure Maven is installed and run:

mvn clean install

Add OpenCV Library:

Download OpenCV.

Add the native library to your project.

Ensure System.loadLibrary(Core.NATIVE_LIBRARY_NAME); is uncommented in the code.

Configure Paths:

Update BASE_PATH and UPLOAD_DIR in the face_controller.java file to desired directories.

Run the Application:

mvn spring-boot:run

Access the Application:
Use Postman or your frontend to interact with the APIs.

Improvements and Suggestions

Dynamic Paths: Replace hardcoded file paths with environment variables or configuration files.

Face Recognition: Enhance the face comparison logic using pre-trained models (e.g., FaceNet or Dlib).

Error Handling: Centralize error handling using @ControllerAdvice.

Database Indexing: Optimize queries for findByStudentId in StudentRepo.

Acknowledgments

OpenCV documentation for image processing techniques.

Spring Boot for simplifying backend development.

Thank you for using the Face Detection and Attendance System! Feel free to contribute or raise issues for improvements.

