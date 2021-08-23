package java.json4calendar;

import android.content.ContentValues;
import android.graphics.Color;
import android.provider.CalendarContract;

import java.util.Date;

/**
 * @author Assia HACHICHI
 */
public class Event {

	private Date dtStart;
	private Date dtEnd;
	/**
	 * id of this Event. It can be concatenation of IdUser and IdRDV
	 */
	private long id;
	/**
	 * id of rendezvous
	 */
	private long idRDV;
	/**
	 * UID is created by concatenating ID and date of Created (sDtCreated),
	 * in order to have the last update about this event
	 */
	private String uid;
	private String description;
	private String location;
	private String title;
	private String function;
	private String type ;
	private Date dtCreated;
	private int color;
	private Reminder reminder;
	private String timeZone;

	//*********************************************//
	//    Constructor                              //
	//*********************************************//


	/**
	 * This constructors initialises instance variables.
	 * By default uid is a calculated ID,
	 * it's concatenation of Event ID (id) and dtCreated
	 * @param id Event ID
	 * @param idRdv id of rendez vous
	 * @param dtStart Event start date
	 * @param dtEnd Event end date
	 * @param dtCreated Created date
	 * @param type Event type
	 * @param title Event title
	 * @param description Event description (optional)
	 * @param function that could be either INSERT, UPDATE or DELETE
	 * @param location Event location
	 * @param reminder Event reminder
	 * @param timeZone Event time zone
	 */
	public Event(long id, long idRdv, Date dtStart, Date dtEnd, Date dtCreated,
				 String type, String title, String description, String function,
				 String location, Reminder reminder, String timeZone) {

		this.color = Color.GREEN;
		this.id = id;
		this.idRDV = idRdv;
		this.uid = id + "" + MappingDateString.convertDateToString4Calendar(dtCreated);
		this.dtStart = dtStart;
		this.dtEnd = dtEnd;
		this.type = type;
		this.title = title;
		this.description = description;
		this.function = function;
		this.location = location;
		this.dtCreated = dtCreated;
		this.reminder = reminder;
		this.timeZone = timeZone;
	}

	//*********************************************//
	//    Getters and Setters                      //
	//*********************************************//

	/**
	 * This method is a getter of dtCreated
	 * @return value of dtCreated
	 */
	public Date getDtCreated() {
		return this.dtCreated;
	}

	/**
	 * This method is a setter of dtCreated
	 * @param dtCreated new value of dtCreated
	 */
	public void setDtCreated(Date dtCreated) {
		this.dtCreated = dtCreated;
	}

	/**
	 * This method is a getter of dtStart in Date format
	 * @return value of dtStart
	 */
	public Date getDtStart() {
		return this.dtStart;
	}

	/**
	 * This method is a getter of dtStart in the String format
	 * @return value of dtStart
	 */
	public String getStringDtStart() {
		return MappingDateString.convertDateToString4Json(this.dtStart);
	}

	/**
	 * This method is a setter of dtStart
	 * @param dtStart new value of dtStart
	 */
	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	/**
	 * This method is a getter of type
	 * @return value of type
	 */
	public String getType() { return this.type; }

	/**
	 * This method is a setter of type
	 * @param type new value of type
	 */
	public void setType(String type) { this.type = type; }

	/**
	 * This method is a getter of dtEnd
	 * @return value of dtEnd
	 */
	public Date getDtEnd() {
		return this.dtEnd;
	}

	/**
	 * This method is a setter of dtEnd
	 * @param dtEnd new value of dtEnd
	 */
	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}

	/**
	 * This method is a getter of id
	 * @return value of id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * This method is a setter of id
	 * @param id new value of id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * This method is a getter of description
	 * @return value of description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * This method is a setter of description
	 * @param description new value of description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method is a getter of location
	 * @return value of location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * This method is a setter of location
	 * @param location new value of location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * This method is a getter of title
	 * @return value of title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * This method is a setter of title
	 * @param title new value of title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method is a getter of idRDV
	 * @return value of idRDV
	 */
	public long getIdRDV() {
		return this.idRDV;
	}

	/**
	 * This method is a setter of idRDV
	 * @param idRDV new value of idRDV
	 */
	public void setIdRDV(long idRDV) {
		this.idRDV = idRDV;
	}

	/**
	 * This method is a getter of uid
	 * @return value of uid
	 */
	public String getUid() {
		return this.uid;
	}

	/**
	 * This method is a setter of uid
	 * @param uid new value of uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * This method is a setter of function
	 * @param function new value of reminder
	 */
	public void setFunction(String function) { this.function = function; }

	/**
	 * This method is a setter of color
	 * @param color new value of color
	 */
	public void setColor(int color){this.color = color;}

	/**
	 * This method is a getter of function
	 * @return value of function
	 */
	public String getFunction() {
		return this.function;
	}

	/**
	 * This method is a getter of reminder
	 * @return value of reminder
	 */
	public Reminder getReminder() {
		return this.reminder;
	}

	/**
	 * This method is a setter of reminder
	 * @param reminder new value of reminder
	 */
	public void setReminder( Reminder reminder) {
		this.reminder = reminder;
	}

	/**
	 * This method is a getter of timeZone
	 * @return value of timeZone
	 */
	public String getTimeZone() {
		return this.timeZone;
	}

	/**
	 * This method is a setter of timeZone
	 * @param timeZone new value of timeZone
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	//*********************************************//
	//    Public Methods                           //
	//*********************************************//

	/**
	 * This method convert this event(object) into a ContentValues variable,
	 * which can be added, updated or deleted in Google Calender.
	 * @return resulting contentValues
	 */
	public ContentValues convertToGoogleCalendar(){
		String s;
		ContentValues cv;

		cv= new ContentValues();

		cv.put(CalendarContract.Events.EVENT_TIMEZONE, this.timeZone);
		cv.put(CalendarContract.Events.EVENT_COLOR,    this.color);
		cv.put(CalendarContract.Events.TITLE,          this.title);
		cv.put(CalendarContract.Events.DESCRIPTION,    this.description);
		cv.put(CalendarContract.Events.EVENT_LOCATION, this.location);
		cv.put(CalendarContract.Events.DTSTART,        this.dtStart.getTime());
		cv.put(CalendarContract.Events.DTEND,          this.dtEnd.getTime());

		s = MappingDateString.convertDateToString4Calendar(this.dtCreated);
		cv.put(CalendarContract.Events.UID_2445,       s);

		cv.put(CalendarContract.Events.CALENDAR_ID,    1);
		cv.put(CalendarContract.Events._ID,            this.id);
		cv.put(CalendarContract.Events.HAS_ALARM,      1);
		return cv;
	}

	/**
	 * Convert state of this event (object) into JSON data
	 * @return obtaining JSON data
	 */
	public String toJson() {

		String s = "{\"VEVENT\":{\n"
				+ "    	\"DTSTART\": \""+ MappingDateString.convertDateToString4Json(this.dtStart) +"\",\n"
				+ "    	\"DTEND\": \""+ MappingDateString.convertDateToString4Json(this.dtEnd) +"\",\n"
				+ "    	\"DTCREATED\": \""+MappingDateString.convertDateToString4Json(this.dtCreated)+"\",\n"
				+ "    	\"IDRDV\": \""+this.idRDV +"\",\n"
				+ "    	\"ID\": \""+this.id +"\",\n"
				+ "    	\"TYPE\": \""+this.type +"\",\n"
				+ "    	\"TITLE\": \""+this.title +"\",\n"
				+ "    	\"DESCRIPTION\": \""+this.description+"\",\n"
				+ "    	\"FUNCTION\": \""+this.function+"\",\n"
				+ "    	\"LOCATION\": \""+this.location+"\",\n";
		if (this.reminder != null) {
			s = s + "    	\"MINUTES\":\"" + this.reminder.getMinutes();
		}else{
			s = s + "    	\"MINUTES\":\"-1\"";
		}
		s = s + "      }},\n";
		return s;
	}

	/**
	 * Convert state this event (object) in String variable
	 * @return obtaining String
	 */
	@Override
	public String toString() {
		return "Event{" +
				"dtStart=" + dtStart +
				", dtEnd=" + dtEnd +
				", id=" + id +
				", idRDV=" + idRDV +
				", uid='" + uid + '\'' +
				", description='" + description + '\'' +
				", location='" + location + '\'' +
				", title='" + title + '\'' +
				", function='" + function + '\'' +
				", type='" + type + '\'' +
				", dtCreated=" + dtCreated +
				", color=" + color +
				", reminder=" + reminder +
				'}';
	}
}
