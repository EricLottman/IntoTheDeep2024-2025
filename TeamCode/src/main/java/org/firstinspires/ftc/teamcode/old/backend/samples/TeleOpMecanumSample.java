package org.firstinspires.ftc.teamcode.old.backend.samples;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.Imu;

/**
 * TeleOp code to test mecanum drive
 */
@Disabled
@TeleOp
public class TeleOpMecanumSample extends LinearOpMode {
    private static Motor frontLeft, frontRight, backLeft, backRight;
    private Imu imu;
    private boolean robotOriented = true, fast = true;

    public void runOpMode() {
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        frontRight = new Motor("frontRight", hardwareMap, telemetry);
        backLeft = new Motor("backLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        backRight = new Motor("backRight", hardwareMap, telemetry);

        imu = new Imu("imu", hardwareMap, RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD, telemetry);

        Mecanum drivetrain = new Mecanum(new Motor[]{frontLeft, frontRight, backLeft, backRight}, telemetry);
        while (opModeIsActive()) {
            // can change these if needed
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            if (robotOriented) {
                double botHeading = imu.getAngle(AngleUnit.RADIANS);
                double rotX = strafe * Math.cos(-botHeading) - drive * Math.sin(-botHeading);
                double rotY = strafe * Math.sin(-botHeading) + drive * Math.cos(-botHeading);

                rotX = rotX * 1.1;

                if (fast) {
                    drivetrain.teleOpDrive(rotY, turn, rotX);
                } else {
                    drivetrain.teleOpDrive(rotY, turn, rotX, 3);
                }
            } else if (fast) {
                drivetrain.teleOpDrive(drive, turn, strafe);
            } else {
                drivetrain.teleOpDrive(drive, turn, strafe, 3);
            }

            if (gamepad1.start && fast) {
                fast = false;
            } else if (gamepad1.start && !fast) {
                fast = true;
            }

            if (gamepad1.back && robotOriented) {
                robotOriented = false;
            } else if (gamepad1.back && !robotOriented) {
                robotOriented = true;
            }
        }
    }
}