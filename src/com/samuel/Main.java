package com.samuel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.HvlTextBox;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{
	public static void main(String [] args){
		new Main();
	}
	public Main(){
		super(60, 720, 680, "Motion Mapper", new HvlDisplayModeDefault());
	}
	public void save(String fileName) {
		fileName = UI.getChildOfType(HvlArrangerBox.class, 0).getFirstOfType(HvlTextBox.class).getText(); //gets text box input; saves as variable
		 
		String userHomeFolder = System.getProperty("user.home")+"/Documents"; 
		File profile = new File(userHomeFolder, fileName+".txt");  
		BufferedWriter output = null;
		
		try {
			
			output = new BufferedWriter(new FileWriter(profile));  
			for(int i = 1; i < points.size(); i++) {
				output.write(Float.toString(lines.get(i).distance)+" "+Float.toString(points.get(i).angleOff));
				output.newLine();
			}
		
			output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	float mouseX;
	float mouseY;
	float drawTimer;
	float pointY;
	boolean generatedPath;
	static HvlFontPainter2D gameFont;
	float xOffsetBet;
	float yOffsetBet;
	float origAngle;
	float angleOff;
	ArrayList<Line> lines;
	ArrayList<Point> points;
	HvlMenu UI;
	String file;
	@Override
	public void initialize() {
		getTextureLoader().loadResource("osfont");
		gameFont =  new HvlFontPainter2D(getTexture(0), HvlFontPainter2D.Preset.FP_INOFFICIAL,.5f,8f,0); //font definition
		
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		drawTimer = 100;
		generatedPath = false;
		file = "";
		lines = new ArrayList<Line>();
		points = new ArrayList<Point>();
		UI = new HvlMenu() {
			public void draw(float delta) {
				mouseX = Mouse.getX();
				mouseY = -Mouse.getY() + Display.getHeight();
				drawTimer-=delta * 200;
				gameFont.drawWordc("Name your \n file and then \n press \nL _ SHIFT \n \n To start \n over, press \n 9", 650, 100, Color.white, 0.35f);
				HvlPainter2D.hvlDrawQuad(520, 0, 200, 680, new Color(130, 130, 130, 0.3f));
				if(drawTimer <=0 && Mouse.isButtonDown(0) && Mouse.getX() < 520) {
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
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && generatedPath == false) {
					for(float i = 0; i < lines.size(); i++) {
						if(lines.size() > 1 && i < lines.size()-1) {
							
							lines.get((int)i).setDistance(HvlMath.distance(lines.get((int)i).end, lines.get((int)i+1).end));
	
						}
						
						pointY = HvlMath.map(lines.get((int)i).end.x, lines.get((int) i).start.x, lines.get((int) i).end.x, lines.get((int) i).start.y, lines.get((int) i).end.y);
						Point point = new Point(lines.get((int)i).end.x, pointY);
						points.add(point);						
					}
					for(int i = 0; i < points.size(); i++) {
						if(i > 0) {
							xOffsetBet = points.get(i).xPos - points.get(i-1).xPos;
							yOffsetBet = points.get(i).yPos - points.get(i-1).yPos;
							//System.out.println(points.get(1).xPos);
							//System.out.println(points.get(0).xPos);

							origAngle =  Math.round(Math.toDegrees(Math.atan2(yOffsetBet, xOffsetBet)));
							points.get(i).setOrig(origAngle);
							angleOff = origAngle-points.get(i-1).origAngle;
							points.get(i).setAngle(angleOff);
							System.out.println(angleOff);
						}
					
					}
					file = UI.getChildOfType(HvlArrangerBox.class, 0).getFirstOfType(HvlTextBox.class).getText(); //gets text box input; saves as variable

					save(file);
					generatedPath = true;
				}
				if(lines.size() > 0) {
					HvlPainter2D.hvlDrawQuadc(lines.get(0).start.x, lines.get(0).start.y, 10, 10, Color.orange);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_9)) {
					UI.getChildOfType(HvlArrangerBox.class, 0).getFirstOfType(HvlTextBox.class).setText("");
					lines.clear();
					points.clear();
					generatedPath = false;
				}
				for(Point pointWave : points) {
					pointWave.draw(delta);
				}
				super.draw(delta);
			}
		};	
		UI.add(new HvlArrangerBox.Builder().setStyle(ArrangementStyle.HORIZONTAL).setWidth(250).setHeight(100).setX(Display.getWidth() - 235).setY(Display.getHeight()-180).build());

		UI.getChildOfType(HvlArrangerBox.class, 0).add(new HvlSpacer(30, 30));
		UI.getChildOfType(HvlArrangerBox.class, 0).add(new HvlTextBox.Builder().setWidth(200).setHeight(50).setFont(gameFont).setTextColor(Color.darkGray).setTextScale(0.25f).setOffsetY(20).setOffsetX(20).setText("").setFocusedDrawable(new HvlComponentDrawable() {	
			@Override
			public void draw(float delta, float x, float y, float width, float height) {
				HvlPainter2D.hvlDrawQuad(x,y,width,height, Color.lightGray);	
			}
		}).setUnfocusedDrawable(new HvlComponentDrawable() {
			
			@Override
			public void draw(float delta, float x, float y, float width, float height) {
				HvlPainter2D.hvlDrawQuad(x,y,width,height, Color.green);	
			}
		}).build());
		HvlMenu.setCurrent(UI);
	}
	@Override
	public void update(float delta) {

		HvlMenu.updateMenus(delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			exit();
		}
	}
}
