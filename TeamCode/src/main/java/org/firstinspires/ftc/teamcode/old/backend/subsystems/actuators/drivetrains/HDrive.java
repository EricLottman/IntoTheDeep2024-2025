package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

import java.util.Arrays;

/**
 * Represents an H-Drive drivetrain for a robot.
 * This drivetrain consists of four base motors arranged in a traditional holonomic setup
 * (frontLeft, frontRight, backLeft, backRight), and a fifth motor (midShift) for lateral movement
 */
public class HDrive extends subsystem implements DrivetrainHolonomic {
    private Motor frontLeft, frontRight, backLeft, backRight, midShift;

    /**
     * Constructs an H-Drive object with the specified base motors and a mid-shift motor for lateral movement
     *
     * @param motors    An array of four base Motor objects representing the base of the drivetrain
     * @param midshift  The mid-shift Motor object for lateral movement
     * @param telemetry The telemetry object for displaying data
     */
    public HDrive(Motor[] motors, Motor midshift, Telemetry telemetry) {
        super(telemetry);
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
        this.midShift = midshift;
    }

    /**
     * Controls the drivetrain during TeleOp mode by setting power to the motors for driving, rotation, and strafing
     *
     * @param y  The power for forward/backward movement
     * @param rx The power for rotation
     * @param x  The power for strafing (lateral movement)
     */
    @Override
    public void teleOpDrive(double y, double rx, double x) {
        RWE(DTMotors.ALL);
        frontLeft.SP(((y + rx)));
        backLeft.SP(((y + rx)));
        frontRight.SP(((y - rx)));
        backRight.SP(((y - rx)));
        midShift.SP(x * 1.5);
    }

    /**
     * Controls the drivetrain during TeleOp mode with adjustable speed for driving, rotation, and strafing
     *
     * @param y     The power for forward/backward movement
     * @param rx    The power for rotation
     * @param x     The power for strafing (lateral movement)
     * @param speed Speed modifier; a higher value means slower movement
     */
    @Override
    public void teleOpDrive(double y, double rx, double x, double speed) {
        RWE(DTMotors.ALL);
        frontLeft.SP(((y + rx)) / speed);
        backLeft.SP(((y + rx)) / speed);
        frontRight.SP(((y - rx)) / speed);
        backRight.SP(((y - rx)) / speed);
        midShift.SP(x * 1.5 / speed);
    }

    /**
     * Drives the robot in a specified direction for a given distance and speed
     *
     * @param direction The direction in which the robot should move
     * @param inches    The distance to move, in inches
     * @param speed     The speed at which the robot should move (0 to 1)
     */
    @Override
    public void drive(Directions direction, double inches, double speed) {
        SAR(DTMotors.ALL);
        RUE(DTMotors.ALL);
        int val;

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
            case LEFT:
                STP(DTMotors.MIDSHIFT, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.DRIVETRAIN_BASIC_4, 0);
                SP(DTMotors.ALL, speed);
                RTP(DTMotors.ALL);
                while (isBusy()) {
                }
                SP(DTMotors.ALL, 0);
                break;
            case RIGHT:
                STP(DTMotors.MIDSHIFT, EaseCommands.inTT_dt(inches));
                STP(DTMotors.DRIVETRAIN_BASIC_4, 0);
                SP(DTMotors.ALL, speed);
                RTP(DTMotors.ALL);
                while (isBusy()) {
                }
                SP(DTMotors.ALL, 0);
                break;
            case DIAGONAL_FORWARDS_RIGHT:
                val = EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.MIDSHIFT, val);
                STP(DTMotors.DRIVETRAIN_BASIC_4, val);
                SP(DTMotors.ALL, speed);
                RTP(DTMotors.ALL);
                while (isBusy()) {
                }
                SP(DTMotors.ALL, 0);
                break;
            case DIAGONAL_BACKWARDS_LEFT:
                val = -EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.MIDSHIFT, val);
                STP(DTMotors.DRIVETRAIN_BASIC_4, val);
                SP(DTMotors.ALL, speed);
                RTP(DTMotors.ALL);
                while (isBusy()) {
                }
                SP(DTMotors.ALL, 0);
                break;
            case DIAGONAL_FORWARDS_LEFT:
                val = EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.MIDSHIFT, -val);
                STP(DTMotors.DRIVETRAIN_BASIC_4, val);
                SP(DTMotors.ALL, speed);
                RTP(DTMotors.ALL);
                while (isBusy()) {
                }
                SP(DTMotors.ALL, 0);
                break;
            case DIAGONAL_BACKWARDS_RIGHT:
                val = -EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.MIDSHIFT, -val);
                STP(DTMotors.DRIVETRAIN_BASIC_4, val);
                SP(DTMotors.ALL, speed);
                RTP(DTMotors.ALL);
                while (isBusy()) {
                }
                SP(DTMotors.ALL, 0);
                break;
            default:
                break;
        }
    }

    /**
     * Checks if any of the drivetrain motors are still running
     *
     * @return True if any of the motors are busy, false otherwise
     */
    @Override
    public boolean isBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy() && backLeft.isBusy() && midShift.isBusy();
    }

    /**
     * Sets the power to specific motors in the drivetrain
     *
     * @param motors Motor group or specific motor to set power for
     * @param power  The power level (0 to 1)
     */
    @Override
    public void SP(DTMotors motors, double power) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.SP(power);
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.SP(power);
                frontRight.SP(power);
                backLeft.SP(power);
                backRight.SP(power);
                break;
            case ALL:
                frontLeft.SP(power);
                frontRight.SP(power);
                backLeft.SP(power);
                backRight.SP(power);
                midShift.SP(power);
                break;
        }
    }

    /**
     * Sets the target position for the drivetrain motors to RUN_TO_POSITION
     *
     * @param motors Motor group or specific motor to run to position
     */
    @Override
    public void RTP(DTMotors motors) {
        RTP(motors, true);
    }

    /**
     * Sets the target position for the drivetrain motors to RUN_TO_POSITION, optionally waiting for completion
     *
     * @param motors Motor group or specific motor to run to position
     * @param wait   Whether to wait for the motors to reach their target position
     */
    public void RTP(DTMotors motors, boolean wait) {
        Telemetry.Item hDriveRTPTelemetry = telemetry().addData("HDrive moving", "");
        switch (motors) {
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
            case MIDSHIFT:
                midShift.RTP();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.RTP();
                frontRight.RTP();
                backLeft.RTP();
                backRight.RTP();
                break;
            case ALL:
                frontLeft.RTP();
                frontRight.RTP();
                backLeft.RTP();
                backRight.RTP();
                midShift.RTP();
                break;
        }
        if (wait) {
            telemetry().update();
            while (isBusy()) {
            }
        }
        telemetry().removeItem(hDriveRTPTelemetry);
        telemetry().update();
    }

    /**
     * Sets the target position of a motor group using a case switch
     *
     * @param motors         Motor group to set the target position for
     * @param targetPosition Target position in encoder ticks
     */
    @Override
    public void STP(DTMotors motors, int targetPosition) {
        switch (motors) {
            case FRONT_LEFT:
                frontLeft.STP(targetPosition);
                break;
            case MIDSHIFT:
                midShift.STP(targetPosition);
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.STP(targetPosition);
                frontRight.STP(targetPosition);
                backLeft.STP(targetPosition);
                backRight.STP(targetPosition);
                break;
            case ALL:
                frontLeft.STP(targetPosition);
                frontRight.STP(targetPosition);
                backLeft.STP(targetPosition);
                backRight.STP(targetPosition);
                midShift.STP(targetPosition);
                break;
        }
    }

    /**
     * Resets the encoder values of the specified motors to zero
     *
     * @param motors Motor group or specific motor to reset
     */
    @Override
    public void SAR(DTMotors motors) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.SAR();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.SAR();
                frontRight.SAR();
                backLeft.SAR();
                backRight.SAR();
                break;
            case ALL:
                frontLeft.SAR();
                frontRight.SAR();
                backLeft.SAR();
                backRight.SAR();
                midShift.SAR();
                break;
        }
    }

    /**
     * Sets the motors to RUN_WITHOUT_ENCODER mode
     *
     * @param motors Motor group or specific motor
     */
    @Override
    public void RWE(DTMotors motors) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.RWE();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.RWE();
                frontRight.RWE();
                backLeft.RWE();
                backRight.RWE();
                break;
            case ALL:
                frontLeft.RWE();
                frontRight.RWE();
                backLeft.RWE();
                backRight.RWE();
                midShift.RWE();
                break;
        }
    }

    /**
     * Sets the motors to RUN_USING_ENCODER mode
     *
     * @param motors Motor group or specific motor
     */
    @Override
    public void RUE(DTMotors motors) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.RUE();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.RUE();
                frontRight.RUE();
                backLeft.RUE();
                backRight.RUE();
                break;
            case ALL:
                frontLeft.RUE();
                frontRight.RUE();
                backLeft.RUE();
                backRight.RUE();
                midShift.RUE();
                break;
        }
    }

    /**
     * Moves the drivetrain in a given direction, using holonomic properties for angles
     *
     * @param inches       The distance to move in inches
     * @param directionDeg The direction in degrees (0° is forward)
     */
    public void moveInDirection(double inches, int directionDeg) {
        double directionRad = Math.toRadians(directionDeg);

        // Calculate the directional components
        double xComponent = Math.cos(directionRad); // strafing
        double yComponent = Math.sin(directionRad); // driving

        double frontLeftPower = yComponent + xComponent;
        double frontRightPower = yComponent - xComponent;
        double backLeftPower = yComponent - xComponent;
        double backRightPower = yComponent + xComponent;

        double midShiftPower = xComponent * 1.5;

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
        midShift.SP(midShiftPower);

        RTP(DTMotors.ALL);

        while (isBusy()) {
        }

        SP(DTMotors.ALL, 0);
    }

}
