package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

public class Integrator {

    private double velocityX, velocityY;
    private double positionX, positionY;
    private double lastAccelerationX, lastAccelerationY;
    private double lastHeading;
    private double alpha = 0.8; // smoothing factor for low-pass filter (0 < alpha < 1)
    // lower value is more aggressive smoothing, so if i say anywhere "more filtering" i really mean lower alpha value
    // more noise needs more filtering
    // when slow filter more
    // when fast filter less
    // quicker we can get values the more we can filter out, so make the loop as tight as possible without killing the cpu or sensors
    // ToDo: adaptive filtering, also plot data and engineering notebook the hell out of judges with a graph

    public Integrator() {
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        this.positionX = 0.0;
        this.positionY = 0.0;
        this.lastAccelerationX = 0.0;
        this.lastAccelerationY = 0.0;
        this.lastHeading = 0.0;
    }

    /**
     * Updates the integrated values based on the current acceleration, heading, and timestamp.
     *
     * @param accelerationX The current acceleration value in x direction in meters per second squared (m/s^2).
     * @param accelerationY The current acceleration value in y direction in meters per second squared (m/s^2).
     * @param heading       The current heading in degrees.
     * @param seconds       The time elapsed in seconds since the last update.
     */
    public synchronized void update(double accelerationX, double accelerationY, double heading, double seconds) {

        // apply low pass filter to increase accuracy
        accelerationX = applyLowPassFilter(accelerationX, lastAccelerationX);
        accelerationY = applyLowPassFilter(accelerationY, lastAccelerationY);

        // average acceleration to simulate trapezoidal rule integration approximation
        double avgAccelerationX = (lastAccelerationX + accelerationX) / 2.0;
        double avgAccelerationY = (lastAccelerationY + accelerationY) / 2.0;

        // integrate velocity and position with respect to the average acceleration
        // delta x is seconds, height of rectangle is acceleration
        velocityX += avgAccelerationX * seconds;
        velocityY += avgAccelerationY * seconds;
        // this feels easier than making you have to call an Integrator within an Integrator, less objects but feels less cool
        positionX += velocityX * seconds;
        positionY += velocityY * seconds;
        // it is rn that i learned that theres such thing as absement, remember that for the future, as well as the fact that theres anti (abs-) versions of things and some people go to abserk

        // adjust position for heading change (aka be capable of turning without breaking algorithm)
        double radians = Math.toRadians((heading + lastHeading) / 2.0); //originally used delta heading, now we use the average heading to account for turning better
        double cosHeading = Math.cos(radians);
        double sinHeading = Math.sin(radians);

        double newPositionX = positionX * cosHeading - positionY * sinHeading;
        double newPositionY = positionX * sinHeading + positionY * cosHeading;

        positionX = newPositionX;
        positionY = newPositionY;

        // update states
        lastAccelerationX = accelerationX;
        lastAccelerationY = accelerationY;
        lastHeading = heading;
    }

    private double applyLowPassFilter(double current, double previous) {
        return alpha * previous + (1 - alpha) * current;
    }

    // getter time
    // 39.3701 is the conversion factor to multiply by to get inches from meters, since I operate in freedom units *cue eagle screech* and BNO055IMU.AccelUnit doesnt have a freedom unit option, trust me I checked
    public synchronized double getVelocityX() {
        return velocityX * 39.3701;
    }

    public synchronized double getVelocityY() {
        return velocityY * 39.3701;
    }

    public synchronized double getPositionX() {
        return positionX * 39.3701;
    }

    public synchronized double getPositionY() {
        return positionY * 39.3701;
    }
}
