package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Background extends GameEntity implements Actor{
	private ShapeGraphics graphics;
	private Polyline polyline;
	
	public Background(ActorGame game, Color color) {
		super(game, true);	
		
		createPolyline();
		
		//create the part associated to the terrain
		PartBuilder partBuilder = getEntity().createPartBuilder();
		//partBuilder.setFriction(friction);
		partBuilder.setGhost(true);
		partBuilder.setShape(polyline);
		partBuilder.build();
		
		graphics = new ShapeGraphics(polyline, color, color, 0.3f, 1.0f, -2);
		graphics.setParent(getEntity());
		
		game.ajouterActor(this);	
	}
	
	
	private void createPolyline() {
		polyline = new Polyline(
				   -400.0f, -400.0f,
				   -399.0f, 400.0f,
				   
				   400.0f, 400.0f,
				   400.0f, -400.0f
				   );
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
