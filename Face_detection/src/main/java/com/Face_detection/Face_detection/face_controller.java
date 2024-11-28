package com.Face_detection.Face_detection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class face_controller {
    @GetMapping("/comparePhotos")
    public String homePage() {
        compare_data();
        return "get";
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            // Specify the directory where you want to save the uploaded file
                File destinationFile = new File("C:/Users/Darshan G N/Documents/project_images/" + fileName);
                // Save the file to the specified directory
                file.transferTo(destinationFile);
                return ResponseEntity.ok("File uploaded successfully: " + fileName);
            }
            catch (IOException e) {
                return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
            }
        }



    private void compare_data() {
        //compare data
        class ImageComparison {

            static {
                // Load OpenCV native library
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            }

            public static void main(String[] args) {
                // Step 1: Capture an image from the webcam
                Mat capturedImage = captureImageFromWebcam();

                // Step 2: Load the downloaded image from the file
                List<String> imagePaths = List.of("image1.jpg", "image2.jpg");
                boolean isFound=false;
                for (String path : imagePaths) {
                    Mat baseImage = Imgcodecs.imread(path);
                    if (baseImage.empty()) {
                        System.out.println("Error loading image: " + path);
                    } else {
                        System.out.println("Successfully loaded image: " + path);
                        // Process the image as needed
                    }

                    //String downloadedImagePath = "stu1.jpg"; // Path to the downloaded image

                    Mat downloadedImage = Imgcodecs.imread(path);

                    // Step 3: Compare the two images using histogram comparison
                    double similarity = compareImagesUsingHistogram(capturedImage, downloadedImage);

                    // Print the similarity score
                    System.out.println("Image similarity score (0.0 - completely different, 1.0 - identical): " + similarity);

                    // You can set a threshold for similarity, e.g., if similarity > 0.8, they are similar
                    if (similarity > 0.9) {
                        isFound=true;
                        System.out.println("The images are considered similar.");
                        break;
//            } else {
//                System.out.println("The images are not similar.");
                    }
                }
                if(!isFound)
                    System.out.println("The images are not similar.");
            }



            // Method to capture an image from the webcam
            public static Mat captureImageFromWebcam() {
                VideoCapture webcam = new VideoCapture(0); // 0 is the default camera index

                if (!webcam.isOpened()) {
                    System.out.println("Error: Could not open the webcam.");
                    return null;
                }

                // Capture an image from the webcam
                Mat frame = new Mat();
                if (webcam.read(frame)) {
                    // Save the captured image to a file (optional)
                    Imgcodecs.imwrite("captured-image.jpg", frame);
                    System.out.println("Image captured from the webcam and saved to: captured-image.jpg");
                } else {
                    System.out.println("Error: Could not capture an image from the webcam.");
                }

                // Release the webcam
                webcam.release();

                return frame;
            }

            // Method to compare two images using histogram comparison
            public static double compareImagesUsingHistogram(Mat img1, Mat img2) {
                // Convert images to HSV (Hue-Saturation-Value) color space
                Mat hsvImg1 = new Mat();
                Mat hsvImg2 = new Mat();
                Imgproc.cvtColor(img1, hsvImg1, Imgproc.COLOR_BGR2HSV);
                Imgproc.cvtColor(img2, hsvImg2, Imgproc.COLOR_BGR2HSV);

                // Calculate the histograms for both images
                Mat histImg1 = new Mat();
                Mat histImg2 = new Mat();

                // Histogram parameters
                MatOfInt histSize = new MatOfInt(50); // Number of bins
                MatOfFloat ranges = new MatOfFloat(0f, 256f); // Range of pixel values (0-256)
                MatOfInt channels = new MatOfInt(0); // Channel to compare (0 for hue)

                // Compute the histograms
                Imgproc.calcHist(List.of(hsvImg1), channels, new Mat(), histImg1, histSize, ranges);
                Imgproc.calcHist(List.of(hsvImg2), channels, new Mat(), histImg2, histSize, ranges);

                // Normalize the histograms
                Core.normalize(histImg1, histImg1, 0, 1, Core.NORM_MINMAX, -1, new Mat());
                Core.normalize(histImg2, histImg2, 0, 1, Core.NORM_MINMAX, -1, new Mat());

                // Compare the histograms using correlation (you can choose other methods like Bhattacharyya, Chi-square, etc.)
                double similarity = Imgproc.compareHist(histImg1, histImg2, Imgproc.CV_COMP_CORREL);

                return similarity; // Similarity score (closer to 1 means more similar)
            }

        }
         }

    @GetMapping("/register")
    public String register(){

        return "ok ok";
    }
}
