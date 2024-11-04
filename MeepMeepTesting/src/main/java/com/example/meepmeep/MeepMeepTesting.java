package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
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
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(1200, 120, Math.toRadians(180), Math.toRadians(180), 15)
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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0,0, Math.PI/2))
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .splineToSplineHeading(new Pose2d(new Vector2d(Math.random()*60*2-60, Math.random()*60*2-60), Math.random()*3*Math.PI/Math.random()*3), Math.random()*3*Math.PI/Math.random()*3)
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .setAxesInterval(10)
                .setTheme(new ColorSchemeCustom())
                .start();
    }
}
