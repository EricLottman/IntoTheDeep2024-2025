package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Manages controller rumble based on elapsed time, specifically made for a teleOp mode, seeing as it would have to be called repetitively
 */
public class WarningRumbler {

    private final Gamepad gamepad;
    private final ElapsedTime elapsedTime;
    private final double timer;
    private boolean rumbled, started;

    private final Gamepad.RumbleEffect rumbleEffect;

    /**
     * Constructs a WarningRumbler
     *
     * @param gamepad The gamepad to be controlled
     * @param timer   The time in seconds after which the controller should rumble
     * @param type    Type of gamepad being used
     */
    public WarningRumbler(Gamepad gamepad, double timer, Gamepad.Type type) {
        this.gamepad = gamepad;
        this.timer = timer;
        this.elapsedTime = new ElapsedTime();
        this.rumbled = false;
        this.started = false;


        if (type == Gamepad.Type.SONY_PS4 || type == Gamepad.Type.XBOX_360) {
            rumbleEffect = new Gamepad.RumbleEffect.Builder() // bzzzz whomp whomp bzzzz
                    .addStep(0.0, 1.0, 500)
                    .addStep(0.0, 0.0, 250)
                    .addStep(1.0, 0.0, 150)
                    .addStep(0.0, 0.0, 100)
                    .addStep(1.0, 0.0, 150)
                    .addStep(0.0, 0.0, 250)
                    .addStep(1.0, 0.0, 500)
                    .build();
        } else if (type == Gamepad.Type.SONY_PS4_SUPPORTED_BY_KERNEL) {
            rumbleEffect = new Gamepad.RumbleEffect.Builder().addStep(1, 1, 1000).build(); // bzzzzzzzzzzzzzzzzzzzzz (etpark controllers dont have the big sound motors that feel like 'whomp'
        } else {
            throw new IllegalArgumentException(type.toString() + " doesn't vibrate");
        }

    }

    /**
     * Starts the timer to track elapsed time
     */
    public void startTimer() {
        elapsedTime.reset();
        started = true;
    }

    /**
     * Checks if the rumble time has passed and triggers the rumble if it has
     */
    public void checkAndRumble() {
        if (elapsedTime.seconds() >= timer && !rumbled && started) {
            gamepad.runRumbleEffect(rumbleEffect);
            rumbled = true;
        }
    }
}
