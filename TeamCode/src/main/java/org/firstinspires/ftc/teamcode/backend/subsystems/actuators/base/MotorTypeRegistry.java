package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.HashMap;

/**
 * The {@code MotorTypeRegistry} class is responsible for registering different types of motor configurations
 * used in FTC robotics. It maintains a registry of various motor types and their respective configuration
 * parameters, such as ticks per revolution, maximum RPM, gearing, and achievable maximum RPM fraction
 *
 * <p>This class defines an enumeration of motor types and maps them to their respective {@link MotorConfigurationType}
 * objects, which contain the detailed hardware configuration for each motor. It is designed to facilitate
 * the configuration and use of specific motors in an FTC robot by providing a consistent and centralized
 * place to retrieve motor parameters</p>
 */
public class MotorTypeRegistry {

    /**
     * Enum representing different types of motors available in the FTC ecosystem
     * Each motor type corresponds to a specific motor configuration
     */
    protected enum MotorType {
        TORQUENADO,
        NEVEREST_40_GEARMOTOR, NEVEREST_60_GEARMOTOR,
        NEVEREST_3_7_ORBITAL_GEARMOTOR, NEVEREST_20_ORBITAL_GEARMOTOR,
        REV_HD_HEX_SPUR, REV_HD_HEX_20_SPUR, REV_HD_HEX_40_SPUR,
        REV_HEX_CORE,
        MAVERICK_61_GEARMOTOR,
        NEVEREST_HEX_1_GEARMOTOR, NEVEREST_HEX_3_7_GEARMOTOR, NEVEREST_HEX_13_7_GEARMOTOR, NEVEREST_HEX_19_2_GEARMOTOR, NEVEREST_HEX_50_9_GEARMOTOR, NEVEREST_HEX_263_7_GEARMOTOR,
        YELLOW_JACKET_1_MOTOR, YELLOW_JACKET_3_7_MOTOR, YELLOW_JACKET_5_2_MOTOR, YELLOW_JACKET_13_7_MOTOR, YELLOW_JACKET_19_2_MOTOR, YELLOW_JACKET_26_9_MOTOR, YELLOW_JACKET_50_9_MOTOR, YELLOW_JACKET_71_2_MOTOR, YELLOW_JACKET_99_5_MOTOR, YELLOW_JACKET_139_MOTOR, YELLOW_JACKET_188_MOTOR,
        REV_ULTRAPLANETARY_HD_HEX_MOTOR
    }

    /**
     * A mapping between motor types and their respective motor configuration
     * <p>The key is the {@link MotorType}, and the value is the corresponding {@link MotorConfigurationType}
     * containing the hardware parameters like ticks per revolution, maximum RPM, and gearing ratio</p>
     */
    protected static final HashMap<MotorType, MotorConfigurationType> motorTypeMap = new HashMap<>();

    /**
     * Static block that initializes the motor type mappings. Each motor type is associated
     * with a specific {@link MotorConfigurationType} which defines the configuration details for that motor.
     * <p>The motor configuration includes:</p>
     * <ul>
     *     <li>Ticks per revolution</li>
     *     <li>Maximum RPM</li>
     *     <li>Gearing ratio</li>
     *     <li>Achievable maximum RPM fraction</li>
     *     <li>Orientation</li>
     * </ul>
     */
    static {
        MotorConfigurationType torquenado = MotorConfigurationType.getMotorType(DcMotorEx.class);
        torquenado.setTicksPerRev(1440);
        torquenado.setMaxRPM(100);
        torquenado.setGearing(60);
        torquenado.setOrientation(null);
        torquenado.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.TORQUENADO, torquenado);
        MotorConfigurationType neverest40Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest40Gearmotor.setTicksPerRev(1120);
        neverest40Gearmotor.setMaxRPM(160);
        neverest40Gearmotor.setGearing(40);
        neverest40Gearmotor.setOrientation(null);
        neverest40Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_40_GEARMOTOR, neverest40Gearmotor);
        MotorConfigurationType neverest60Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest60Gearmotor.setTicksPerRev(1680);
        neverest60Gearmotor.setMaxRPM(105);
        neverest60Gearmotor.setGearing(60);
        neverest60Gearmotor.setOrientation(null);
        neverest60Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_60_GEARMOTOR, neverest60Gearmotor);
        MotorConfigurationType neverest3_7OrbitalGearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest3_7OrbitalGearmotor.setTicksPerRev(103.6);
        neverest3_7OrbitalGearmotor.setMaxRPM(1780);
        neverest3_7OrbitalGearmotor.setGearing(3.7);
        neverest3_7OrbitalGearmotor.setOrientation(null);
        neverest3_7OrbitalGearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_3_7_ORBITAL_GEARMOTOR, neverest3_7OrbitalGearmotor);
        MotorConfigurationType neverest20OrbitalGearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest20OrbitalGearmotor.setTicksPerRev(537.6);
        neverest20OrbitalGearmotor.setMaxRPM(340);
        neverest20OrbitalGearmotor.setGearing(19.2);
        neverest20OrbitalGearmotor.setOrientation(null);
        neverest20OrbitalGearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_20_ORBITAL_GEARMOTOR, neverest20OrbitalGearmotor);
        MotorConfigurationType revHexCore = MotorConfigurationType.getMotorType(DcMotorEx.class);
        revHexCore.setTicksPerRev(288);
        revHexCore.setMaxRPM(125);
        revHexCore.setGearing(72);
        revHexCore.setOrientation(null);
        revHexCore.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.REV_HEX_CORE, revHexCore);
        MotorConfigurationType neverestHex1Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex1Gearmotor.setTicksPerRev(7);
        neverestHex1Gearmotor.setMaxRPM(6600);
        neverestHex1Gearmotor.setGearing(1);
        neverestHex1Gearmotor.setOrientation(null);
        neverestHex1Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_1_GEARMOTOR, neverestHex1Gearmotor);
        MotorConfigurationType neverestHex3_7Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex3_7Gearmotor.setTicksPerRev(25.9);
        neverestHex3_7Gearmotor.setMaxRPM(1784);
        neverestHex3_7Gearmotor.setGearing(3.7);
        neverestHex3_7Gearmotor.setOrientation(null);
        neverestHex3_7Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_3_7_GEARMOTOR, neverestHex3_7Gearmotor);
        MotorConfigurationType neverestHex13_7Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex13_7Gearmotor.setTicksPerRev(95.9);
        neverestHex13_7Gearmotor.setMaxRPM(482);
        neverestHex13_7Gearmotor.setGearing(13.7);
        neverestHex13_7Gearmotor.setOrientation(null);
        neverestHex13_7Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_13_7_GEARMOTOR, neverestHex13_7Gearmotor);
        MotorConfigurationType neverestHex19_2Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex19_2Gearmotor.setTicksPerRev(134.4);
        neverestHex19_2Gearmotor.setMaxRPM(344);
        neverestHex19_2Gearmotor.setGearing(19.2);
        neverestHex19_2Gearmotor.setOrientation(null);
        neverestHex19_2Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_19_2_GEARMOTOR, neverestHex19_2Gearmotor);
        MotorConfigurationType neverestHex50_9Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex50_9Gearmotor.setTicksPerRev(356.3);
        neverestHex50_9Gearmotor.setMaxRPM(130);
        neverestHex50_9Gearmotor.setGearing(50.9);
        neverestHex50_9Gearmotor.setOrientation(null);
        neverestHex50_9Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_50_9_GEARMOTOR, neverestHex50_9Gearmotor);
        MotorConfigurationType neverestHex263_7Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex263_7Gearmotor.setTicksPerRev(1845.9);
        neverestHex263_7Gearmotor.setMaxRPM(25);
        neverestHex263_7Gearmotor.setGearing(263.7);
        neverestHex263_7Gearmotor.setOrientation(null);
        neverestHex263_7Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_263_7_GEARMOTOR, neverestHex263_7Gearmotor);
        MotorConfigurationType mavrick61Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        mavrick61Gearmotor.setTicksPerRev(1464);
        mavrick61Gearmotor.setMaxRPM(112);
        mavrick61Gearmotor.setGearing(61);
        mavrick61Gearmotor.setOrientation(null);
        mavrick61Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.MAVERICK_61_GEARMOTOR, mavrick61Gearmotor);
        MotorConfigurationType hdHex20Spur = MotorConfigurationType.getMotorType(DcMotorEx.class);
        hdHex20Spur.setTicksPerRev(560);
        hdHex20Spur.setMaxRPM(300);
        hdHex20Spur.setGearing(20);
        hdHex20Spur.setOrientation(null);
        hdHex20Spur.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.REV_HD_HEX_20_SPUR, hdHex20Spur);
        MotorConfigurationType hdHex40Spur = MotorConfigurationType.getMotorType(DcMotorEx.class);
        hdHex40Spur.setTicksPerRev(560);
        hdHex40Spur.setMaxRPM(300);
        hdHex40Spur.setGearing(20);
        hdHex40Spur.setOrientation(null);
        hdHex40Spur.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.REV_HD_HEX_40_SPUR, hdHex40Spur);
        MotorConfigurationType hdHexSpur = MotorConfigurationType.getMotorType(DcMotorEx.class);
        hdHexSpur.setTicksPerRev(1120);
        hdHexSpur.setMaxRPM(150);
        hdHexSpur.setGearing(40);
        hdHexSpur.setOrientation(null);
        hdHexSpur.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.REV_HD_HEX_SPUR, hdHexSpur);
        MotorConfigurationType yellowJacket5203_1Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_1Motor.setTicksPerRev(28);
        yellowJacket5203_1Motor.setMaxRPM(6000);
        yellowJacket5203_1Motor.setGearing(1);
        yellowJacket5203_1Motor.setOrientation(null);
        yellowJacket5203_1Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_1_MOTOR, yellowJacket5203_1Motor);
        MotorConfigurationType yellowJacket5203_3_7Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_3_7Motor.setTicksPerRev(103.8);
        yellowJacket5203_3_7Motor.setMaxRPM(1620);
        yellowJacket5203_3_7Motor.setGearing(3.7);
        yellowJacket5203_3_7Motor.setOrientation(null);
        yellowJacket5203_3_7Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_3_7_MOTOR, yellowJacket5203_3_7Motor);
        MotorConfigurationType yellowJacket5203_5_2Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_5_2Motor.setTicksPerRev(145.1);
        yellowJacket5203_5_2Motor.setMaxRPM(1150);
        yellowJacket5203_5_2Motor.setGearing(5.2);
        yellowJacket5203_5_2Motor.setOrientation(null);
        yellowJacket5203_5_2Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_5_2_MOTOR, yellowJacket5203_5_2Motor);
        MotorConfigurationType yellowJacket5203_13_7Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_13_7Motor.setTicksPerRev(384.5);
        yellowJacket5203_13_7Motor.setMaxRPM(435);
        yellowJacket5203_13_7Motor.setGearing(13.7);
        yellowJacket5203_13_7Motor.setOrientation(null);
        yellowJacket5203_13_7Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_13_7_MOTOR, yellowJacket5203_13_7Motor);
        MotorConfigurationType yellowJacket5203_19_2Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_19_2Motor.setTicksPerRev(537.7);
        yellowJacket5203_19_2Motor.setMaxRPM(312);
        yellowJacket5203_19_2Motor.setGearing(19.2);
        yellowJacket5203_19_2Motor.setOrientation(null);
        yellowJacket5203_19_2Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_19_2_MOTOR, yellowJacket5203_19_2Motor);
        MotorConfigurationType yellowJacket5203_26_9Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_26_9Motor.setTicksPerRev(751.8);
        yellowJacket5203_26_9Motor.setMaxRPM(223);
        yellowJacket5203_26_9Motor.setGearing(26.9);
        yellowJacket5203_26_9Motor.setOrientation(null);
        yellowJacket5203_26_9Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_26_9_MOTOR, yellowJacket5203_26_9Motor);
        MotorConfigurationType yellowJacket5203_50_9Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_50_9Motor.setTicksPerRev(1425.1);
        yellowJacket5203_50_9Motor.setMaxRPM(117);
        yellowJacket5203_50_9Motor.setGearing(50.9);
        yellowJacket5203_50_9Motor.setOrientation(null);
        yellowJacket5203_50_9Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_50_9_MOTOR, yellowJacket5203_50_9Motor);
        MotorConfigurationType yellowJacket5203_71_2Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_71_2Motor.setTicksPerRev(1993.6);
        yellowJacket5203_71_2Motor.setMaxRPM(84);
        yellowJacket5203_71_2Motor.setGearing(71.2);
        yellowJacket5203_71_2Motor.setOrientation(null);
        yellowJacket5203_71_2Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_71_2_MOTOR, yellowJacket5203_71_2Motor);
        MotorConfigurationType yellowJacket5203_99_5Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_99_5Motor.setTicksPerRev(2786.2);
        yellowJacket5203_99_5Motor.setMaxRPM(60);
        yellowJacket5203_99_5Motor.setGearing(99.5);
        yellowJacket5203_99_5Motor.setOrientation(null);
        yellowJacket5203_99_5Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_99_5_MOTOR, yellowJacket5203_99_5Motor);
        MotorConfigurationType yellowJacket5203_139Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_139Motor.setTicksPerRev(3895.9);
        yellowJacket5203_139Motor.setMaxRPM(43);
        yellowJacket5203_139Motor.setGearing(139);
        yellowJacket5203_139Motor.setOrientation(null);
        yellowJacket5203_139Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_139_MOTOR, yellowJacket5203_139Motor);
        MotorConfigurationType yellowJacket5203_188Motor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        yellowJacket5203_188Motor.setTicksPerRev(5281.1);
        yellowJacket5203_188Motor.setMaxRPM(30);
        yellowJacket5203_188Motor.setGearing(188);
        yellowJacket5203_188Motor.setOrientation(null);
        yellowJacket5203_188Motor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.YELLOW_JACKET_188_MOTOR, yellowJacket5203_188Motor);
        MotorConfigurationType revUltraPlanetaryMotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        revUltraPlanetaryMotor.setTicksPerRev(-1);
        revUltraPlanetaryMotor.setMaxRPM(-1);
        revUltraPlanetaryMotor.setGearing(-1);
        revUltraPlanetaryMotor.setOrientation(null);
        revUltraPlanetaryMotor.setAchieveableMaxRPMFraction(-1);
        motorTypeMap.put(MotorType.REV_ULTRAPLANETARY_HD_HEX_MOTOR, revUltraPlanetaryMotor);
    }

}
