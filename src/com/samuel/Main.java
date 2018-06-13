package com.samuel;

import org.newdawn.slick.Color;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
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
	float pointY;
	boolean generatedPath;
	ArrayList<Line> lines;
	ArrayList<Point> points;
	
	@Override
	public void initialize() {
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		drawTimer = 50;
		generatedPath = false;
		lines = new ArrayList<Line>();
		points = new ArrayList<Point>();
	}
	@Override
	public void update(float delta) {
		mouseX = Mouse.getX();
		mouseY = -Mouse.getY() + Display.getHeight();
		drawTimer-=delta * 10000;
		if(drawTimer <=0 && Mouse.isButtonDown(0)) {
			if(lines.size() == 0) {
				Line line = new Line(new HvlCoord2D(mouseX, mouseY), new HvlCoord2D(mouseX, mouseY));
				lines.add(line);
				drawTimer = 50;
			}
			if(lines.size() > 0) {
				Line line = new Line(lines.get(lines.size()-1).end, new HvlCoord2D(mouseX, mouseY));
				lines.add(line);
				drawTimer = 50;

			}
		}
		for(Line lineWave : lines) {
			lineWave.draw(delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_G) && generatedPath == false) {
			for(float i = 0; i < lines.size()-1; i++) {
			
				pointY = HvlMath.map(i, lines.get((int) i).start.x, lines.get((int) i).end.x, lines.get((int) i).start.y, lines.get((int) i).end.y);
				Point point = new Point(lines.get((int)i).end.x, pointY);
				points.add(point);
			}
		
			generatedPath = true;
		}
		for(Point pointWave : points) {
			pointWave.draw(delta);
		}
		
	}
}
