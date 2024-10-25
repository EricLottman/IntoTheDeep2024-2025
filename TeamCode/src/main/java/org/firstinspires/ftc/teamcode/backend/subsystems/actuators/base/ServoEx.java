package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * An extension of the {@link Servo} class that adds support for servos with defined
 * angular ranges in degrees
 *
 * @see Servo
 */
public class ServoEx extends Servo {
    private int degrees; // The maximum range of the servo in degrees

    /**
     * Constructs a {@code ServoEx} object and initializes the hardware reference
     * This is intended for servos that are configured with an angular range
     *
     * @param name      The name of the servo as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode, used to map the servo
     * @param telemetry The {@link Telemetry} object to display runtime information
     * @param degrees   The range of the servo in degrees (e.g., 180 degrees)
     */
    public ServoEx(String name, HardwareMap hwMap, Telemetry telemetry, int degrees) {
        super(name, hwMap, telemetry);
        this.degrees = degrees;
        servo.scaleRange(0, degrees);
    }

    /**
     * Sets the servo's position based on the degree input
     * The position is clamped between 0 and the maximum range of the servo
     *
     * @param degrees The target position in degrees, clamped between 0 and the specified range
     */
    @Override
    public void setPosition(double degrees) {
        servo.setPosition(degrees > this.degrees ? this.degrees : (degrees < 0 ? 0 : degrees));
    }
}
