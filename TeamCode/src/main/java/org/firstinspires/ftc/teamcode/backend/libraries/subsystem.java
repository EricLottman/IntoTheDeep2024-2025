package org.firstinspires.ftc.teamcode.backend.libraries;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Base class for subsystems, used to facilitate hardware mapping and telemetry reporting
 * This class simplifies the handling of objects that interact with hardware components and telemetry output
 */
public class subsystem {
    HardwareMap hwMap;
    Telemetry telemetry;

    /**
     * Constructor for a subsystem that does not require hardware mapping
     * This can be used for classes where pre-mapped hardware objects are passed
     *
     * @param telemetry The {@link Telemetry} object used to report runtime information
     */
    public subsystem(Telemetry telemetry) {
        this(null, telemetry);
    }

    /**
     * Constructor for a subsystem that requires hardware mapping
     * This is used to initialize subsystems that interact with hardware devices
     *
     * @param hardwareMap The {@link HardwareMap} object used to map hardware components
     * @param telemetry   The {@link Telemetry} object used to report runtime information
     */
    public subsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        hwMap = hardwareMap;
        this.telemetry = telemetry;
    }

    /**
     * Returns the telemetry object associated with this subsystem
     * This allows the subsystem to report status and debug information during runtime
     *
     * @return The {@link Telemetry} object
     */
    public Telemetry telemetry() {
        return this.telemetry;
    }
}
