package org.team2851.subsystems.hatchIntake;

import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;

public class Chuck extends Subsystem
{
    private static Chuck mInstance = null;

    public static Chuck getInstance()
    {
        if (mInstance == null) mInstance = new Chuck();
        return mInstance;
    }

    private Chuck()
    {
        super("Chuck");
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
