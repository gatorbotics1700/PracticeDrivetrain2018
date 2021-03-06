package org.usfirst.frc.team1700.robot.subsystems;

import org.usfirst.frc.team1700.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;


public class DriveSubsystem{

	AHRS imu = RobotMap.ahrs; 
	
	//Value needs to be changed based on what's on drivetrain
	private static double wheelCircumference = 4 * Math.PI; //in inches 
	private static double numTicks = 4096; //double-check this value!!
	public static final double inchesPerTick = wheelCircumference / numTicks;
	public static final double ticksPerInch = 1/inchesPerTick;

	// MOTORS AND SENSORS : UPDATE BASED ON WHAT'S ON THE PRACTICE DRIVETRAIN
	TalonSRX L1 = RobotMap.leftFirstDrive; 
	TalonSRX L2 = RobotMap.leftSecondDrive;
	TalonSRX L3 = RobotMap.leftThirdDrive;
	TalonSRX R1 = RobotMap.rightFirstDrive;
	TalonSRX R2 = RobotMap.rightSecondDrive;
	TalonSRX R3 = RobotMap.rightThirdDrive;

	public DriveSubsystem() {
		L1.setInverted(true);
		L2.setInverted(true);
		L3.setInverted(true);
	}

	//Intakes the speeds of joysticks and updates motor speeds 
	public void driveTank(double leftSpeed, double rightSpeed) { 
		L1.set(ControlMode.PercentOutput, leftSpeed);
		L2.set(ControlMode.PercentOutput, leftSpeed);
		L3.set(ControlMode.PercentOutput, leftSpeed);
		R1.set(ControlMode.PercentOutput, rightSpeed); 
		R2.set(ControlMode.PercentOutput, rightSpeed);
		R3.set(ControlMode.PercentOutput, rightSpeed);
	}

	//Velocity 
	public double getSensorVelocity()
	{
		double x = imu.getVelocityX();
		double y = imu.getVelocityY();
		return Math.sqrt(x*x + y*y);
	}
	
	//Left wheel displacement
	public double getLeftEncoderValue()
	{
		double leftWheelDisplacement = L2.getSensorCollection().getQuadraturePosition() * inchesPerTick;
		return leftWheelDisplacement; 
	}

	//Right wheel displacement
	public double getRightEncoderValue()
	{
		double rightWheelDisplacement = R2.getSensorCollection().getQuadraturePosition() * inchesPerTick;
		return rightWheelDisplacement;  
	}

	//Scales speed of wheel by distance per pulse
	public Double getVelocity() 
	{
		Double velocity = ((getVelocityL()+getVelocityR())/2.0) * inchesPerTick;
		return velocity; //getQuadratureVelocity returns in units per 100ms
	}
	
	//right encoder's velocity 
	public double getVelocityL(){
		return L2.getSensorCollection().getQuadratureVelocity();
	}

	//left encoder's velocity
	public double getVelocityR(){
		return R2.getSensorCollection().getQuadratureVelocity();
	}

	//Position
	public double getSensorDisplacement()
	{
		double x = imu.getDisplacementX();
		double y = imu.getDisplacementY();
		return Math.sqrt(x*x + y*y);
	}

	public boolean isMoving()
	{
		return imu.isMoving(); 
	}

	public void resetEncoders(){
		L2.setSelectedSensorPosition(0, 0, 10);
		L2.getSensorCollection().setQuadraturePosition(0, 10);
		R2.setSelectedSensorPosition(0, 0, 10); 
		R2.getSensorCollection().setQuadraturePosition(0, 10);

	}

}
