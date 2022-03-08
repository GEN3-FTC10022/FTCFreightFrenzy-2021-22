package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.OpModes.VisionPipeline;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Utility.Subsystem;
@Autonomous(name = "RedLeftSide")

public class RedLeftSide extends LinearOpMode {
    OpenCvWebcam webcam;

    @Override
    public void runOpMode()
    {
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Drivetrain.AutoInitialize();
        Intake.initialize();
        
        waitForStart();
        
     
        
        
        
        // parck red and blue near polls 
        Intake.position(2);
        Drivetrain.automove(28,270);
        Drivetrain.automove(5,0);
        sleep(100);
        Drivetrain.automove(28,270);
        Drivetrain.automove(5,0);
        sleep(100);
        Drivetrain.automove(32,270);
        Drivetrain.automove(5,0);
        sleep(100);
        Drivetrain.automove(32,270);
        sleep(100);
        Drivetrain.automove(10,180);
        
        
        
        
        
    }
        
        
    
    
    
    

    // todo: write your code here
}