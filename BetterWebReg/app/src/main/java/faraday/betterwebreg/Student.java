package faraday.betterwebreg;

import android.util.Log;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Student implements Serializable {
    public Course[] schedule;
    //when using serializable to save object, transient is a keyword that makes a data member as not to be saved! Static is also not saved
    transient int thisWillNotBeSaved;
    public Student(Course[] student) {
        schedule = student;
    }
    @Override
    public String toString() {
        String o = "";
        for (Course a : schedule) {
            o += a.toString() + "\n";
        }
        return o;
    }
    public static CourseTime[] getCourseTimesOnDay(Student student,String dayOfWeek){
        dayOfWeek=dayOfWeek.toLowerCase();
        ArrayList<CourseTime> times = new ArrayList<CourseTime>(10);
        for(Course course:student.schedule)
            for(CourseTime courseTime:course.courseTimes) {
                courseTime.updateTime();
                if (courseTime.dayOfWeek.equals(dayOfWeek))
                    times.add(courseTime);
            }


            for(int i = 0; i<times.size();i++){
                int smallestIndex = i;
            for(int j = i; j<times.size();j++){
                if(times.get(smallestIndex).timeComparator>times.get(j).timeComparator)
                    smallestIndex=j;
            }
            Collections.swap(times,smallestIndex,i);
            }

            Object[] objects = times.toArray();
            CourseTime[] res = new CourseTime[objects.length];
            for(int i = 0; i<res.length;i++)
                res[i]=(CourseTime)objects[i];



            return res;
    }
    public static CourseTime[][] getTimesOnSchedule(Student student){
        CourseTime[][] res = new CourseTime[5][];
        res[0]=getCourseTimesOnDay(student,"monday");
        res[1]=getCourseTimesOnDay(student,"tuesday");
        res[2]=getCourseTimesOnDay(student,"wednesday");
        res[3]=getCourseTimesOnDay(student,"thursday");
        res[4]=getCourseTimesOnDay(student,"friday");
        return res;
    }
    public static Course[][] getCoursesOnSchedule(Student student){
        Course[][] res = new Course[5][];
        res[0]=getCoursesOnDay(student,"monday");
        res[1]=getCoursesOnDay(student,"tuesday");
        res[2]=getCoursesOnDay(student,"wednesday");
        res[3]=getCoursesOnDay(student,"thursday");
        res[4]=getCoursesOnDay(student,"friday");
        return res;
    }
    public static Course[] getCoursesOnDay(Student student, String dayOfWeek){
        CourseTime[] temp1 = getCourseTimesOnDay(student,dayOfWeek);
        Course[] res = new Course[temp1.length];
        for(int i = 0; i < temp1.length;i++)
            for(Course course:student.schedule)
                for(CourseTime courseTime:course.courseTimes)
                    if(courseTime.courseLocation.equals(temp1[i].courseLocation))
                        res[i]=course;
        return res;
    }
}
