package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.manipulators;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.Constants;

import java.util.LinkedList;

/**
 * The Lift2Motor class controls two motors that run in parallel for lifting mechanisms.
 * It manages motor operations such as moving to a specific position,
 * setting power, and handling various modes of operation (with or without encoders).
 * This class extends the subsystem class to integrate with the telemetry system.
 */
public class Lift2Motor extends subsystem {
    private Motor motor1, motor2;
    public final String name;
    private final int min, max;
    private double power;
    private int targetPosition, tolerance;
    LinkedList<Integer> levels = new LinkedList<>();

    /**
     * Creates a Lift1Motor object, one of these motors must be reversed
     *
     * @param name      Name of the system, used pretty much only for telemetry
     * @param motor1    First Motor for lift
     * @param motor2    Second Motor for lift
     * @param telemetry Telemetry Object
     * @param min       Max ticks to extend
     * @param max       Min ticks, zero position
     */
    public Lift2Motor(String name, Motor motor1, Motor motor2, Telemetry telemetry, int min, int max) {
        super(telemetry);
        this.name = name;
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.min = min;
        this.max = max;
        this.power = 0;
        this.tolerance = 5;
        this.motor1.setTolerance(tolerance);
        this.motor2.setTolerance(tolerance);
    }

    /**
     * Checks if the lift motors are currently busy (running).
     *
     * @return true if the motors are busy; false otherwise.
     */
    public boolean isBusy() {
        return motor1.isBusy() && motor2.isBusy();
    }

    private class SetPower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor1.setPower(power);
            motor2.setPower(power);
            return false;
        }
    }

    /**
     * Sets the power to the motors.
     *
     * @param power Power value for the motors, should be between 0 and 1.
     * @return An Action that sets the motors' power.
     */
    private Action setPower(double power) {
        this.power = power;
        return new SetPower();
    }

    private class RunToPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor1.runToPosition();
            motor2.runToPosition();
            return false;
        }
    }

    /**
     * Returns an Action that runs the motors to the target position.
     *
     * @return An Action that executes the run to position command.
     */
    private Action runToPosition() {
        return new RunToPosition();
    }

    private class SetTargetPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor1.setTargetPosition(targetPosition);
            motor2.setTargetPosition(targetPosition);
            return false;
        }
    }

    /**
     * Sets the target position of the motors while respecting min and max constraints.
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
            motor1.stopAndReset();
            motor2.stopAndReset();
            return false;
        }
    }

    /**
     * Returns an Action that stops and resets the motors encoders.
     *
     * @return An Action that executes the stop and reset command.
     */
    private Action stopAndReset() {
        return new StopAndReset();
    }

    private class RunWithoutEncoder implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor1.runWithoutEncoder();
            motor2.runWithoutEncoder();
            return false;
        }
    }

    /**
     * Returns an Action that sets the motors to RUN_WITHOUT_ENCODER mode.
     *
     * @return An Action that executes the run without encoder command.
     */
    private Action runWithoutEncoder() {
        return new RunWithoutEncoder();
    }

    private class RunUsingEncoder implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor1.runUsingEncoder();
            motor2.runUsingEncoder();
            return false;
        }
    }

    /**
     * Returns an Action that sets the motors to RUN_USING_ENCODER mode.
     *
     * @return An Action that executes the run using encoder command.
     */
    private Action runUsingEncoder() {
        return new RunUsingEncoder();
    }

    public class SetTolerance implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor1.setTolerance(tolerance);
            motor2.setTolerance(tolerance);
            return false;
        }
    }

    /**
     * Sets the tolerance of the motors, which defines how close the current
     * position must be to the target position for isBusy() to return false.
     *
     * @param tolerance The tolerance in ticks.
     * @return An Action that sets the motors' tolerance.
     */
    public Action setTolerance(int tolerance) {
        this.tolerance = tolerance;
        return new SetTolerance();
    }

    /**
     * Returns the current position of the motors.
     *
     * @return The current position of the motors in ticks.
     */
    public int getCurrentPosition() {
        return (int) (motor1.getCurrentPosition() + motor2.getCurrentPosition()) / 2;
    }

    /**
     * Returns the target position of the motors.
     *
     * @return The target position of the motors in ticks.
     */
    public int getTargetPosition() {
        return this.targetPosition;
    }

    /**
     * Returns the power currently set on the motors.
     *
     * @return The motors' power as a double.
     */
    private double getPower() {
        return motor1.getPower();
    }

    /**
     * Calculates the power to set for the motors based on its current position
     * relative to the target position and the defined tolerance.
     *
     * @return The power value to set for the motors.
     */
    private double powerSetter() {
        if (getCurrentPosition() > this.targetPosition - this.tolerance && getCurrentPosition() < this.targetPosition + this.tolerance)
            return (Constants.downPower + Constants.upPower) / 2;
        else if (getCurrentPosition() < this.targetPosition - this.tolerance)
            return Constants.upPower;
        else
            return Constants.downPower;
    }

    /**
     * Moves the motors to a specified target position with calculated power.
     *
     * @param targetPosition The height in ticks you want the motors to travel to.
     * @return An Action that executes the movement to the target position.
     */
    public Action goToPosition(int targetPosition) {
        return new ParallelAction(new SequentialAction(
                setTargetPosition(targetPosition),
                runToPosition()
        ), setPower(powerSetter()));
    }

    /**
     * Moves the motors to a specified target position and waits until
     * the position is reached before proceeding.
     *
     * @param targetPosition The height in ticks you want the motors to travel to.
     * @return An Action that executes the movement and waits for completion.
     */
    public Action goToPositionWaitTillPosition(int targetPosition) {
        return new ParallelAction(new SequentialAction(
                setTargetPosition(targetPosition),
                runToPosition(),
                new Action() {
                    @Override
                    public boolean run(@NonNull TelemetryPacket packet) {
                        return !motor1.isBusy() || !motor2.isBusy();
                    }
                }
        ), setPower(powerSetter()));
    }

    /**
     * Adds a level of height to maintain and travel to.
     *
     * @param ticks The height in ticks to maintain.
     * @return false if the level input is invalid (already exists).
     */
    public boolean addLevels(int ticks) {
        if (levels.contains(ticks))
            return false;
        levels.add(ticks);
        return true;
    }

    /**
     * Moves the motors to a predetermined level.
     *
     * @param level The index of the level to travel to.
     * @return An Action that moves the motors to the specified level.
     */
    public Action goToLevel(int level) {
        return goToPosition(levels.get(level));
    }

    /**
     * Moves the motor to a predetermined level and waits until
     * the position is reached before proceeding.
     *
     * @param level The index of the level to travel to.
     * @return An Action that executes the movement and waits for completion.
     */
    public Action goToLevelWaitTillPosition(int level) {
        return goToPositionWaitTillPosition(levels.get(level));
    }
}
