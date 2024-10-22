package org.firstinspires.ftc.teamcode.old.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.Integrator;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.UpdatedIMU;

@Disabled
@TeleOp
public class IntegratorSample extends LinearOpMode {
    UpdatedIMU imu;
    private Integrator integrator = new Integrator();
    private Thread integratorThread;
    private volatile boolean running = true; // use volatile to ensure visibility across threads

    @Override
    public void runOpMode() {
        imu = new UpdatedIMU("imu", hardwareMap, telemetry);

        integratorThread = new Thread(() -> {
            ElapsedTime timer = new ElapsedTime();
            double lastTime = 0;
            while (running) {
                // get current data
                Acceleration acceleration = imu.getAcceleration();
                double xA = acceleration.xAccel;
                double yA = acceleration.yAccel;
                double heading = imu.getAngle();

                double currentTime = timer.seconds();
                double timeDelta = currentTime - lastTime; // less taxing then a timer.reset();
                lastTime = currentTime; // update state for loop

                // update the integrator
                integrator.update(xA, yA, heading, timeDelta);
                timer.reset();

                // sleep to control stress on the cpu, as well as accuracy
                try {
                    Thread.sleep(20); // bigger to stress cpu less but will make result less accurate, vice versa with smaller values
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        integratorThread.start();

        waitForStart();

        while (opModeIsActive()) {
//            telemetry.addData("Velocity X: ", integrator.getVelocityX());
//            telemetry.addData("Velocity Y: ", integrator.getVelocityY());
            telemetry.addData("Position X: ", integrator.getPositionX());
            telemetry.addData("Position Y: ", integrator.getPositionY());
            telemetry.update();
        }
        // stop the integrator when opMode is no longer active
        running = false;
        try {
            integratorThread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
