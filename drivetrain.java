
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
//import org.firstinspires.ftc.teamcode.Util.Subsystem;

public class Drivetrain {
    private ElapsedTime runtime = new ElapsedTime();
    public static DcMotor FlDrive = null;
    public static DcMotor FrDrive = null;
    public static DcMotor BlDrive = null;
    public static DcMotor BrDrive = null;
    public static DcMotor Craw = null;
    public static Servo servo0 = null;
    
    
    public static void setup(HardwareMap hardwareMap) {
        FlDrive  = hardwareMap.get(DcMotor.class, "Fl");
        FrDrive = hardwareMap.get(DcMotor.class, "Fr");
        BlDrive  = hardwareMap.get(DcMotor.class, "Bl");
        BrDrive = hardwareMap.get(DcMotor.class, "Br");
        Craw = hardwareMap.get(DcMotor.class, "motor4");
        servo0 = hardwareMap.get(Servo.class, "Servo");
        Craw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FlDrive.setDirection(DcMotor.Direction.REVERSE);
        BrDrive.setDirection(DcMotor.Direction.FORWARD);
        FrDrive.setDirection(DcMotor.Direction.FORWARD);
        BlDrive.setDirection(DcMotor.Direction.REVERSE);
    }
    public static void move(double y, double x, double rx){
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx), 1);
        double fl = (y + x +rx)/max;
        double bl = (y - x + rx)/max;
        double fr = (y - x -rx)/max;
        double br = (y + x -rx)/max;
        FlDrive.setPower(fl);
        BlDrive.setPower(bl);
        FrDrive.setPower(fr);
        BrDrive.setPower(br);
    }
    public static void intake(boolean open, boolean close, boolean pad, double crawmove){
        
        if (open == true){
            servo0.setPosition(-0.4);
        }
        if(close ==true){ 
            servo0.setPosition(1.0);
        }              
        double power = 1.8;
        if (pad == true){
            power = 0.3;
        }
       // dont set following to high val 
        Craw.setPower(crawmove/power);
    }
    public static void SetMotorPower(double angle, double rotate){
        double rad  = Math.toRadians(angle);
        double x = Math.cos(rad);
        double y = Math.sin(rad);
        move(y, x, rotate);
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
            SetMotorPower(angle,rotate);
        }


    }
    
    
}
