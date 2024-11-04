package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.manipulators;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

import java.util.LinkedList;

/**
 * This class represents a parallel motion linkage system that controls a motor
 * for a mechanism. It includes methods to manage position,
 * power, and control behaviors for the motor.
 */
public class ParallelMotionLinkage extends subsystem {
    private Motor motor;
    public final String name;
    public final double inchRadius;
    private double power;
    private int targetPosition, tolerance;
    private final int min, max, ticksPerRotation;
    LinkedList<Integer> positions = new LinkedList<>();

    /**
     * Creates a ParallelMotionLinkage object.
     *
     * @param name      Name of the system, used primarily for telemetry.
     * @param motor     Motor for the linkage.
     * @param telemetry Telemetry object for logging data.
     * @param min       Minimum ticks for the motor to extend.
     * @param max       Maximum ticks for the motor (zero position).
     * @param inchRadius The radius in inches for the linkage.
     * @param gearRatio The gear ratio for the motor.
     */
    public ParallelMotionLinkage(String name, Motor motor, Telemetry telemetry, int min, int max, double inchRadius, int gearRatio) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.min = min;
        this.max = max;
        this.inchRadius = inchRadius;
        this.ticksPerRotation = (int) (gearRatio * 28);
        this.power = .7;
        this.tolerance = 5;
        this.motor.setTolerance(tolerance);
        this.motor.setPower(power);
    }

    /**
     * Checks if the linkage is currently running.
     *
     * @return true if the motor is busy, false otherwise.
     */
    public boolean isBusy() {
        return motor.isBusy();
    }

    private class SetPower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setPower(power);
            return false;
        }
    }

    /**
     * Sets the power to the motor.
     *
     * @param power Power value for the motor, should be between 0 and 1.
     * @return An Action that sets the motor's power.
     */
    private Action setPower(double power) {
        this.power = power;
        return new SetPower();
    }

    private class RunToPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.runToPosition();
            return false;
        }
    }

    /**
     * Returns an Action that commands the motor to run to the target position.
     *
     * @return An Action for executing the run to position command.
     */
    private Action runToPosition() {
        return new RunToPosition();
    }

    private class SetTargetPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setTargetPosition(targetPosition);
            return false;
        }
    }

    /**
     * Sets the target position of the motor while respecting min and max constraints.
     *
     * @param targetPosition Desired target position in ticks.
     * @return An Action that sets the target position.
     */
    private Action setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition > max ? max : (targetPosition < min ? min : targetPosition);
        return new SetTargetPosition();
    }

    private class StopAndReset implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.stopAndReset();
            return false;
        }
    }

    /**
     * Returns an Action that stops and resets the motor encoders.
     *
     * @return An Action that executes the stop and reset command.
     */
    private Action stopAndReset() {
        return new StopAndReset();
    }

    private class RunWithoutEncoder implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.runWithoutEncoder();
            return false;
        }
    }

    /**
     * Returns an Action that sets the motor to RUN_WITHOUT_ENCODER mode.
     *
     * @return An Action that executes the run without encoder command.
     */
    private Action runWithoutEncoder() {
        return new RunWithoutEncoder();
    }

    private class RunUsingEncoder implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.runUsingEncoder();
            return false;
        }
    }

    /**
     * Returns an Action that sets the motor to RUN_USING_ENCODER mode.
     *
     * @return An Action that executes the run using encoder command.
     */
    private Action runUsingEncoder() {
        return new RunUsingEncoder();
    }

    public class SetTolerance implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setTolerance(tolerance);
            return false;
        }
    }

    /**
     * Sets the tolerance of the motor, which defines how close the current
     * position must be to the target position for isBusy() to return false.
     *
     * @param tolerance The tolerance in ticks.
     * @return An Action that sets the motor's tolerance.
     */
    public Action setTolerance(int tolerance) {
        this.tolerance = tolerance;
        return new SetTolerance();
    }

    /**
     * Returns the current position of the motor.
     *
     * @return The current position of the motor in ticks.
     */
    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    /**
     * Returns the target position of the motor.
     *
     * @return The target position of the motor in ticks.
     */
    public int getTargetPosition() {
        return this.targetPosition;
    }

    /**
     * Returns the power currently set on the motor.
     *
     * @return The motor's power as a double.
     */
    private double getPower() {
        return motor.getPower();
    }

    /**
     * Moves the motor to a specified position based on rotation in degrees.
     *
     * @param degrees The rotation in degrees you want to travel.
     * @return An Action that commands the motor to move to the specified position.
     */
    public Action goToPosition(int degrees) {
        return new SequentialAction(
                setTargetPosition((ticksPerRotation * degrees) / 360),
                runToPosition());
    }

    /**
     * Moves the motor to a specified position and waits until the position is reached.
     *
     * @param targetPosition The target position in ticks you want to travel to.
     * @return An Action that commands the motor to move and waits for completion.
     */
    public Action goToPositionWaitTillPosition(int targetPosition) {
        return new SequentialAction(
                setTargetPosition(targetPosition),
                runToPosition(),
                new Action() {
                    @Override
                    public boolean run(@NonNull TelemetryPacket packet) {
                        return !motor.isBusy();
                    }
                }
        );
    }

    /**
     * Adds a position of rotation to maintain and travel to.
     *
     * @param ticks The height in ticks you want to maintain.
     * @return Returns false if the position input isn't valid; true otherwise.
     */
    public boolean addRotationPosition(int ticks) {
        if (positions.contains(ticks))
            return false;
        positions.add(ticks);
        return true;
    }

    /**
     * Moves the motor to a predetermined level.
     *
     * @param position The index of the level you want to travel to.
     * @return An Action that commands the motor to move to the specified rotation position.
     */
    public Action goToRotationPosition(int position) {
        return goToPosition(positions.get(position));
    }

    /**
     * Moves the motor to a predetermined level and waits until
     * the position is reached before proceeding.
     *
     * @param level The index of the level to travel to.
     * @return An Action that executes the movement and waits for completion.
     */
    public Action goToRotationPositionWaitTillPosition(int level) {
        return goToPositionWaitTillPosition(positions.get(level));
    }
}
