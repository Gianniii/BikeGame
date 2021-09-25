package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.math.World;
import java.awt.Color;

public class RopeGame implements Game {
		private ImageGraphics graphics;
		private ShapeGraphics ballGraphics;
		private Entity ball;
		private Entity block;
		private Window window;
		private World world;

		@Override
		public boolean begin(Window window, FileSystem fileSystem) {
			this.window = window;
			world = new World();
			world.setGravity(new Vector(0.0f, -9.81f));

			EntityBuilder entityBuilder = world.createEntityBuilder();
			entityBuilder.setFixed(true);
			entityBuilder.setPosition(new Vector(1.f, 0.5f));
			block = entityBuilder.build();

			EntityBuilder entityBuilder1 = world.createEntityBuilder();
			entityBuilder1.setFixed(false);
			entityBuilder1.setPosition(new Vector(0.6f, 4.0f));
			ball = entityBuilder1.build();
			
		// At this point, your body is in the world, but it ha no geometry
		// You need to use another builder to add shapes
		PartBuilder partBuilder = block.createPartBuilder();
		// Create a square polygon, and set the shape of the builder to this polygon
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), 
				new Vector(1.0f, 0.0f), 
				new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));
		partBuilder.setShape(polygon);

		// Finally, do not forget the following line
		partBuilder.build();

		// Note : we do not need to keep a reference on partBuilder
			
			PartBuilder partBuilder1 = ball.createPartBuilder();
			Circle circle = new Circle(0.6f);
			partBuilder1.setShape(circle);
			partBuilder1.build();
			
			ballGraphics = new ShapeGraphics(circle , Color.BLUE, Color.RED, 0.1f, 1.0f, 0) ;
			ballGraphics.setParent(ball);

			graphics = new ImageGraphics("stone.broken.4.png", 1, 1);
			graphics.setParent(block);

			RopeConstraintBuilder ropeConstraintBuilder =
					world.createRopeConstraintBuilder() ;
					ropeConstraintBuilder.setFirstEntity(block) ;
					ropeConstraintBuilder.setFirstAnchor(new Vector(1.0f/2,
					1.0f/2)) ;
					ropeConstraintBuilder.setSecondEntity(ball) ;
					ropeConstraintBuilder.setSecondAnchor(Vector.ZERO) ;
					ropeConstraintBuilder.setMaxLength(6.0f) ;
					ropeConstraintBuilder.setInternalCollision(true) ;
					ropeConstraintBuilder.build() ;
					
			return true;
		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			world.update(deltaTime);
			window.setRelativeTransform(Transform.I.scaled(10.0f));
			graphics.draw(window);
			ballGraphics.draw(window);
		}

		@Override
		public void end() {
			// TODO Auto-generated method stub
		}
	}
