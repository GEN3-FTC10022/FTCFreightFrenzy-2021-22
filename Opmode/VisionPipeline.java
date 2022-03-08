package org.firstinspires.ftc.teamcode.OpModes;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;



public class VisionPipeline extends OpenCvPipeline{
    Telemetry telemetry;
    Mat mat = new Mat();
    static double  centerValue;
    static double  leftValue;
    static double rightValue;
    static final Rect LEFT_ROI = new Rect(
            new Point(0, 100),
            new Point(100, 240));
    static final Rect CENTER_ROI = new Rect(
            new Point(110, 100),
            new Point(210, 240));
    static final Rect RIGHT_ROI = new Rect(
            new Point(220, 100),
            new Point(320, 240));
    static double PERCENT_COLOR_THRESHOLD = 0.4;

    public VisionPipeline(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(23, 50, 70);
        Scalar highHSV = new Scalar(32, 255, 255);

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat center = mat.submat(CENTER_ROI);
        Mat right = mat.submat (RIGHT_ROI);

        leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        centerValue = Core.sumElems(center).val[0] / CENTER_ROI.area() / 255;
        rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;
        left.release();
        center.release();
        right.release();

        

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar colorStone = new Scalar(255, 0, 0);
        Scalar colorSkystone = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, colorStone);
        Imgproc.rectangle(mat, CENTER_ROI, colorStone);
        Imgproc.rectangle(mat, RIGHT_ROI, colorStone);
        return mat;
    }
    
    public static long getCenterLocation() {
        return Math.round(centerValue * 100);
    }
    public static long getLeftLocation() {
        return Math.round(leftValue * 100);
    }
    public static long getRightLocation() {
        return Math.round(rightValue * 100);
    }


}
