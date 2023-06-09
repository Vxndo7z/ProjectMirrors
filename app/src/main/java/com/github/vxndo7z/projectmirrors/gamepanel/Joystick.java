package com.github.vxndo7z.projectmirrors.gamepanel;

import android.graphics.*;
import com.github.vxndo7z.projectmirrors.graphics.*;

public class Joystick {

	private int outerCircleCenterPositionX;
	private int outerCircleCenterPositionY;
	private int innerCircleCenterPositionX;
	private int innerCircleCenterPositionY;
	private int outerCircleRadius;
	private int innerCircleRadius;
	private Paint innerCirclePaint;
	private Paint outerCirclePaint;
	private boolean isPressed = false;
	private double joystickCenterToTouchDistance;
	private double actuatorX;
	private double actuatorY;

	public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
		outerCircleCenterPositionX = centerPositionX;
		outerCircleCenterPositionY = centerPositionY;
		innerCircleCenterPositionX = centerPositionX;
		innerCircleCenterPositionY = centerPositionY;
		this.outerCircleRadius = outerCircleRadius;
		this.innerCircleRadius = innerCircleRadius;
		outerCirclePaint = new StylePaint(true, true);
		outerCirclePaint.setColor(Color.GRAY);
		outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
		innerCirclePaint = new Paint();
		innerCirclePaint.setColor(Color.BLUE);
		innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	public void draw(Canvas canvas) {
		canvas.drawCircle(
			outerCircleCenterPositionX,
			outerCircleCenterPositionY,
			outerCircleRadius,
			outerCirclePaint
		);
		canvas.drawCircle(
			innerCircleCenterPositionX,
			innerCircleCenterPositionY,
			innerCircleRadius,
			innerCirclePaint
		);
	}

	public void update() {
		innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX * outerCircleRadius);
		innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY * outerCircleRadius);
	}

	public void setActuator(double touchPositionX, double touchPositionY) {
		double deltaX = touchPositionX - outerCircleCenterPositionX;
		double deltaY = touchPositionY - outerCircleCenterPositionY;
		double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		if (deltaDistance < outerCircleRadius) {
			actuatorX = deltaX / outerCircleRadius;
			actuatorY = deltaY / outerCircleRadius;
		} else {
			actuatorX = deltaX / deltaDistance;
			actuatorY = deltaY / deltaDistance;
		}
	}

	public boolean isPressed(double touchPositionX, double touchPositionY) {
		joystickCenterToTouchDistance = Math.sqrt(
			Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
			Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
		);
		return joystickCenterToTouchDistance < outerCircleRadius;
	}

	public boolean getIsPressed() {
		return isPressed;
	}

	public void setIsPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}
	
	public void setActuatorX(double actuatorX) {
		this.actuatorX = actuatorX;
	}

	public void setActuatorY(double actuatorY) {
		this.actuatorY = actuatorY;
	}
	
	public double getActuatorX() {
		return actuatorX;
	}

	public double getActuatorY() {
		return actuatorY;
	}

	public void resetActuator() {
		actuatorX = 0;
		actuatorY = 0;
	}
}
