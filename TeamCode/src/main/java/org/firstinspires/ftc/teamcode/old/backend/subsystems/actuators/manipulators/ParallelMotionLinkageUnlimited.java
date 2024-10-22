package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.manipulators;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

import java.util.LinkedList;

public class ParallelMotionLinkageUnlimited extends subsystem {
    private Motor motor;
    public final String name;
    public final double inchRadius;
    private final int ticksPerRotation;
    LinkedList<Integer> positions = new LinkedList<>();

    /**
     * Creates a ChainLinkage object with no min/max bounds
     *
     * @param name       Name of system, used pretty much only for telemetry
     * @param motor      Motor for the chain linkage
     * @param telemetry  Telemetry Object
     * @param inchRadius Radius of the sprocket in inches
     * @param gearRatio  Gear ratio for the linkage
     */
    public ParallelMotionLinkageUnlimited(String name, Motor motor, Telemetry telemetry, double inchRadius, int gearRatio) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.inchRadius = inchRadius;
        this.ticksPerRotation = (int) (gearRatio * 28); // Assume 28 ticks per rotation per motor revolution
    }

    /**
     * Checks if the motor is busy
     *
     * @return Boolean, true if the motor is still running
     */
    public boolean isBusy() {
        return motor.isBusy();
    }

    /**
     * Sets power to the motor
     *
     * @param power Power for the motor, from 0 to 1
     */
    public void SP(double power) {
        motor.SP(power);
    }

    /**
     * Sets the motor mode to RUN_TO_POSITION
     */
    public void RTP() {
        motor.RTP();
    }

    /**
     * Set the target position without any bounds since chain linkage has no min or max
     *
     * @param targetPosition Target position in ticks
     */
    public void STP(int targetPosition) {
        motor.STP(targetPosition); // No min/max checks here
    }

    /**
     * Resets the motor encoder to zero
     */
    public void SAR() {
        motor.SAR();
    }

    /**
     * Runs the motor without using an encoder
     */
    public void RWE() {
        motor.RWE();
    }

    /**
     * Runs the motor using an encoder
     */
    public void RUE() {
        motor.RUE();
    }

    /**
     * Sets the tolerance in ticks for the motor
     *
     * @param ticks Number of ticks for the tolerance
     */
    public void ST(int ticks) {
        motor.ST(ticks);
    }

    /**
     * Gets the current position of the motor in ticks
     *
     * @return The current motor position in ticks
     */
    public int GCP() {
        return motor.GCP();
    }

    /**
     * Gets the target position of the motor
     *
     * @return The motor's target position in ticks
     */
    public int GTP() {
        return motor.GTP();
    }

    /**
     * Returns the motor's power
     *
     * @return The current power of the motor as a double
     */
    public double GP() {
        return motor.GP();
    }

    /**
     * Moves the motor to a specific position in degrees
     *
     * @param degrees The target position in degrees
     */
    public void goToPosition(int degrees) {
        goToPosition(degrees, true);
    }

    /**
     * Moves to the closest target position in degrees, taking into account circular nature (0 == 360).
     *
     * @param targetDegrees The target position in degrees (0-360).
     * @param wait          Whether to wait until the motor reaches the position.
     */
    public void goToPosition(int targetDegrees, boolean wait) {
        int currentDegrees = getCurrentDegrees(); // Get current position in degrees
        int closestTarget = getClosestTargetPosition(currentDegrees, targetDegrees);

        // Convert closest target position (degrees) to encoder ticks
        int targetTicks = (ticksPerRotation * closestTarget) / 360;

        // Move the motor
        Telemetry.Item chainLinkageTelemetry = telemetry().addData(this.name, " moving to " + closestTarget + " degrees");
        STP(targetTicks);
        SP(.8); // Start motor at higher speed
        RTP();
        if (wait) {
            telemetry().update();
            while (isBusy()) {
            }
        }
        SP(.4); // Slow down as it approaches the target
        telemetry().removeItem(chainLinkageTelemetry);
        telemetry().update();
    }

    /**
     * Returns the closest target position to the current position (in degrees),
     * considering the circular nature (0 degrees = 360 degrees).
     *
     * @param currentDegrees The current position in degrees.
     * @param targetDegrees  The target position in degrees.
     * @return The closest target position to move to, in degrees.
     */
    private int getClosestTargetPosition(int currentDegrees, int targetDegrees) {
        // Normalize both current and target positions between 0 and 360
        currentDegrees = normalizeDegrees(currentDegrees);
        targetDegrees = normalizeDegrees(targetDegrees);

        // Calculate clockwise and counterclockwise distances
        int clockwiseDistance = targetDegrees >= currentDegrees ?
                targetDegrees - currentDegrees :
                360 - currentDegrees + targetDegrees;

        int counterClockwiseDistance = currentDegrees >= targetDegrees ?
                currentDegrees - targetDegrees :
                currentDegrees + 360 - targetDegrees;

        // Return the direction with the shortest distance
        return clockwiseDistance <= counterClockwiseDistance ? targetDegrees : targetDegrees - 360;
    }

    /**
     * Adds a position in ticks to the list of positions, making sure it's normalized within the 0-360 range.
     *
     * @param ticks The position in ticks to add
     * @return Returns false if the equivalent position is already present
     */
    public boolean addRotationPosition(int ticks) {
        // Normalize ticks to degrees (0 to 360)
        int degrees = normalizeDegrees((ticks * 360) / ticksPerRotation);

        // Check if the equivalent position (in degrees) is already in the list
        for (int existingTicks : positions) {
            int existingDegrees = normalizeDegrees((existingTicks * 360) / ticksPerRotation);
            if (existingDegrees == degrees) {
                return false; // Equivalent position already exists
            }
        }

        positions.add(ticks); // Add ticks if not already present
        return true;
    }

    /**
     * Moves to the closest predefined position in the list of positions.
     *
     * @param position The index of the position in the list
     * @param wait     Whether to wait for the motor to reach the position
     * @return Returns false if the position index is invalid
     */
    public boolean goToRotationPosition(int position, boolean wait) {
        if (positions.size() <= position)
            return false;

        int targetTicks = positions.get(position);
        int closestTarget = getClosestPosition(targetTicks);
        goToPosition(closestTarget, wait);
        return true;
    }

    /**
     * Finds the closest target position in ticks, considering the circular nature of 0-360 degrees.
     *
     * @param targetTicks The target position in ticks
     * @return The closest target position in ticks
     */
    private int getClosestPosition(int targetTicks) {
        int currentDegrees = getCurrentDegrees(); // Get the current motor position in degrees
        int targetDegrees = normalizeDegrees((targetTicks * 360) / ticksPerRotation); // Convert target ticks to degrees

        return (ticksPerRotation * getClosestTargetPosition(currentDegrees, targetDegrees)) / 360; // Convert closest degrees back to ticks
    }

    /**
     * Normalizes the input degrees to be within the 0-360 range.
     *
     * @param degrees Input degrees.
     * @return Degrees normalized to the range 0-360.
     */
    private int normalizeDegrees(int degrees) {
        degrees = degrees % 360;
        return (degrees < 0) ? degrees + 360 : degrees;
    }

    /**
     * Get the current position in degrees based on the motor's current tick count.
     *
     * @return The current position of the motor in degrees.
     */
    private int getCurrentDegrees() {
        int currentTicks = GCP(); // Get current position in ticks
        return (currentTicks * 360) / ticksPerRotation; // Convert ticks to degrees
    }

}
