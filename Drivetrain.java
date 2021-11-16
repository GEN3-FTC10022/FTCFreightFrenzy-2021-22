package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
import org.firstinspires.ftc.teamcode.Subsystem;

public abstract class Drivetrain extends Subsystem{
  public static DcMotor FlDrive, FrDrive, BlDrive, BrDrive, Craw;
  
  public static Servo servo0;
  public static void initialize(boolean isAuto){
      FlDrive = hm.get(DcMotor.class, "Fl");
      FrDrive = hm.get(DcMotor.class, "Fr");
      BlDrive = hm.get(DcMotor.class, "Bl");
      BrDrive = hm.get(DcMotor.class, "Br");
      FlDrive.setDirection(DcMotor.Direction.REVERSE);
      BlDrive.setDirection(DcMotor.Direction.REVERSE);
      
  }
  
  
    
}
