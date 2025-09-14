import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

class DrawRectangle extends JComponent{
    private int x = 0;
    private int y = 0;
    private int height = 50;
    private int width = 50;
    private int cir_x = 450;
    private  int cir_y = 450;
    private Random random = new Random();
    private ArrayList<snakeBody> snake = new ArrayList<>();
    boolean move_right = false;
    boolean move_left = false;
    boolean move_up = false;
    boolean move_down = false;
    boolean right_arrow = false;
    boolean left_arrow = false;
    boolean up_arrow = false;
    boolean down_arrow = false;
    boolean game = false;
    boolean pause = false;
    boolean is_game_started = false;
    private int score = 0;
    String hightScore;
    JLabel score_card = new JLabel("Score  "+ score);
    private snakeBody snakeHead;
    private int length = 1;
    JLabel gameOVer = new JLabel("");
    JLabel paused = new JLabel("");
    JLabel Restart = new JLabel("");
    private class snakeBody{
        int xx;
        int yy;

        public snakeBody(int x, int y){
            this.xx = x;
            this.yy = y;
        }
    }

    
    public DrawRectangle() {
        // Initialize the score label
        score_card = new JLabel("Score: " + score);
        score_card.setFont(new Font("Arial", Font.BOLD, 30));
        score_card.setForeground(Color.BLACK);
        
        // Add the label to the JComponent
        this.setLayout(null); // Use null layout for absolute positioning
        score_card.setBounds(220, -80, 200, 200); // Position label at top-left corner
        this.add(score_card);
        this.add(gameOVer);
        this.add(paused);
        this.snakeHead = new snakeBody(50,50);
        snake.add(snakeHead);

    }

    
    @Override
    public void paintComponent(Graphics g){
    Graphics2D C = (Graphics2D) g;
    C.setColor(Color.RED);
    int temp = x;
    for(snakeBody s: snake){
        
        C.fillRect(s.xx,s.yy, width,height);
        C.setColor(Color.BLACK);
        C.drawRect(s.xx,s.yy,width,height);
        C.setColor(Color.RED);
    }
    this.x = temp;
    C.setColor(Color.BLUE);
    C.drawOval( cir_x, cir_y, 50, 50);
    C.fillOval(cir_x,cir_y, 50, 50);
    
  }

  public int snakeHead_xx(){
    return snakeHead.xx;
  }
  public int snakeHead_yy(){
    return snakeHead.yy;
  }
  
    public int length(){
        return this.snake.size();
    }  
    public void collision() {
        // Create rectangles representing the bounds of the snake and the circle
        Rectangle snakeBounds = new Rectangle(snakeHead.xx, snakeHead.yy, width, height);
        Rectangle circleBounds = new Rectangle(cir_x, cir_y, 40, 40);

        // Check for intersection
        if (snakeBounds.intersects(circleBounds)) {
            snakeBody newBody = new snakeBody(snake.get(snake.size()-1).xx,snake.get(snake.size()-1).yy);
            snake.add(newBody); // Increase snake size   
            
            this.MoveCircle(); // Move the circle to a new position
            this.score += 10;
            score_card.setText("Score: " + score);
            score_card.repaint();
            score_card.revalidate();
            this.repaint(); // Repaint to reflect changes
        }
    }

    private String highScore(){
        String file_path = "Score.txt";
        String last_highScore = null;
            try(FileReader fileReader = new FileReader(file_path);
                BufferedReader bufferedReader = new BufferedReader(fileReader)){

                    String line;
                    while((line = bufferedReader.readLine())!=null){
                        last_highScore = line;                        
                    }
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            if(Integer.parseInt(last_highScore)<this.score){
                last_highScore = Integer.toString(this.score);
                try(FileWriter fileWriter = new FileWriter(file_path);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
                bufferedWriter.write(last_highScore);
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

            return last_highScore;
    }

    public void GameOver(){
        Rectangle HeadBounds = new Rectangle(snakeHead.xx, snakeHead.yy, 50, 50);
                
        for(int i=3; i<snake.size();i++){
            int x1 = snake.get(i).xx;
            int y1 = snake.get(i).yy;
            Rectangle BodyBounds = new Rectangle(x1, y1, 50,50);

        // Check for intersection
        if (HeadBounds.intersects(BodyBounds)) {
            this.game = true;
            this.hightScore = this.highScore();
            gameOVer.setFont(new Font("Arial", Font.BOLD, 30));
            gameOVer.setForeground(Color.BLACK);
            gameOVer.setBounds(100, -100, 400, 600); // Position label at top-left corner
            gameOVer.setText("<html><center><b>Game Over!<b><br>"
            + "Score: " + score + "<br>"
            + "High Score: " + hightScore + "<br>"
            + "Press SPACE to Restart!</center></html>");            // Exit the method once a collision is detected

            // Exit the method once a collision is detected
            return;   
            
        }
        
    }
}

    

    public void move(){
        int temp = snakeHead.xx;
        int temp2 = snakeHead.yy;
        if(this.pause || this.game){
            return;
        }

        //to make the game complecated
        
        if(this.snakeHead.xx >= 600+(snake.size())*50){
            this.snakeHead.xx = -100;
            
        }

        else if(this.snakeHead.xx <= -(snake.size())*50){
            this.snakeHead.xx = 700;
        }

        else if(this.snakeHead.yy <= -(snake.size())*50){
            this.snakeHead.yy = 700;
        }
        else if(this.snakeHead.yy>=600+(snake.size())*50){
            this.snakeHead.yy = -100;
            }

        else if(snakeHead.xx>750){
                snakeHead.xx +=50;
    
             }
         else if(snakeHead.xx<=-150){
                snakeHead.xx -=50;
                
             }
    
        else if(snakeHead.yy<=-150){
                snakeHead.yy -=50;
                
             }
        else if(snakeHead.yy>750){
                snakeHead.yy +=50;
                
             }

        else if(snakeHead.yy>=0 && snakeHead.yy<=600 && this.move_right){
                 snakeHead.xx+=50;
                }
        else if(snakeHead.yy>=0 && snakeHead.yy<=600 && this.move_left){
             snakeHead.xx-=50;
            }
        
        else if(snakeHead.xx>=0 && snakeHead.xx<=600 && this.move_up){

            snakeHead.yy-=50;

        }  
        else if(snakeHead.xx>=9 && snakeHead.xx<=600 && this.move_down){
            
             snakeHead.yy+=50;
                   
         }

         

            
            int temp3;
            int temp4;    
            for(int l = 1;l<snake.size();l++){
                        temp3 = snake.get(l).xx;
                        temp4 = snake.get(l).yy;
                        snake.get(l).xx = temp;
                        snake.get(l).yy = temp2;
                        temp = temp3;
                        temp2 = temp4;
                    }
                
            
         
        this.repaint();

    }


    public void MoveCircle(){
        if(this.game){
            return;
        }
        this.cir_x = random.nextInt(525);
        this.cir_y = random.nextInt(525);
        this.repaint();
    }

    public void Restart() {
        // Reset game state
        this.snake.clear(); // Clear the snake body
        this.snakeHead = new snakeBody(50, 50); // Reset head position
        this.snake.add(snakeHead);
    
        // Reset game state variables
        this.score = 0;
        this.move_right = false;  // Default direction
        this.move_left = false;
        this.move_up = false;
        this.move_down = false;
        this.right_arrow = false;
        this.left_arrow = false;
        this.up_arrow = false;
        this.down_arrow = false;
        this.pause = false;
        this.game = false;
        this.is_game_started = false;
    
        // Reset labels
        this.gameOVer.setText("");
        this.paused.setText("");
        this.score_card.setText("Score: " + score);
    
        // Reset the circle (food) position
        this.MoveCircle();
    
        // Repaint the component
        this.repaint();
    }

    public void SaveGame(){
        String file_path = "Save.txt";

        try(FileWriter fileWriter = new FileWriter(file_path);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
                for(snakeBody s:snake){
                    bufferedWriter.write(Integer.toString(s.xx));
                    bufferedWriter.newLine();
                    bufferedWriter.write(Integer.toString(s.yy));
                    bufferedWriter.newLine();
                }
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
    }
    
    public void LoadSavedGame(){
        String file_path = "Save.txt";
        ArrayList<Integer> oldSnake = new ArrayList<>();
        try(FileReader fileReader = new FileReader(file_path);
                BufferedReader bufferedReader = new BufferedReader(fileReader)){

                    String line;
                    int val;
                    while((line = bufferedReader.readLine())!=null){
                        val = Integer.parseInt(line);
                        oldSnake.add(val);
                    }
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            this.snakeHead.xx = oldSnake.get(0);
            this.snakeHead.yy = oldSnake.get(1);
            for(int i = 2;i<oldSnake.size()-3;i+=2){
                snakeBody s = new snakeBody(oldSnake.get(i), oldSnake.get(i+1));
                snake.add(s);
                this.score+=10;
            }
        this.repaint();
    }

} 
