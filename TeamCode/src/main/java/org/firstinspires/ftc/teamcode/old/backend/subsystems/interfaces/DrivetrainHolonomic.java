package org.firstinspires.ftc.teamcode.old.backend.subsystems.interfaces;

public interface DrivetrainHolonomic extends DrivetrainMotorControls {

    enum Directions {
        FORWARD, BACKWARDS, LEFT, RIGHT, DIAGONAL_FORWARDS_RIGHT, DIAGONAL_FORWARDS_LEFT, DIAGONAL_BACKWARDS_RIGHT, DIAGONAL_BACKWARDS_LEFT
    }

    /**
     * Set power to motors for teleOp driving
     *
     * @param y  Driving
     * @param rx Rotation
     * @param x  Strafing
     */
    void teleOpDrive(double y, double rx, double x);

    /**
     * Set power to motors for teleOp driving, allows for adjustment to speed
     *
     * @param y     Driving
     * @param rx    Rotation
     * @param x     Strafing
     * @param speed Speed reduction, higher reduction means slower speed
     */
    void teleOpDrive(double y, double rx, double x, double speed);

    /**
     * Driving method used for autonomous using case switch, distance, and power
     *
     * @param direction Direction to drive
     * @param inches    Distance using inches
     * @param speed     Power (between -1 and 1)
     */
    void drive(Directions direction, double inches, double speed);
}
