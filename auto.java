package org.firstinspires.ftc.teamcode;
import java.io.*;
import java.lang.Thread;
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
import org.firstinspires.ftc.teamcode.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystem;

import org.firstinspires.ftc.teamcode.Intake;
@Autonomous(name = "REDSIDE")
public class auto extends LinearOpMode {
    //Drivetrain test = new Drivetrain();
    //Intake.initialize();
    
    
    
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Subsystem.initialize(hardwareMap, telemetry);
        Drivetrain.initialize();
        Drivetrain.AutoInitialize();
        Intake.initialize();
        Intake.run(175); 
        Drivetrain.SetPosition(100,0.0,0.0);
        Drivetrain.SetMotorPowerRotate(700, -1);
        Drivetrain.SetPosition(3800,0.0,0.0);

        

        
    }
    
}
