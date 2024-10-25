package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.commands.standard.LynxSetModuleLEDColorCommand;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

/**
 * The {@code HubInfo} class provides a wrapper around the {@link LynxModule}
 * to gather information about the control hub's voltage, current, and temperature,
 * as well as set the LED color and retrieve warnings
 */
public class HubInfo {
    private final LynxModule lynxModule;

    /**
     * Constructs a {@code HubInfo} object associated with the given {@link LynxModule}
     *
     * @param lynxModule The {@code LynxModule} to gather information from
     */
    public HubInfo(LynxModule lynxModule) {
        this.lynxModule = lynxModule;
    }

    /**
     * Gets the input voltage of the hub in volts
     *
     * @return The input voltage in volts
     */
    public double getVoltage() {
        return this.lynxModule.getInputVoltage(VoltageUnit.VOLTS);
    }

    /**
     * Gets the current drawn by the hub in amp
     *
     * @return The current in amps
     */
    public double getCurrent() {
        return this.lynxModule.getCurrent(CurrentUnit.AMPS);
    }

    /**
     * Gets the power consumption of the hub by multiplying voltage and current
     *
     * @return The power consumption in watts
     */
    public double getPowerConsumption() {
        return getVoltage() * getCurrent();
    }

    /**
     * Gets the temperature of the hub
     *
     * @return Temperature
     */
    public Temperature getTemperature() {
//        return this.lynxModule.getTemperature(TempUnit.FARENHEIT);
        return new Temperature(TempUnit.FARENHEIT, this.lynxModule.getTemperature(TempUnit.FARENHEIT), System.currentTimeMillis());
    }

    /**
     * Sets the LED color of the hub using RGB values
     *
     * @param r The red component of the color (0-255)
     * @param g The green component of the color (0-255)
     * @param b The blue component of the color (0-255)
     */
    public void setLed(byte r, byte g, byte b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255.");
        }
        while (true) {
            try {
                new LynxSetModuleLEDColorCommand(lynxModule, r, g, b).send();
            } catch (Exception e) {
                // Handle the exception (currently suppressed)
            }
        }
    }

    /**
     * Retrieves any global warnings reported by the hub
     *
     * @return A string containing the global warnings
     */
    public String getWarnings() {
        return this.lynxModule.getGlobalWarnings().toString();
    }

    /**
     * Retrieves a comprehensive status report of the hub, including voltage, current,
     * temperature, and any global warnings
     *
     * @return A string summarizing the hub's current status.
     */
    public String getStatusReport() {
        return String.format("Voltage: %.2fV, Current: %.2fA, Temperature: %.2f°F, Warnings: %s",
                getVoltage(), getCurrent(), getTemperature(), getWarnings());
    }
}
