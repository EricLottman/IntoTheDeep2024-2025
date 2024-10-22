package org.firstinspires.ftc.teamcode.old.backend.subsystems.actuators.base;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorEncoderPositionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorChannelModeCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorTargetPositionCommand;
import com.qualcomm.robotcore.exception.TargetPositionNotSetException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * A class representing a motor and providing methods for controlling and interacting with it.
 * This class extends the functionality of {@link DcMotorEx} to simplify the use of motors
 */
public class Motor extends subsystem implements Comparable<Motor> {
    private DcMotorEx motor;
    private String name;
    private Integer globalTicks, tolerance, targetPosition;
    private final LynxModule lynxModule;
    private final int motorPort;

    /**
     * Constructs a {@code Motor} object without specifying a direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public Motor(String name, HardwareMap hwMap, Telemetry telemetry) {
        this(name, hwMap, DcMotorSimple.Direction.FORWARD, telemetry);
    }

    /**
     * Constructs a {@code Motor} object with a specified direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param direction The direction of the motor
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public Motor(String name, HardwareMap hwMap, DcMotorSimple.Direction direction, Telemetry telemetry) {
        super(hwMap, telemetry);
        motor = hwMap.get(DcMotorEx.class, name);
        motor.setDirection(direction);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.name = name;
        lynxModule = (LynxModule) motor.getController();
        motorPort = motor.getPortNumber();
        tolerance = 10;
        targetPosition = null;
    }

    /**
     * Returns the name of the motor
     *
     * @return The name of the motor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the power of the motor
     *
     * @param power The power of the motor, between -1.0 and 1.0
     */
    public void SP(double power) {
        motor.setPower(power);
    }

    /**
     * Sets a target position for the motor in encoder ticks
     *
     * @param targetPosition The target position in ticks
     */
    public void STP(int targetPosition) {
        this.targetPosition = targetPosition + globalTicks;
        while (true) {
            try {
                new LynxSetMotorTargetPositionCommand(lynxModule, motorPort, this.targetPosition, tolerance).send();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Sets the motor to run to the target position
     *
     * @throws TargetPositionNotSetException If the target position is not set before calling this method
     */
    public void RTP() throws TargetPositionNotSetException {
        if (this.targetPosition == null)
            throw new TargetPositionNotSetException();
        while (true) {
            try {
                new LynxSetMotorChannelModeCommand(lynxModule, motorPort, DcMotor.RunMode.RUN_TO_POSITION, DcMotor.ZeroPowerBehavior.BRAKE).send();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Resets the motor's encoder and sets the global ticks
     */
    public void SAR() {
        globalTicks = GCP();
    }

    /**
     * Sets the motor to run without using encoders
     */
    public void RWE() {
        while (true) {
            try {
                new LynxSetMotorChannelModeCommand(lynxModule, motorPort, DcMotor.RunMode.RUN_WITHOUT_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE).send();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Sets the motor to run using the encoders
     */
    public void RUE() {
        while (true) {
            try {
                new LynxSetMotorChannelModeCommand(lynxModule, motorPort, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE);
            } catch (Exception e) {

            }
        }
    }

    /**
     * Sets the tolerance for determining whether the motor has reached its target position
     *
     * @param ticks The number of encoder ticks within which the motor is considered to have reached its target
     */
    public void ST(int ticks) {
        this.tolerance = ticks;
    }

    /**
     * Returns whether the motor is busy (still moving to its target position)
     *
     * @return {@code true} if the motor is busy, {@code false} otherwise
     */
    public boolean isBusy() {
        return Math.abs(GCP() - GTP()) > this.tolerance;
    }

    /**
     * Returns the current position of the motor in encoder ticks
     *
     * @return The current position of the motor in ticks
     */
    public int GCP() {
        while (true) {
            try {
                return new LynxGetMotorEncoderPositionCommand(lynxModule, motorPort).sendReceive().getPosition() - globalTicks;
            } catch (Exception e) {

            }
        }
    }

    /**
     * Returns the target position of the motor in encoder ticks
     *
     * @return The target position of the motor in ticks
     */
    public int GTP() {
        return this.targetPosition;
    }

    /**
     * Returns the current power of the motor
     *
     * @return The power of the motor as a double between -1.0 and 1.0
     */
    public double GP() {
        return motor.getPower();
    }

    protected MotorConfigurationType getMotorConfiguration() {
        return motor.getMotorType();
    }

    /**
     * Compares this motor to another motor based on their names
     *
     * @param otherMotor The other motor to compare to
     * @return A negative integer, zero, or a positive integer as this motor's name is lexicographically less than, equal to, or greater than the other motor's name
     */
    @Override
    public int compareTo(Motor otherMotor) {
        return this.name.compareTo(otherMotor.getName());
    }
}
