package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;

import java.util.Arrays;

/**
 * This class represents a Mecanum drivetrain for autonomous and teleoperation
 * It allows for control of a holonomic robot using mecanum wheels, enabling movements in multiple directions
 */
public class Mecanum extends subsystem implements DrivetrainHolonomic {
    private Motor frontLeft, frontRight, backLeft, backRight;

    /**
     * Constructs a Mecanum drivetrain object and initializes the motor positions
     * The motor array is sorted to ensure motors are assigned to correct drivetrain positions
     *
     * @param motors    An array containing four motor objects, representing the drivetrain motors
     * @param telemetry The telemetry object for logging data
     */
    public Mecanum(Motor[] motors, Telemetry telemetry) {
        super(telemetry);
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
    }

    /**
     * Controls the drivetrain in teleOp mode, allowing driving, strafing, and rotating
     *
     * @param y  Forward or backward motion
     * @param rx Rotational motion (turning)
     * @param x  Lateral motion (strafing)
     */
    @Override
    public void teleOpDrive(double y, double rx, double x) {
        RWE(DTMotors.DRIVETRAIN_BASIC_4);
        // maintain ratio in case of range clip
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeft.SP(((y + x * 1.1 + rx) / denominator));
        backLeft.SP(((y - x * 1.1 + rx) / denominator));
        frontRight.SP(((y - x * 1.1 - rx) / denominator));
        backRight.SP(((y + x * 1.1 - rx) / denominator));
    }

    /**
     * Controls the drivetrain in teleOp mode with speed adjustment
     *
     * @param y     Forward or backward motion
     * @param rx    Rotational motion (turning)
     * @param x     Lateral motion (strafing)
     * @param speed Speed adjustment factor; higher value slows down the drivetrain
     */
    @Override
    public void teleOpDrive(double y, double rx, double x, double speed) {
        RWE(DTMotors.DRIVETRAIN_BASIC_4);
        // maintain ratio in case of range clip
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeft.SP(((y + x * 1.1 + rx) / denominator) / speed);
        backLeft.SP(((y - x * 1.1 + rx) / denominator) / speed);
        frontRight.SP(((y - x * 1.1 - rx) / denominator) / speed);
        backRight.SP(((y + x * 1.1 - rx) / denominator) / speed);
    }

    /**
     * Sets motor power using motor abbreviations and power levels
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param power  Power level (from -1 to 1)
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
     * Sets the target position for motors using a motor abbreviation and target position in ticks
     *
     * @param motors         Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param targetPosition Target position in ticks
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
     * Sets motors to run to a specific position
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RTP(DTMotors motors) {
        RTP(motors, true);
    }

    /**
     * Sets motors to run to a specific position and optionally waits until the position is reached
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param wait   Whether to wait until the position is reached
     */
    public void RTP(DTMotors motors, boolean wait) {
        Telemetry.Item mecanumRTPTelemetry = telemetry().addData("Mecanum moving", "");
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
        telemetry().removeItem(mecanumRTPTelemetry);
        telemetry().update();
    }

    /**
     * Stops and resets the motor encoders using a motor abbreviation
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
     * Runs motors without using encoders based on the motor abbreviation
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
     * Runs motors using encoders based on the motor abbreviation
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
     * Sets the tolerance for considering the motor "not busy" when running to a position
     *
     * @param i Tolerance value in ticks
     */
    public void ST(int i) {
        frontLeft.ST(i);
        backLeft.ST(i);
        backRight.ST(i);
        frontRight.ST(i);
    }

    /**
     * Checks if the drivetrain motors are still busy
     *
     * @return true if the drivetrain is busy, false otherwise
     */
    @Override
    public boolean isBusy() {
        return frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy();
    }

    /**
     * Drives the robot autonomously based on direction, distance in inches, and speed
     *
     * @param direction Direction to drive (FORWARD, BACKWARDS, LEFT, RIGHT, etc.)
     * @param inches    Distance to drive in inches
     * @param speed     Speed of the movement (from -1 to 1)
     */
    @Override
    public void drive(Directions direction, double inches, double speed) {
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
            case BACKWARDS:
                STP(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(-inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case LEFT:
                STP(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(-inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case RIGHT:
                STP(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_FORWARDS_RIGHT:
                STP(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(0));
                STP(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(0));
                STP(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_BACKWARDS_LEFT:
                STP(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(0));
                STP(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(0));
                STP(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(-inches));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_FORWARDS_LEFT:
                STP(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(0));
                STP(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(0));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_BACKWARDS_RIGHT:
                STP(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(0));
                STP(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(0));
                SP(DTMotors.DRIVETRAIN_BASIC_4, speed);
                RTP(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
            default:
                throw new IllegalArgumentException(direction + " is an invalid direction for Mecanum");
        }
    }

    /**
     * Moves the robot in a specific direction using degrees and distance
     *
     * @param inches       Distance to move in inches
     * @param directionDeg Direction in degrees (0-360)
     */
    public void moveInDirection(double inches, int directionDeg) {
        double directionRad = Math.toRadians(directionDeg);

        double xComponent = Math.cos(directionRad);
        double yComponent = Math.sin(directionRad);

        double frontLeftPower = yComponent + xComponent;
        double frontRightPower = yComponent - xComponent;
        double backLeftPower = yComponent - xComponent;
        double backRightPower = yComponent + xComponent;

        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        STP(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(inches));
        frontLeft.SP(frontLeftPower);
        frontRight.SP(frontRightPower);
        backLeft.SP(backLeftPower);
        backRight.SP(backRightPower);

        RTP(DTMotors.DRIVETRAIN_BASIC_4);

        while (isBusy()) {
        }
        SP(DTMotors.DRIVETRAIN_BASIC_4, 0);
    }

    // need to find a way to stop roadrunnering
    private void rampUpPower(Motor motor, double targetPower, long durationMillis) {
        double currentPower = motor.GP();
        double steps = 50; // number of ramp steps
        double stepDuration = durationMillis / steps;
        double powerIncrement = (targetPower - currentPower) / steps;

        for (int i = 0; i < steps; i++) {
            double newPower = currentPower + powerIncrement * i;
            motor.SP(newPower);
            try {
                Thread.sleep((long) stepDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        motor.SP(targetPower);
    }
}
