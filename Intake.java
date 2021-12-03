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
public abstract  class Intake extends Subsystem{
    public static DcMotor clawMotor;
    public static Servo clawServo;
    
    public static void initialize(){
      clawMotor = hm.get(DcMotor.class, "motor4");
      clawServo = hm.get(Servo.class, "Servo");
     // clawMotor.setDirection(DcMotor.Direction.REVERSE);
      clawMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  
      clawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      public int zero = clawMotor.getCurrentPosition();
      
    } 
    public static void run(){
      clawMotor.setTargetPosition(clawMotor.getCurrentPosition() + 100);
    
      clawMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      while (clawMotor.isBusy()){
        clawMotor.setPower(0.5);
        tm.addData("number",clawMotor.getCurrentPosition());
        tm.addData("target", clawMotor.getTargetPosition());
        tm.update();
        
      }
      //clawMotor.setPower(0.0);
     
      
    }




    // todo: write your code here
}
