package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;

@Autonomous
public class FindLimits extends LinearOpMode {

    Motor liftMotor;
    Servo claw;

    Gamepad last = new Gamepad();
    double clawPos = 0;

    final double BOOSTER = 8, CLAW_STEP = .05;

    @Override
    public void runOpMode() {
        liftMotor = new Motor("lift", hardwareMap, telemetry);
        claw = new Servo("claw", hardwareMap, telemetry);

        claw.setPosition(clawPos);
        liftMotor.setTargetPosition(0);
        liftMotor.runToPosition();

        waitForStart();

        while (opModeIsActive()) {
            if (!liftMotor.isBusy())
                liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + (int) ((BOOSTER * gamepad1.right_trigger) - (BOOSTER * gamepad1.left_trigger)));

            if (gamepad1.dpad_up && last.dpad_up)
                clawPos += CLAW_STEP;

            if (gamepad1.dpad_down && last.dpad_down)
                clawPos -= CLAW_STEP;

            telemetry.addData("lift: ", liftMotor.getCurrentPosition());
            telemetry.addData("servo: ", clawPos);
            telemetry.update();

            claw.setPosition(clawPos);
            last.copy(gamepad1);
            liftMotor.runToPosition();
        }
    }
}
