package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * A wrapper class for the {@link com.qualcomm.robotcore.hardware.Servo} that provides methods
 * to streamline the usage of standard servos
 */
public class Servo extends subsystem {
    protected com.qualcomm.robotcore.hardware.Servo servo; // changed to protected to make a version 2, a ServoEx or NormalizedServo, not set on a name yet

    /**
     * Constructs a {@code Servo} object and initializes the hardware reference
     *
     * @param name      The name of the servo as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode, used to map the servo
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public Servo(String name, HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry);
        servo = hwMap.get(com.qualcomm.robotcore.hardware.Servo.class, name);
    }

    /**
     * Sets the position of the servo
     * The target position will be clamped between 0 and 1
     *
     * @param targetPosition The target position for the servo, as a double between 0.0 and 1.0
     */
    public void setPosition(double targetPosition) {
        servo.setPosition(targetPosition > 1 ? 1 : (targetPosition < 0 ? 0 : targetPosition));
    }

    public double getPosition() {
        return servo.getPosition();
    }
}