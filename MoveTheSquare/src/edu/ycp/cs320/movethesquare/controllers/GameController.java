package edu.ycp.cs320.movethesquare.controllers;

import edu.ycp.cs320.movethesquare.model.Game;
import edu.ycp.cs320.movethesquare.model.Square;

public class GameController {
	public void computeSquareMoveDirection(Game game, Square square, double mouseX, double mouseY) {
		if (mouseX >= 0 && mouseX < game.getWidth() && mouseY >= 0 && mouseY < game.getHeight()) {

/*
 * Modified code to normalize the dx and dy vectors and apply those to moveX and moveY
 */
			double dx = mouseX - (square.getX() + square.getWidth()/2);
			double dy = mouseY - (square.getY() + square.getHeight()/2);
			
			double mag = Math.sqrt(dx * dx + dy * dy);
			
			double moveX = dx / mag, moveY = dy / mag;
			
			//Old code that is irrelevant now
			/*
			if (dx > 0) {
				moveX = Game.MOVE_DIST;
			} else {
				moveX = -Game.MOVE_DIST;
			}
			if (dy > 0) {
				moveY = Game.MOVE_DIST;
			} else {
				moveY = -Game.MOVE_DIST;
			}
			*/

			game.setSquareDx(moveX * Game.MOVE_DIST);
			game.setSquareDy(moveY *  Game.MOVE_DIST);
			
/*
 * Modified code to avoid square vibrating when on mouse
 */
			double maxDist = 1;
			double distance = Math.sqrt(Math.pow(square.getX() + (square.getWidth() / 2) - mouseX, 2) + Math.pow(square.getY() + (square.getHeight() / 2) - mouseY, 2));
			System.out.println(distance);
			if (distance < maxDist) {
				game.setSquareDx(0);
				game.setSquareDy(0);
			};
			
		}
	}

	public void moveSquare(Game game, Square square) {
/*
 * Modified code to stop square from going outside of window
 * Also changed parameter name for "model" to "game" to match parameter name in above method (because who would do that?)
 */
		//Snapping to horizontal window bounds
		if (square.getX() < 0) {
			square.setX(0);
		}
		else if (square.getX() + square.getWidth() > game.getWidth())  {
			square.setX(game.getWidth() - square.getWidth());
		}
		else {
			square.setX(square.getX() + game.getSquareDx());
		}
		
		//Snapping to vertical window bounds
		if (square.getY() < 0) {
			square.setY(0);
		}
		else if (square.getY() + square.getHeight() > game.getHeight()) {
			square.setY(game.getHeight() - square.getHeight());
		}
		else {
			square.setY(square.getY() + game.getSquareDy());
		}

		//If at edge, remove velocity
		//(Cannot remove velocity unless velocity is in same direction of edge because otherwise it would just lock it if it tried to move away)
		if (square.getX() == 0 && game.getSquareDx() < 0) game.setSquareDx(0);
		if (square.getX() + square.getWidth() == game.getWidth() && game.getSquareDx() > 0) game.setSquareDx(0);
		if (square.getY() == 0 && game.getSquareDy() > 0) game.setSquareDy(0);
		if (square.getY() + square.getHeight() == game.getHeight() && game.getSquareDy() < 0) game.setSquareDy(0);
	}
}
