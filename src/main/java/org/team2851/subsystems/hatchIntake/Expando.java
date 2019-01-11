package org.team2851.subsystems.hatchIntake;

import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;

public class Expando extends Subsystem
{
    private static Expando mInstance = null;

    public static Expando getInstance()
    {
        if (mInstance == null) mInstance = new Expando();
        return mInstance;
    }

    private Expando()
    {
        super("Expando");
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
