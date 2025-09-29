package exercise_1;



import exercise_1.Circle;
import exercise_1.Shape;
import exercise_1.ShapeFactory;
import exercise_1.Square;


	interface Shape { void draw(); }

	
	class Circle implements Shape {
	 public void draw() { System.out.println("Drawing Circle"); }
	}
	class Square implements Shape {
	 public void draw() { System.out.println("Drawing Square"); }
	}

	
	class ShapeFactory {
	 public static Shape getShape(String type) {
	     switch(type.toLowerCase()) {
	         case "circle": return new Circle();
	         case "square": return new Square();
	         default: throw new IllegalArgumentException("Unknown shape");
	     }
	 }
	}

	
	public class CreationalPatteern_FactoryPattern {
	 public static void main(String[] args) {
	     Shape s1 = ShapeFactory.getShape("circle");
	     s1.draw();
	     Shape s2 = ShapeFactory.getShape("square");
	     s2.draw();
	 }
	}
