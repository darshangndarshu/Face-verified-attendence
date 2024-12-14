package com.Face_detection.Face_detection;

import com.Face_detection.Face_detection.Model.Student;
import com.Face_detection.Face_detection.Repository.StudentRepo;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500/")  // Allow CORS for your frontend
public class face_controller {
//    static {
//        // Load the native OpenCV library
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }

    @Autowired
    private StudentRepo studentRepo;

    // Endpoint to save student attendance
    @PostMapping("/mark")
    public Student markAttendance(@RequestParam String studentId) {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setPresentDate(LocalDate.now());
        return studentRepo.save(student);
    }
// check the student information

    @GetMapping("/status")
    public Student getAttendance(@RequestParam String studentId) {
        Optional<Student> attendance = StudentRepo.findByStudentId(studentId); // findByStudentId
        return attendance.orElse(null);
    }
// uploaded image limit start here

    private static final String BASE_PATH = "add_your_uploaded_images_folder"; // Adjust to your desired path


        @PostMapping("/upload")
        public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
            }

            try {
                // Create the destination file path

                File destinationFile = new File(BASE_PATH + file.getOriginalFilename());

                // Save the file to the specified directory

                file.transferTo(destinationFile);
                return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error uploading file: " + e.getMessage());
            }
        }
    // uploaded image limit end here

    //  captured image processing limit start here

    private static final String UPLOAD_DIR = "add_your_captured_image_file";

    public face_controller() {
        // Create the upload directory if it does not exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @PostMapping("/camera")
    public ResponseEntity<String> uploadPhoto(@RequestParam("photo") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
        }

        try {
            // Save the uploaded file to the server
            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok("Photo uploaded successfully: " + filePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving the photo.");
        }
    }
    // captured image limit end here

    // comparison call limit start here

    @GetMapping("/compare")
    public ResponseEntity<String> compareImages() {
        // Load the captured image
        Mat capturedImage = Imgcodecs.imread(UPLOAD_DIR);
        if (capturedImage.empty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Captured image not found or could not be loaded.");
        }

        // Iterate through uploaded images and find the best match

        File folder = new File(BASE_PATH);
        File[] images = folder.listFiles((_, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

        if (images == null || images.length == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No uploaded images found for comparison.");
        }

        double bestMatchScore = Double.MAX_VALUE;
        String bestMatchFile = null;
        boolean isMatched = false;

        for (File imageFile : Objects.requireNonNull(images)) {
            Mat uploadedImage = Imgcodecs.imread(imageFile.getAbsolutePath());
            double score = compareImagesUsingHistogram(capturedImage, uploadedImage);  // Method call here

            // Consider it a match if the score is above a threshold (e.g., 0.9)
            if (score > 0.9) {
                isMatched = true;
                bestMatchFile = imageFile.getName();
                bestMatchScore = score;
                break; // Stop on the first match
            }
        }

        if (isMatched) {
            return ResponseEntity.ok("Face matched with: " + bestMatchFile + " (Score: " + bestMatchScore + ")");
        } else {
            return ResponseEntity.ok("No matching face found.");
        }
    }


    // comparison call limit end here

    // comparison algorithm start here

        public static double compareImagesUsingHistogram(Mat img1, Mat img2) {
            // Convert images to HSV color space
            Mat hsvImg1 = new Mat();
            Mat hsvImg2 = new Mat();
            Imgproc.cvtColor(img1, hsvImg1, Imgproc.COLOR_BGR2HSV);
            Imgproc.cvtColor(img2, hsvImg2, Imgproc.COLOR_BGR2HSV);

            // Calculate histograms
            Mat histImg1 = new Mat();
            Mat histImg2 = new Mat();
            int[] histSize = {256};
            float[] range = {0, 256};
            Imgproc.calcHist(List.of(hsvImg1), new MatOfInt(0), new Mat(), histImg1, new MatOfInt(histSize), new MatOfFloat(range));
            Imgproc.calcHist(List.of(hsvImg2), new MatOfInt(0), new Mat(), histImg2, new MatOfInt(histSize), new MatOfFloat(range));

            // Normalize histograms
            Core.normalize(histImg1, histImg1, 0, 1, Core.NORM_MINMAX);
            Core.normalize(histImg2, histImg2, 0, 1, Core.NORM_MINMAX);

            // Compare histograms using correlation
            return Imgproc.compareHist(histImg1, histImg2, Imgproc.CV_COMP_CORREL);
        }
    }
 // Algorithm ends here
// This is the end THANK YOU;