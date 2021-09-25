/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {
	private ShapeGraphics graphics;

		public Terrain(ActorGame game, Polyline polyline, Color fillColor, Color outlineColor) {
			super(game, true);	
			if(polyline  == null) {
				throw new NullPointerException("L'argument polyline est null");
			}
			
			//create the part associated to the terrain
			PartBuilder partBuilder = getEntity().createPartBuilder();
			//partBuilder.setFriction(friction);
			partBuilder.setShape(polyline);
			partBuilder.build();
			
			graphics = new ShapeGraphics(polyline, fillColor, outlineColor, .1f, 1.0f, 0);
			graphics.setParent(getEntity());
			
			game.ajouterActor(this);	
		}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroy() {
		super.destroy();
		getOwner().supprimerActor(this);
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		graphics.draw(canvas);

	}
}
