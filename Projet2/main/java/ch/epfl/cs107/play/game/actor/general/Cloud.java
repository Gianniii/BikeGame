package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Cloud extends GameEntity implements Actor {
	private final static String nomImage ="smoke.white.1.png";
	private ImageGraphics graphics;
	
	public Cloud(ActorGame game, Vector position,float length, float height, float transparency) {
		super(game, true, position);	
		if (length < 0 || height < 0) {
			throw new IllegalArgumentException("La hauteur et la longueur doivent être strictement positive");
		}
		if (transparency > 1 || transparency < 0) {
			throw new IllegalArgumentException("La transparence doit être entre zéro et un");
		}
		graphics = new ImageGraphics(nomImage, length, height);
		graphics.setDepth(1);
		graphics.setAlpha(transparency);
		graphics.setParent(getEntity());
		
		partBuilder(length, height);
		
		game.ajouterActor(this);
	}
	
	//construit le corp physique de L'objet
	public void partBuilder(float length, float height) {
		   PartBuilder partBuilder = getEntity().createPartBuilder();
		   Polygon polygon = new Polygon(
					new Vector(0.0f, 0.0f),
					new Vector(length, 0.0f),
					new Vector(length, height),
					new Vector(0.0f, height)
				);
		   //want to be able to go through it we
		    partBuilder.setGhost(true);
			partBuilder.setShape(polygon);
			partBuilder.build();
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
