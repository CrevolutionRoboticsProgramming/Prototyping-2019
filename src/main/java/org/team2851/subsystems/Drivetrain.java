package org.team2851.subsystems;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.RobotDrive;
import org.frc2851.crevolib.drivers.TalonSRXFactory;
import org.frc2851.crevolib.io.Axis;
import org.frc2851.crevolib.io.Controller;
import org.frc2851.crevolib.subsystem.Command;
import org.frc2851.crevolib.subsystem.Subsystem;
import org.team2851.Constants;
import org.team2851.Robot;

public class Drivetrain extends Subsystem
{
    private static Drivetrain mInstance = null;

    private WPI_TalonSRX mLeftMaster, mLeftSlaveA, mLeftSlaveB;
    private WPI_TalonSRX mRightMaster, mRightSlaveA, mRightSlaveB;
    private PigeonIMU mPigeon;
    private Controller controller = Robot.controller;

    // Constants
    private final double CPR = 4096;
    private final double WHEEL_DIAMETER = 1.0d / 3.0d;
    private final double DRIVE_WIDTH = 2.5;
    private final double DEFAULT_PEAK_OUT = 1;
    private final double DEFAULT_NOMINAL_OUT = 0;
    private final int TALON_TIMEOUT = 100;
    private final int PID_PRIMARY = 0, PID_AUX = 1;
    private final int PID_SLOT_DRIVE = 0, PID_SLOT_GYRO = 1, PID_SLOT_ENC_TURN = 2;
    private final int PIGEON_REMOTE_LEFT = 0, PIGEON_REMOTE_RIGHT = 0;

    private final boolean USE_SENSORS = false;

    public static Drivetrain getInstance()
    {
        if (mInstance == null) mInstance = new Drivetrain();
        return mInstance;
    }

    private Drivetrain()
    {
        super("Drivetrain");
    }

    @Override
    protected boolean init()
    {
        mLeftMaster = TalonSRXFactory.createDefaultMasterWPI_TalonSRX(Constants.LEFT_MASTER);
        mLeftSlaveA = TalonSRXFactory.createPermanentSlaveWPI_TalonSRX(Constants.LEFT_SLAVE_A, mLeftMaster);
//        mLeftSlaveB = TalonSRXFactory.createPermanentSlaveTalonSRX(Constants.LEFT_SLAVE_B, mLeftMaster);

        mRightMaster = TalonSRXFactory.createDefaultMasterWPI_TalonSRX(Constants.RIGHT_MASTER);
        mRightSlaveA = TalonSRXFactory.createPermanentSlaveWPI_TalonSRX(Constants.RIGHT_SLAVE_A, mRightMaster);
//        mRightSlaveB = TalonSRXFactory.createPermanentSlaveTalonSRX(Constants.RIGHT_SLAVE_B, mRightMaster);

        if (USE_SENSORS)
        {
            // Sensor Configuration
        }

        BadLog.createTopic("Drivetrain/Left Percent", BadLog.UNITLESS, () -> mLeftMaster.get(), "hide", "join:Drivetrain/Percent Outputs");
        BadLog.createTopic("Drivetrain/Right Percent", BadLog.UNITLESS, () -> mRightMaster.get(), "hide", "join:Drivetrain/Percent Outputs");

        BadLog.createTopic("Drivetrain/Left A Voltage", "V", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Outputs");
        BadLog.createTopic("Drivetrain/Left B Voltage", "V", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Outputs");
        BadLog.createTopic("Drivetrain/Right A Voltage", "V", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Outputs");
        BadLog.createTopic("Drivetrain/Right B Voltage", "V", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Outputs");

        BadLog.createTopic("Drivetrain/Left A Current", "A", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Currents");
        BadLog.createTopic("Drivetrain/Left B Current", "A", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Currents");
        BadLog.createTopic("Drivetrain/Right A Current", "A", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Currents");
        BadLog.createTopic("Drivetrain/Right B Current", "A", () -> mLeftMaster.getBusVoltage(), "hide", "join:Drivetrain/Voltage Currents");

        if (USE_SENSORS)
        {
            BadLog.createTopic("Drivetrain/Left Encoder", "counts", () -> (double) mLeftMaster.getSensorCollection().getQuadraturePosition(), "hide", "join:Drivetrain/Encoders (Pos)");
            BadLog.createTopic("Drivetrain/Right Encoder", "counts", () -> (double)mLeftMaster.getSensorCollection().getQuadraturePosition(), "hide", "join:Drivetrain/Encoders (Pos)");
            BadLog.createTopic("Drivetrain/Left Velocity", "f/s", () -> ctreVelToFPS(mLeftMaster.getSensorCollection().getQuadratureVelocity()), "hide", "join:Drivetrain/Encoders (Vel)");
            BadLog.createTopic("Drivetrain/Right Velocity", "f/s", () -> ctreVelToFPS(mRightMaster.getSensorCollection().getQuadratureVelocity()), "hide", "join:Drivetrain/Encoders (Vel)");
            BadLog.createTopic("Drivetrain/Angle", "deg", () -> mPigeon.getFusedHeading());
        }

        configControllers();

        return true;
    }

    @Override
    public Command getTeleopCommand()
    {
        return new Command()
        {
            RobotDrive drive = new RobotDrive(mLeftMaster, mRightMaster);
            @Override
            public String getName()
            {
                return "Teleop";
            }

            @Override
            public boolean isFinished()
            {
                return false;
            }

            @Override
            public boolean init()
            {
                return true;
            }

            @Override
            public void update()
            {
                drive.arcadeDrive(-controller.get(Axis.AxisID.LEFT_Y), controller.get(Axis.AxisID.RIGHT_X));
            }

            @Override
            public void stop()
            {
                drive.stopMotor();
            }
        };
    }

    public void configControllers()
    {
        controller.config(Axis.AxisID.LEFT_Y);
        controller.config(Axis.AxisID.RIGHT_X);
    }

    private int distanceToCounts(double distance)
    {
        return (int)(distance / (CPR * WHEEL_DIAMETER * Math.PI));
    }

    private double applyDeadband(double val, double deadband) {
        return (Math.abs(val) < deadband) ? 0 : val;
    }

    // counts / 100ms
    private double ctreVelToFPS(int ctreVel) {
        return ((ctreVel * 10) / CPR) * WHEEL_DIAMETER * Math.PI;
    }
}