package org.firstinspires.ftc.teamcode;
import java.lang.Thread;
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
    public static int zero;
    public static void initialize(){
      clawMotor = hm.get(DcMotor.class, "motor4");
      clawServo = hm.get(Servo.class, "Servo");
     // clawMotor.setDirection(DcMotor.Direction.REVERSE);
      clawMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
      
      clawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      zero = clawMotor.getCurrentPosition();
      
      
    } 
    public static void run(int X){
      double y = 0.5;
      if (  X > clawMotor.getCurrentPosition()){
        y= 0.1;
      }
      clawMotor.setTargetPosition(X);
      clawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      clawMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      while (clawMotor.isBusy()){
        clawMotor.setPower(y);
        //sleep(100);
     //   tm.addData("erfg");
        tm.addData("nu", zero);
        tm.addData("cu",clawMotor.getCurrentPosition());
        tm.addData("tr", X);
        tm.update();
      }
     // clawMotor.setPower(0.0);
    }
    
    public static void position(int x ){
      switch(x){
        
        case 0:
          run(50);
          break;
        case 1:
          run(100);
          break;
        case 2:
          run(175);
          break;
          
        case 3:
          run(300);
          break;
      }
    }
    public static void open(){
      clawServo.setPosition(0.4);
    }
    public static void close(){
      clawServo.setPosition(1);
    }
    





}
