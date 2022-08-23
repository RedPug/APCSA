import javax.swing.*;

public class Window{
    public Window(){
        JFrame frame = new JFrame("Windows!");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500,400);

        

        JPanel panel = new JPanel();

        JTextField in = new JTextField(5);

        Double value = 0.0;
        System.out.println("text: "+in.getText().length());
        if(in.getText().length() > 1){
            value = Double.parseDouble(in.getText());
        }

        JLabel text1 = new JLabel(String.valueOf(value));

        panel.add(in);
        panel.add(text1);

        frame.add(panel);
        
        frame.setVisible(true);
    }
    
    public static void main(String[] args){
        Window window = new Window();
    }
}
