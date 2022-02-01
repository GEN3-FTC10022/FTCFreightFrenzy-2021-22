package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utility.Subsystem;

public abstract  class Intake extends Subsystem{
    public static DcMotor clawMotor, can;
    public static Servo clawServo;
    public static int zero;
    public static void initialize(){
        clawMotor = hm.get(DcMotor.class, "motor4");
        clawServo = hm.get(Servo.class, "Servo");
        can = hm.get(DcMotor.class, "can");
        clawMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        clawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        zero = clawMotor.getCurrentPosition();


    }
    public static void run(int X){
        double y = 1.0;
        if (  X < clawMotor.getCurrentPosition()){
            y= 0.1;
        }
        clawMotor.setTargetPosition(X);
        clawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        clawMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        clawMotor.setPower(1.0);
    }

    public static void position(int x ){
        switch(x){

            case 0:
                run(0);
                break;
            case 1:
                run(125);
                break;
            case 2:
                run(250);
                break;

            case 3:
                run(375);
                break;
        }
    }
    public static void open(){
        clawServo.setPosition(0.7);
    }
    public static void close(){
        clawServo.setPosition(1);
    }
    public static void canrun(double x){
        can.setPower(x);
    }
}
