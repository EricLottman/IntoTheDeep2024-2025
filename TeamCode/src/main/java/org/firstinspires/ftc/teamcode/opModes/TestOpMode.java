package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.endEffectors.TwoStateServo;
import org.firstinspires.ftc.teamcode.backend.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "Test", group = "Autonomous")
public class TestOpMode extends LinearOpMode {

    private TwoStateServo claw;

    @Override
    public void runOpMode() {

        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        claw = new TwoStateServo("claw", new Servo("grabber", hardwareMap, telemetry), telemetry, 0, 1, true);

//        Actions.runBlocking(claw.close());

        TrajectoryActionBuilder test = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(10, 10), Math.toRadians(90));

        while (!isStopRequested() && !opModeIsActive()) { // essentially wait for start
        }

        while (opModeIsActive()) {
        }
    }
}
