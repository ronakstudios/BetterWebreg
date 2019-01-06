package faraday.betterwebreg;

public class ScheduleGetter {
    static String schedule = "";

    //This java object is inserted into the window in javascript and then this function is called
    // and it sets the schedule static string with a string that is entered as a parameter.
    //Basically it parses javascript strings to java for use elsewhere
    @android.webkit.JavascriptInterface //this annotation is just necessary
    public void getSchedule_in_html(String scheduleFromJS){
        this.schedule=scheduleFromJS;
    }
    public static Student HTMLParser(String input){
        String[] k = input.split("<tbody>");
        Course[] courseArr = new Course[k.length-1];
        for(int n = 1; n<k.length;n++) {

            String[] p = k[n].split("<tr>");
            Course temp1 = new Course();
            temp1.courseName=p[1].substring(p[1].indexOf("4")+3,p[1].indexOf("<span")-1);
            String temp2 = p[1].substring(p[1].indexOf("School/Course Offering Unit")+29);
            String temp3 = temp2.substring(0,temp2.indexOf("</span"));
            temp2 = p[1].substring(p[1].indexOf("Subject Area")+14);
            temp3+=":"+ temp2.substring(0,temp2.indexOf("</span"));
            temp2 = p[1].substring(p[1].indexOf("Course Number")+15);
            temp3+=":"+ temp2.substring(0,temp2.indexOf("</span"));
            temp1.courseNumber=temp3;
            temp1.courseSection=temp2.substring(temp2.indexOf("Section ")+8,temp2.indexOf("|")-1);
            CourseTime[] times = new CourseTime[p.length-2];
            for(int i = 2; i<p.length;i++) {
                String[] b = p[i].split("<td");
                for(int j = 0; j<b.length;j++) {
                    b[j]=b[j].replaceAll("</td>", "");
                }

                //The try catch blocks are in case the student is taking a course online!
                try {
                    temp1.courseCampus = b[4].substring(1, b[4].indexOf("</tr>") - 9);
                }catch(StringIndexOutOfBoundsException e){
                    temp1.courseCampus="";
                }
                String loctemp;

                try {
                    loctemp = b[3].substring(b[3].indexOf("selected") + 15, b[3].indexOf("</a>"));
                }catch(StringIndexOutOfBoundsException e){
                    loctemp = "";
                }


                String day = b[1].substring(b[1].indexOf("capitalize")+12,b[1].indexOf("</span>"));
                String timetemp = b[2].substring(b[2].indexOf(">")+1);
                String t1 = timetemp.substring(0,timetemp.indexOf("-")-1);
                String t2 = timetemp.substring(timetemp.indexOf("-")+2,timetemp.length()-10/*,timetemp.substring(timetemp.indexOf("-")+2).indexOf("M")+1*/);
                times[i-2]=new CourseTime(day,t1,t2,loctemp);

            }
            temp1.courseTimes=times;
            courseArr[n-1]=temp1;
        }
        return new Student(courseArr);
    }
}