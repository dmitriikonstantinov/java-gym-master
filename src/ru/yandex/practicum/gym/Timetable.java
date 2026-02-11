package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(day);

        List<TrainingSession> sessionsAtTime = dayTimetable.get(time);
        if (sessionsAtTime == null) {
            sessionsAtTime = new ArrayList<>();
            dayTimetable.put(time, sessionsAtTime);
        }
        sessionsAtTime.add(trainingSession);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null || dayTimetable.isEmpty()) {
            return Collections.emptyMap();
        }
        return new TreeMap<>(dayTimetable);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);
        if (dayTimetable == null) {
            return Collections.emptyList();
        }
        List<TrainingSession> sessionsAtTime = dayTimetable.get(timeOfDay);
        if (sessionsAtTime == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(sessionsAtTime);

    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(day);
            if (dayTimetable != null) {
                for (List<TrainingSession> sessions : dayTimetable.values()) {
                    for (TrainingSession session : sessions) {
                        Coach coach = session.getCoach();
                        coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        // 4. Сортируем по убыванию (CounterOfTrainings реализует Comparable)
        Collections.sort(result);

        return result;
    }
}
