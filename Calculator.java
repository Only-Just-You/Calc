package org.example.jsq;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

public class Calculator extends WindowAdapter {
    Panel p1 = new Panel();
    Panel p2 = new Panel();
    Panel p3 = new Panel();

    TextField txt;

    private Button[] b = new Button[17];
    private String ss[] = {"7","8","9","+","4","5","6","-","1","2","3","*","clear",
    "0","=","/","close"};

    static Integer a;
    static String str;



    public static void main(String[] args) {
        (new Calculator()).frame();
    }

    private void frame() {
        Frame fm = new Frame("计算器");
        for(int i=0; i <= 16; i++){
            b[i] = new Button(ss[i]);
        }
        for(int i=0; i <= 15; i++){
            p2.add(b[i]);
        }
        b[16].setBackground(Color.YELLOW);

        txt = new TextField(15);
        txt.setEditable(true);
        for(int i=0; i < 16; i++){
            b[i].addActionListener(new ButtonListener());
        }
        b[16].addActionListener(new Close());
        fm.addWindowListener(this);

        fm.setBackground(Color.red);
        p1.setLayout(new BorderLayout());
        p1.add(txt,"North");
        p2.setLayout(new GridLayout(4,4));
        p3.setLayout(new BorderLayout());
        p3.add(b[16]);

        fm.add(p1,"North");
        fm.add(p2,"Center");
        fm.add(p3,"South");

        fm.pack();
        fm.setSize(300,400);
        fm.setLocation(350,350);
        fm.setVisible(true);
    }
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }

    public class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Button btn = (Button) e.getSource();

            if(btn.getLabel() == "="){
                str += "=";
                jisuan();
                str = String.valueOf(a);
                txt.setText(str);
            }else if(btn.getLabel() == "+"){
                txt.setText(txt.getText()+btn.getLabel());
            }else if(btn.getLabel() == "-"){
                txt.setText(txt.getText()+btn.getLabel());
            }else if(btn.getLabel() == "*"){
                txt.setText(txt.getText()+btn.getLabel());
            }else if(btn.getLabel() == "/"){
                txt.setText(txt.getText()+btn.getLabel());
            }else if(btn.getLabel() == "clear") {
                txt.setText("");
            }else{
                txt.setText(txt.getText()+btn.getLabel());
            }
        }

        private void jisuan() {
            String str = txt.getText();
            str += "=";
            Stack<Integer> queueN = new Stack<>();
            Stack<Character> queueFu = new Stack<>();

            int i=0;
            while(true){
                if(str.charAt(i) == '=') break;
                else if(str.charAt(i) == '+'){
                    queueFu.push('+');
                }else if(str.charAt(i) == '-'){
                    queueFu.push('-');
                }else if(str.charAt(i) == '*'){
                    i++;
                    Integer n = queueN.pop();;
                    queueN.push((str.charAt(i) - '0') * n) ;
                }else if(str.charAt(i) == '/'){
                    i++;
                    Integer n = queueN.pop();;
                    queueN.push(n / (str.charAt(i) - '0')) ;
                }else{
                    queueN.push(str.charAt(i) - '0');
                }
                i++;
            }

            while(queueFu.size() != 0){
                Integer n = queueN.pop();
                Character c =queueFu.pop();
                Integer n1 = queueN.pop();
                if(c == '+'){
                    queueN.push(n + n1);
                }else if(c == '-'){
                    queueN.push(n1 - n);
                }else{
                    System.out.println("逻辑错误");
                }
            }
            a = queueN.pop();
        }
    }
}
