package com.samuel;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class Line {
	public HvlCoord2D start;
	public HvlCoord2D end;

	public Line(HvlCoord2D startPt, HvlCoord2D endPt) {
		this.start = startPt;
		this.end = endPt;
	}
	public void draw(float delta) {
		HvlPainter2D.hvlDrawLine(start.x, start.y, end.x, end.y, Color.white);
	}
}
