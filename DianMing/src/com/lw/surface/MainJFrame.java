package com.lw.surface;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.lw.operation.DianMing;

public class MainJFrame {
	
	private static List<String> names = new ArrayList<String>();
	private static List<String> hasName = new ArrayList<String>();//已经点过名的跳过
	
	
	/**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        final JFrame frame = new JFrame("点名系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);

        
        /*
         * 1. 点名册选择Panel
         */
        JPanel dmPanel = new JPanel();
        dmPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //点名册选择
        JLabel dmLabel = new JLabel("点名册选择：");
        dmPanel.add(dmLabel);
        
        // 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(1, 40);
        msgTextArea.setLineWrap(true);
        msgTextArea.setEditable(false);
        dmPanel.add(msgTextArea);
        
        JButton openBtn = new JButton("打开");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(frame, msgTextArea);
            }
        });
        dmPanel.add(openBtn);
        
        /*
         * 2.自动点名间隔时长，单位秒
         */
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel timeLabel = new JLabel("自动点名间隔时长（单位秒）：");
        timePanel.add(timeLabel);
        
        final JTextField timeField = new JTextField(8);
        timeField.setFont(new Font(null, Font.PLAIN, 20));
        timePanel.add(timeField);
        
        /*
         * 3.自动点名多少人
         */
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel countLabel = new JLabel("自动点名多少人：");
        countPanel.add(countLabel);
        
        final JTextField countField = new JTextField(8);
        countField.setFont(new Font(null, Font.PLAIN, 20));
        countPanel.add(countField);        
        
        /*
         * 5.额外信息展示处
         */
        JPanel extraPanel = new JPanel();
        extraPanel.setLayout(new FlowLayout());
        JLabel extraLabel = new JLabel("其他信息展示处:");
        final JTextArea extraArea = new JTextArea(15,40);
        extraArea.setLineWrap(true);
        extraArea.setEditable(false);
     // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane scrollPane = new JScrollPane(
        		extraArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        extraPanel.add(extraLabel);
        extraPanel.add(scrollPane);
        
        /*
         *4.按钮区域，自动点名，暂停/播放
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton autoButton = new JButton("自动点名");
        autoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autoDMAction(timeField,countField,extraArea);
			}
		});
        buttonPanel.add(autoButton);
        
        // 显示窗口
        JPanel panel = new JPanel();
        GridLayout grid=new GridLayout(5,1);//弄一个aXb的网格
//        panel.setLayout(grid);
        panel.add(dmPanel);
        panel.add(timePanel);
        panel.add(countPanel);
        panel.add(buttonPanel);
        panel.add(extraPanel);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    
    private static void autoDMAction(JTextField timeField,JTextField countField,JTextArea extraArea){
    	String timeoutStr = timeField.getText();
		int timeout = 0;
		if(timeoutStr!=null&&!"".equals(timeoutStr)&&timeoutStr.matches("\\d*")){
			try{
				timeout = Integer.parseInt(timeoutStr);
				timeout *= 1000;//转换成毫秒
			}catch(NumberFormatException e){
				timeout = 0;
			}
		}
		String countStr = countField.getText();
		int count = 0;
		if(countStr!=null&&!"".equals(countStr)&&countStr.matches("\\d*")){
			try{
				count = Integer.parseInt(countStr);
			}catch(NumberFormatException e){
				count = 0;
			}
		}
		String[] name = new String[count];
		int namesLength = names.size();
		if(count>namesLength){
			extraArea.setText("输入人数为："+count+",实际人数为："+namesLength+",由于输入人数大于实际人数将开始全额点名。");
			count = namesLength;
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Collections.shuffle(names);//随机排序
		extraArea.setText("以下是随机选取的人：\r\n");
		for(int i=0;i<count;i++){
			int repeatCount = 0;
			String dmName = names.get(i);
			name[i] = dmName;
			extraArea.append(dmName+",");
		}
		boolean isFinish = DianMing.play(timeout,name);
		if(isFinish){
			extraArea.setText(extraArea.getText()+"\r\n"+"完结撒花。");;
		}
    }
    
    
    /*
     * 打开文件
     */
    private static void showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("文本(*.txt)","txt"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("文本(*.txt)","txt"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            try {
            	if(names==null||names.size()==0)
            		names = DianMing.readFromFile(file.getAbsolutePath());
				if(names==null||names.size()==0){
					msgTextArea.append("文件读取出错。 请联系QQ:1216187041");
				}
			} catch (IOException e) {
				msgTextArea.append("打开文件出错。 请联系QQ:1216187041");
				e.printStackTrace();
			}
            msgTextArea.append("打开文件: " + file.getAbsolutePath());
        }
    }
    
    public static void main(String[] args) {
        // 显示应用 GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    	
//    	MainJFrame mframe= new MainJFrame();
//    	mframe.createAndShowGUI();
    }
}
