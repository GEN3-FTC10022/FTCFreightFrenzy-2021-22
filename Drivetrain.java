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
  
  
  public static void initialize(){
      FlDrive = hm.get(DcMotor.class, "Fl");
      FrDrive = hm.get(DcMotor.class, "Fr");
      BlDrive = hm.get(DcMotor.class, "Bl");
      BrDrive = hm.get(DcMotor.class, "Br");
      FlDrive.setDirection(DcMotor.Direction.REVERSE);
      BlDrive.setDirection(DcMotor.Direction.REVERSE);
      
  } 
  public static void AutoInitialize(){
      FlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      BlDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      FrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      BrDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
   public static void move(double y, double x, double rx){
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx),1);
        double fl = (y + x +rx)/max;
        double bl = (y - x + rx)/max;
        double fr = (y - x -rx)/max;
        double br = (y + x -rx)/max;
        FlDrive.setPower(fl);
        BlDrive.setPower(bl);
        FrDrive.setPower(fr);
        BrDrive.setPower(br);
    }
   
    public static void SetMotorPowerMove(double angle, double rotate){
        double rad  = Math.toRadians(angle);
        double x = Math.cos(rad);
        double y = Math.sin(rad);
        move(y/2, x/2, rotate);
    }
    public static void SetMotorPowerRotate(int dist, int angle){
    //int dist, double angle,
        FrDrive.setTargetPosition(FrDrive.getCurrentPosition()+ (dist* angle));
        FlDrive.setTargetPosition(FlDrive.getCurrentPosition()- (dist* angle));
        BrDrive.setTargetPosition(BrDrive.getCurrentPosition()+ (dist* angle));
        BlDrive.setTargetPosition(BlDrive.getCurrentPosition()- (dist*angle));
        
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Busy()){
        
           move(0.0,0.0,angle);
        }
    }
    private static boolean Busy() {
        return FlDrive.isBusy() && BlDrive.isBusy() && FrDrive.isBusy() && BrDrive.isBusy();
    }
    public static void SetPosition(int dist, double angle, double rotate){
        FrDrive.setTargetPosition(FrDrive.getCurrentPosition()+ dist);
        FlDrive.setTargetPosition(FlDrive.getCurrentPosition()+ dist);
        BrDrive.setTargetPosition(BrDrive.getCurrentPosition()+ dist);
        BlDrive.setTargetPosition(BlDrive.getCurrentPosition()+ dist);
        
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Busy()){
        
            SetMotorPowerMove(angle, rotate);
            //SetMotorPowerRotate(rotate);
        }


    }
}
