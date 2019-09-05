package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TypeConverters {

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

    @TypeConverter
    public static Exercise.ExerciseType toExerciseType(int exerciseType) {
        if (exerciseType == 0) {
            return Exercise.ExerciseType.CARDIO;
        } else if (exerciseType == 1) {
            return Exercise.ExerciseType.STRENGTH;
        } else {
            throw new IllegalArgumentException("Could not recognize this intensity level");
        }
    }

    @TypeConverter
    public static int toInt(Exercise.ExerciseType exerciseType) {
        return exerciseType.getCategory();
    }

    @TypeConverter
    public static Exercise.Intensity toIntensity(int intensity) {
        if (intensity == 0) {
            return Exercise.Intensity.LOW;
        } else if (intensity == 1) {
            return Exercise.Intensity.MEDIUM;
        } else if (intensity == 2) {
            return Exercise.Intensity.HIGH;
        } else {
            throw new IllegalArgumentException("Could not recognize this intensity level");
        }
    }

    @TypeConverter
    public static int toInt(Exercise.Intensity intensity) {
        return intensity.getIntensityLevel();
    }
}