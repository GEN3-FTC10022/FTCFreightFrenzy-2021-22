package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

public abstract class Subsystem {

    // Definitely not a pokemon reference
    public static HardwareMap hm;
    public static Telemetry tm;

   
    public static void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        hm = hardwareMap;
        tm = telemetry;

        tm.addLine("Subsystem Hardware Map and Telemetry initialized");
        tm.update();
    }

    /**
     * Sleeps for the given amount of milliseconds, or until the thread is interrupted. This is
     * simple shorthand for the operating-system-provided {@link Thread#sleep(long) sleep()} method.
     *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
     * @param milliseconds amount of time to sleep, in milliseconds
     * @see Thread#sleep(long)
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
