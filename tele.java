package org.firstinspires.ftc.teamcode;
import java.io.*;
import java.lang.Thread;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystem;
import org.firstinspires.ftc.teamcode.Intake;

@TeleOp(name="tele", group="Linear Opmode")

public class tele extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
  

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Intake.initialize();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
 
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y/2;
            double x = gamepad1.left_stick_x/2;
            double rx  = gamepad1.right_stick_x/2;
            double crawmove = gamepad1.right_stick_y;
            // Changed t to right stick button
            
            Drivetrain.move(y,x,rx);
           
            if (gamepad1.x == true){
                Intake.position(1);
            }
             if (gamepad1.a == true){
                Intake.position(2);
            }
            if (gamepad1.b == true){
                Intake.position(3);
            }
            if (gamepad1.y == true){
                Intake.position(0);
            }
            if (gamepad1.right_trigger >0){
                Intake.open();
            }
            if (gamepad1.left_trigger > 0){
                Intake.close();
            }
            
            
           
        }
    }
}
