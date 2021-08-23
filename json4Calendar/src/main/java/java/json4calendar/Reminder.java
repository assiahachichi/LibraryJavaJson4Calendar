package java.json4calendar;
import android.provider.CalendarContract;

/**
 * @author Assia HACHICHI
 */
public class Reminder  {
	/**
	 * message to return when a reminder is null.
	 */
	public static final String REMINDER_NULL = "{\"VREMINDER\":{\n" +
			"        			\"MINUTES\": \"-1\"\n" +
			"      			}}";

	/**
	 * This variable give information about used method type in this reminder.
	 * By default the method is CalendarContract.Reminders.METHOD_ALERT
	 */
	private int method;

	/**
	 * This variable contains a number of minutes, to generate a reminder before the event start
	 */
	private int minutes;

	//*********************************************//
	//    Constructors                             //
	//*********************************************//

	/**
	 * This constructor initialises a reminder variable, which is minutes.
	 * This constructor initialises A default value of method, which is CalendarContract.Reminders.METHOD_ALERT
	 * @param minutes
	 */
	public Reminder(int minutes) {
		this(minutes, CalendarContract.Reminders.METHOD_ALERT);
	}

	/**
	 * This constructor initialises a reminder variables, which are minutes and method.
	 * @param minutes
	 * @param method
	 */
	public Reminder(int minutes, int method) {
		this.minutes = minutes;
		this.method = method;
	}

	//*********************************************//
	//    Getters and Setters                      //
	//*********************************************//

	/**
	 * This method is a getter,
	 * which returns the number of minutes to generate a reminder before the start of the associated event(s).
	 * @return number of minutes to generate a reminder before the event start
	 */
	public int getMinutes() {
		return this.minutes;
	}

	/**
	 * This method is a setter,
	 * which sets the number of minutes to generate a reminder before the start of the associated event(s).
	 * @param minutes number of minutes to generate a reminder before the event start
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * This method is a getter, which returns the method used in this reminder.
	 * By default the method is CalendarContract.Reminders.METHOD_ALERT
	 * @return method used
	 */
	public int getMethod() {
		return this.method;
	}

    //*********************************************//
	//    Methods                                  //
	//*********************************************//

	/**
	 * This static method returns a Json message, which indicates that this reminder is null.
	 * @return jSON indicating that this reminder is null
	 */
	public static String toJsonNull() {
		return REMINDER_NULL;
	}

	/**
	 * This method returns reminder state on a Json format.
	 * @return JSON reminder state
	 */
	public String toJson() {
		String s;
		s = "{\"VREMINDER\":{\n"
				+ "        			\"MINUTES\": \""+this.minutes+"\"\n"
				+ "      			}}";
		return s;
	}

	/**
	 * This method returns reminder state on a String format.
	 * @return String reminder state
	 */
	@Override
	public String toString() {
		return "Reminder{" +
				"minutes='" + minutes + '\'' +
				'}';
	}
}
