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
@Autonomous(name = "RedRgihtSide")

public class RedRightSide extends LinearOpMode {
    OpenCvWebcam webcam;

    @Override
    public void runOpMode()
    {
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Drivetrain.AutoInitialize();
        Intake.initialize();
        
        Intake.close();
       

        waitForStart();
        
        Intake.position(2);
        sleep(1000);
        Drivetrain.automove(35,270);
        sleep(1000);
        
       
        Intake.open();
        sleep(1000);
        Drivetrain.automove(20,180);
        sleep(1000);
        
        
        
      
        
        
        
        
        
        
    }
        
        
    
    
    
    

    // todo: write your code here
}