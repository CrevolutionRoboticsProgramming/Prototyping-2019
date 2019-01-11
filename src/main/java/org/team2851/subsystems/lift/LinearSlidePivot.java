package org.team2851.subsystems.lift;

import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;

public class LinearSlidePivot extends Subsystem
{
    private static LinearSlidePivot mInstance = null;

    public static LinearSlidePivot getInstance()
    {
        if (mInstance == null) mInstance = new LinearSlidePivot();
        return mInstance;
    }

    private LinearSlidePivot()
    {
        super("LinearSlidePivot");
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
