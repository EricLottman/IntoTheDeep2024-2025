package org.firstinspires.ftc.teamcode.backend.subsystems.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

public class TouchSensor extends subsystem {

    private com.qualcomm.robotcore.hardware.TouchSensor touchSensor;

    /**
     * TouchSensor Object
     *
     * @param name      Name of the TouchSensor in the phone
     * @param hwMap     HardwareMap object from OpMode
     * @param telemetry Telemetry object from OpMode
     */
    public TouchSensor(String name, @NonNull HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry);
        touchSensor = hwMap.get(com.qualcomm.robotcore.hardware.TouchSensor.class, name);
    }

    /**
     * Returns the value on whether or not the sensor is pressed
     *
     * @return Boolean, true if pressed
     */
    public boolean isPressed() {
        return touchSensor.isPressed();
    }

    /**
     * Forces a loop until we hit an object with the sensor
     */
    public void untilHitObject() {
        while (true) {
            if (isPressed())
                break;
        }
    }

}
