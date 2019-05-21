package com.subject.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.subject.Exception.DescribeException;
import com.subject.Exception.ExceptionEnum;
import com.subject.model.Student;
import com.subject.model.Stusub;
import com.subject.model.Subject;
import com.subject.service.ExportService;
import com.subject.service.StusubService;
import com.subject.service.SubjectService;
import com.subject.service.SysarguService;
import com.subject.util.ExcelUtil;
import com.subject.util.GetDataUtil;
import com.subject.util.JsonUtil;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author liangqin
 * @date 2019/5/15 14:31
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private SysarguService sysarguService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StusubService stusubService;

    /**
     * 获取文件路径
      * @return
     */
    public String getFilePath() {
        String filePath = null;
        try{
            filePath = sysarguService.selectSysargu("tempfilepath").getArguvalue();
            if(filePath == null || filePath.equals("")) throw new DescribeException(ExceptionEnum.FILEPATH_NOTSET);
        }catch (Exception e) {
            System.out.println("error: getFilePath() ------->" + e);
        }
        return filePath;
    }


    /**
     * 导出学生课题明细表
     * @param specid
     * @param classname
     * @throws IOException
     * @throws WriteException
     */
    @Override
    public void exportStuSubList(String specid, String classname, HttpServletResponse response) {

        //获得专业列表
        String specUrl = "http://localhost:8089/speciality/getallbyspecid?specid=" + specid;
        String specJson = GetDataUtil.getData(specUrl);
        JSONArray jsonArray = (JSONArray) JsonUtil.JsonToList(specJson, "data");

        String[][] data = null;
        String sheetName = "";
        String titleName = "";
        String fileName = "output.xls";
        int columnNumber = 13;
        int[] columnWidth = { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };

        for(Object obj : jsonArray) {
            JSONObject jsonOb = (JSONObject) obj;
            String specname = jsonOb.getString("specname");
            String specidTemp = jsonOb.getString("specid");

            sheetName = specname+"课题明细表";

            //将数据写入二维数组
            List<Student> students = subjectService.getStusBySpec(specid, classname, "");
            int length = students.size();
            data = new String[length][13];
            for(int i = 0; i < length; i++) {
                Student student = students.get(i);
                String tid = student.getSubject().getTid();
                String asstid = student.getSubject().getAsstid();
                //获取教师json对象
                JSONObject teacherObject = null, assteacherObject = null, teacherdata = null,assteacherdata = null;
                String tname = null, tposTdegree = null ,asstname = null ,asstposTdegree = null;
                if(tid != null && asstid != null) {
                    String[] teacherArr = tid.split(" ");
                    String[] assteacher = asstid.split(" ");
                    if(teacherArr[0] != null && assteacher[0] != null) {
                        tid = teacherArr[0];
                        asstid = assteacher[0];
                    }
                    String url = "http://localhost:8089/teacher/getteabytno?tno=";
                    String turl = url + tid;
                    String assturl = url + asstid;
                    teacherObject = GetDataUtil.getJSONObject(turl);
                    assteacherObject = GetDataUtil.getJSONObject(assturl);


                    tname = teacherObject.getString("tname");
                    tposTdegree = teacherObject.getString("tpost") + "-" + teacherObject.getString("tdegree");

                    asstname = assteacherObject.getString("tname");
                    asstposTdegree = assteacherObject.getString("tpost") + "-" + assteacherObject.getString("tdegree");


                    if(asstname != null || !asstname.equals("")) {
                        tname += " " + asstname;
                        tposTdegree += " " + asstposTdegree;
                    }
                }

                data[i][0] = String.valueOf(i);
                data[i][1] = student.getStuid();
                data[i][2] = student.getSname();
                data[i][3] = specname;
                data[i][4] = student.getClassname();
                data[i][5] = student.getSubject().getSubname();
                data[i][6] = student.getSubject().getSubsort();
                data[i][7] = student.getSubject().getSubtype();
                data[i][8] = student.getSubject().getSubkind();
                data[i][9] = student.getSubject().getSubsource();
                data[i][10] = tname;
                data[i][11] = tposTdegree;
                int isoutschool=student.getSubject().getIsoutschool();
                if(isoutschool==1){
                    data[i][12] = "是";
                }else{
                    data[i][12] = "否";
                }
            }

        }

        String[] columnName = { "序号", "学号", "姓名" , "专业", "班级",
                "毕业设计（论文）题目", "题目类别", "题目类型", "题目性质",
                "题目来源", "指导教师姓名", "学位/职称", "是否校外"};

        try {
            ExcelUtil.ExportWithResponse(sheetName, titleName, fileName,
                    columnNumber, columnWidth, columnName, data, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导出评阅意见（盲审）（批量）,按专业一个专业生成一个excel文件
     * @param specid
     * @param classname
     * @param sname
     */
    @Override
    public void exportBlindReviewContentBySpec(String specid, String classname, String sname) {

    }

    /**
     * 导出进程表（批量）
     * @param specid
     * @param classname
     * @param sname
     */
    @Override
    public void exportProgressTableBySpec(String specid, String classname, String sname) {
        String filePath = this.getFilePath();
        WritableWorkbook workbook =null;
        try{

        }catch (Exception e) {

        }
    }

    /**
     * 导出任务书（根据教师编号导出该教师的所有任务书-一个excel文件）
     * @param tid
     */
    @Override
    public void exportTaskBooksByTid(String tid) throws  WriteException {
        String filePath = this.getFilePath();
        WritableWorkbook workbook =null;
        try{
            workbook = Workbook.createWorkbook(new File(filePath+tid+"taskbookoutput.xls"));
            List<Subject> subjects = subjectService.getAllinfo(tid);
            ListIterator listIterator = subjects.listIterator();
            long subid;
            int sheetNum = 0;
            while(listIterator.hasNext()) {
                subid = ((Subject)listIterator.next()).getSubid();
                taskbookcontent(workbook, sheetNum, subid);
                sheetNum += 1;
            }
            workbook.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(workbook != null) {
                try{
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 下载文件
     * @param response
     */
    @Override
    public void download(HttpServletResponse response, String fileName) {
        try{
            String fliePath = sysarguService.selectSysargu("tempfilepath").getArguvalue();
            //通过路径和文件名获取文件
            File file = new File(fliePath, fileName);

            //当文件存在
            if(file.exists()) {
                //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
                response.setContentType("application/force-download");
                //response.setContentType("application/msexcel");
                //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
                response.setHeader("Content-Disposition",String.format("attachment; filename=\"%s\"", file.getName()));

                //进行读写操作
                byte[]buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                try{

                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();

                    //从源文件中读
                    int i = bis.read(buffer);
                    while(i != -1){
                        //写到response的输出流中
                        os.write(buffer,0,i);
                        i = bis.read(buffer);
                    }
                    os.flush();
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {

                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }else{
                response.setContentType("text/html;charset=gb2312");
                response.setCharacterEncoding("gb2312");
                throw new DescribeException(ExceptionEnum.FILE_NOTEXIT);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param workbook //excel文件
     * @param sheetnum //sheet序号
     * @param subid //任务对应课题编号，一个任务书对应一个sheet
     * @throws Exception
     */
    public void taskbookcontent(WritableWorkbook workbook, int sheetnum, long subid) throws Exception {
        GetDataUtil getDataUtil = new GetDataUtil();
        Subject subject = subjectService.selectBySubid(subid);

        //获得stuid
        Stusub stusub = stusubService.whetherSelectSub(subid);
        //获取学生对象
        String url = "http://localhost:8089/student/getstubystuid?stuid=" + stusub.getStuid();
        JSONObject studentJsonObject = GetDataUtil.getJSONObject(url);

        //创建任务书表单
        WritableSheet sheet = workbook.createSheet(subid+"课题任务书", sheetnum);

        //共6列，设置每列列宽
        sheet.setColumnView( 0,10 );//设置列宽
        sheet.setColumnView( 1,14 );//设置列宽
        sheet.setColumnView( 2,14 );//设置列宽
        sheet.setColumnView( 3,16 );//设置列宽
        sheet.setColumnView( 4,14 );//设置列宽
        sheet.setColumnView( 5,18 );//设置列宽
        //合并第一行所有列，显示题头。表格共6列
        sheet.mergeCells(0, 0, 5, 0);//(col,row)
        //设置第一行，行高为800
        sheet.setRowView(0, 800);
        //第1行填写标题
        WritableFont wfont=new WritableFont(WritableFont.createFont("宋体"),14);//设置单元格字体(宋体、四号、加粗）
        WritableCellFormat wc=new WritableCellFormat(wfont);
        wc.setAlignment(jxl.format.Alignment.CENTRE);//设置水平居中
        wc.setVerticalAlignment(VerticalAlignment.CENTRE);
        Label label=new Label(0,0,"山东建筑大学毕业设计（论文）任务书",wc);
        sheet.addCell(label);
        //设置表中每个单元格格式
        //标题文本内容格式
        WritableCellFormat wctitle=new WritableCellFormat();
        wctitle.setAlignment(Alignment.CENTRE);//标题居中
        wctitle.setVerticalAlignment(VerticalAlignment.CENTRE);
        wctitle.setBorder(Border.BOTTOM, BorderLineStyle.THIN);//边框为细线,默认为none
        wctitle.setBorder(Border.TOP, BorderLineStyle.THIN);//边框为细线
        wctitle.setBorder(Border.LEFT, BorderLineStyle.THIN);
        wctitle.setBorder(Border.RIGHT, BorderLineStyle.THIN);
        wctitle.setWrap(true);//支持自动换行
        //表格内容格式
        WritableCellFormat wccontent=new WritableCellFormat();
        wccontent.setAlignment(Alignment.LEFT);//内容居左
        wccontent.setVerticalAlignment(VerticalAlignment.CENTRE);
        wccontent.setBorder(Border.BOTTOM, BorderLineStyle.THIN);//边框为细线,默认为none
        wccontent.setBorder(Border.TOP, BorderLineStyle.THIN);//边框为细线
        wccontent.setBorder(Border.LEFT, BorderLineStyle.THIN);
        wccontent.setBorder(Border.RIGHT, BorderLineStyle.THIN);
        wccontent.setWrap(true);//支持自动换行

        //获取教师姓名
        String tid = subject.getTid();
        String asstid = subject.getAsstid();

        //第2行填写基本信息
        sheet.setRowView(1, 400,false);//行高
        sheet.addCell(new Label(0,1, "班级",wctitle)); //(col,row)
        sheet.addCell(new Label(1,1, studentJsonObject.getString("classname"),wctitle));
        sheet.addCell(new Label(2,1, "学生姓名",wctitle));
        sheet.addCell(new Label(3,1, studentJsonObject.getString("sname"),wctitle));
        sheet.addCell(new Label(4,1, "指导教师",wctitle));
        sheet.addCell(new Label(5,1, " " + GetDataUtil.getTeacherNameByTid(tid) + " " + GetDataUtil.getTeacherNameByTid(asstid),wctitle));

        //第3行论文题目
        sheet.setRowView(2, 400,false);//行高
        sheet.mergeCells(0,2,1,2);
        sheet.addCell(new Label(0,2, "设计（论文）题目",wctitle));
        sheet.mergeCells(2,2,5,2);
        sheet.addCell(new Label(2,2, subject.getSubname(),wccontent));

        //第4行设计（论文）原始参数
        String ss = subject.getSummary();
        sheet.setRowView(3, this.evaluateRowHeight(40, subject.getSummary()));
        sheet.mergeCells(1,3,5,3);
        sheet.addCell(new Label(0,3, "设计（论文）概述",wctitle));
        sheet.addCell(new Label(1,3, subject.getSummary(),wccontent));

        //第5行设计（论文）工作内容4000
        sheet.setRowView(4, this.evaluateRowHeight(40, subject.getContent()));
        sheet.mergeCells(1,4,5,4);
        sheet.addCell(new Label(0,4, "设计（论文）工作内容",wctitle));
        sheet.addCell(new Label(1,4, subject.getContent(),wccontent));

        //第6行设计（论文）工作基本要求3000
        sheet.setRowView(5, this.evaluateRowHeight(40, subject.getRequirement()));
        sheet.mergeCells(1,5,5,5);
        sheet.addCell(new Label(0,5, "设计（论文）工作基本要求",wctitle));
        sheet.addCell(new Label(1,5, subject.getRequirement(),wccontent));

        //第7行设计（论文）工作日程
        //JSONArray jsonArray = JSON.parseArray(subject.getSubprog());
        //String subprog =  JSON.parse();
        //String sub = subject.getSubprog();
        sheet.setRowView(6, this.evaluateRowHeight(40, subject.getSubprog()));
        sheet.mergeCells(1,6,5,6);
        sheet.addCell(new Label(0,6, "设计（论文）工作日程",wctitle));
        sheet.addCell(new Label(1,6, subject.getSubprog(),wccontent));

        //第8行主要参考资料及文献
        sheet.setRowView(7, this.evaluateRowHeight(40, subject.getRefpapers()));
        sheet.mergeCells(1,7,5,7);
        sheet.addCell(new Label(0,7, "主要参考资料及文献",wctitle));
        sheet.addCell(new Label(1,7, subject.getRefpapers(),wccontent));

        //第9行 签字
        sheet.setRowView(8,500);//行高
        sheet.mergeCells(0,8,5,8);
        sheet.addCell(new Label(0,8,"指导教师（签字）：                  教研室主任（签字）：                  院系主任（签字）："));
    }

    /**估算行高
     * countperrow-每行字数。任务书大约每行为40个。
     * text-显示的文本
     * */
    public int evaluateRowHeight(int countperrow, String text)throws Exception{
        int rowcounts = 0;//行数
        String[] textarr = text.split("\n");
        for(int i = 0;i < textarr.length; i++){
            int rowtemp = textarr[i].length()/countperrow+1;
            rowcounts = rowcounts+rowtemp;
        }
        //excel中默认行高为300
        return rowcounts*300;
    }
}
