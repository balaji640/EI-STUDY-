package exercise_1;


import exercise_1.CreditCardPayment;
import exercise_1.PayPalPayment;
import exercise_1.PaymentStrategy;
import exercise_1.ShoppingCart;

//Strategy interface
	interface PaymentStrategy {
	 void pay(int amount);
	}

	//Concrete strategies
	class CreditCardPayment implements PaymentStrategy {
	 public void pay(int amount) { System.out.println("Paid " + amount + " using Credit Card."); }
	}
	class PayPalPayment implements PaymentStrategy {
	 public void pay(int amount) { System.out.println("Paid " + amount + " using PayPal."); }
	}

	//Context
	class ShoppingCart {
	 private PaymentStrategy strategy;
	 public void setPaymentStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
	 public void checkout(int amount) { strategy.pay(amount); }
	}

	//Demo
	public class BehavioralPattern_StrategyPattern {
	 public static void main(String[] args) {
	     ShoppingCart cart = new ShoppingCart();
	     cart.setPaymentStrategy(new CreditCardPayment());
	     cart.checkout(500);
	     cart.setPaymentStrategy(new PayPalPayment());
	     cart.checkout(300);
	 }
	}
