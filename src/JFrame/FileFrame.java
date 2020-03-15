package JFrame;

import com.HDH.WC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

/**
 * GUI
 * @author Dawson_Huang
 * @Date 2020/3/14 - 9:27
 */

public class FileFrame implements ActionListener {
    JFrame frame = new JFrame("文件选择");// 框架布局
    JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
    Container con = new Container();
    JLabel label1 = new JLabel("文件目录");
    JTextField text1 = new JTextField();// TextField 目录的路径
    JButton button1 = new JButton("选择");// 选择
    JFileChooser jfc = new JFileChooser();// 文件选择器
    JLabel label2 = new JLabel("选择命令");
    JCheckBox jCheckBoxC = new JCheckBox("-c");
    JCheckBox jCheckBoxW = new JCheckBox("-w");
    JCheckBox jCheckBoxL = new JCheckBox("-l");
    JCheckBox jCheckBoxA = new JCheckBox("-a");
    JButton button2 = new JButton("确定");//

    public static void main(String[] args) {
        new FileFrame();
    }

    public FileFrame() {
        jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  // 获得显示屏幕的宽度
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // 获得显示屏幕的高度
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150)); // 设置窗口出现的位置
        frame.setSize(500, 360);// 设定窗口大小
        frame.setContentPane(tabPane);// 设置布局
        label1.setBounds(10, 10, 70, 20);
        text1.setBounds(75, 10, 120, 20);
        button1.setBounds(210, 10, 50, 20);
        label2.setBounds(10, 60, 70, 20);
        button2.setBounds(240, 60, 60, 20);
        jCheckBoxC.setBounds(80, 60, 40, 20);
        jCheckBoxW.setBounds(120, 60, 40, 20);
        jCheckBoxL.setBounds(140, 60, 40, 20);
        jCheckBoxA.setBounds(180, 60, 40, 20);
        button1.addActionListener(this); // 添加事件处理
        button2.addActionListener(this); // 添加事件处理
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(label2);
        con.add(jCheckBoxC);
        con.add(jCheckBoxW);
        con.add(jCheckBoxL);
        con.add(jCheckBoxA);
        con.add(button2);
        frame.setVisible(true);// 窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
        tabPane.add("1面板", con);// 添加布局1
    }

    public String path;
    public String order;
    /**
     * 时间监听的方法
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button1)) { // 判断触发方法的按钮是哪个
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;
            } else {
                File f = jfc.getSelectedFile();// f为选择到的目录
                text1.setText(f.getAbsolutePath());
                path = f.getAbsolutePath();
            }
        }
        if (e.getSource().equals(button2)) {
            JCheckBox[] jcbs = {jCheckBoxA, jCheckBoxC, jCheckBoxL, jCheckBoxW};
            for (JCheckBox j : jcbs) {
                if (j.isSelected()) {
                    order = j.getText();
                    switch(order) {
                        case "-c":
                            try {
                                WC.BasicOperation("-c",path);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case "-w":
                            try {
                                WC.BasicOperation("-c",path);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case "-l":
                            try {
                                WC.BasicOperation("-c",path);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case "-a":
                            try {
                                WC.SpecialLine(path);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }
    }
}
