package com.samuel;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class Point {
	public float xPos;
	public float yPos;
	
	public Point(float xArg, float yArg) {
		this.xPos = xArg;
		this.yPos = yArg;
	}
	public void draw(float delta) {
		HvlPainter2D.hvlDrawQuadc(this.xPos, this.yPos, 10, 10, Color.blue);
	}
}
