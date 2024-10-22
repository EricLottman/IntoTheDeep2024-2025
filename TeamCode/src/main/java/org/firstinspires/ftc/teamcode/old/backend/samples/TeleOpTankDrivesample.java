package org.firstinspires.ftc.teamcode.old.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.TankDrive;

/**
 * TeleOp code to test hdrive
 */
@Disabled
@TeleOp
public class TeleOpTankDrivesample extends LinearOpMode {
    public static Motor frontLeft, frontRight, backLeft, backRight;

    public void runOpMode() {
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        frontRight = new Motor("frontRight", hardwareMap, telemetry);
        backLeft = new Motor("backLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        backRight = new Motor("backRight", hardwareMap, telemetry);

        TankDrive drivetrain = new TankDrive(new Motor[]{frontLeft, frontRight, backLeft, backRight}, telemetry);
        while (opModeIsActive()) {
            double left = gamepad1.left_stick_y;
            double right = gamepad1.right_stick_y;

            drivetrain.teleOpDrive(left, right);
        }
    }
}