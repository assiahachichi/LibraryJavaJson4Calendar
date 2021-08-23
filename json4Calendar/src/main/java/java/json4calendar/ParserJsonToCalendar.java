package java.json4calendar;


import android.content.ContentResolver;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;


/**
 * @author Assia HACHICHI
 */

public class ParserJsonToCalendar {
	private ContentResolver contentResolver;
	private Context context;
	private String timeZone;

	//*********************************************//
	//    Constructor                              //
	//*********************************************//

	/**
	 * This constructor initialises instance variables.
	 * @param cr is a ContentResolver variable
	 * @param context is a Context variable
	 * @param timeZone is a String containing time zone information
	 */
	public ParserJsonToCalendar(ContentResolver cr, Context context, String timeZone) {
		this.contentResolver = cr;
		this.context = context;
		this.timeZone = timeZone;
	}

	//*********************************************//
	//    Public Methods                           //
	//*********************************************//

	/**
	 * This method parses a File variable into String
	 * @param file a File variable
	 * @return  file content in String format
	 */
	public static String parseFileToString(File file) {
		String ligne, sFile="";
		InputStream flux;
		InputStreamReader lecture;
		BufferedReader buffer;
		try {
			flux = new FileInputStream(file);
			lecture = new InputStreamReader(flux);
			buffer = new BufferedReader(lecture);

			while ((ligne = buffer.readLine()) != null) {
				sFile = sFile.concat(ligne);
				sFile = sFile.concat("\n");
			}
			buffer.close();
			//Toast.makeText(context, "The file is parsed", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sFile;
	}

	/**
	 * This method parses a File into String. File name is given in String format
	 * @param filename is a String containing a file name
	 * @return file content in String format
	 */
	public static String parseFileToString(String directory, String filename) {
		String sFile="";
		File file;
		try {
			sFile = parseFileToString(new File(directory, filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sFile;
	}

	/**
	 * This method convert JSON Data into a Calendar variable
	 * @param sCalendar is a JSON data to be converted
	 * @param color is a desired color of an Event into Google Calendar
	 * @return a Calendar variable
	 */
	public Calendar convertJsonToCalendar(String sCalendar, int color) {
		Calendar calendar;
		JSONObject jsonCalendar, vEvent, vReminder;
		JSONArray allEventsJSON;
		Reminder reminder;
		Event event;
		int size, i;

		calendar = new Calendar(contentResolver, context, timeZone);
		if ((sCalendar==null)||(sCalendar.equals(""))) {return null;}
		try {
			jsonCalendar = new JSONObject(sCalendar);
			allEventsJSON = jsonCalendar.getJSONArray("VCALENDAR");
			size = allEventsJSON.length();
			for (i=0; i<size-1; i++)
			{
				vEvent = allEventsJSON.getJSONObject(i);
				event = convertJsonToEvent((JSONObject) vEvent.get("VEVENT"), color);
				calendar.addEvent(event);
				calendar.setLastDateCreated(event.getDtCreated());
			}

			if (size >0) {
				vReminder = allEventsJSON.getJSONObject(size - 1);
				reminder = convertJsonToReminder((JSONObject) vReminder.get("VREMINDER"));
				calendar.setDefaultReminder(reminder);
			}

		} catch (JSONException ex2) {
			ex2.printStackTrace();
		}

		return calendar;
	}

	//*********************************************//
	//    Private Methods                          //
	//*********************************************//

	/**
	 * This private method convert a JSONObject into a Reminder variable
	 * @param vReminder is a JSONObject, which represents a Reminder
	 * @return a Reminder variable
	 */
	private Reminder convertJsonToReminder(JSONObject vReminder) {
		Reminder reminder = null;
		int minutes;
		try {
			minutes = vReminder.getInt("MINUTES");
			if (minutes == -1) return null;
			reminder = new Reminder(minutes);
		}catch (JSONException ex){
			ex.printStackTrace();
		}
		return reminder;
	}

	/**
	 * This private method convert a JSONObject into a Event variable
	 * @param vEvent is a JSONObject, which represents an Event
	 * @param color is a color to represent this event into Google Calendar
	 * @return an Event variable
	 */
	private Event convertJsonToEvent(JSONObject vEvent, int color) {
		Event event = null;
		String s, sDtCreated, uid, type, title, description, function, location;
		long id, idRdv;
		Date dtStart, dtEnd, dtCreated;
		Reminder reminder;
		int minutes;

		try {
			s = vEvent.getString("DTSTART");
			dtStart = MappingDateString.convertStringToDate(s);

			s = vEvent.getString("DTEND");
			dtEnd = MappingDateString.convertStringToDate(s);

			s = vEvent.getString("DTCREATED");
			dtCreated = MappingDateString.convertStringToDate(s);
			sDtCreated = s;

			idRdv =  vEvent.getLong("IDRDV");

			//Id can be concatenation of IdUser and IdRDV
			id =  vEvent.getLong("ID");

			//UID is created by concatenating ID and date of Created (sDtCreated),
			// in order to have the last update about this event
			//uid = id + "" + sDtCreated;

			s = vEvent.getString("TYPE");
			type = s;

			s = vEvent.getString("TITLE");
			title = s;

			s = vEvent.getString("DESCRIPTION");
			description = s;

			s = vEvent.getString("FUNCTION");
			function = s;

			s = vEvent.getString("LOCATION");
			location = s;

			// Add a reminder
			minutes = vEvent.getInt("MINUTES");
			if (minutes == -1){
				reminder = null;
			}else{
				reminder = new Reminder(minutes);
			}

			// Create an event
			event = new Event(id, idRdv, dtStart, dtEnd, dtCreated, type, title,
					description, function, location, reminder, timeZone);
			event.setColor(color);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return event;
	}




}