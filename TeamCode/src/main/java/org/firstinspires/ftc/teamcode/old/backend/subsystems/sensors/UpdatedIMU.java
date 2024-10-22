package org.firstinspires.ftc.teamcode.old.backend.subsystems.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * UpdatedIMU is a subsystem class that encapsulates the functionality of the
 * BNO055IMU (Bosch Inertial Measurement Unit) sensor for use in FTC robots.
 * This class provides methods for retrieving orientation, acceleration,
 * and temperature data from the IMU.
 */
public class UpdatedIMU extends subsystem {
    private Orientation globalAngles = new Orientation();
    private double referenceAngle;
    private BNO055IMU imu;
    private BNO055IMU.Parameters parameters;

    /**
     * Constructs an UpdatedIMU object using the default BNO055IMU parameters.
     *
     * @param name      The name of the IMU sensor in the HardwareMap.
     * @param hwMap     The robot's HardwareMap to initialize the IMU.
     * @param telemetry The telemetry instance for logging.
     */
    public UpdatedIMU(String name, @NonNull HardwareMap hwMap, Telemetry telemetry) {
        this(name, hwMap, null, telemetry);
    }

    /**
     * Constructs an UpdatedIMU object, optionally using custom BNO055IMU parameters.
     *
     * @param name       The name of the IMU sensor in the HardwareMap.
     * @param hwMap      The robot's HardwareMap to initialize the IMU.
     * @param parameters The custom parameters to configure the IMU, or null for defaults.
     * @param telemetry  The telemetry instance for logging.
     */
    public UpdatedIMU(String name, @NonNull HardwareMap hwMap, BNO055IMU.Parameters parameters, Telemetry telemetry) {
        super(hwMap, telemetry);
        imu = hwMap.get(BNO055IMU.class, name);
        if (parameters != null) {
            this.parameters = parameters;
        } else {
            this.parameters = new BNO055IMU.Parameters();
            this.parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            this.parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            this.parameters.loggingEnabled = false;
            imu.initialize(this.parameters);
        }
        referenceAngle = 0;
    }

    /**
     * Resets the reference angle of the IMU to zero. This is useful for recalibrating
     * the robot's heading during operation
     */
    public void resetAngle() {
        globalAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        referenceAngle = 0;
    }

    /**
     * Gets the current angle of the robot in degrees
     *
     * @return The current angle in degrees
     */
    public double getAngle() {
        return this.getAngle(AngleUnit.DEGREES);
    }

    /**
     * Gets the current angle of the robot in the specified angle unit
     *
     * @param angleunit The angle unit in which to retrieve the angle
     * @return The current angle in the specified angle unit
     */
    public double getAngle(AngleUnit angleunit) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleunit);
        referenceAngle += AngleUnit.DEGREES.normalizeDegrees(angles.firstAngle);
        globalAngles = angles;
        return referenceAngle;
    }

    /**
     * Gets the global angle of the robot in the specified angle unit
     *
     * @param angleunit The angle unit in which to retrieve the global angle
     * @return The global angle in the specified angle unit
     */
    public double getGlobalAngle(AngleUnit angleunit) {
        return globalAngles.firstAngle;
    }

    /**
     * Gets the current linear acceleration of the robot
     *
     * @return The linear acceleration as measured by the IMU
     */
    public Acceleration getAcceleration() {
        return imu.getLinearAcceleration();
    }

    /**
     * Retrieves the temperature as measured by the IMU
     *
     * @return The current temperature measured by the IMU
     */
    public Temperature getTemp() {
        return imu.getTemperature();
    }
}
