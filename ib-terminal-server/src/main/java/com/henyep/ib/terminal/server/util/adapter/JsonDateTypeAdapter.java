package com.henyep.ib.terminal.server.util.adapter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.henyep.ib.terminal.api.global.Constants;

public class JsonDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private final DateFormat dateFormat;

	public JsonDateTypeAdapter() {
		dateFormat = new SimpleDateFormat(Constants.FORMAT_DATETIME);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(dateFormat.format(date));
	}

	@Override
	public synchronized Date deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext) {
		try {
			return dateFormat.parse(jsonElement.getAsString());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}
