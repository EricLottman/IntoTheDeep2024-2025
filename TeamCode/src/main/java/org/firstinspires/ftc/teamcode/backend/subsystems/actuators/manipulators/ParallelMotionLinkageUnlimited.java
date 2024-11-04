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
 * for a mechanism without hard bounds. It includes methods to manage position,
 * power, and control behaviors for the motor.
 */
public class ParallelMotionLinkageUnlimited extends subsystem {
    private Motor motor;
    public final String name;
    public final double inchRadius;
    private final int ticksPerRotation;
    private double power;
    private int targetPosition, tolerance;
    LinkedList<Integer> positions = new LinkedList<>();

    /**
     * Creates a ParallelMotionLinkageUnlimited object with no min/max bounds.
     *
     * @param name       Name of the system, primarily used for telemetry.
     * @param motor      Motor for the chain linkage.
     * @param telemetry  Telemetry object for logging and debugging.
     * @param inchRadius Radius of the sprocket in inches.
     * @param gearRatio  Gear ratio of the linkage system.
     */
    public ParallelMotionLinkageUnlimited(String name, Motor motor, Telemetry telemetry, double inchRadius, int gearRatio) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.inchRadius = inchRadius;
        this.ticksPerRotation = (int) (gearRatio * 28); // Assume 28 ticks per motor revolution
        this.power = .7;
        this.tolerance = 5;
        this.motor.setTolerance(tolerance);
        this.motor.setPower(power);
    }

    /**
     * Checks if the linkage's motor is currently running.
     *
     * @return True if the motor is busy, false otherwise.
     */
    public boolean isBusy() {
        return motor.isBusy();
    }

    /**
     * Represents an action to set the motor's power.
     */
    private class SetPower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setPower(power);
            return false;
        }
    }

    /**
     * Sets the power of the motor and returns an action that applies this setting.
     *
     * @param power Power level to set for the motor (range: 0 to 1).
     * @return An action that sets the motor's power.
     */
    private Action setPower(double power) {
        this.power = power;
        return new SetPower();
    }

    /**
     * Represents an action to run the motor to the target position.
     */
    private class RunToPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.runToPosition();
            return false;
        }
    }

    /**
     * Returns an action that commands the motor to run to its target position.
     *
     * @return An action that runs the motor to the target position.
     */
    private Action runToPosition() {
        return new RunToPosition();
    }

    /**
     * Represents an action to set the target position for the motor.
     */
    private class SetTargetPosition implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setTargetPosition(targetPosition);
            return false;
        }
    }

    /**
     * Sets the motor's target position in ticks and returns an action to apply this change.
     *
     * @param targetPosition Desired target position in ticks.
     * @return An action that sets the motor's target position.
     */
    private Action setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
        return new SetTargetPosition();
    }

    /**
     * Represents an action to stop and reset the motor encoders.
     */
    private class StopAndReset implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.stopAndReset();
            return false;
        }
    }

    /**
     * Returns an action to stop and reset the motor's encoders.
     *
     * @return An action that stops and resets the motor.
     */
    private Action stopAndReset() {
        return new StopAndReset();
    }

    /**
     * Represents an action to run the motor without using the encoders.
     */
    private class RunWithoutEncoder implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.runWithoutEncoder();
            return false;
        }
    }

    /**
     * Returns an action that sets the motor to run without encoders.
     *
     * @return An action that applies the RUN_WITHOUT_ENCODER mode.
     */
    private Action runWithoutEncoder() {
        return new RunWithoutEncoder();
    }

    /**
     * Represents an action to run the motor using encoders.
     */
    private class RunUsingEncoder implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.runUsingEncoder();
            return false;
        }
    }

    /**
     * Returns an action that sets the motor to run using encoders.
     *
     * @return An action that applies the RUN_USING_ENCODER mode.
     */
    private Action runUsingEncoder() {
        return new RunUsingEncoder();
    }

    /**
     * Represents an action to set the tolerance for the motor.
     */
    public class SetTolerance implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setTolerance(tolerance);
            return false;
        }
    }

    /**
     * Sets the tolerance for the motor, which defines how close it must be to the target
     * position for isBusy() to return false.
     *
     * @param tolerance Tolerance value in ticks.
     * @return An action that sets the motor's tolerance.
     */
    public Action setTolerance(int tolerance) {
        this.tolerance = tolerance;
        return new SetTolerance();
    }

    /**
     * Gets the current position of the motor.
     *
     * @return Current motor position in ticks.
     */
    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    /**
     * Gets the motor's target position.
     *
     * @return Target position of the motor in ticks.
     */
    public int getTargetPosition() {
        return this.targetPosition;
    }

    /**
     * Gets the power currently set on the motor.
     *
     * @return The power level of the motor as a double.
     */
    private double getPower() {
        return motor.getPower();
    }

    /**
     * Moves the motor to the specified target position in degrees, considering
     * the circular nature of rotation (0 to 360 degrees).
     *
     * @param targetDegrees Desired target position in degrees (0-360).
     * @return An action to move the motor to the specified position in degrees.
     */
    public Action goToDegrees(int targetDegrees) {
        return new SequentialAction(
                setTargetPosition((ticksPerRotation * getClosestTargetPosition(getCurrentDegrees(), targetDegrees)) / 360),
                runToPosition());
    }

    /**
     * Moves the motor to the target position and waits until it reaches that position.
     *
     * @param targetPosition Desired position in degrees.
     * @return An action that moves the motor and waits for it to reach the target.
     */
    public Action goToDegreesWaitTillPosition(int targetPosition) {
        return new SequentialAction(
                setTargetPosition((ticksPerRotation * getClosestTargetPosition(getCurrentDegrees(), targetPosition)) / 360),
                runToPosition(),
                packet -> !motor.isBusy()
        );
    }

    /**
     * Returns the closest target position to the current one, in degrees,
     * considering the circular nature of rotation (0 to 360 degrees).
     *
     * @param currentDegrees Current position of the motor in degrees.
     * @param targetDegrees  Desired target position in degrees.
     * @return Closest target position in degrees.
     */
    private int getClosestTargetPosition(int currentDegrees, int targetDegrees) {
        currentDegrees = normalizeDegrees(currentDegrees);
        targetDegrees = normalizeDegrees(targetDegrees);

        int clockwiseDistance = targetDegrees >= currentDegrees ?
                targetDegrees - currentDegrees :
                360 - currentDegrees + targetDegrees;

        int counterClockwiseDistance = currentDegrees >= targetDegrees ?
                currentDegrees - targetDegrees :
                currentDegrees + 360 - targetDegrees;

        return clockwiseDistance <= counterClockwiseDistance ? targetDegrees : targetDegrees - 360;
    }

    /**
     * Adds a position in ticks to the list of predefined positions.
     * Ensures the position is normalized to the 0-360 degrees range.
     *
     * @param ticks Position in ticks to add.
     * @return False if an equivalent position already exists, true otherwise.
     */
    public boolean addRotationPosition(int ticks) {
        int degrees = normalizeDegrees((ticks * 360) / ticksPerRotation);

        for (int existingTicks : positions) {
            int existingDegrees = normalizeDegrees((existingTicks * 360) / ticksPerRotation);
            if (existingDegrees == degrees) {
                return false;
            }
        }
        positions.add(ticks);
        return true;
    }

    /**
     * Moves the motor to the closest predefined position from the list of positions.
     *
     * @param position Index of the position in the list.
     * @return False if the position index is invalid.
     */
    public Action goToRotationPosition(int position) {
        return goToDegrees(getClosestPosition(positions.get(position)));
    }

    /**
     * Moves the motor to the closest predefined position from the list of positions,
     * and waits until it reaches that position.
     *
     * @param level Index of the position in the list.
     * @return An action that moves the motor to the position and waits.
     */
    public Action goToRotationPositionWaitTillPosition(int level) {
        return goToDegreesWaitTillPosition(positions.get(level));
    }

    /**
     * Finds the closest target position in ticks based on the specified target position,
     * considering the circular nature of rotation (0 to 360 degrees).
     *
     * @param targetTicks Target position in ticks.
     * @return Closest target position in ticks.
     */
    private int getClosestPosition(int targetTicks) {
        return (ticksPerRotation * getClosestTargetPosition(getCurrentDegrees(), normalizeDegrees((targetTicks * 360) / ticksPerRotation))) / 360;
    }

    /**
     * Normalizes a given angle in degrees to the range of 0 to 360 degrees.
     *
     * @param degrees Angle in degrees to normalize.
     * @return Normalized angle within the 0-360 degree range.
     */
    private int normalizeDegrees(int degrees) {
        degrees = degrees % 360;
        return (degrees < 0) ? degrees + 360 : degrees;
    }

    /**
     * Retrieves the motor's current position in degrees based on its current tick count.
     *
     * @return Current motor position in degrees.
     */
    private int getCurrentDegrees() {
        return (getCurrentPosition() * 360) / ticksPerRotation;
    }
}
