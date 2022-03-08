package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Utility.Subsystem;

public abstract class Drivetrain extends Subsystem{
    public static DcMotor FlDrive, FrDrive, BlDrive, BrDrive, Craw;

    // Constants
    private static final double WHEEL_DIAMETER_INCHES = 4;
    private static final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * Math.PI;
    private static final double TICKS_PER_REV = 537.6;
    private static final double GEAR_REDUCTION = 1;
    private static final double TICKS_PER_INCH = (TICKS_PER_REV * GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_INCHES;
    private static final double INCHES_PER_DEGREE = 70.0/360;

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
        
        tm.addLine("fl: " + fl);
        tm.addLine("bl: " + bl);
        tm.addLine("fr: " + fr);
        tm.addLine("br: " + br);
        
        tm.update();
        Zero();
    }


    public static void automove(int xdist, double angle){
        int dist = (int) (xdist * TICKS_PER_INCH);
        double y = ysin(angle);
        double x = xcos(angle);
        double rx = 0;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx),1);
        double fl = Math.signum((y + x +rx)/max);
        double bl = Math.signum((y - x + rx)/max);
        double fr = Math.signum((y - x -rx)/max);
        double br = Math.signum((y + x -rx)/max);
        FrDrive.setTargetPosition(FrDrive.getCurrentPosition()- (dist* (int)fr));
        FlDrive.setTargetPosition(FlDrive.getCurrentPosition()+ (dist* (int)fl));
        BrDrive.setTargetPosition(BrDrive.getCurrentPosition()+ (dist* (int)br));
        BlDrive.setTargetPosition(BlDrive.getCurrentPosition()- (dist* (int)bl));

        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Busy()){
            FlDrive.setPower(.5);
            BlDrive.setPower(.5);
            FrDrive.setPower(.5);
            BrDrive.setPower(.5);
        }



    }
    public static double xcos(double angle){
        double rad  = Math.toRadians(angle);
        double x = Math.cos(rad);
        return(x);

    }
    public static double ysin(double angle){
        double rad  = Math.toRadians(angle);
        double y = Math.sin(rad);
        return(y);

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
    public static void Zero(){
        FlDrive.setPower(0);
        BlDrive.setPower(0);
        FrDrive.setPower(0);
        BrDrive.setPower(0);
    }


    public static void driveForward(double power, double inches)
    {
        // 1. Stop and reset drive encoders
        FlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // 2. Convert inches to ticks
        int target = (int) (inches * TICKS_PER_INCH);

        // 3. Set target position
        FlDrive.setTargetPosition(target);
        FrDrive.setTargetPosition(target);
        BlDrive.setTargetPosition(target);
        BrDrive.setTargetPosition(target);

        // 4. Set zero power behavior
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // 5. Run to Position
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FlDrive.isBusy() || FrDrive.isBusy()
                || BlDrive.isBusy() || BrDrive.isBusy()) {

            FlDrive.setPower(power);
            FrDrive.setPower(power);
            BlDrive.setPower(power);
            BrDrive.setPower(power);
        }

        // 6. Stop Motors
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FlDrive.setPower(0);
        FrDrive.setPower(0);
        BlDrive.setPower(0);
        BrDrive.setPower(0);

        // 7. Reset Modes
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void driveBackward(double power, double inches)
    {
        // 1. Stop and reset drive encoders
        FlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // 2. Convert inches to ticks
        int target = (int) (inches * TICKS_PER_INCH);

        // 3. Set target position
        FlDrive.setTargetPosition(-target);
        FrDrive.setTargetPosition(-target);
        BlDrive.setTargetPosition(-target);
        BrDrive.setTargetPosition(-target);

        // 4. Set zero power behavior
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // 5. Run to Position
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FlDrive.isBusy() || FrDrive.isBusy()
                || BlDrive.isBusy() || BrDrive.isBusy()) {

            FlDrive.setPower(power);
            FrDrive.setPower(power);
            BlDrive.setPower(power);
            BrDrive.setPower(power);
        }

        // 6. Stop Motors
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FlDrive.setPower(0);
        FrDrive.setPower(0);
        BlDrive.setPower(0);
        BrDrive.setPower(0);

        // 7. Reset Modes
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void strafeRight(double power, double inches)
    {
        // 1. Stop and reset drive encoders
        FlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // 2. Convert inches to ticks
        int target = (int) (inches * TICKS_PER_INCH);

        // 3. Set target position
        FlDrive.setTargetPosition(target);
        FrDrive.setTargetPosition(-target);
        BlDrive.setTargetPosition(-target);
        BrDrive.setTargetPosition(target);

        // 4. Set zero power behavior
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // 5. Run to Position
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FlDrive.isBusy() || FrDrive.isBusy()
                || BlDrive.isBusy() || BrDrive.isBusy()) {

            FlDrive.setPower(power);
            FrDrive.setPower(power);
            BlDrive.setPower(power);
            BrDrive.setPower(power);
        }

        // 6. Stop Motors
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FlDrive.setPower(0);
        FrDrive.setPower(0);
        BlDrive.setPower(0);
        BrDrive.setPower(0);

        // 7. Reset Modes
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void strafeLeft(double power, double inches)
    {
        // 1. Stop and reset drive encoders
        FlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // 2. Convert inches to ticks
        int target = (int) (inches * TICKS_PER_INCH);

        // 3. Set target position
        FlDrive.setTargetPosition(-target);
        FrDrive.setTargetPosition(target);
        BlDrive.setTargetPosition(target);
        BrDrive.setTargetPosition(-target);

        // 4. Set zero power behavior
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // 5. Run to Position
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FlDrive.isBusy() || FrDrive.isBusy()
                || BlDrive.isBusy() || BrDrive.isBusy()) {

            FlDrive.setPower(power);
            FrDrive.setPower(power);
            BlDrive.setPower(power);
            BrDrive.setPower(power);
        }

        // 6. Stop Motors
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FlDrive.setPower(0);
        FrDrive.setPower(0);
        BlDrive.setPower(0);
        BrDrive.setPower(0);

        // 7. Reset Modes
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void rotateRight(double power, double angle)
    {
        // 1. Stop and reset drive encoders
        FlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // 2. Convert inches to ticks
        double inches = angle * INCHES_PER_DEGREE;
        int target = (int) (inches * TICKS_PER_INCH);

        // 3. Set target position
        FlDrive.setTargetPosition(target);
        FrDrive.setTargetPosition(-target);
        BlDrive.setTargetPosition(target);
        BrDrive.setTargetPosition(-target);

        // 4. Set zero power behavior
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // 5. Run to Position
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FlDrive.isBusy() || FrDrive.isBusy()
                || BlDrive.isBusy() || BrDrive.isBusy()) {

            FlDrive.setPower(power);
            FrDrive.setPower(power);
            BlDrive.setPower(power);
            BrDrive.setPower(power);
        }

        // 6. Stop Motors
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FlDrive.setPower(0);
        FrDrive.setPower(0);
        BlDrive.setPower(0);
        BrDrive.setPower(0);

        // 7. Reset Modes
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public static void rotateLeft(double power, double angle)
    {
        // 1. Stop and reset drive encoders
        FlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BlDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // 2. Convert inches to ticks
        double inches = angle * INCHES_PER_DEGREE;
        int target = (int) (inches * TICKS_PER_INCH);

        // 3. Set target position
        FlDrive.setTargetPosition(-target);
        FrDrive.setTargetPosition(target);
        BlDrive.setTargetPosition(-target);
        BrDrive.setTargetPosition(target);

        // 4. Set zero power behavior
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // 5. Run to Position
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FlDrive.isBusy() || FrDrive.isBusy()
                || BlDrive.isBusy() || BrDrive.isBusy()) {

            FlDrive.setPower(power);
            FrDrive.setPower(power);
            BlDrive.setPower(power);
            BrDrive.setPower(power);
        }

        // 6. Stop Motors
        FlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BlDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FlDrive.setPower(0);
        FrDrive.setPower(0);
        BlDrive.setPower(0);
        BrDrive.setPower(0);

        // 7. Reset Modes
        FlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BlDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}