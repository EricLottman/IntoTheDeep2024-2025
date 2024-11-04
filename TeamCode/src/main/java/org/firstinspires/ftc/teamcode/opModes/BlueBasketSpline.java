package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.endEffectors.TwoStateServo;
import org.firstinspires.ftc.teamcode.backend.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.manipulators.Lift1Motor;

@Config
@Autonomous(name = "Test", group = "Autonomous")
public class BlueBasketSpline extends LinearOpMode {

    private TwoStateServo claw;
    private Lift1Motor lift;

    @Override
    public void runOpMode() {

        double
                blockHeading = 3 * Math.PI / 2,
                basketHeading = Math.PI / 4,
                endHeading = Math.PI / 2;

        Vector2d
                initialVector = new Vector2d(11.8, 61.7),
                basketVector = new Vector2d(60, 52),
                midBlockVector = new Vector2d(60, 38),
                rightBlockVector = new Vector2d(50, 38),
                leftBlockVector = new Vector2d(70, 38),
                endVector = new Vector2d(-52, 52);

        Pose2d
                initialPose = new Pose2d(initialVector, blockHeading),
                basketPose = new Pose2d(basketVector, basketHeading),
                midBlockPose = new Pose2d(midBlockVector, blockHeading),
                rightBlockPose = new Pose2d(rightBlockVector, blockHeading),
                leftBlockPose = new Pose2d(leftBlockVector, blockHeading);

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        claw = new TwoStateServo("claw", new Servo("grabber", hardwareMap, telemetry), telemetry, 0, 1, true);
        lift = new Lift1Motor("lift", new Motor("lift", hardwareMap, telemetry), telemetry, 0, 4200);
        lift.addLevels(0); // base level
        lift.addLevels(1500); // middle bucket height
        lift.addLevels(4200); // top bucket height

        Actions.runBlocking(new ParallelAction(
                claw.close(),
                lift.goToLevel(0)
        ));

        while (!isStopRequested() && !opModeIsActive()) { // essentially wait for start
        }

        if (opModeIsActive()) {

            Actions.runBlocking(drive.actionBuilder(initialPose)
                    .splineTo(midBlockVector, blockHeading) // mid block
                    .stopAndAdd(claw.close()) // first grab
                    .waitSeconds(.1)
                    .afterTime(0, lift.goToLevel(2)) // lift up
                    .lineToYLinearHeading(52, basketHeading) // basket
                    .stopAndAdd(lift.untilPosition()) // make sure lift up
                    .strafeTo(basketVector.plus(new Vector2d(2, 2))) // position over basket
                    .stopAndAdd(claw.open()) // first drop
                    .strafeTo(basketVector) // position back
                    .afterTime(0, lift.goToLevel(0)) // lift down
                    .splineTo(rightBlockVector, blockHeading) // right block
                    .stopAndAdd(new SequentialAction(lift.untilPosition(), claw.close())) // make sure lift is down, then second grab
                    .waitSeconds(.1)
                    .afterTime(0, lift.goToLevel(2)) // lift up
                    .splineTo(basketVector, basketHeading) // basket
                    .stopAndAdd(lift.untilPosition()) // make sure lift up
                    .strafeTo(basketVector.plus(new Vector2d(2, 2))) // position over basket
                    .stopAndAdd(claw.open()) // second drop
                    .strafeTo(basketVector) // position back
                    .afterTime(0, lift.goToLevel(0)) // lift down
                    .splineTo(leftBlockVector, blockHeading) // left block
                    .stopAndAdd(new SequentialAction(lift.untilPosition(), claw.close())) // make sure lift is down, then third grab
                    .splineTo(basketVector, basketHeading) // basket
                    .stopAndAdd(lift.untilPosition()) // make sure lift up
                    .strafeTo(basketVector.plus(new Vector2d(2, 2))) // position over basket
                    .stopAndAdd(claw.open()) // third drop
                    .afterDisp(24, lift.goToLevel(0)) // after we get 2 feet awat we start to bring the lift down
                    .strafeToLinearHeading(endVector, endHeading) // go to end
                    .build()
            );
        }
    }
}
