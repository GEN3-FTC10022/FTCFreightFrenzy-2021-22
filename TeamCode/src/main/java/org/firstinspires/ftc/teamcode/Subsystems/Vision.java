package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Utility.Subsystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Bitmap.createBitmap;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public abstract class Vision extends Subsystem {

    // IMPORTANT: If you are using a USB WebCam, camera choice "BACK" and phone portrait "false"
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    private static final String VUFORIA_KEY = "AaMuRa7/////AAABmeeXefeDrEfGtTjiMIEuO2djgL8Uz6M9/NrJ" +
            "CrNousZ9V7tnau7MP3q5eACYGf+HgjNwjsOkV8ERj5yJglYfVjm3W9NBeAEAP18/1TMnFvSY6+dalmccEnnbag" +
            "eBAPAVMBLk5OLCA35uka2sjuLb37/rdMPNJGmSqklqcthb1NuxWzpVe7BZcf2YODtUPWnTHKi5t5s6XKQA5p4T" +
            "u6x73Mha8a6jN7hv/pnvneUoG0N5Eih6gZ1sSXKcGfpqjf1npkJUb4AcMoqYE0DE31kUk+V/N2hjNsg9mQSGw2" +
            "TmXG7Iq15ugKdyFwzgpWf6IueyoTKkwOczEiGALV2lObz+fyFLob4rq6HtpkCpL4gkh4xy";

    // Class Members
    private static VuforiaLocalizer vuforia;

    private static int cameraMonitorViewId;
    private static VuforiaLocalizer.Parameters parameters;
    private static WebcamName webcamName;
    private static final String HM_WEBCAM = "Webcam";

    private static Image rgbImage;
    private static VuforiaLocalizer.CloseableFrame closeableFrame;
    private static Bitmap bitmap;
    private static Bitmap croppedBitmap;

    public static int duckPosition;

    /**
     * Configures the hardware map and initializes vuforia parameters.
     */
    public static void initialize() {

        duckPosition = 1;

        webcamName = null;
        rgbImage = null;
        closeableFrame = null;
        bitmap = null;
        croppedBitmap = null;

        // Hardware Map
        webcamName = hm.get(WebcamName.class, HM_WEBCAM);

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         * Note: Preview is shown on the DS phone.
         */
        cameraMonitorViewId = hm.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hm.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // parameters = new VuforiaLocalizer.Parameters();

        parameters.cameraName = webcamName;
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.useExtendedTracking = false; // Make sure extended tracking is disabled.

        vuforia = ClassFactory.getInstance().createVuforia(parameters); // Instantiate the Vuforia engine

        tm.addLine("Vision initialized");
        tm.update();
    }

    /**
     * Captures a single frame in RGB565 pixel format and saves it.
     */
    private static void captureFrame() {

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); // Enables RGB565 format for image
        vuforia.setFrameQueueCapacity(1); // Store only one frame at a time

        while (rgbImage == null) {
            try {
                closeableFrame = vuforia.getFrameQueue().take();
                long numImages = closeableFrame.getNumImages();

                for (int i = 0; i < numImages; i++) {
                    if (closeableFrame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                        rgbImage = closeableFrame.getImage(i);
                        if (rgbImage != null)
                            break;
                    }
                }

            } catch (InterruptedException ignored) {

            } finally {
                if (closeableFrame != null)
                    closeableFrame.close();
            }
        }
    }

    /**
     * Creates a bitmap and a cropped bitmap from the captured frame and the crop variables.
     */
    private static void setBitmaps() {

        // Create bitmap based on image dimensions
        bitmap = createBitmap(rgbImage.getWidth(), rgbImage.getHeight(), Bitmap.Config.RGB_565);

        // Copy pixels to bitmap
        rgbImage.getPixels().rewind();
        bitmap.copyPixelsFromBuffer(rgbImage.getPixels());

        // Create cropped bitmap to focus
        croppedBitmap = createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    /**
     * Stores a bitmap object in the device storage as a png file.
     *
     * @param name Output file name
     * @param bMap Bitmap object to save
     */
    private static void saveBitmap(String name, Bitmap bMap) {

        // Find directory
        String path = Environment.getExternalStorageDirectory().toString();
        FileOutputStream out = null;

        // Save bitmap to .png file
        try {
            File file = new File(path, name + ".png");
            out = new FileOutputStream(file);
            bMap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks RGB values in a vertical row in the middle of the cropped bitmap to determine the
     * number of rings.
     *
     * @param showPixelData Shows extended information about individual RGB values for each pixel scan.
     */
    private static void scanBitmap(boolean showPixelData) {
        int targetPixels = 0;
        int pixel, r, g, b, total;
        int xPos = bitmap.getWidth() / 2;

        // Set pixel and get RGB values
        pixel = croppedBitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        r = Color.red(pixel);
        g = Color.green(pixel);
        b = Color.blue(pixel);

        // Print per pixel RGB to telemetry
        if (showPixelData) {
            tm.addData("Red", r);
            tm.addData("Green", g);
            tm.addData("Blue", b);
            tm.update();
        }
    }

    /**
     * Checks whether the pixel satisfies the target thresholds for a duck pixel.
     *
     * @param r Amount of Red
     * @param g Amount of Green
     * @param b Amount of Blue
     * @return If the pixel is a duck pixel.
     */
    private static boolean isTargetPixel(int r, int g, int b) {
        int total = r + g + b;
        try {
            if (total > 375) {
                if (r > g + 40 && g > b && b > 70) {
                    tm.addLine("Bright Check:\tSuccessful");
                    tm.addLine();
                    tm.update();
                    return true;
                } else {
                    tm.addLine("Bright Check:\tFailed");
                    tm.addLine();
                    tm.update();
                    return false;
                }
            } else {
                if ((17.5 / 100.0) * (r + g) > b) {
                    tm.addLine("Normal Check:\tSuccessful");
                    tm.addLine();
                    tm.update();
                    return true;
                } else {
                    tm.addLine("Normal Check:\tFailed");
                    tm.addLine();
                    tm.update();
                    return false;
                }
            }

        } catch (Exception e) {
            tm.addLine("Poor lighting condition. Unable to detect pixel.");
            tm.addLine();
            tm.update();
            return false;
        }
    }

    /**
     * Scans the height of the starter stack and updates the number of rings found.
     *
     * @param saveBitmaps   Stores the bitmap and the cropped bitmap as png files in the device storage
     * @param showPixelData Shows extended information about individual RGB values for each pixel scan
     */
    public static void scanDuck(boolean saveBitmaps, boolean showPixelData) {

        tm.setAutoClear(false);

        // Capture frame from camera
        captureFrame();
        tm.addLine("Frame captured");
        tm.addLine();
        tm.update();

        if (rgbImage != null) {

            // Transpose frame into bitmaps
            setBitmaps();
            tm.addLine("Frame converted to bitmaps");
            tm.addLine();
            tm.update();

            // Save bitmaps to .png files
            if (saveBitmaps) {
                saveBitmap("Bitmap", bitmap);
                saveBitmap("CroppedBitmap", croppedBitmap);
                tm.addLine("Bitmaps saved to device");
                tm.addLine();
                tm.update();
            }

            // Scan bitmap for starter stack height
            scanBitmap(showPixelData);
            tm.addLine("Bitmap scan finished");
            tm.update();
        }
    }
}
