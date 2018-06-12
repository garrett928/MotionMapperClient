package com.samuel;

import org.newdawn.slick.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{
	public static void main(String [] args){
		new Main();
	}
	public Main(){
		super(60, 720, 680, "Motion Mapper", new HvlDisplayModeDefault());
	}
	
	float mouseX;
	float mouseY;
	float drawTimer;
	
	@Override
	public void initialize() {
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		drawTimer = 50;
	}
	@Override
	public void update(float delta) {
		mouseX = Mouse.getX();
		mouseY = -Mouse.getY() + Display.getHeight();
		
		HvlPainter2D.hvlDrawQuadc(mouseX, mouseY, 50, 50, Color.blue);
	}
}
