package org.firstinspires.ftc.teamcode.old.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.WarningRumbler;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;

public class Basic extends LinearOpMode {
    @Override
    public void runOpMode() {
        WarningRumbler endGame = new WarningRumbler(gamepad1, 80, Gamepad.Type.SONY_PS4);
        Mecanum drivetrain = new Mecanum(new Motor[]{new Motor("backLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry), new Motor("backRight", hardwareMap, telemetry), new Motor("frontLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry), new Motor("frontRight", hardwareMap, telemetry)}, telemetry);

        waitForStart();
        endGame.startTimer();

        while (opModeIsActive()) {
            drivetrain.teleOpDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
            endGame.checkAndRumble(); // will rumble after 80 seconds (10 seconds left)
        }
    }
}