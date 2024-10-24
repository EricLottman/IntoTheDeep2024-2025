package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.endEffectors;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.old.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.base.Servo;

/**
 * The Pincer2Servo class controls a two-servo mechanism that simulates a pincer or claw.
 * It can open and close by setting both servos to the same position.
 * This class extends the subsystem class to integrate with the telemetry system for monitoring and debugging.
 */
public class Pincer2Servo extends subsystem {
    private Servo servo1, servo2;
    public final String name;
    private final int openPos, closedPos;
    private boolean isOpen;

    /**
     * Creates a Pincer2Servo object.
     *
     * @param name      Name of the pincer system, used primarily for telemetry.
     * @param servo1    The first Servo for the pincer mechanism.
     * @param servo2    The second Servo for the pincer mechanism.
     * @param telemetry Telemetry object for logging and debugging.
     * @param openPos   The position of the servos when open, using the value for servo1.
     * @param closedPos The position of the servos when closed, using the value for servo1.
     * @param startOpen Boolean indicating whether the pincer should start in the open position.
     */
    public Pincer2Servo(String name, Servo servo1, Servo servo2, Telemetry telemetry, int openPos, int closedPos, boolean startOpen) {
        super(telemetry);
        this.name = name;
        this.servo1 = servo1;
        this.servo2 = servo2;
        this.openPos = openPos;
        this.closedPos = closedPos;
        this.isOpen = startOpen;
        if (isOpen) {
            servo1.setPosition(openPos);
            servo2.setPosition(openPos);
        } else {
            servo1.setPosition(closedPos);
            servo2.setPosition(closedPos);
        }
    }

    /**
     * Runs the servos by setting their positions based on the current state.
     * If the pincer is open, both servos will move to the open position.
     * If the pincer is closed, both servos will move to the closed position.
     *
     * @return An Action that sets the positions of both servos and logs the current state to the telemetry packet.
     */
    private Action run() {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (isOpen) {
                    servo1.setPosition(openPos);
                    servo2.setPosition(openPos);
                    packet.put(name, " open");
                } else {
                    servo1.setPosition(closedPos);
                    servo2.setPosition(closedPos);
                    packet.put(name, " closed");
                }
                return false; // Indicates that the action is ongoing.
            }
        };
    }

    /**
     * Opens the pincer by setting both servos to the open position.
     *
     * @return An Action that opens the pincer and updates the telemetry.
     */
    public Action open() {
        isOpen = true;
        return run();
    }

    /**
     * Closes the pincer by setting both servos to the closed position.
     *
     * @return An Action that closes the pincer and updates the telemetry.
     */
    public Action close() {
        isOpen = false;
        return run();
    }

    /**
     * Toggles the state of the pincer between open and closed.
     * If the pincer is currently open, it will be closed, and vice versa.
     *
     * @return An Action that toggles the pincer state and updates the telemetry.
     */
    public Action toggle() {
        if (isOpen) {
            return close();
        } else {
            return open();
        }
    }
}
