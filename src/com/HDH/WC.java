package com.HDH;

//import com.sun.javafx.image.BytePixelSetter;
//import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

import JFrame.FileFrame;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * @author Dawson_Huang
 * @Date 2020/3/8 - 18:47
 * 功能：实现 wc.exe 命令
 */

/**
 * 本程序支持的命令行格式：
 * 1、wc.exe -c 文件路径
 * 2、wc.exe -w 文件路径
 * 3、wc.exe -l 文件路径
 * 4、wc.exe -a 文件路径
 * 5、wc.exe -s -a 文件路径
 * 6、wc.exe -x 文件路径
 */

public class WC {
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("请注意输入格式 'wc.exe [order] [file_name]:");
        String[] input = in.nextLine().split(" ");
        //System.out.println(Arrays.toString(input));
        if("-s".equals(input[1])) {     //输入类型是 wc.exe -s ....
            System.out.println("请输入你要在以上目录中递归查找的文件的后缀：");
            String endwith_name = in.nextLine();  //后缀名
            String order = input[2];   //操作命令
            String path = input[3];    //文件夹路径
            RecrusiveOperation(endwith_name,order,path);
        }else if("-a".equals(input[1])){  //输入类型是 wc.exe -a ....
            SpecialLine(input[2]);
        }else if("-x".equals(input[1])){
            _x();
        }else{
            String order = input[1];
            String path = input[2];
            BasicOperation(order,path);
        }
    }
    //基本功能实现（-c / -w / -l）
    public static void BasicOperation(String order,String path) throws Exception{
        int Count_Char=0;
        int Count_Word=0;
        int Count_Line=0;
        FileInputStream file = new FileInputStream(path);
        InputStreamReader is = new InputStreamReader(file);
        BufferedReader br = new BufferedReader(is);
        String s;
        switch(order) {
            //计算字符数
            case "-c":
                while((s=br.readLine())!=null){
                    Count_Char += s.length();
                }
                System.out.println("字符数："+Count_Char);
                break;
            //计算单词数
            case "-w":
                while((s=br.readLine())!=null){
                    Count_Word += s.split(" ").length;
                }
                System.out.println("单词数："+Count_Word);
                break;
            //计算行数
            case "-l":
                while(br.readLine()!=null){
                    Count_Line++;
                }
                System.out.println("行数："+Count_Line);
                break;
            default:
                System.out.println("输入错误！");
        }
    }
    //扩展功能实现：-s 递归处理目录下符合条件的文件
    public static void RecrusiveOperation(String endwith_name,String input,String path) throws Exception{
        List<String> fileNameList = new ArrayList<>();//文件名列表
        List<String> filePathList = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) { //路径不存在
            System.out.println("该路径不存在！");
        }else{
            if(f.isFile()){ //路径为文件
                if(f.getName().indexOf(endwith_name)!=-1) {
                    fileNameList.add(f.getName());
                    filePathList.add(f.getAbsolutePath());
                }
            }else if(f.isDirectory()){ //路径为文件夹
                File fa[] = f.listFiles();    //获取改目录下所有文件和目录的绝对路径
                //System.out.println(Arrays.toString(fa));
                for (int i = 0; i < fa.length; i++) {
                    File fs = fa[i];
                    if(fs.isFile()){
                        if(fs.getName().indexOf(endwith_name)!=-1){
                            fileNameList.add(fs.getName());
                            filePathList.add(fs.getAbsolutePath());
                        }
                    }else if(fs.isDirectory()) {
                        RecrusiveOperation(endwith_name, input, fs.getAbsolutePath());
                    }
                }
            }
        }
        for(int i = 0;i < filePathList.size();i++){
            //System.out.println(fileNameList.get(i));
            System.out.println(filePathList.get(i)+":");
            switch(input) {
                case "-c":
                    BasicOperation("-c", input);    break;
                case "-w":
                    BasicOperation("-w", input);    break;
                case "-l":
                    BasicOperation("-l", input);    break;
                case "-a":
                    SpecialLine(filePathList.get(i));     break;
            }
            System.out.println();
        }
    }
    //扩展功能实现：-a 返回更复杂的数据（代码行/空行/注释行）
    public static void SpecialLine(String path) throws Exception {
        int BlankLine = 0;      //空行数
        int AnnotationLine = 0;     //注释行数
        int CodeLine = 0;       //代码行数
        FileInputStream file = new FileInputStream(path);
        InputStreamReader is = new InputStreamReader(file);
        BufferedReader br = new BufferedReader(is);
        String s;
        //正则表达式表示空行
        String RegexBlankLine = "(\\{|\\})?\\s*";   //空行
        //正则表达式表示单行注释
        String RegexAnnotation = "\\s*(\\{|\\})?\\s*//.*";    //单行注释符‘//’
        //正则表达式表示多行注释
        String RegexAnnotationStart = "\\s*(\\{||\\})?/\\*.*";   //多行注释起始符‘/*’
        String RegexAnnotationEnd = ".*\\*/";       //多行注释结束符‘*/’
        while ((s = br.readLine()) != null) {
            if (s.matches(RegexBlankLine)) {
                BlankLine++;
            } else if (s.matches(RegexAnnotation)) {
                AnnotationLine++;
            } else if (s.matches(RegexAnnotationStart)) {
                do {
                    AnnotationLine++;
                    s = br.readLine();
                } while (!s.matches(RegexAnnotationEnd));
                AnnotationLine++;     //多行注释结束符所在行也应加上
            } else {
                CodeLine++;
            }
        }
        System.out.println("空白行：" + BlankLine + " 行.");
        System.out.println("注释行：" + AnnotationLine + " 行.");
        System.out.println("代码行：" + CodeLine + " 行.");
    }

    public static void _x(){
        new FileFrame();
    }
}