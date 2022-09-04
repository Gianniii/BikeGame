/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Window;

public abstract class Level extends Node implements Actor {
	protected ActorGame game; // je l'ai en protected pr l'instant pour que les autres levels ont accés
	private float alpha = 1.0f;

	public Level(ActorGame game) {
		this.game = game;
	}

	public void update(float deltaTime) {
		alpha = alpha - 0.004f;
		if (alpha < 0) {
			game.supprimerActor(this);
		}
	}

	public abstract void createAllActors();

	public void draw(Canvas canvas) {
		TextGraphics message = new TextGraphics("", 0.3f, Color.BLUE, Color.BLUE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), alpha, 100.0f);
		message.setParent(canvas);
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		message.setText("Next Level");
		message.draw(canvas);
	}
}
