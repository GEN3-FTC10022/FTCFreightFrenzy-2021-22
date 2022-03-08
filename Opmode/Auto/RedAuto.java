package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Utility.Subsystem;

@Autonomous(name = "REDSIDE")
public class RedAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Drivetrain.AutoInitialize();
        Intake.initialize();

        Intake.close();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        //Drivetrain.automove(10, 180);
        //sleep(1000);
        Drivetrain.SetMotorPowerRotate(5,90);
        //sleep(1000);
        //Drivetrain.automove(48, 180);
        //sleep(1000);
        //Drivetrain.SetMotorPowerRotate(-10,90);
        //sleep(1000);
        //Drivetrain.automove(20, 180);
        
        
    }
}
