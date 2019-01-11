package org.team2851.subsystems.lift;

import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;

public class LinearSlide extends Subsystem
{
    private static LinearSlide mInstance = null;

    public static LinearSlide getInstance()
    {
        if (mInstance == null) mInstance = new LinearSlide();
        return mInstance;
    }

    private LinearSlide()
    {
        super("LinearSlide");
    }

    @Override
    protected boolean init() {
        return false;
    }

    @Override
    public Command getTeleopCommand() {
        return null;
    }
}
