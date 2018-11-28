package ca.bcit.fitmeet.event.model;

import java.util.HashSet;
import java.util.List;

public class EventTagData {
    public static HashSet<String> OUTDOORS;
    public static HashSet<String> RELAXING;
    public static HashSet<String> ANIMALS;
    public static HashSet<String> SPORTS;
    public static HashSet<String> CULTURAL;

    public static void init() {
        OUTDOORS = new HashSet<>();
        RELAXING = new HashSet<>();
        ANIMALS = new HashSet<>();
        SPORTS = new HashSet<>();
        CULTURAL = new HashSet<>();

        addValues();
    }

    private static void addValues() {
        OUTDOORS.add("Running");
        OUTDOORS.add("Outdoors");
        OUTDOORS.add("Hiking");
        OUTDOORS.add("Adventure");
        OUTDOORS.add("Park");
        OUTDOORS.add("Recreational Activities");
        OUTDOORS.add("Fitness");

        RELAXING.add("Meditation");
        RELAXING.add("Yoga");
        RELAXING.add("Dance");
        RELAXING.add("Beach");

        ANIMALS.add("Dog");
        ANIMALS.add("Cat");
        ANIMALS.add("Animals");
        ANIMALS.add("Off Leash Dog Area");

        SPORTS.add("Running");
        SPORTS.add("Hiking");
        SPORTS.add("Sports Field");
        SPORTS.add("Soccer");
        SPORTS.add("Sports");
        SPORTS.add("Cricket");
        SPORTS.add("Football");
        SPORTS.add("Fitness");
        SPORTS.add("Recreational Activities");
        SPORTS.add("Baseball");

        CULTURAL.add("Cultural");
    }

    public static boolean hasOutdoorTags(List<String> o) {
        for (String str: o) {
            if (OUTDOORS.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRalxingTags(List<String> o) {
        for (String str: o) {
            if (RELAXING.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnimalTags(List<String> o) {
        for (String str: o) {
            if (ANIMALS.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSportsTags(List<String> o) {
        for (String str: o) {
            if (SPORTS.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCulturalTags(List<String> o) {
        for (String str: o) {
            if (CULTURAL.contains(str)) {
                return true;
            }
        }
        return false;
    }

}
