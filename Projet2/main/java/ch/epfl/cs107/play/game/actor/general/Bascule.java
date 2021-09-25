package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bascule extends GameEntity implements Actor {
	private final static String nomImage ="wood.3.png";
	private ImageGraphics plankGraphics;
	private BaseBlock base;

	public Bascule(ActorGame game, Vector position) {
		super(game, false, position);

		base = new BaseBlock(game, position, 3.0f, 0.5f);
		
		// set graphics
		plankGraphics = new ImageGraphics(nomImage, 8.0f, 0.2f);
		plankGraphics.setParent(getEntity());
		
		// build part
		partBuilder();

		base.buildRevoluteConstraint(getEntity());

		game.ajouterActor(this);
	}

	// construit le corp physique de L'objet
	public void partBuilder() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(8.0f, 0.0f), new Vector(8.0f, 0.2f),
				new Vector(0.0f, 0.2f));
		partBuilder.setShape(polygon);
		partBuilder.build();
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		plankGraphics.draw(canvas);
	}

	// à modifier
	public void destroy() {
		base.destroy();
		super.destroy();
		getOwner().supprimerActor(this);

	}
}
