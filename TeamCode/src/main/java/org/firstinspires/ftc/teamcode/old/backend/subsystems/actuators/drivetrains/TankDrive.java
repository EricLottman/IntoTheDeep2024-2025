package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainMotorControls;

import java.util.Arrays;

/**
 * Object in order to create tank drive for autonomous and teleop
 */
public class TankDrive extends subsystem implements DrivetrainMotorControls {
    private Motor frontLeft, frontRight, backLeft, backRight;

    /**
     * Creates a TankDrive drive Object by putting motors into a sorted array
     *
     * @param motors Four motor Objects in an array
     */
    public TankDrive(Motor[] motors, Telemetry telemetry) {
        super(telemetry);
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
    }

    /**
     * Put power to motors for the left and right sides
     *
     * @param left  Left joystick
     * @param right Right joystick
     */
    public void teleOpDrive(double left, double right) {
        RWE(DTMotors.DRIVETRAIN_BASIC_4);
        frontLeft.SP(left);
        frontRight.SP(right);
    }

    /**
     * Set power to motors using a case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param power Power (between -1 and 1)
     */
    @Override
    public void SP(DTMotors motors, double power) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.SP(power);
                frontRight.SP(power);
                backLeft.SP(power);
                backRight.SP(power);
                break;
            case FRONT_LEFT:
                frontLeft.SP(power);
                break;
            case FRONT_RIGHT:
                frontRight.SP(power);
                break;
            case BACK_LEFT:
                backLeft.SP(power);
                break;
            case BACK_RIGHT:
                backRight.SP(power);
                break;
            case FRONT:
                frontLeft.SP(power);
                frontRight.SP(power);
                break;
            case BACK:
                backLeft.SP(power);
                backRight.SP(power);
                break;
            case LEFT:
                frontLeft.SP(power);
                backLeft.SP(power);
                break;
            case RIGHT:
                frontRight.SP(power);
                backRight.SP(power);
                break;
        }
    }

    /**
     * Set the target position of the motors using a case switch
     *
     * @param motors  Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param targetPosition Target Position in ticks
     */
    @Override
    public void STP(DTMotors motors, int targetPosition) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.STP(targetPosition);
                frontRight.STP(targetPosition);
                backLeft.STP(targetPosition);
                backRight.STP(targetPosition);
                break;
            case FRONT_LEFT:
                frontLeft.STP(targetPosition);
                break;
            case FRONT_RIGHT:
                frontRight.STP(targetPosition);
                break;
            case BACK_LEFT:
                backLeft.STP(targetPosition);
                break;
            case BACK_RIGHT:
                backRight.STP(targetPosition);
                break;
            case FRONT:
                frontLeft.STP(targetPosition);
                frontRight.STP(targetPosition);
                break;
            case BACK:
                backLeft.STP(targetPosition);
                backRight.STP(targetPosition);
                break;
            case LEFT:
                frontLeft.STP(targetPosition);
                backLeft.STP(targetPosition);
                break;
            case RIGHT:
                frontRight.STP(targetPosition);
                backRight.STP(targetPosition);
                break;
        }
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RTP(DTMotors motors) {
        RTP(motors, true);
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param motors    Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param wait If you want to wait till the drivetrain is in position or if the code should just continue
     */
    public void RTP(DTMotors motors, boolean wait) {
        Telemetry.Item tankDriveRTPTelemetry = telemetry().addData("TankDrive moving", "");
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.RTP();
                frontRight.RTP();
                backLeft.RTP();
                backRight.RTP();
                break;
            case FRONT_LEFT:
                frontLeft.RTP();
                break;
            case FRONT_RIGHT:
                frontRight.RTP();
                break;
            case BACK_LEFT:
                backLeft.RTP();
                break;
            case BACK_RIGHT:
                backRight.RTP();
                break;
            case FRONT:
                frontLeft.RTP();
                frontRight.RTP();
                break;
            case BACK:
                backLeft.RTP();
                backRight.RTP();
                break;
            case LEFT:
                frontLeft.RTP();
                backLeft.RTP();
                break;
            case RIGHT:
                frontRight.RTP();
                backRight.RTP();
                break;
        }
        if (wait) {
            telemetry().update();
            while (isBusy()) {
            }
        }
        telemetry().removeItem(tankDriveRTPTelemetry);
        telemetry().update();
    }

    /**
     * Sets the mode of the motor to STOP_AND_RESET_ENCODERS using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void SAR(DTMotors motors) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.SAR();
                frontRight.SAR();
                backLeft.SAR();
                backRight.SAR();
                break;
            case FRONT_LEFT:
                frontLeft.SAR();
                break;
            case FRONT_RIGHT:
                frontRight.SAR();
                break;
            case BACK_LEFT:
                backLeft.SAR();
                break;
            case BACK_RIGHT:
                backRight.SAR();
                break;
            case FRONT:
                frontLeft.SAR();
                frontRight.SAR();
                break;
            case BACK:
                backLeft.SAR();
                backRight.SAR();
                break;
            case LEFT:
                frontLeft.SAR();
                backLeft.SAR();
                break;
            case RIGHT:
                frontRight.SAR();
                backRight.SAR();
                break;
        }
    }

    /**
     * Sets the mode of the motor to RUN_WITHOUT_ENCODERS using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RWE(DTMotors motors) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.RWE();
                frontRight.RWE();
                backLeft.RWE();
                backRight.RWE();
                break;
            case FRONT_LEFT:
                frontLeft.RWE();
                break;
            case FRONT_RIGHT:
                frontRight.RWE();
                break;
            case BACK_LEFT:
                backLeft.RWE();
                break;
            case BACK_RIGHT:
                backRight.RWE();
                break;
            case FRONT:
                frontLeft.RWE();
                frontRight.RWE();
                break;
            case BACK:
                backLeft.RWE();
                backRight.RWE();
                break;
            case LEFT:
                frontLeft.RWE();
                backLeft.RWE();
                break;
            case RIGHT:
                frontRight.RWE();
                backRight.RWE();
                break;
        }
    }

    /**
     * Sets the mode of the motor to RUN_USING_ENCODERS using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RUE(DTMotors motors) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.RUE();
                frontRight.RUE();
                backLeft.RUE();
                backRight.RUE();
                break;
            case FRONT_LEFT:
                frontLeft.RUE();
                break;
            case FRONT_RIGHT:
                frontRight.RUE();
                break;
            case BACK_LEFT:
                backLeft.RUE();
                break;
            case BACK_RIGHT:
                backRight.RUE();
                break;
            case FRONT:
                frontLeft.RUE();
                frontRight.RUE();
                break;
            case BACK:
                backLeft.RUE();
                backRight.RUE();
                break;
            case LEFT:
                frontLeft.RUE();
                backLeft.RUE();
                break;
            case RIGHT:
                frontRight.RUE();
                backRight.RUE();
                break;
        }
    }

    /**
     * Sets tolerance of motor
     *
     * @param i Tolerance in ticks to consider not busy when running to position
     */
    public void ST(int i) {
        frontLeft.ST(i);
        backLeft.ST(i);
        backRight.ST(i);
        frontRight.ST(i);
    }

    /**
     * Returns whether or not the drivetrain is busy
     *
     * @return isBusy (true or false)
     */
    public boolean isBusy() {
        return frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy();
    }

    /**
     * Driving method used for autonomous using case switch, distance, and power
     *
     * @param direction Direction to drive
     * @param inches    Distance using inches
     * @param speed     Power (between -1 and 1)
     */
    public void drive(DrivetrainHolonomic.Directions direction, double inches, double speed) {
        SAR(DTMotors.DRIVETRAIN_BASIC_4);
        RUE(DTMotors.DRIVETRAIN_BASIC_4);
        switch (direction) {
            case FORWARD:
                STP(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
                break;
            case BACKWARDS:
                STP(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(-inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
                break;
            default:
                throw new IllegalArgumentException(direction + " is an invalid direction for TankDrive");
        }
    }
}