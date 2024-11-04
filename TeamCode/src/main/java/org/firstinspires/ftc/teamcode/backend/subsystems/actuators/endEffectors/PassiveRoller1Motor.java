package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.endEffectors;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

/**
 * The PassiveRoller1Motor class controls a passive roller mechanism using a single motor.
 * It provides functionality to roll the mechanism forward, backward, or disengage it.
 */
public class PassiveRoller1Motor extends subsystem {
    private Motor motor;
    public final String name;
    private double rollerSpeed;

    /**
     * Enum representing the control directions for the roller mechanism:
     * FORWARD, BACKWARD, or DISENGAGE (stop the roller).
     */
    enum RollerControl {
        FORWARD, BACKWARD, DISENGAGE
    }

    /**
     * Constructs a new PassiveRoller1Motor instance.
     *
     * @param name        The name of the roller system, primarily for telemetry and identification.
     * @param motor       The motor object responsible for controlling the roller.
     * @param telemetry   Telemetry object for logging and debugging purposes.
     * @param rollerSpeed The speed at which the roller will operate (range from 0 to 1, typically).
     */
    public PassiveRoller1Motor(String name, Motor motor, Telemetry telemetry, int rollerSpeed) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.rollerSpeed = rollerSpeed;
        motor.runWithoutEncoder();  // Resets or configures the motor for running without encoders.
    }

    /**
     * Sets the speed at which the roller will operate.
     *
     * @param rollerSpeed The desired speed for the roller (between 0 and 1).
     */
    public void setRollerSpeed(double rollerSpeed) {
        this.rollerSpeed = rollerSpeed;
    }

    /**
     * Controls the roller mechanism according to the specified direction.
     * The roller can roll forward, backward, or stop.
     *
     * @param control The desired control direction: FORWARD, BACKWARD, or DISENGAGE.
     * @return A new Action object that, when run, will control the roller based on the specified direction.
     */
    public Action roll(RollerControl control) {
        return packet -> {
            switch (control) {
                case FORWARD:
                    motor.setPower(rollerSpeed);  // Move the roller forward at the specified speed.
                    break;
                case BACKWARD:
                    motor.setPower(-rollerSpeed);  // Move the roller backward at the specified speed.
                    break;
                case DISENGAGE:
                    motor.setPower(0);  // Stop the roller.
                    break;
            }
            packet.put(name + " direction: ", control.toString());  // Log the control direction to telemetry.
            return control != RollerControl.DISENGAGE;  // Return true if the roller is moving, false if disengaged.
        };
    }
}
