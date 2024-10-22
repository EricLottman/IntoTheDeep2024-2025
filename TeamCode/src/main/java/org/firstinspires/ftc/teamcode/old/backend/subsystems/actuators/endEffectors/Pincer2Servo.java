package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.endEffectors;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;

public class Pincer2Servo extends subsystem {
    private Servo servo1, servo2;
    public final String name;
    private final int openPos, closedPos;
    private boolean isOpen;

    /**
     * Creates a Pincer object, we will assume the direction is set the same, and that the motors must travel in opposite directions
     *
     * @param name      Name of system, used pretty much only for telemetry
     * @param servo1    First Servo for the Pincer
     * @param servo2    Second Servo for the Pincer
     * @param telemetry Telemetry Object
     * @param openPos   Position of open servo, use the value for servo1 for this
     * @param closedPos Position of closed servo, use the value for servo1 for this
     */
    public Pincer2Servo(String name, Servo servo1, Servo servo2, Telemetry telemetry, int openPos, int closedPos, boolean startOpen) {
        super(telemetry);
        this.name = name;
        this.servo1 = servo1;
        this.servo2 = servo2;
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
            servo1.setPosition(openPos);
            servo2.setPosition(1 - openPos);
        } else {
            servo1.setPosition(closedPos);
            servo2.setPosition(1 - closedPos);
        }
    }
}
