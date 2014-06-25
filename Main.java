import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

/**
 * Created by Artem on 24.06.14.
 */
public class Main {
    JButton[] lattice = new JButton[9];//
    public ImageIcon xIcon, oIcon;
    public int[] cells = new int[9];

    public int player = 1;
    public void play(int id){
        if (switchPlayer(id)){
            player = (player == 1) ? 2 : 1;
            checkWin();
        }
    }
    public void IconsForFrame(){
        try{
            Image x = ImageIO.read(Main.class.getResource("/x_Gi.png"));
            Image o = ImageIO.read(Main.class.getResource("/o_Gi.png"));

            xIcon = new ImageIcon(x.getScaledInstance(lattice[0].getWidth(), lattice[0].getHeight(), Image.SCALE_SMOOTH));
            oIcon = new ImageIcon(o.getScaledInstance(lattice[0].getWidth(), lattice[0].getHeight(), Image.SCALE_SMOOTH));

            for (int i = 0; i < 9; i++){
                if (lattice[i].getIcon() != null) lattice[i].setIcon(cells[i] == 1 ? xIcon : oIcon);//тернарная операция
            }
        }catch(IOException e){}

    }
    public void checkWin(){
        if (cells[0] == cells[1] && cells[1] == cells[2] && cells[2] != 0)
            win(0);
        if (cells[3] == cells[4] && cells[4] == cells[5] && cells[5] != 0)
            win(5);
        if (cells[6] == cells[7] && cells[7] == cells[8] && cells[8] != 0)
            win(8);
        //================================================================
        if (cells[0] == cells[3] && cells[3] == cells[6] && cells[6] != 0)
            win(6);
        if (cells[1] == cells[4] && cells[4] == cells[7] && cells[7] != 0)
            win(7);
        if (cells[2] == cells[5] && cells[5] == cells[8] && cells[8] != 0)
            win(8);
        //================================================================

        if (cells[0] == cells[4] && cells[4] == cells[8] && cells[8] != 0)
            win(8);
        if (cells[6] == cells[4] && cells[4] == cells[2] && cells[2] != 0)
            win(2);

        if (cells[0] !=0 && cells[1] != 0 && cells[2] != 0 && cells[3] != 0 && cells[4] != 0 && cells[5] != 0 && cells[6] != 0 && cells[7] != 0 && cells[8] != 0){
            draw();
        }
    }
    public void draw(){
        if (JOptionPane.showConfirmDialog(new JFrame("It's a draw"), "It's a draw! Wanna play again?") ==  JOptionPane.YES_OPTION)
            restart();
        else
            System.exit(0);
    }
    public void win(int square){
        String winner = cells[square] == 1 ? "1 " : "2 ";
        if (JOptionPane.showConfirmDialog(new JFrame("Congrats!!! " + winner), "Player " + winner + " has won... Wanna play again?")
                ==  JOptionPane.YES_OPTION)
            restart();
        else
            System.exit(0);
    }
    public void restart(){
        player = 1;
        for (int i = 0; i < 9; i++){
            cells[i] = 0;
            lattice[i].setIcon(null);
        }
    }
    public boolean switchPlayer(int cellNumb){
        if (cells[cellNumb] != 0){
            return false;
        }
        cells[cellNumb] = player;
        lattice[cellNumb].setIcon(player == 1 ? xIcon : oIcon);
        return true;
    }
    public void makeFrame(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(50, 50);


        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new GridLayout(3, 3, 0, 0));

        panel.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                IconsForFrame();
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
            }
            public void componentHidden(ComponentEvent e) {

            }
        });
        for (int i = 0; i < 9; i++){
            final int pos = i;

            lattice[i] = new JButton();
            lattice[i].addActionListener(new ActionListener() {
                int id = pos;
                public void actionPerformed(ActionEvent e) {
                    play(id);
                }
            });
            panel.add(lattice[i]);
        }
        frame.setSize(450, 450);

    }
    public static void main(String[] args){
        Main instance = new Main();
        instance.makeFrame();
        instance.IconsForFrame();
    }
}
