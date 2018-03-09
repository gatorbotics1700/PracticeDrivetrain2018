package org.usfirst.frc.team1700.robot.commands.AutoCGs;

import org.usfirst.frc.team1700.robot.Robot;
import org.usfirst.frc.team1700.robot.commands.Drivetrain.DriveForwardTimeOutCommand;
import org.usfirst.frc.team1700.robot.commands.Drivetrain.DriveToAngleCommand;
import org.usfirst.frc.team1700.robot.commands.Drivetrain.DriveToDistanceCommand;
import org.usfirst.frc.team1700.robot.commands.Elevator.ElevatorResetCommand;
import org.usfirst.frc.team1700.robot.commands.Elevator.ElevatorToTicksCommand;
import org.usfirst.frc.team1700.robot.commands.Intake.FoldIntakeCommand;
import org.usfirst.frc.team1700.robot.commands.Intake.ReleaseIntakeCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSwitchAuto extends CommandGroup {

    public LeftSwitchAuto() {
    	addSequential(new DriveForwardTimeOutCommand());
		
		addSequential(new DriveToDistanceCommand(Robot.driveSubsystem.sameSwitchDist1, Robot.driveSubsystem.crossSwitchDist1)); //distance given in inches
		addSequential(new DriveToAngleCommand(0, Robot.driveSubsystem.right));
		addSequential(new DriveToDistanceCommand(0, Robot.driveSubsystem.crossSwitchDist2));
		addSequential(new DriveToAngleCommand(0, Robot.driveSubsystem.left));
		addSequential(new DriveToDistanceCommand(0, Robot.driveSubsystem.crossSwitchDist3));
		
		addSequential(new ElevatorResetCommand());
		addParallel(new ElevatorToTicksCommand(Robot.elevatorSubsystem.switchTicks));
		addSequential(new FoldIntakeCommand(true));
		addSequential(new ReleaseIntakeCommand());
    }
}
