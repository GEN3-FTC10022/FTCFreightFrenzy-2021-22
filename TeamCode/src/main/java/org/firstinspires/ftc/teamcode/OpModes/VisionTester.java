package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.teamcode.Utility.Subsystem;

@TeleOp
public class VisionTester extends LinearOpMode {

    boolean hasSaved = false;

    @Override
    public void runOpMode() {

        Subsystem.initialize(hardwareMap, telemetry);
        Vision.initialize();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            telemetry.setAutoClear(true);
            telemetry.update();

            if(!hasSaved)
                Vision.scanDuck(true, true);
            else
                Vision.scanDuck(false, true);

            sleep(1000);
        }
    }
}