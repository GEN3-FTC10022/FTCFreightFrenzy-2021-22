/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//import org.firstinspires.ftc.teamcode.Drivetrain;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name="TeleOpMode", group="Linear Opmode")

public class TeamTeleOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FlDrive = null;
    private DcMotor FrDrive = null;
    private DcMotor BlDrive = null;
    private DcMotor BrDrive = null;
    private DcMotor Craw = null;
    private Servo servo0 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
    

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FlDrive  = hardwareMap.get(DcMotor.class, "Fl");
        FrDrive = hardwareMap.get(DcMotor.class, "Fr");
        BlDrive  = hardwareMap.get(DcMotor.class, "Bl");
        BrDrive = hardwareMap.get(DcMotor.class, "Br");
        Craw = hardwareMap.get(DcMotor.class, "motor4");
        servo0 = hardwareMap.get(Servo.class, "Servo");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FlDrive.setDirection(DcMotor.Direction.REVERSE);
        BrDrive.setDirection(DcMotor.Direction.FORWARD);
        FrDrive.setDirection(DcMotor.Direction.FORWARD);
        BlDrive.setDirection(DcMotor.Direction.REVERSE);
        // set 0 power behavior for Craw
        Craw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //end
        int tc = 0;
        int tdt = 0;
        boolean kevin = true;
        double y = 0;
        double x = 0;
        double rx = 0;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
           
            leftPower    =  1.0;
            rightPower   =  1.0;
            //Caleb servoPower   =  0.1;
            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            

            if(gamepad1.b){
                y = -gamepad1.left_stick_y/2;
                x = (gamepad1.left_stick_x * 1.1)/2 ;
                rx  = gamepad1.right_stick_x/2;
            }else{
                y = -gamepad1.right_stick_y/2;
                x = (gamepad1.left_stick_x * 1.1)/2 ;
                rx  = gamepad1.right_stick_x/2;
                
                }
            // Changed t to right stick button
            double you = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx), 1);
            double fl = (y + x +rx)/you;
            double bl = (y - x + rx)/you;
            double fr = (y - x -rx)/you;
            double br = (y + x -rx)/you;
            FlDrive.setPower(fl);
            BlDrive.setPower(bl);
            FrDrive.setPower(fr);
            BrDrive.setPower(br);
            
            boolean t = gamepad1.x;
            boolean h = gamepad1.b;
            int rightturn = 1;
            int leftturn = 1;
            String s = "1";
            leftPower    = y/2 ;
            rightPower   = x/2;
            

            if(gamepad1.right_bumper == true)
            {
            servo0.setPosition(0.4);
            }
            if(gamepad1.left_bumper == true)
            {
            servo0.setPosition(3);
            }
            
            
            
            
            /*
            boolean lu = gamepad1.dpad_up;
            boolean ld = gamepad1.dpad_down;
            double c = 0;
            if (lu == true){
                c = -0.6;
            }else if(ld == true){
                c = 0.6;
            }else{
                c = 0;
            }
           // dont set following to high val 
            Craw.setPower( c );
            */
            double c = gamepad1.right_trigger - gamepad1.left_trigger;
            
            Craw.setPower(1.5*c);
            

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f) (%.2f)", leftPower, rightPower, c);
            //telemetry.addData("Status (%.2f)" + rightturn);
            telemetry.update();
        }
    }
    }
