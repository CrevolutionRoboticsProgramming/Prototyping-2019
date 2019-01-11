package org.team2851;

import org.frc2851.crevolib.CrevoRobot;
import org.frc2851.crevolib.io.Controller;
import org.team2851.subsystems.Drivetrain;
import org.team2851.subsystems.climber.Cam;
import org.team2851.subsystems.climber.GorillaArm;
import org.team2851.subsystems.hatchIntake.Chuck;
import org.team2851.subsystems.hatchIntake.Expando;
import org.team2851.subsystems.lift.LinearSlide;
import org.team2851.subsystems.lift.LinearSlidePivot;

public class Robot extends CrevoRobot
{
    public static Controller controller;

    public Robot()
    {

        addSubsystem(Drivetrain.getInstance());
//        addSubsystem(Cam.getInstance());
//        addSubsystem(GorillaArm.getInstance());
//        addSubsystem(Expando.getInstance());
//        addSubsystem(Chuck.getInstance());
//        addSubsystem(LinearSlide.getInstance());
//        addSubsystem(LinearSlidePivot.getInstance());
    }
}