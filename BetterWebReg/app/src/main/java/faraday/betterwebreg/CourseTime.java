package faraday.betterwebreg;

import java.io.Serializable;

public class CourseTime implements Serializable {
    public String dayOfWeek,startTime,endTime,courseLocation;
    public int starttimehour,starttimemunuite;
    public double timeComparator;
    public CourseTime(String dayOfWeek, String startTime, String endTime,String courseLocation){
        this.dayOfWeek=dayOfWeek;
        this.startTime=startTime;
        this.endTime=endTime;
        this.courseLocation=courseLocation;
    }
    public void updateTime(){
        starttimehour=(startTime.contains("P"))?12+Integer.parseInt(startTime.substring(0,startTime.indexOf(':'))):Integer.parseInt(startTime.substring(0,startTime.indexOf(':')));
        if(starttimehour%12==0){
            starttimehour=(startTime.contains("P"))?12:0;
        }
        starttimemunuite=Integer.parseInt(startTime.substring(startTime.indexOf(':')+1,startTime.indexOf(':')+3));
        timeComparator=starttimehour+(starttimemunuite/100.0);
    }

    @Override
    public String toString() {
        return dayOfWeek+" "+startTime+" "+endTime+" "+courseLocation;
    }
}

