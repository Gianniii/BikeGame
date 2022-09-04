package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.BasicContactListener;

public class ContactGame implements Game{
	private ImageGraphics graphics;
	private ShapeGraphics ballGraphics;
	private Entity ball;
	private Entity block;
	private Window window;
	private World world;
	private BasicContactListener contactListener;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(true);
		entityBuilder.setPosition(new Vector(-5.0f, -1.0f));
		block = entityBuilder.build();

		EntityBuilder entityBuilder1 = world.createEntityBuilder();
		entityBuilder1.setFixed(false);
		entityBuilder1.setPosition(new Vector(0.0f, 2.0f));
		ball = entityBuilder1.build();
		
	
	PartBuilder partBuilder = block.createPartBuilder();
	Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), 
			new Vector(10.0f, 0.0f), 
			new Vector(10.0f, 1.0f),
			new Vector(0.0f, 1.0f));
	partBuilder.setShape(polygon);
	partBuilder.build();
		
		PartBuilder partBuilder1 = ball.createPartBuilder();
		Circle circle = new Circle(0.5f);
		partBuilder1.setShape(circle);
		partBuilder1.build();
		
		ballGraphics = new ShapeGraphics(circle , Color.BLUE, Color.BLUE, 0.1f, 1, 0) ;
		ballGraphics.setParent(ball);

		graphics = new ImageGraphics("stone.broken.4.png", 10, 1);
		graphics.setParent(block);
		
		contactListener = new BasicContactListener();
		ball.addContactListener(contactListener);
		
		return true;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		graphics.draw(window);
		// contactListener is associated to ball
		// contactListener.getEntities() returns the list of entities in collision with ball
		int numberOfCollisions = contactListener.getEntities().size() ;
		if (numberOfCollisions > 0){
		ballGraphics.setFillColor(Color.RED) ;
		}
		ballGraphics.draw(window);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
	}
}


