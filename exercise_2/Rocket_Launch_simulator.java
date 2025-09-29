package exercise_2;

	import java.util.*;

	interface Observer {
	    void update(String msg);
	}

	class ConsoleLogger implements Observer {
	    public void update(String msg) {
	        System.out.println(msg);
	    }
	}

	class Rocket {
	    private String stage = "Pre-Launch";
	    private int fuel = 100;      // percentage
	    private int altitude = 0;    // km
	    private int speed = 0;       // km/h
	    private boolean checksDone = false;
	    private boolean launched = false;

	    public String getStage() { return stage; }
	    public int getFuel() { return fuel; }
	    public int getAltitude() { return altitude; }
	    public int getSpeed() { return speed; }
	    public boolean isChecksDone() { return checksDone; }
	    public boolean isLaunched() { return launched; }

	    public void performChecks() {
	        checksDone = true;
	    }

	    public void launchStep() {
	        if (!launched) {
	            launched = true;
	            stage = "Stage 1";
	        }
	        if (fuel <= 0) {
	            stage = "Mission Failed";
	            return;
	        }

	        fuel -= 10;
	        altitude += 10;
	        speed += 1000;

	        if (fuel == 50 && stage.equals("Stage 1")) {
	            stage = "Stage 2";
	        }
	        if (fuel <= 20 && altitude >= 100) {
	            stage = "Orbit Achieved";
	        }
	    }

	    public String getStatus() {
	        return "Stage: " + stage +
	               ", Fuel: " + fuel + "%" +
	               ", Altitude: " + altitude + " km" +
	               ", Speed: " + speed + " km/h";
	    }
	}

	
	class MissionControl {
	    private static MissionControl instance;
	    private Rocket rocket = new Rocket();
	    private List<Observer> observers = new ArrayList<>();

	    private MissionControl() {}

	    public static MissionControl getInstance() {
	        if (instance == null) instance = new MissionControl();
	        return instance;
	    }

	    public void addObserver(Observer o) { observers.add(o); }
	    private void notifyAllObservers(String msg) {
	        for (Observer o : observers) o.update(msg);
	    }

	    public void startChecks() {
	        rocket.performChecks();
	        notifyAllObservers("✅ All systems are 'Go' for launch.");
	    }

	    public void launch() {
	        if (!rocket.isChecksDone()) {
	            notifyAllObservers("⚠ Cannot launch. Run start_checks first!");
	            return;
	        }
	        notifyAllObservers("🚀 Launch initiated...");
	        runStep(); // start first second
	    }

	    public void fastForward(int seconds) {
	        for (int i = 0; i < seconds; i++) {
	            runStep();
	            if (rocket.getStage().equals("Orbit Achieved") || rocket.getStage().equals("Mission Failed")) break;
	        }
	    }

	    private void runStep() {
	        rocket.launchStep();
	        notifyAllObservers(rocket.getStatus());
	    }
	}

	
	interface Command {
	    void execute(MissionControl mc, String... args);
	}

	class StartChecksCommand implements Command {
	    public void execute(MissionControl mc, String... args) { mc.startChecks(); }
	}
	class LaunchCommand implements Command {
	    public void execute(MissionControl mc, String... args) { mc.launch(); }
	}
	class FastForwardCommand implements Command {
	    public void execute(MissionControl mc, String... args) {
	        try {
	            int secs = Integer.parseInt(args[0]);
	            mc.fastForward(secs);
	        } catch (Exception e) {
	            System.out.println("⚠ Invalid fast_forward argument.");
	        }
	    }
	}

	
	public class Rocket_Launch_simulator {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        MissionControl mc = MissionControl.getInstance();
	        mc.addObserver(new ConsoleLogger());

	        Map<String, Command> commands = new HashMap<>();
	        commands.put("start_checks", new StartChecksCommand());
	        commands.put("launch", new LaunchCommand());
	        commands.put("fast_forward", new FastForwardCommand());

	        System.out.println("=== Rocket Launch Simulator ===");
	        System.out.println("Commands: start_checks | launch | fast_forward X | exit");

	        while (true) {
	            System.out.print("> ");
	            String input = sc.nextLine().trim();
	            if (input.equalsIgnoreCase("exit")) break;

	            String[] parts = input.split(" ");
	            Command cmd = commands.get(parts[0]);
	            if (cmd != null) {
	                if (parts.length > 1) cmd.execute(mc, Arrays.copyOfRange(parts, 1, parts.length));
	                else cmd.execute(mc);
	            } else {
	                System.out.println("⚠ Unknown command.");
	            }
	        }

	        sc.close();
	    }
	}
