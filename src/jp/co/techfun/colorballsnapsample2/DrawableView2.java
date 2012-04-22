package jp.co.techfun.colorballsnapsample2;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.Toast;

public class DrawableView2 extends View{
	/** class fields
	 * 
	 * @param context
	 */
	// array for halls
	private ShapeDrawable[] drawables;
	
	// array for fallen balls
	private ShapeDrawable[] fallenBallDrawables;
	
	// ball
	private ShapeDrawable ballDrawables;
	
	// number of halls
	private static final int HOLES_SIZE = 5;
	
	// number of fallen balls
	private static final int FALL_BALL_STOCKS_SIZE = 20;
	
	// index for the red ball
	private static final int RED_OVAL = 0;

	// index for the green ball
	private static final int GREEN_OVAL = 1;

	// index for the blue ball
	private static final int BLUE_OVAL = 2;

	// index for the white ball
	private static final int WHITE_OVAL = 3;

	// diameter
	private static final int DIAMETER = 50;
	
	// radius of the circle
	private static final int RADIUS = DIAMETER / 2;

	/** define gradations
	 * 
	 */
	// blue
	private static final RadialGradient RADIAL_GRADIENT_BLUE =
			new RadialGradient(10, 10, 
									RADIUS, Color.CYAN, Color.BLUE, 
									Shader.TileMode.MIRROR);
	
	private static final RadialGradient RADIAL_GRADIENT_RED =
			new RadialGradient(10, 10, 
									RADIUS, Color.YELLOW, Color.RED, 
									Shader.TileMode.MIRROR);
	
	private static final RadialGradient RADIAL_GRADIENT_GREEN =
			new RadialGradient(10, 10, 
									RADIUS, Color.WHITE, Color.GREEN, 
									Shader.TileMode.MIRROR);
	
	private static final RadialGradient RADIAL_GRADIENT_WHITE =
			new RadialGradient(10, 10, 
									RADIUS, Color.WHITE, Color.DKGRAY, 
									Shader.TileMode.MIRROR);
	
	// the current color of the ball
	private int currentColor;
	
	// the number of balls fallen
	private int fallCount;
	
	// margin for the both sides of the display
	private static final int OFFSET_X = 0;
	
	// margin for the both sides of the display
	private static final int OFFSET_Y = 100;
		
	// location of balls
	private int movableBallX;
	private int movableBallY;

	// Constructor
	public DrawableView2(Context context) {
		super(context);
		
		// generate a ShapedDrawable instance
		drawables = new ShapeDrawable[HOLES_SIZE];
		fallenBallDrawables = new ShapeDrawable[FALL_BALL_STOCKS_SIZE];
		
		// draw halls
		drawables[RED_OVAL] = new ShapeDrawable(new OvalShape());
		drawables[GREEN_OVAL] = new ShapeDrawable(new OvalShape());
		drawables[BLUE_OVAL] = new ShapeDrawable(new OvalShape());
		drawables[WHITE_OVAL] = new ShapeDrawable(new OvalShape());
		
		// set colors on the halls
		drawables[RED_OVAL].getPaint().setShader(
						new RadialGradient(25, 25, 20, 
														Color.BLACK, Color.RED, 
														Shader.TileMode.MIRROR));
		drawables[GREEN_OVAL].getPaint().setShader(
				new RadialGradient(25, 25, 20, 
												Color.BLACK, Color.GREEN, 
												Shader.TileMode.MIRROR));
		drawables[BLUE_OVAL].getPaint().setShader(
				new RadialGradient(25, 25, 20, 
												Color.BLACK, Color.BLUE, 
												Shader.TileMode.MIRROR));
		drawables[WHITE_OVAL].getPaint().setShader(
				new RadialGradient(25, 25, 20, 
												Color.BLACK, Color.WHITE, 
												Shader.TileMode.MIRROR));
		
		// draw the ball
		ballDrawables = new ShapeDrawable(new OvalShape());
		
		// set the color
		ballDrawables.getPaint().setShader(getRandomRadialGradient());
		
		// set the initial location of the ball
		movableBallX = - DIAMETER - 1;
		movableBallY = movableBallX;
	}//public DrawableView2(Context context)

	protected void onDraw(Canvas canvas) {
		// set the draw area
		int width = canvas.getWidth() - OFFSET_X;
		int height = canvas.getHeight() - OFFSET_Y;
		
		// set the coordinates for the circle to be located at the center
		int cX = width / 2 - RADIUS;
		int cY = height / 2 - RADIUS;
		
		// draw 4 holes
		drawColorHole(canvas, width, height, cX, cY);
		
		// if the ball is on top of the hole?
		if (drawables[currentColor].getBounds().contains(movableBallX, movableBallY)) {
			
			//debug
			// toast
//			Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
			
			
			// if the stock is full
			if (fallCount >= fallenBallDrawables.length) {
				// empty the stock
				for (@SuppressWarnings("unused")
					Drawable dr : fallenBallDrawables) {
						// nullify
						dr = null;
				}//for (Drawable dr : fallenBallDrawables)
				
				// reset the counter
				fallCount = 0;
				
			}//if (fallCount >= fallenBallDrawables.length)
			
			// stock the fallen ball
			fallenBallDrawables[fallCount] = ballDrawables;
			
			// increment the count
			fallCount ++;
			
			// generate a new ball
			ballDrawables = new ShapeDrawable(new OvalShape());
			
			// set the color
			ballDrawables.getPaint().setShader(getRandomRadialGradient());
			
			// set the location
			movableBallX = cX;
			movableBallY = cY;
			
		}//if (drawables[currentColor].getBounds().contains(movableBallX, movableBallY))
		
		// draw the ball on the screen
		drawMovableBall(canvas, width, height, cX, cY);
		
		
	}//protected void onDraw(Canvas canvas)
	
	
	
	private void drawMovableBall(Canvas canvas, int width, int height, int cX,
			int cY) {
		// the ball out of the screen?
		//	=> i.e. the entire ball out of the screen?
//		if (movableBallX < - DIAMETER || movableBallX > width 
//		if (movableBallY > height) {
//			// set the ball at the center of the screen
//			movableBallX = cX;
//			movableBallY = cY;
//		}//if (movableBallX < - DIAMETER || movableBallX > width || movableBallY < - DIAMETER || movableBallY > height)
		
		/** The ball out of the screen
		 * 
		 */
		// Left side
		if (movableBallX < - DIAMETER) {
			movableBallX = width - DIAMETER;
		}//if (movableBallX < - DIAMETER)
		
		// Right side
		if (movableBallX > width) {
			movableBallX = OFFSET_X;
		}//if (movableBallX > width)
		
		// Top side
		if (movableBallY < - DIAMETER) {
			movableBallY = height - DIAMETER;
		}//if (movableBallX < - DIAMETER)
		
		// Bottom side
		if (movableBallY > height) {
			movableBallY = OFFSET_Y;
		}//if (movableBallY > height)
		
		/** display the ball
		 * 
		 */
		// set the bounds
		ballDrawables.setBounds(movableBallX, movableBallY, 
						movableBallX + DIAMETER, movableBallY + DIAMETER);
		
		// display
		ballDrawables.draw(canvas);
	}//drawMovableBall()

	private void drawColorHole(Canvas canvas, int width, int height, int cX,
			int cY) {
		
		/** draw the hole for the red ball
		 *		At the top of the screen
		 */
		// set the starting coordinate
		int left = cX;
		int top = OFFSET_Y;
		
		// set the ending coordinate
		int right = cX + DIAMETER;
		int bottom = OFFSET_Y + DIAMETER;
		
		// draw the hole
		drawables[RED_OVAL].setBounds(left, top, right, bottom);
		drawables[RED_OVAL].draw(canvas);
		
		/** draw the hole for the green ball
		 *		At the right of the screen
		 */
		// set the starting coordinate
		left = width - DIAMETER;
		top = cY;
		
		// set the ending coordinate
		right = width;
		bottom = cY + DIAMETER;
		
		// draw the hole
		drawables[GREEN_OVAL].setBounds(left, top, right, bottom);
		drawables[GREEN_OVAL].draw(canvas);
		
		/** draw the hole for the blue ball
		 *		At the bottom of the screen
		 */
		// set the starting coordinate
		left = cX;
		top = height - DIAMETER;
		
		// set the ending coordinate
		right = cX + DIAMETER;
		bottom = height;
		
		// draw the hole
		drawables[BLUE_OVAL].setBounds(left, top, right, bottom);
		drawables[BLUE_OVAL].draw(canvas);
		
		/** draw the hole for the white ball
		 *		At the left of the screen
		 */
		// set the starting coordinate
		left = OFFSET_X;
		top = cY;
		
		// set the ending coordinate
		right = OFFSET_X + DIAMETER;
		bottom = cY + DIAMETER;
		
		// draw the hole
		drawables[WHITE_OVAL].setBounds(left, top, right, bottom);
		drawables[WHITE_OVAL].draw(canvas);
		
	}//private void drawColorHole()

	private Shader getRandomRadialGradient() {
		// number of gradation types
		final int type = 4;
		
		// set the color number
		currentColor = new Random().nextInt(type);
		
		// set the gradation type
		switch (currentColor) {
			case RED_OVAL:
				return RADIAL_GRADIENT_RED;
			case GREEN_OVAL:
				return RADIAL_GRADIENT_GREEN;
			case BLUE_OVAL:
				return RADIAL_GRADIENT_BLUE;
			case WHITE_OVAL:
				return RADIAL_GRADIENT_WHITE;
				
		}//switch (currentColor)
		
		return null;
	}

	public void effectAccelaration(float x, float y, float z) {
		// TODO 自動生成されたメソッド・スタブ
		movableBallX -= x * 2;
		
		movableBallY += y * 2;
	}

}

