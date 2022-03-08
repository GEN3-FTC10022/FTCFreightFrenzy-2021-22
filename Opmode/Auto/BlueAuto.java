package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Utility.Subsystem;

@Autonomous(name = "BLUESIDE")
public class BlueAuto extends LinearOpMode {

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

        
        
        
        
        Intake.position(3);
        sleep(1500);
        
    }
}