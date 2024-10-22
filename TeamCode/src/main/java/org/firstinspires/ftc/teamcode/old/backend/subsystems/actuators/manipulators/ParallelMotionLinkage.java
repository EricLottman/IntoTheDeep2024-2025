package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.manipulators;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.Constants;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

import java.util.LinkedList;

public class ParallelMotionLinkage extends subsystem {
    private Motor motor;
    public final String name;
    public final double inchRadius;
    // it was at this moment I realized that in the case of a chain and sprocket parallel motion linkage, it is possible for both the minimum and maximum to be null, this is going to be painful and fun, mostly painful
    // after doing some programming (20 minutes later) I am starting to think that a chain and sprocket parallel motion linkage should be its own class
    // probably an hour later, totally different coding session, for sure want to do it separate due to how calculations will function
    private final int min, max, ticksPerRotation;
    LinkedList<Integer> positions = new LinkedList<>();

    /**
     * Creates a ParallelMotionLinkage object
     *
     * @param name      Name of system, used pretty much only for telemetry
     * @param motor     Motor for lift
     * @param telemetry Telemetry Object
     * @param min       Max ticks to extend
     * @param max       Min ticks, zero position
     */
    public ParallelMotionLinkage(String name, Motor motor, Telemetry telemetry, int min, int max, double inchRadius, int gearRatio) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.min = min;
        this.max = max;
        this.inchRadius = inchRadius;
        this.ticksPerRotation = (int) (gearRatio * 28);
    }

    /**
     * If the linkage is running
     *
     * @return Boolean, true if motors are running
     */
    public boolean isBusy() {
        return motor.isBusy();
    }

    /**
     * Sets power to the motor
     *
     * @param power Power you want the motor to travel at, 0-1
     */
    public void SP(double power) {
        motor.SP(power);
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     */
    public void RTP() {
        motor.RTP();
    }

    /**
     * Set the target position of the motors using a case switch
     */
    public void STP(int targetPosition) {
        motor.STP(targetPosition > this.max ? this.max : (targetPosition < this.min ? this.min : targetPosition));
    }

    /**
     * Sets given motors relative ticks to 0, STOP_AND_RESET_ENCODERS
     */
    public void SAR() {
        motor.SAR();
    }

    /**
     * Sets given motors to RunMode.RUN_WITHOUT_ENCODER
     */
    public void RWE() {
        motor.RWE();
    }

    /**
     * Sets given motors to RunMode.RUN_USING_ENCODER
     */
    public void RUE() {
        motor.RUE();
    }

    /**
     * Set the tolerance of the motor, used to determine how close the target position must be to the current position for isBusy() to return false
     *
     * @param ticks Number of ticks
     */
    public void ST(int ticks) {
        motor.ST(ticks);
    }

    /**
     * Returns the current position of the motor Object, Ex frontLeft.GCP();
     *
     * @return The current position of the motor in ticks
     */
    public int GCP() {
        return motor.GCP();
    }

    /**
     * Returns the target position of the motor Object, Ex frontLeft.GTP();
     *
     * @return The target position of the motor in ticks
     */
    public int GTP() {
        return motor.GTP();
    }

    /**
     * Returns the power of the motor Object
     *
     * @return The motor's power as a double
     */
    public double GP() {
        return motor.GP();
    }

    /**
     * Go to a position of rotation
     *
     * @param degrees The height in ticks you want travel to
     */
    public void goToPosition(int degrees) {
        goToPosition(degrees, true);
    }

    /**
     * Go to a position of rotation
     *
     * @param degrees The rotation in degrees you want to travel
     * @param wait    If you want to wait till you get to position or if the code should just continue
     */
    public void goToPosition(int degrees, boolean wait) {
        Telemetry.Item parallelMotorLinkageTelemetry = telemetry().addData(this.name, " moving");
        STP((ticksPerRotation * degrees) / 360);
        SP(.8);
        RTP();
        if (wait) {
            telemetry().update();
            while (isBusy()) {
            }
        }
        SP(.4);
        telemetry().removeItem(parallelMotorLinkageTelemetry);
        telemetry().update();
    }

    /**
     * Adds a position of rotation to maintain and travel to
     *
     * @param ticks The height in ticks you want to maintain
     * @return Returns false if the position input isnt valid
     */
    public boolean addRotationPosition(int ticks) {
        if (positions.contains(ticks))
            return false;
        positions.add(ticks);
        return true;
    }

    /**
     * Adds a level of height to maintain and travel to
     *
     * @param position The level you have predetermined and want to travel to
     * @param wait     If you would or would like to proceed the code without getting to position or not
     * @return Returns false if the level input isnt valid
     */
    public boolean goToRotationPosition(int position, boolean wait) {
        if (positions.size() > position)
            return false;
        goToPosition(positions.get(position), wait);
        return true;
    }

}
