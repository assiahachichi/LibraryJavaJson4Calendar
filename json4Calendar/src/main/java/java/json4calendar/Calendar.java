package java.json4calendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Assia HACHICHI
 */
public class Calendar {
	private ContentResolver contentResolver;
	private Context context;
	private final String timeZone;
	private Reminder defaultReminder;
	private Date lastDateCreated;
	private ArrayList<Event> listEvents;

	//*********************************************//
	//    Constructor                              //
	//*********************************************//

	/**
	 * This constructors initialises instance variables.
	 * @param contentResolver Application Content Resolver
	 * @param context Application context
	 * @param timeZone Calendar time zone
	 */
	public Calendar(ContentResolver contentResolver, Context context, String timeZone) {
		this.lastDateCreated = null;
		this.defaultReminder = null;
		this.contentResolver = contentResolver;
		this.context = context;
		this.timeZone = timeZone;
		this.listEvents = new ArrayList<Event>();
	}

	//*********************************************//
	//    Getters and Setters                      //
	//*********************************************//

	/**
	 * This method is a setter of lastDateCreated
	 * @param date new date value
	 */
	public void setLastDateCreated(Date date) {
		if (lastDateCreated == null){
			lastDateCreated = date;
		}else{
			if (date.compareTo(lastDateCreated)>0){
				lastDateCreated = date;}
		}
	}

	/**
	 * This method is a getter of lastDateCreated
	 * @return the value of lastDateCreated
	 */
	public Date getLastDateCreated(){
		return lastDateCreated;
	}

	/**
	 * This method add a given event into the ArrayList "allEvents"
	 * @param event the event to be added
	 */
	public void addEvent(Event event) {
		listEvents.add(event);
	}

	/**
	 * This method is a getter of allEvents
	 * @return an ArrayList, which contains all events
	 */
	public ArrayList<Event> getAllEvents(){return this.listEvents;}

	/**
	 * This method is a getter of defaultReminder
	 * @return value of defaultReminder
	 */
	public Reminder getDefaultReminder() {
		return this.defaultReminder;
	}

	/**
	 * This method is a setter of defaultReminder
	 * @param defaultReminder new value of defaultReminder
	 */
	public void setDefaultReminder(Reminder defaultReminder) {
		this.defaultReminder = defaultReminder;
	}


	//*********************************************//
	//    Public Methods                           //
	//*********************************************//
	/**
	 * This method generates a reminder for each event of this calendar
	 */
	public void generateAllNewReminders() {
		Uri eventUri;

		for (Event event : this.listEvents){
			if (!event.getFunction().equals("DELETE")){
				eventUri = queryEvent(event);
				if (eventUri!=null) generateEventReminders(eventUri, event);
			}
		}
	}


	/**
	 * This method converts (insert/delete or update)
	 * each event of this Calendar into an event in GoogleCalendar,
	 * by taking into consideration the given event function (INSERT/UPDATE or DELETE).
	 */
	public void convertToGoogleCalendar( ) {

				for (Event event : this.listEvents) {
					if (event.getFunction().equals("INSERT")) {
						insertEvent(event);
					} else {
						if (event.getFunction().equals("UPDATE")) {
							updateEvent(event);
						} else {
							//DELETE
							deleteEvent(event);
						}
					}
				}
			}



	/**
	 *
	 * This method converts this Calendar into a File.
	 * File name is given in String format
	 * //@param fileName is a String containing a file name
	 * //@param directoryName is a name of directory
	 */

	public void save( ) {
		String text = "toto";
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput("a.txt", Context.MODE_PRIVATE);
			fos.write(text.getBytes());
			String s = "Saved to " ;//+ Environment.DIRECTORY_DOCUMENTS.toString() + "/a.txt";
			Toast.makeText(context, s, Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void convertCalendarToFile(String directory, String fileName)  {
		String text = this.toJson();
		Calendar.writeToFile(context, text, directory, fileName);
	}


	public static void writeToFile(Context context, String text, String directory, String fileName)  {
		FileOutputStream fos = null;
		File file, dir;

		dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();

		}
		file = new File(directory, fileName);
		try {

			fos = new FileOutputStream(file, false);
			fos.write(text.getBytes());
			fos.flush();
			Toast.makeText(context, "Saved to " + fileName, Toast.LENGTH_SHORT).show();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null){
				try {
					fos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method returns Calendar state on a Json format.
	 * @return JSON Calendar state
	 */
	public String toJson() {
		String s ;

		s = "{\n"
				+ "\"VCALENDAR\":[\n";
		for (Event event : this.listEvents) {
			s = s + event.toJson();
		}
		if (defaultReminder == null){
			s = s + Reminder.toJsonNull();
		}else{
			s = s + defaultReminder.toJson();
		}
		s = s +  "]}";
		return s;
	}

	/**
	 * This method returns Calendar state on a String format.
	 * @return String Calendar state
	 */
	@Override
	public String toString() {
		return "Calendar{" +
				"listEvents=" + listEvents +
				", reminder=" + defaultReminder +
				", lastDate=" + lastDateCreated +
				'}';
	}

	//*********************************************//
	//    Private Methods                          //
	//*********************************************//

	/**
	 * This private method launches query to search the target event in Google Calendar
	 * This method requires Manifest.permission.WRITE_CALENDAR
	 * @param event target event
	 * @return Uri corresponding to a found event in Google Calendar
	 */
	private Uri queryEvent(Event event){
		long myEventId, idEvent;
		int id1, id2, i;
		String dtNew, dtCreatedEvent;
		Date dtCreated;
		Uri eventUri = null;
		Cursor cursor;

		myEventId = event.getId();
		dtCreated = event.getDtCreated();


		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED)
		{
			cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
			while (cursor.moveToNext()) {
				if (cursor != null) {
					id1 = cursor.getColumnIndex(CalendarContract.Events._ID);
					idEvent = cursor.getLong(id1);

					id2 = cursor.getColumnIndex(CalendarContract.Events.UID_2445);
					dtCreatedEvent = cursor.getString(id2);

					if ((idEvent == myEventId)&&(dtCreatedEvent!=null)) {//
						dtNew =  "" + dtCreated;
						i = dtNew.compareTo(dtCreatedEvent);
						if (i>0){
							eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, idEvent);
							break;
						}
					}
				}
			}
		}
		return eventUri;
	}

	/**
	 * This private method inserts event in Google Calendar
	 * @param event event to be inserted
	 */
	private void insertEvent(Event event){
		Uri eventUri ;
		ContentValues cvNew;

		cvNew = event.convertToGoogleCalendar();
		eventUri= queryEvent(event);
		if (eventUri != null){
			contentResolver.delete(eventUri, null, null);
		}
		eventUri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, cvNew);
	}

	/**
	 * This private method deletes event into Google Calendar
	 * @param event event to be deleted
	 */
	private void deleteEvent( Event event){
		Uri eventUri = queryEvent(event);
		if (eventUri != null){
			contentResolver.delete(eventUri, null, null);
		}
	}

	/**
	 * This private method updates event into Google Calendar
	 * @param event new value of an event
	 */
	private void updateEvent(Event event){
		insertEvent(event);
	}

	/**
	 * This private method generates a reminder for the target event
	 * @param eventUri
	 * @param event target event
	 */
	private void generateEventReminders(Uri eventUri, Event event) {
		int method, minutes;
		Reminder r;
		long eventID;
		ContentValues reminders;
		Uri uri2;

		r = event.getReminder();
		if (event.getReminder()!=null){
			minutes = r.getMinutes();
			method = r.getMethod();
		}else{
			if(this.defaultReminder !=null){
				minutes = defaultReminder.getMinutes();
				method = defaultReminder.getMethod();
			}else{
				return;
			}

		}

		eventID = Long.parseLong(eventUri.getLastPathSegment());
		reminders = new ContentValues();
		reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
		reminders.put(CalendarContract.Reminders.METHOD, method);
		reminders.put(CalendarContract.Reminders.MINUTES, minutes);
		uri2 = contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
	}


}


