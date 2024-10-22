package org.firstinspires.ftc.teamcode.old.backend.subsystems.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * Methods to streamline the usage of the IMU
 */
public class Imu extends subsystem {
    private Orientation globalAngles = new Orientation();
    private double referenceAngle;
    private IMU imu;

    /**
     * IMU Object
     *
     * @param name          Name of the IMU in the phone
     * @param hwMap         HardwareMap object from OpMode
     * @param logoDirection Logo Direction
     * @param usbDirection  USB Direction
     */
    public Imu(String name, @NonNull HardwareMap hwMap, RevHubOrientationOnRobot.LogoFacingDirection logoDirection, RevHubOrientationOnRobot.UsbFacingDirection usbDirection, Telemetry telemetry) {
        super(hwMap, telemetry);
        imu = hwMap.get(IMU.class, name);
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        referenceAngle = 0;
    }

    /**
     * Resets the global angle of the IMU
     */
    public void resetAngle() {
        globalAngles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        referenceAngle = 0;
    }

    /**
     * Returns the current angle of the robot
     *
     * @return referenceAngle Current angle
     */
    public double getAngle(AngleUnit angleunit) {
        // imu works in eulear angles so we have to detect when it rolls across the backwards 180 threshold
        Orientation angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleunit);
        double change = angles.firstAngle - globalAngles.firstAngle;

        if (change < -180) {
            change += 360;
        } else if (change > 180) {
            change -= 360;
        }
        referenceAngle += change;
        globalAngles = angles;
        return referenceAngle;
    }
}
