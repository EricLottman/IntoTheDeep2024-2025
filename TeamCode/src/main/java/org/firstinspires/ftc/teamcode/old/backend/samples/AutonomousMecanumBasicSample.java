package org.firstinspires.ftc.teamcode.old.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainMotorControls;

@Disabled
@Autonomous
public class AutonomousMecanumBasicSample extends LinearOpMode {
    public static Motor frontLeft, frontRight, backLeft, backRight;

    public void runOpMode() {
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        frontRight = new Motor("frontRight", hardwareMap, telemetry);
        backLeft = new Motor("backLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        backRight = new Motor("backRight", hardwareMap, telemetry);

        // accessing motors in drivetrain any other way but through the mecanum class after this line will lead to an error
        Mecanum drivetrain = new Mecanum(new Motor[]{frontLeft, frontRight, backLeft, backRight}, telemetry);
        while (opModeIsActive()) {
            // drive forward 12 inches at a speed of 1 (full speed)
            drivetrain.drive(DrivetrainHolonomic.Directions.FORWARD, 12, 1);
        }
    }
}
