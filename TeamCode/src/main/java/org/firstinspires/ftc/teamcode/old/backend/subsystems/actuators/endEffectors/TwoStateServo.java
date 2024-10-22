package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.endEffectors;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;

public class TwoStateServo extends subsystem {
    private Servo servo;
    public final String name;
    private final int openPos, closedPos;
    private boolean isOpen;

    /**
     * Creates a TwoStateServo object
     *
     * @param name      Name of system, used pretty much only for telemetry
     * @param servo     Servo for two state system
     * @param telemetry Telemetry Object
     * @param openPos   Position of open servo
     * @param closedPos Position of closed servo
     */
    public TwoStateServo(String name, Servo servo, Telemetry telemetry, int openPos, int closedPos, boolean startOpen) {
        super(telemetry);
        this.name = name;
        this.servo = servo;
        this.openPos = openPos;
        this.closedPos = closedPos;
        this.isOpen = startOpen;
        run();
    }

    /**
     * Opens the claw or sets the servo to the open position.
     */
    public void open() {
        isOpen = true;
        run();
    }

    /**
     * Closes the claw or sets the servo to the closed position.
     */
    public void close() {
        isOpen = false;
        run();
    }

    /**
     * Toggles the state of the servo between open and closed.
     * If the servo is currently open, it will be closed, and vice versa.
     */
    public void toggle() {
        isOpen = !isOpen;
        run();
    }

    /**
     * Runs the servo by setting its position based on the current state.
     * If the claw is open, it will move to the open position.
     * If the claw is closed, it will move to the closed position.
     */
    private void run() {
        if (isOpen) {
            servo.setPosition(openPos);
        } else {
            servo.setPosition(closedPos);
        }
    }
}
