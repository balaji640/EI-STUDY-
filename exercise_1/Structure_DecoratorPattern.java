package exercise_1;


import exercise_1.Coffee;
import exercise_1.CoffeeDecorator;
import exercise_1.MilkDecorator;
import exercise_1.SimpleCoffee;
import exercise_1.SugarDecorator;


	interface Coffee {
	 String getDescription();
	 double getCost();
	}


	class SimpleCoffee implements Coffee {
	 public String getDescription() { return "Simple Coffee"; }
	 public double getCost() { return 2.0; }
	}

	
	abstract class CoffeeDecorator implements Coffee {
	 protected Coffee coffee;
	 public CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
	 public String getDescription() { return coffee.getDescription(); }
	 public double getCost() { return coffee.getCost(); }
	}


	class MilkDecorator extends CoffeeDecorator {
	 public MilkDecorator(Coffee coffee) { super(coffee); }
	 public String getDescription() { return super.getDescription() + ", Milk"; }
	 public double getCost() { return super.getCost() + 0.5; }
	}
	class SugarDecorator extends CoffeeDecorator {
	 public SugarDecorator(Coffee coffee) { super(coffee); }
	 public String getDescription() { return super.getDescription() + ", Sugar"; }
	 public double getCost() { return super.getCost() + 0.2; }
	}

	public class Structure_DecoratorPattern{
	 public static void main(String[] args) {
	     Coffee coffee = new SimpleCoffee();
	     coffee = new MilkDecorator(coffee);
	     coffee = new SugarDecorator(coffee);
	     System.out.println(coffee.getDescription() + " -> $" + coffee.getCost());
	 }
	}


