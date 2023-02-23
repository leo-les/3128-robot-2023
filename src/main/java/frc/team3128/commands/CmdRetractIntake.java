package frc.team3128.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team3128.subsystems.Intake;
import frc.team3128.subsystems.Manipulator;

public class CmdRetractIntake extends SequentialCommandGroup{

    private Manipulator manipulator;
    private Intake intake;

    public CmdRetractIntake(){
        manipulator = Manipulator.getInstance();
        intake = Intake.getInstance();

        addCommands(
            new InstantCommand(()-> intake.disableRollers()),
            new CmdMoveIntake(Intake.IntakeState.STOWED),
            new InstantCommand(()-> manipulator.intakeCubes(), manipulator),
            new CmdMoveArm(0, 11.5),
            new InstantCommand(()-> intake.enableRollersReverse()),
            new WaitCommand(0.1),
            new InstantCommand(()-> manipulator.enableRollerObject()),
            new InstantCommand(()-> intake.disableRollers())
        );
    }
    
}