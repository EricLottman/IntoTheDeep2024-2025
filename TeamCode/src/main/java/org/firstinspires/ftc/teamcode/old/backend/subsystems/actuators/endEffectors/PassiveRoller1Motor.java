package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.endEffectors;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

/**
 * The PassiveRoller1Motor class controls a passive roller mechanism using a single motor
 * It provides functionality to roll the mechanism forward, backward, or disengage it
 */
public class PassiveRoller1Motor extends subsystem {
    private Motor motor;
    public final String name;
    private final double rollerSpeed;

    /**
     * Enum to define roller control directions: forward, backward, or disengaged
     */
    enum RollerControl {
        FORWARD, BACKWARD, DISENGAGE
    }

    /**
     * Constructs a new PassiveRoller1Motor instance
     *
     * @param name        The name of the roller system
     * @param motor       The Motor object controlling the roller
     * @param telemetry   Telemetry object for logging and debugging
     * @param rollerSpeed The speed at which the roller operates
     */
    public PassiveRoller1Motor(String name, Motor motor, Telemetry telemetry, int rollerSpeed) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.rollerSpeed = rollerSpeed;
        motor.RWE();  // Reset motor encoders or setup (assuming RWE stands for Reset With Encoders).
    }

    /**
     * Controls the roller mechanism based on the given control direction
     *
     * @param control The desired control direction for the roller (FORWARD, BACKWARD, or DISENGAGE)
     */
    public void roll(RollerControl control) {
        switch (control) {
            case FORWARD:
                motor.SP(rollerSpeed);  // Set motor power to rollerSpeed for forward motion.
                break;
            case BACKWARD:
                motor.SP(-rollerSpeed);  // Set motor power to negative rollerSpeed for backward motion.
                break;
            case DISENGAGE:
                motor.SP(0);  // Stop the motor, disengaging the roller.
                break;
        }
    }
}
