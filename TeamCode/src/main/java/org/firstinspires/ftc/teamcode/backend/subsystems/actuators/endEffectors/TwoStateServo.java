package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.endEffectors;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;

/**
 * The TwoStateServo class controls a servo mechanism that can be toggled between two positions: open and closed.
 * It extends the subsystem class to integrate with the telemetry system for monitoring and debugging.
 */
public class TwoStateServo extends subsystem {
    private Servo servo;
    public final String name;
    private final int openPos, closedPos;
    private boolean isOpen;

    /**
     * Creates a TwoStateServo object.
     *
     * @param name        Name of the servo system, used primarily for telemetry.
     * @param servo       The Servo object that controls the two-state mechanism.
     * @param telemetry   Telemetry object for logging and debugging.
     * @param openPos     The position of the servo when it is open.
     * @param closedPos   The position of the servo when it is closed.
     * @param startOpen   Boolean indicating whether the servo should start in the open position.
     */
    public TwoStateServo(String name, Servo servo, Telemetry telemetry, int openPos, int closedPos, boolean startOpen) {
        super(telemetry);
        this.name = name;
        this.servo = servo;
        this.openPos = openPos;
        this.closedPos = closedPos;
        this.isOpen = startOpen;
        if (isOpen) {
            servo.setPosition(openPos);
        } else {
            servo.setPosition(closedPos);
        }
    }

    /**
     * Runs the servo by setting its position based on the current state.
     * If the claw is open, it will move to the open position.
     * If the claw is closed, it will move to the closed position.
     *
     * @return An Action that sets the servo's position and logs its current state to the telemetry packet.
     */
    private Action run() {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (isOpen) {
                    servo.setPosition(openPos);
                    packet.put(name, " open");
                } else {
                    servo.setPosition(closedPos);
                    packet.put(name, " closed");
                }
                return false; // Indicates that the action is ongoing.
            }
        };
    }

    /**
     * Opens the servo by setting it to the open position.
     *
     * @return An Action that opens the servo and updates the telemetry.
     */
    public Action open() {
        isOpen = true;
        return run();
    }

    /**
     * Closes the servo by setting it to the closed position.
     *
     * @return An Action that closes the servo and updates the telemetry.
     */
    public Action close() {
        isOpen = false;
        return run();
    }

    /**
     * Toggles the state of the servo between open and closed.
     * If the servo is currently open, it will be closed, and vice versa.
     *
     * @return An Action that toggles the servo's position and updates the telemetry.
     */
    public Action toggle() {
        if (isOpen) {
            return close();
        } else {
            return open();
        }
    }
}
