package faraday.betterwebreg;

import java.io.Serializable;

public class Course implements Serializable {
    public String courseName, courseNumber, courseSection, courseCampus;
    public CourseTime[] courseTimes;
    //Should only READ these values, never edit them when using them they are all aliases!
    //Note courseCampus and courseLocation may be empty strings for online classes!
    public Course(String courseName, String courseNumber, String courseSection, String courseCampus, CourseTime[] courseTimes ){
        this.courseName=courseName;
        this.courseSection=courseSection;
        this.courseCampus=courseCampus;
        this.courseTimes=courseTimes;
    }
    public Course() {}
    @Override
    public String toString() {
        String o = courseName+"\n"+courseNumber+"\n"+courseSection+"\n"+((!courseCampus.equals(""))?courseCampus+"\n":"Online+\n");
        for(CourseTime a:courseTimes) {
            o+=a.toString()+"\n";
        }
        return o;
    }
}
