package org.team2851.subsystems.climber;

import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;

public class Cam extends Subsystem
{
    private static Cam mInstance = null;

    public static Cam getInstance()
    {
        if (mInstance == null) mInstance = new Cam();
        return mInstance;
    }

    private Cam()
    {
        super("Cam");
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
