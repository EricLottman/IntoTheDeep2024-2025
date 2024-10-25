package org.firstinspires.ftc.teamcode.backend.subsystems.sensors;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;

import java.util.Objects;

// add a while not at color, also make color an enum

/**
 * Methods to streamline the usage of ColorSensors
 */
public class ColorSensor extends subsystem {
    private com.qualcomm.robotcore.hardware.ColorSensor colorSensor;

    /**
     * ColorSensor Object
     *
     * @param name  Name of the ColorSensor in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public ColorSensor(String name, @NonNull HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry);
        colorSensor = hwMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, name);
    }

    /**
     * Returns red value
     *
     * @return Red value
     */
    public double getRed() {
        return colorSensor.red();
    }

    /**
     * Returns blue value
     *
     * @return Blue value
     */
    public double getBlue() {
        return colorSensor.blue();
    }

    /**
     * Returns green value
     *
     * @return Green value
     */
    public double getGreen() {
        return colorSensor.green();
    }

    /**
     * Returns color as string
     *
     * @return Color
     */

    public String getColor() {
        return EaseCommands.findClosestColor(colorSensor.red(), colorSensor.green(), colorSensor.blue()).toString();
    }

    /**
     * Tells you how close to a given color the sensor is right now
     *
     * @param color Color you want to determine similarity to
     * @return Similarity score
     */
    public double getSimilarity(EaseCommands.Colors color) {
        return EaseCommands.calculateColorSimilarity(Objects.requireNonNull(EaseCommands.basicColors.get(color)), colorSensor.red(), colorSensor.green(), colorSensor.blue());
    }

    /**
     * Controls the led on the color sensor
     *
     * @param power Boolean representing if power is on (true) or off (false)
     */
    public void setLed(boolean power) {
        colorSensor.enableLed(power);
    }
}