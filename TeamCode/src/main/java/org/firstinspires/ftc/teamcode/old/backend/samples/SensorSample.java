package org.firstinspires.ftc.teamcode.old.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainMotorControls;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.ColorSensor;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.DistanceSensor;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.TouchSensor;

/**
 * TeleOp code to test sensors, will be updated as more sensors get added, uses mecanum drive
 */
@Disabled
@TeleOp
public class SensorSample extends LinearOpMode {
    private static Motor frontLeft, frontRight, backLeft, backRight;
    private static ColorSensor colorSensor;
    private static DistanceSensor distanceSensor;
    private static TouchSensor touchSensor;

    public void runOpMode(){
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        frontRight = new Motor("frontRight", hardwareMap, telemetry);
        backLeft = new Motor("backLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        backRight = new Motor("backRight", hardwareMap, telemetry);
        colorSensor = new ColorSensor("colorSensor", hardwareMap, telemetry);
        distanceSensor = new DistanceSensor("distanceSensor", hardwareMap, telemetry);
        touchSensor = new TouchSensor("touchSensor", hardwareMap, telemetry);

        Mecanum drivetrain = new Mecanum(new Motor[]{frontLeft, frontRight, backLeft, backRight}, telemetry);
        drivetrain.RWE(DrivetrainMotorControls.DTMotors.DRIVETRAIN_BASIC_4);
        drivetrain.SP(DrivetrainMotorControls.DTMotors.DRIVETRAIN_BASIC_4, 0);

        waitForStart();
        while (opModeIsActive()){

            if (gamepad1.a) { // if you press a, auto drive forward until wall is hit
                drivetrain.SP(DrivetrainMotorControls.DTMotors.DRIVETRAIN_BASIC_4, .75);
                touchSensor.untilHitObject();
                drivetrain.SP(DrivetrainMotorControls.DTMotors.DRIVETRAIN_BASIC_4, 0);
            } else {
                // allow driver control
                drivetrain.teleOpDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
            }

            telemetry.addData("Color: ", colorSensor.getColor());
            telemetry.addData("Distance: ", distanceSensor.getDistance(DistanceUnit.INCH) + "in.");
            telemetry.addData("Touch Sensor pressed: ", touchSensor.isPressed());
            telemetry.update();
        }
    }
}