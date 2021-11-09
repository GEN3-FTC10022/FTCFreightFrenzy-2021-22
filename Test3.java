package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "Single motor test")
public class Test3 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FlDrive = null;
    private DcMotor FrDrive = null;
    private DcMotor BlDrive = null;
    private DcMotor BrDrive = null;
    private DcMotor Craw = null;
    private Servo servo0 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FlDrive  = hardwareMap.get(DcMotor.class, "Fl");
        FrDrive = hardwareMap.get(DcMotor.class, "Fr");
        BlDrive  = hardwareMap.get(DcMotor.class, "Bl");
        BrDrive = hardwareMap.get(DcMotor.class, "Br");
        Craw = hardwareMap.get(DcMotor.class, "motor4");
        servo0 = hardwareMap.get(Servo.class, "Servo");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FlDrive.setDirection(DcMotor.Direction.REVERSE);
        BrDrive.setDirection(DcMotor.Direction.FORWARD);
        FrDrive.setDirection(DcMotor.Direction.FORWARD);
        BlDrive.setDirection(DcMotor.Direction.REVERSE);
        FrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // set 0 power behavior for Craw
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Craw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //end
        //FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        // Wait for the game to start (driver presses PLAY)
       
        // run until the end of the match (driver presses STOP)
        
        
        FrDrive.setTargetPosition(FrDrive.getCurrentPosition()+ 10000);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //FrDrive.setPower(1);
    
        telemetry.addLine("Check for isBusy()" );
        
        while(FrDrive.isBusy()){
            
            FrDrive.setPower(0.5);
            
            telemetry.addData("Motors", FrDrive.getCurrentPosition() ); 
             telemetry.update();
            
        }
        telemetry.addLine("No longer busy; set power 0" );
        FrDrive.setPower(0.0);
        telemetry.update();


            // Setup a variable for each drive wheel to save power level for telemetry
            
        //}
    }
    
}
