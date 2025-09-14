import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class Main{
   public static void main(String[] args) {
       
       JFrame First = new JFrame("Snake Game");
       DrawRectangle one = new DrawRectangle();
       
       First.setLayout(new GridLayout());
       First.add(one);
       
       First.setSize(600,600);
       
       First.setVisible(true);
       First.setLocationRelativeTo(null);
       First.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       First.setResizable(false);


       InputMap inputMap = one.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
       ActionMap actionMap = one.getActionMap();

       Timer great_move = new Timer(220, e-> one.move());
       Timer stop = new Timer(1, e-> one.GameOver());
       stop.start();
       if(one.game){
        stop.stop();
       }
       // Define the Action for Moving Right
       inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
       actionMap.put("moveRight", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
           if(one.snakeHead_yy()>=0 && one.snakeHead_yy()<=600){
            one.right_arrow = true;
            one.left_arrow = false;
            one.move_up = false;
            one.move_down = false;
            one.is_game_started = true;
            if (!one.move_left){
            one.move_right = true;
            }
            
            great_move.start();
           
            if(one.game){great_move.stop();
                        stop.stop();}      
        }
        else{
        great_move.start();
        }
        }
       });
       
       inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
       actionMap.put("moveLeft", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
            if(one.snakeHead_yy()<=-50 || one.snakeHead_yy()>650)
            {
                return;
            }
            
            one.left_arrow = true;
            one.right_arrow = false;
            one.move_up = false;
            one.move_down = false;
            one.is_game_started = true;
            if(!one.move_right){
            one.move_left = true;
            }
            
            great_move.start();
            if(one.game){great_move.stop();
                        stop.stop();}
        }
       });

       inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUP");
       actionMap.put("moveUP", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
            if(one.snakeHead_xx()<=-50 || one.snakeHead_xx()>650)
            {
                return;
            }
            one.up_arrow = true;
            one.down_arrow = false;
            one.move_left = false;
            one.move_right = false;
            one.is_game_started = true;
            if (!one.move_down){
            one.move_up = true;
        
            }
        
            great_move.start();
            if(one.game){great_move.stop();
                        stop.stop();
            }
        }
       });
       inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDOWN");
       actionMap.put("moveDOWN", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
                if(one.snakeHead_xx()<=-50 || one.snakeHead_xx()>650)
                {
                    return;
                }
                one.is_game_started = true;
                one.down_arrow = true;
                one.up_arrow = false;
                one.move_right = false;
                one.move_left = false;
                if(!one.move_up){
                one.move_down = true;
                }
            
            great_move.start();
            if(one.game){great_move.stop();
                         stop.stop();}
        }
       });

       
       Timer for_cir = new Timer(30000,e-> one.MoveCircle());
       for_cir.start();
       Timer for_collision = new Timer(1,e-> one.collision());
       for_collision.start();
       
       inputMap.put(KeyStroke.getKeyStroke("ENTER"), "Pause");
       actionMap.put("Pause", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
            if(! one.pause){
            great_move.stop();
            for_cir.stop();
            one.paused.setFont(new Font("Arial", Font.BOLD, 40));
            one.paused.setForeground(Color.GREEN);
            one.paused.setBounds(100, 70, 500, 300); 
            one.paused.setText("Paused! Press Enter");
            one.pause = true;
            }
            else {
            great_move.start();
            for_cir.start();
            one.pause = false;
            one.paused.setText("");
            }
         }
       });

       // restart 
       inputMap.put(KeyStroke.getKeyStroke("SPACE"), "space");
       actionMap.put("space", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e){
            one.Restart();
        }
       });

       inputMap.put(KeyStroke.getKeyStroke("S"), "save");
       actionMap.put("save", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e){
            one.SaveGame();
            First.dispose();
        }
       });

       inputMap.put(KeyStroke.getKeyStroke("L"), "load");
       actionMap.put("load", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e){
            if(one.length()>1 || one.is_game_started){
                return;
            }
            one.LoadSavedGame();
        }
       });
    
   } 
}