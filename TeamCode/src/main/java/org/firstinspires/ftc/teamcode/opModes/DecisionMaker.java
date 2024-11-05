package org.firstinspires.ftc.teamcode.opModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.backend.roadrunner.MecanumDrive;


@Autonomous(name = "Decision maker", group = "Autonomous")
public class DecisionMaker extends LinearOpMode {
    @Override
    public void runOpMode() {

        Pose2d position = new Pose2d(0, 0, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, position);

        waitForStart();

        while (opModeIsActive()) {
            Pose2d currentPosition = position;
            position = new Pose2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60, Math.random() * 3 * Math.PI / Math.random() * 3);
            Actions.runBlocking(drive.actionBuilder(currentPosition)
                    .splineToSplineHeading(position, Math.random() * 3 * Math.PI / (Math.random() * 3 + 0.1))
                    .build());
        }
    }
}
