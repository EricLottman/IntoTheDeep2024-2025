package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                .setDimensions(10,10)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .setColorScheme(new ColorSchemeRedDark())
//                .setColorScheme(new ColorScheme())
                .build();

        double
                blockHeading = Math.PI / 2,
                basketHeading = 5 * Math.PI / 4,
                endHeading = 3 * Math.PI / 2;

        Vector2d
                initialVector = new Vector2d(-11.8, -61.7),
                basketVector = new Vector2d(-60, -52),
                midBlockVector = new Vector2d(-60, -38),
                rightBlockVector = new Vector2d(-50, -38),
                leftBlockVector = new Vector2d(-70, -38),
                endVector = new Vector2d(52, -54);

        Pose2d
                initialPose = new Pose2d(initialVector, blockHeading),
                basketPose = new Pose2d(basketVector, basketHeading),
                midBlockPose = new Pose2d(midBlockVector, blockHeading),
                rightBlockPose = new Pose2d(rightBlockVector, blockHeading),
                leftBlockPose = new Pose2d(leftBlockVector, blockHeading);

        myBot.runAction(randomMove(myBot, new Pose2d(0, 0, Math.PI / 2), 10));

//        myBot.getDrive().actionBuilder(new Pose2d(0,0, Math.PI/2))
//                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60), Math.random() * 3 * Math.PI / Math.random() * 3), Math.random() * 3 * Math.PI / Math.random() * 3)
//                .splineTo(new Vector2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60), Math.random() * 3 * Math.PI / Math.random() * 3)
//                .strafeTo(new Vector2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60))
//                .splineToLinearHeading(new Pose2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60, Math.random() * 3 * Math.PI / Math.random() * 3), Math.random() * 3 * Math.PI / Math.random() * 3)
//                .strafeToLinearHeading(new Vector2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60), Math.random() * 60 * 2 - 60)
//                .build()

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .setAxesInterval(10)
                .setTheme(new ColorSchemeCustom())
                .start();
    }

    static Action randomMove(RoadRunnerBotEntity myBot, Pose2d currentPosition, int iterations) {
        System.out.println("\nGenerating " + iterations + " random moves");
        Action[] output = new Action[iterations];
        for (int i = 0; i < iterations; i++) {
            Pose2d targetPosition = new Pose2d(new Vector2d(Math.random() * 60 * 2 - 60, Math.random() * 60 * 2 - 60), Math.random() * 3 * Math.PI / (Math.random() * 3 + 0.1));
            boolean isReversed = Math.abs(Math.toDegrees(Math.abs(currentPosition.heading.toDouble() - targetPosition.heading.toDouble()))) > 180;
            System.out.println(i + 1 + " of " + iterations + " moves generated");
            switch ((int) Math.round(Math.random() * 4)) {
                case 0:
                    output[i] = myBot.getDrive().actionBuilder(currentPosition)
                            .splineToSplineHeading(targetPosition, Math.random() * 3 * Math.PI / (Math.random() * 3 + 0.1))
                            .setReversed(isReversed)
                            .build();
                    break;
                case 1:
                    output[i] = myBot.getDrive().actionBuilder(currentPosition)
                            .splineTo(targetPosition.position, targetPosition.heading)
                            .setReversed(isReversed)
                            .build();
                    break;
                case 2:
                    output[i] = myBot.getDrive().actionBuilder(currentPosition)
                            .strafeTo(targetPosition.position)
                            .setReversed(isReversed)
                            .build();
                    break;
                case 3:
                    output[i] = myBot.getDrive().actionBuilder(currentPosition)
                            .splineToLinearHeading(targetPosition, Math.random() * 3 * Math.PI / (Math.random() * 3 + 0.1))
                            .setReversed(isReversed)
                            .build();
                    break;
                case 4:
                    output[i] = myBot.getDrive().actionBuilder(currentPosition)
                            .strafeToLinearHeading(targetPosition.position, targetPosition.heading)
                            .setReversed(isReversed)
                            .build();
                    break;
            }
            currentPosition = targetPosition;
        }

        System.out.println("\nRunning sequence");
        return new SequentialAction(output);
    }
}
