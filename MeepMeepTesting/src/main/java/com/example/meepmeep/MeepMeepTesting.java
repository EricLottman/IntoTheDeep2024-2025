package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(160, 160, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Vector2d basket = new Vector2d(60, 52);
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(11.8, 61.7, 0))
                .waitSeconds(1)
                .splineTo(new Vector2d(60, 38), 3 * Math.PI / 2)
                .waitSeconds(.25)
                .lineToYLinearHeading(52, Math.PI / 4)
                .waitSeconds(.25)
                .splineTo(new Vector2d(50, 38), 3 * Math.PI / 2)
                .waitSeconds(.25)
                .splineTo(basket, Math.PI / 4)
                .waitSeconds(.25)
                .splineTo(new Vector2d(70, 38), 3 * Math.PI / 2)
                .waitSeconds(.25)
                .splineTo(basket, Math.PI / 4)
                .waitSeconds(.25)
                .strafeToLinearHeading(new Vector2d(-52, 52), Math.PI / 2)
                .waitSeconds(1)
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
