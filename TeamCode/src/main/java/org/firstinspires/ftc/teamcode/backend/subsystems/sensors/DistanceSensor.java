package org.firstinspires.ftc.teamcode.backend.subsystems.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

public class DistanceSensor extends subsystem {
    private com.qualcomm.robotcore.hardware.DistanceSensor distanceSensor;

    /**
     * DistanceSensor Object
     *
     * @param name  Name of the DistanceSensor in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public DistanceSensor(String name, @NonNull HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry);
        distanceSensor = hwMap.get(com.qualcomm.robotcore.hardware.DistanceSensor.class, name);
    }

    /**
     * Returns distance
     *
     * @return Distance as double
     */
    public double getDistance() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }

    /**
     * Returns distance based on unit provided
     *
     * @param distanceUnit DistanceUnit you want distance in
     * @return Distance in double
     */
    public double getDistance(DistanceUnit distanceUnit) {
        return distanceSensor.getDistance(distanceUnit);
    }

    /**
     * Forces a loop until we notice an object within a certain distance, object must be 5 inches closer than the background
     *
     * @param distance Max amount of inches we expect the object to be from us
     */
    public void untilPassObject(double distance) {
        untilPassObject(distance, 5);
    }

    /**
     * Forces a loop until we notice an object within a certain distance
     *
     * @param distance Max amount of inches we expect the object to be from us
     * @param delta    Amount min amount of inches we expect the object to be from the background, higher value can reduce false readings and means we dont expect to be near a wall
     */
    public void untilPassObject(double distance, double delta) {
        untilPassObject(distance, delta, DistanceUnit.INCH);
    }

    /**
     * Forces a loop until we notice an object within a certain distance
     *
     * @param distance     Max amount of distance we expect the object to be from us
     * @param delta        Amount min amount of distance we expect the object to be from the background, higher value can reduce false readings and means we dont expect to be near a wall
     * @param distanceUnit Unit of distance, ex. Inches, meters, mm, cm
     */
    public void untilPassObject(double distance, double delta, DistanceUnit distanceUnit) {
        telemetry().addData("Waiting till passing an object ", distance + " " + distanceUnit.toString() + " away");
        telemetry().update();
        double old = distanceSensor.getDistance(distanceUnit);
        while (true) {
            double current = distanceSensor.getDistance(distanceUnit);
            if (old - current > delta && old < distance)
                break;
        }
        telemetry().clear();
    }

    /**
     * Forces a loop until we notice an object within a certain distance, object must be 5 inches closer than the background
     *
     * @param distance Max amount of inches we expect the object to be from us
     */
    public void untilHitObject(double distance) {
        untilHitObject(distance, 5);
    }

    /**
     * Forces a loop until we notice an object within a certain distance
     *
     * @param distance Max amount of inches we expect the object to be from us
     * @param delta    Amount min amount of inches we expect the object to be from the background, higher value can reduce false readings and means we dont expect to be near a wall
     */
    public void untilHitObject(double distance, double delta) {
        untilHitObject(distance, delta, DistanceUnit.INCH);
    }

    /**
     * Forces a loop until we notice an object within a certain distance
     *
     * @param distance     Max amount of distance we expect the object to be from us
     * @param delta        Amount min amount of distance we expect the object to be from the background, higher value can reduce false readings and means we dont expect to be near a wall
     * @param distanceUnit Unit of distance, ex. Inches, meters, mm, cm
     */
    public void untilHitObject(double distance, double delta, DistanceUnit distanceUnit) {
        telemetry().addData("Waiting till we see an object ", distance + " " + distanceUnit.toString() + " away");
        telemetry().update();
        double old = distanceSensor.getDistance(distanceUnit);
        while (true) {
            double current = distanceSensor.getDistance(distanceUnit);
            if (current - old > delta && current < distance)
                break;
        }
        telemetry().clear();
    }
}
