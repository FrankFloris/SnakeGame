import javax.swing.*;
import java.awt.*;

public class Main {
    
    public static void main (String[] args){
        JFrame window = new JFrame();
        Gameplay gameplay = new Gameplay();

        
        window.setBounds(100,100,915,700);
        window.setBackground(Color.DARK_GRAY);
//        window.setBackground(Color.RED);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gameplay);
        
    }
}
