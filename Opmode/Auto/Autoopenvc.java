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
@Autonomous(name = "Autoopenvc")

public class Autoopenvc extends LinearOpMode {
    OpenCvWebcam webcam;

    @Override
    public void runOpMode()
    {
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Drivetrain.AutoInitialize();
        Intake.initialize();
        int position = 0;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);

       
        VisionPipeline detector = new VisionPipeline(telemetry);
        webcam.setPipeline(detector);
      
        
        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened(){
                  webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode){
            }
        });

        telemetry.addLine("Waiting for start");
        telemetry.update();
        Intake.close();

        waitForStart();
        sleep(1000);
        // tells the position of the cap stone 
        for(int i = 0; i < 8; i++){
            long Left = VisionPipeline.getLeftLocation();
            long Center = VisionPipeline.getCenterLocation();
            long Right = VisionPipeline.getRightLocation();
            if(Center >= Left){
                if (Center> Right){
                    position = 2;
                }
                else{
                    position = 3;
                }
            }
            else{
                if ( Left > Right){
                    position = 1;
                }
                else {
                    position = 3;
                }

            } 
            
            telemetry.addData("position", position);
            telemetry.update();
            if(gamepad1.a)
            {
                
                webcam.stopStreaming();
                webcam.closeCameraDevice();
            }

           
            sleep(100);
        }
        Intake.position(position);
        Drivetrain.automove(35,90);
        sleep(1000);
        Drivetrain.automove(10,0);
        sleep(1000);
        Drivetrain.automove(30,180);
        sleep(1000);
        Intake.open();
        sleep(1000);
        Drivetrain.automove(30,0);
        sleep(1000);
        
        Drivetrain.automove(10, 180);
        sleep(1000);
        Drivetrain.SetMotorPowerRotate(10,90);
        sleep(1000);
        Drivetrain.automove(48, 180);
        sleep(1000);
        Drivetrain.SetMotorPowerRotate(-10,90);
        sleep(1000);
        Drivetrain.automove(20, 180);
        
        
        
      
        
        
        
        
        
        
    }
        
        
    
    
    
    

    // todo: write your code here
}