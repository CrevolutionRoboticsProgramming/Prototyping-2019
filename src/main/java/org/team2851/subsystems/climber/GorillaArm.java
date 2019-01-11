package org.team2851.subsystems.climber;

import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;

public class GorillaArm extends Subsystem
{
    private static GorillaArm mInstance = null;

    public static GorillaArm getInstance()
    {
        if (mInstance == null) mInstance = new GorillaArm();
        return mInstance;
    }

    private GorillaArm()
    {
        super("GorillaArm");
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
