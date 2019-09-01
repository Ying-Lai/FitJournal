package com.gmail.plai2.ying.fitjournal;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<Set> stringToSetList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Set>>(){}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String setListToString(List<Set> listOfSets) {
        Gson gson = new Gson();
        String json = gson.toJson(listOfSets);
        return json;
    }

    @TypeConverter
    public static Date stringToDate(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Date>(){}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String dateToString(Date date) {
        Gson gson = new Gson();
        String json = gson.toJson(date);
        return json;
    }

}