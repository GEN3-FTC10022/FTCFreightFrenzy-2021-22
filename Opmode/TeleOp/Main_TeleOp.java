package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Utility.Subsystem;

@TeleOp
public class Main_TeleOp extends LinearOpMode {
    double rx =0;
    double y = 0;
    double x = 0;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Intake.initialize();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            y = (gamepad1.left_stick_y /2)*1.2;
            rx  = (-gamepad1.right_stick_x/2)*1.2;
            x = -gamepad1.left_stick_x *1.2 ;
            
            Drivetrain.move(y,x,rx);
            
            //Arm
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
            //Claw
            
            if (gamepad1.right_trigger >0){
                Intake.close();
            }
            if (gamepad1.left_trigger > 0){
                Intake.open();
            }
            
            //Wheel
            if((gamepad1.right_bumper && gamepad1.left_bumper) || (!gamepad1.right_bumper && !gamepad1.left_bumper)){
                Intake.canrun(0);
            }else if(gamepad1.right_bumper){
                Intake.canrun(-0.25);
            }else if(gamepad1.left_bumper){
                Intake.canrun(0.25);
            }
        }
    }
}
