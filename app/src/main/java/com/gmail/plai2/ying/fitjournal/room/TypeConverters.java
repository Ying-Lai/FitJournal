package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.threeten.bp.LocalDate;

import java.lang.reflect.Type;

import java.util.Collections;
import java.util.List;

public class TypeConverters {

    @TypeConverter
    public static List<Session> stringToSessionList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Session>>(){}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String sessionListToString(List<Session> listOfSessions) {
        Gson gson = new Gson();
        return gson.toJson(listOfSessions);
    }

    @TypeConverter
    public static LocalDate stringToDate(String data) {
        if (data == null) {
            return null;
        } else {
            return LocalDate.parse(data);
        }
    }

    @TypeConverter
    public static String dateToString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }

    @TypeConverter
    public static ExerciseType intToExerciseType(int exerciseType) {
        if (exerciseType == 0) {
            return ExerciseType.CARDIO;
        } else if (exerciseType == 1) {
            return ExerciseType.STRENGTH;
        } else if (exerciseType == 2) {
            return ExerciseType.CALISTHENICS;
        }else {
            throw new IllegalArgumentException("Could not recognize this exercise type");
        }
    }

    @TypeConverter
    public static int exerciseTypeToInt(ExerciseType exerciseType) {
        return exerciseType.getCategory();
    }
}