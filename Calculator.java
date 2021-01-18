package org.example.jsq;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import static java.lang.Character.isDigit;

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

        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyChar() == '\n'){
                    new ButtonListener().jisuan();
                    str = String.valueOf(a);
                    txt.setText(str);
                }
            }
        });

        for(int i=0; i < 16; i++){
            b[i].addActionListener(new ButtonListener());
        }
        b[16].addActionListener(new Close());

        fm.addWindowListener(this);

        fm.setBackground(Color.red);
        p1.setLayout(new BorderLayout());
        p1.add(txt,"North");
        p2.setLayout(new GridLayout(4,4));
        p3.setLayout(new BorderLayout(50,50));
        p3.add(b[16]);

        fm.add(p1,"North");
        fm.add(p2,"Center");
        fm.add(p3,"South");

        fm.pack();
        fm.setSize(300,450);
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

        public void jisuan() {
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
                    Integer n = queueN.pop();
                    String tmp = "";
                    int j = i;
                    for(; !isDigit(str.charAt(j)); j++);
                    tmp = str.substring(i,j);
                    queueN.push(Integer.parseInt(tmp) * n) ;
                }else if(str.charAt(i) == '/'){
                    i++;
                    Integer n = queueN.pop();
                    String tmp = "";
                    int j = i;
                    for(; !isDigit(str.charAt(j)); j++);
                    tmp = str.substring(i,j);
                    queueN.push(n / Integer.parseInt(tmp)) ;
                    i = j;
                }else{
                    String tmp = "";
                    int j = i;
                    for(; isDigit(str.charAt(j)); j++);
                    tmp = str.substring(i,j);
                    queueN.push(Integer.parseInt(tmp));
                    i = j - 1;
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
