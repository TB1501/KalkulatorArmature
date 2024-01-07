import com.TBasic.Armatura;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        JFrame frame=new JFrame("Kalkulator armature");
        frame.setContentPane(new Armatura().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}