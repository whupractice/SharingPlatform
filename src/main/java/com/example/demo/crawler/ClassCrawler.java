
package com.example.demo.crawler;

import com.example.demo.baseClass.Lesson;
import com.example.demo.baseClass.Teacher;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;


/**
 * @ Author     ：Theory
 * @ Description：爬取浙江省高校资源共享平台的课程信息
 */
public class ClassCrawler {

    private final String[] category = {"哲学","经济学","法学","教育学","文学","历史学",
            "理学","工学","农学","医学","","管理学","艺术学"};

    List<Lesson> lessons = new ArrayList<>();

    public List<Lesson> crawl(){

        initClass();//爬取课程初步信息
        initMore();//爬取课程详细信息和教师信息


        return lessons;
        //crawler();
        //getLink();


    }

    //爬取工具
    public String getHtml(String url){
        try {
            Connection con = Jsoup.connect(url);
            con.header("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");// 配置模拟浏览器
            Connection.Response rs = con.execute();// 获取响应
            return rs.body();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    //爬取分类课程网页
    public void crawler(){
        /*保存所有需要爬取的链接*/
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,3);
        map.put(2,14);
        map.put(3,7);
        map.put(4,21);
        map.put(5,10);
        map.put(6,3);
        map.put(7,21);
        map.put(8,42);
        map.put(9,4);
        map.put(10,31);
        map.put(12,24);
        map.put(13,36);

        String baseLink = "http://zjedu.moocollege.com/Province/Course/course/";
        ArrayList<String> allLinks = new ArrayList<>();

        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                int key = (Integer) entry.getKey();
                int val = (Integer) entry.getValue();
                for(int j = 1;j<val;j++) {
                    if (key / 10 == 0)
                        allLinks.add(baseLink + j + "?aCode=0" + key);
                    else
                        allLinks.add(baseLink + j + "?aCode=" + key);
                }
        }

        /*爬取所有课程网页*/
        try {
            for(int i = 0;i<allLinks.size();i++) {
                String url = allLinks.get(i);
                String[] sp = url.split("\\/");
                String[] sp2 = sp[sp.length-1].split("\\?");
                String name = url.substring(url.length()-2)+"_"+sp2[0];
                String html = getHtml(url);
                Document d1 = Jsoup.parse(html);// 转换为Dom树
                List<Element> et = d1.getElementsByAttributeValue("class","detail");

                File file = new File("src\\info\\" + name + ".html");
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                fw.write(et.get(0).html());
                fw.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //提取链接信息
    public void getLink(){
        try {
            File dir = new File("src\\Info");
            File[] files = dir.listFiles();//遍历info下所有文件
            for(File file : files){
                Document doc = Jsoup.parse(file,"UTF-8");
                List<Element> aeList = doc.getElementsByTag("a");
                List<Element> imgList = doc.getElementsByTag("img");

                int len = file.getName().length();
                String linkHref="", linkText="";
                File file2 = new File("src\\info2\\"+file.getName().substring(0,len-4)+"txt");
                file.createNewFile();
                FileWriter fw = new FileWriter(file2);
                int j = -3;
                for(int i = 0;i<aeList.size();i++)
                {
                    linkHref = aeList.get(i).attr("href");
                    linkText = aeList.get(i).text();
                    Element e = imgList.get(j+3);
                    fw.write(linkHref+" "+linkText+" "+e.attr("src")+"\n");
                }
                fw.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /*初始化类*/
    public void initClass(){

        try {
            /*遍历info2下所有文件*/
            File file = new File("info3");
            File[] files = file.listFiles();
            BufferedReader br;
            for(File f : files){
                br = new BufferedReader(new FileReader(f));
                String line = null;
                while((line=br.readLine())!=null){
                    Lesson lesson = analysisString(line);
                    lessons.add(lesson);
                    String name = f.getName().substring(0,2);
                    lesson.setCategory(getCategoryName(name));
                }
                br.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public Lesson analysisString(String line){

        Lesson lesson = new Lesson();
        int len = line.length();
        boolean flag = true;
        boolean hasStatus = false;
        int start = 0;
        String left = "";
        for(int i = 0; i < len;){
            if(line.charAt(i) == ' ' && flag)//读取课程链接
            {
                String detailLink = line.substring(0,i);
                lesson.setLessonLink(detailLink);
                flag = false;
                start = i+1;
                i++;
            }
            else if(line.charAt(i) == '已' && line.charAt(i+1)=='完' &&line.charAt(i+2)=='结'){
                String lessonName = line.substring(start,i-1);
                lesson.setLessonName(lessonName);
                lesson.setStatus("已完结");
                hasStatus = true;
                start = i+3;
                i = i+3;
            }
            else if(line.charAt(i)=='正' && line.charAt(i+1) =='在' && line.charAt(i+2)=='开' && line.charAt(i+3) == '课'){
                String lessonName = line.substring(start,i-1);
                lesson.setLessonName(lessonName);
                lesson.setStatus("正在开课");
                hasStatus = true;
                start = i+4;
                i = i+4;
            }
            else if(line.charAt(i)=='即' && line.charAt(i+1) =='将' && line.charAt(i+2)=='开' && line.charAt(i+3) == '课'){
                String lessonName = line.substring(start,i-1);
                lesson.setLessonName(lessonName);
                lesson.setStatus("即将开课");
                hasStatus = true;
                start = i+4;
                i = i+4;
            }
            else if(hasStatus && isdigit(line.charAt(i))){
                String num="";
                num+=line.charAt(i);
                i++;
                while(isdigit(line.charAt(i))){
                    num+=line.charAt(i);
                    i++;
                }
                i++;
                start = i;
                left = line.substring(start,len);
                break;
            }
            else
                i++;

        }
        String[] lefts = left.split(" ");
        lesson.setTeacherName(lefts[0]);
        lesson.setSchoolName(lefts[1]);
        lesson.setImgLink(lefts[2]);

        return lesson;

    }


    public boolean isdigit(char c){
        if(c>='0' && c<='9')
            return true;
        return false;
    }


    public String getCategoryName(String index){
        if(index.charAt(0)=='0'){
            int i = index.charAt(1)-'0';
            return category[i-1];
        }
        else{
            int i = Integer.parseInt(index);
            return category[i-1];
        }
    }

    public void initMore(){
        for(Lesson lesson : lessons) {
            String url = "http://zjedu.moocollege.com"+lesson.getLessonLink();
            String detailHtml = getHtml(url);
            Document doc = Jsoup.parse(detailHtml);

            //获取基本信息
            List<Element>et = doc.getElementsByAttributeValue("class","right");
            Element e = et.get(1);
            List<Element> et2 = e.getElementsByTag("span");
            String subject="",startTime="",endTime="";
            int credit=0;
            if(et2.get(2).text().substring(0,4).equals("开课时间")) {
                subject= et2.get(0).text();
                startTime = et2.get(2).text().substring(5, 15);
                endTime = et2.get(2).text().substring(18, 28);
                credit = Integer.parseInt(et2.get(4).text().substring(3, 4));
            }
            else if(et2.get(1).text().substring(0,4).equals("开课时间")){
                subject= et2.get(0).text();
                startTime = et2.get(1).text().substring(5, 15);
                endTime = et2.get(1).text().substring(18, 28);
                credit = Integer.parseInt(et2.get(3).text().substring(3, 4));
            }
            //获取课程简介和概述
            List<Element>et3 = doc.getElementsByAttributeValue("class","x-detail");
            String intro = et3.get(0).getElementsByTag("p").text();

            lesson.setSubject(subject);
            lesson.setStartTime(startTime);
            lesson.setEndTime(endTime);
            lesson.setCredit(credit);
            lesson.setIntro(intro);
            lesson.setTeachers(initTeacher(doc));//获取老师信息
        }
    }



    //爬取老师信息
    public ArrayList<Teacher> initTeacher(Document doc) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        List<Element> prim_e = doc.getElementsByAttributeValue("class", "right teacher-intro");
        List<Element> et = prim_e.get(0).getElementsByAttributeValue("class", "c-item");
        for (Element e : et) {
            String imgLink = e.getElementsByTag("img").attr("src");

            List<Element> e1 = e.getElementsByAttributeValue("class", "name");
            String job = e1.get(0).getElementsByTag("span").text();
            String teacherName = e1.get(0).text().replaceAll(job, "");

            List<Element> e2 = e.getElementsByAttributeValue("class", "xl");
            String[] school_academy = e2.get(0).text().split("·");
            String schoolName = "";
            String academyName = "";
            if (school_academy.length == 2) {
                schoolName = school_academy[0];
                academyName = school_academy[1];
            }

            List<Element> e3 = e.getElementsByTag("p");
            String intro = e3.get(0).text();

           teachers.add(new Teacher(teacherName, job, schoolName, academyName, intro, imgLink));
        }
        return teachers;
    }


}
