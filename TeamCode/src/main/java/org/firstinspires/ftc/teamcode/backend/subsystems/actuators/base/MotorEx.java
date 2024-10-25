package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The {@code MotorEx} class extends the {@link Motor} class and provides additional functionality
 * for motors with customizable configurations such as gear ratios
 * It supports REV UltraPlanetary motors but for that you need to specify gearbox ratio at the end of the constructor
 *
 * @see Motor
 * @see MotorTypeRegistry
 */
public class MotorEx extends Motor {

    private final MotorConfigurationType motorConfiguration;

    /**
     * Constructs a {@code MotorEx} object without specifying a direction
     * <p>
     * This constructor assumes that the motor is configured with a forward direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param telemetry The {@link Telemetry} object used to display runtime information
     * @param motorType The type of the motor, as defined in {@link MotorTypeRegistry.MotorType}
     */
    public MotorEx(String name, HardwareMap hwMap, Telemetry telemetry, MotorTypeRegistry.MotorType motorType) {
        this(name, hwMap, DcMotorSimple.Direction.FORWARD, telemetry, motorType);
    }

    /**
     * Constructs a {@code MotorEx} object with a specified direction
     * <p>
     * This constructor allows you to specify the direction of the motor as forward ('f') or reverse ('r')
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param direction The direction of the motor
     * @param telemetry The {@link Telemetry} object used to display runtime information
     * @param motorType The type of the motor, as defined in {@link MotorTypeRegistry.MotorType}
     */
    public MotorEx(String name, HardwareMap hwMap, DcMotorSimple.Direction direction, Telemetry telemetry, MotorTypeRegistry.MotorType motorType) {
        super(name, hwMap, direction, telemetry);
        if (motorType == MotorTypeRegistry.MotorType.REV_ULTRAPLANETARY_HD_HEX_MOTOR)
            throw new IllegalArgumentException(motorType.toString() + " is invalid for constructor public MotorEx(String name, HardwareMap hwMap, String direction, Telemetry telemetry, MotorTypeRegistry.MotorType motorType)\n Use constructor public MotorEx(String name, HardwareMap hwMap, String direction, Telemetry telemetry, MotorTypeRegistry.MotorType motorType, double gearboxRatio)");
        this.motorConfiguration = MotorTypeRegistry.motorTypeMap.get(motorType);
    }

    /**
     * Constructs a {@code MotorEx} object for a Rev Ultrapanetary HD Hex Motor with a custom gearbox ratio
     * <p>
     * This constructor allows you to set a custom gear ratio, which is applied to the REV UltraPlanetary motor configuration
     *
     * @param name         The name of the motor as configured in the robot configuration on the phone
     * @param hwMap        The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param direction    The direction of the motor
     * @param telemetry    The {@link Telemetry} object used to display runtime information
     * @param motorType    The type of the motor, as defined in {@link MotorTypeRegistry.MotorType}
     * @param gearboxRatio The gearbox ratio to be applied for motors like the REV UltraPlanetary HD Hex motor
     */
    public MotorEx(String name, HardwareMap hwMap, DcMotorSimple.Direction direction, Telemetry telemetry, MotorTypeRegistry.MotorType motorType, double gearboxRatio) {
        // 3:1 is 2.89:1, 4:1 is 3.61:1, 5:1 is 5.23:1
        super(name, hwMap, direction, telemetry);
        this.motorConfiguration = MotorTypeRegistry.motorTypeMap.get(motorType);
        if (motorType == MotorTypeRegistry.MotorType.REV_ULTRAPLANETARY_HD_HEX_MOTOR) {
            motorConfiguration.setTicksPerRev(28 * gearboxRatio);
            motorConfiguration.setMaxRPM(6000 / gearboxRatio);
            motorConfiguration.setGearing(gearboxRatio);
        } else {
            throw new IllegalArgumentException("The constructor used to create a MotorEx object of type " + motorType.toString() + " only supports creation of DcMotorEx objects of type " + MotorTypeRegistry.MotorType.REV_ULTRAPLANETARY_HD_HEX_MOTOR.toString());
        }
    }
}
