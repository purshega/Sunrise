package main.java.myapp;

import java.util.ArrayList;

import com.example.mysuperapplication.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;


@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements OnLoadCompleteListener {
	 final String LOG_TAG = "myLogs";
	  final int MAX_STREAMS = 5;
	  
	  static View v;
	  private SoundPool sp;
	  private int pauseOfAction=300;
	  
	  private int soundIdShoot;
	  private int soundIdMissTarget;
	  private int soundIdExplosion;
	  private int enemyTargetHit=0;
	  private int myTargetHit=0;
	  
	
	
	Game game = new Game();
	ArrayList<Ship> MyShips = new ArrayList<Ship>();
	ArrayList<Ship> EnemyShips = new ArrayList<Ship>();
	

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		BitmapDrawable ship = (BitmapDrawable) this.getResources().getDrawable(R.drawable.ship);
		BitmapDrawable water = (BitmapDrawable) this.getResources().getDrawable(R.drawable.water);
		MyShips = new ArrayList<Ship>();
		EnemyShips = new ArrayList<Ship>();
		addShips(MyShips,1);
		addShips(EnemyShips,2);
	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		AbsoluteLayout show = (AbsoluteLayout) findViewById(R.id.layout_main);
		BitmapDrawable gameBackGround = (BitmapDrawable) this.getResources().getDrawable(R.drawable.img1);
		show.setBackgroundDrawable(gameBackGround);
		
		
		 sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
		 sp.setOnLoadCompleteListener(this);
		    
		
		    soundIdShoot = sp.load(this, R.raw.shot,1);
		    soundIdMissTarget = sp.load(this, R.raw.water_explo, 1);
		    soundIdExplosion = sp.load(this, R.raw.ship_explo, 1);
		    
		    
		  
		   
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				game.getButtons()[i][j] = new Button(this);
				game.getShootButtons()[i][j]=new Button(this);
				game.getButtons()[i][j].setLayoutParams(
				new AbsoluteLayout.LayoutParams(game.getSizeOfButton(), game.getSizeOfButton(), j * game.getSizeOfButton(), i * game.getSizeOfButton()));
				game.getShootButtons()[i][j].setLayoutParams(
				new AbsoluteLayout.LayoutParams(game.getSizeOfButton(), game.getSizeOfButton(), j * game.getSizeOfButton(), i * game.getSizeOfButton()+350));		
				if (game.getBatleMyField()[i][j] == 1){
					game.getButtons()[i][j].setBackgroundDrawable(ship);
				}
				else {
					game.getButtons()[i][j].setBackgroundDrawable(water);
				}
				game.getShootButtons()[i][j].setBackgroundDrawable(water);
				show.addView(game.getButtons()[i][j]);
				show.addView(game.getShootButtons()[i][j]);
			}
		}
		
		for(int i=0; i<10;i++){
			for (int j=0; j<10;j++){
			gameStart(i,j);
			}
		}
	}

	public void addShips(ArrayList<Ship> Ships,int who){
		Ships.add(new Ship(4));
		Ships.add(new Ship(3));
		Ships.add(new Ship(3));
		Ships.add(new Ship(2));
		Ships.add(new Ship(2));
		Ships.add(new Ship(2));
		Ships.add(new Ship(1));
		Ships.add(new Ship(1));
		Ships.add(new Ship(1));
		Ships.add(new Ship(1));
		
		 for(int i=0; i<Ships.size();i++){
			 while(game.addShip(Ships.get(i),who)!=true);
		 }
	}
		
			
		public void gameStart(final int  x, final int y){		
		game.getShootButtons()[x][y].setOnClickListener(new OnClickListener() {
			
			

			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				
				
				AbsoluteLayout show2 = (AbsoluteLayout) findViewById(R.id.layout_main);
				final BitmapDrawable destroy_ship = (BitmapDrawable) show2.getResources().getDrawable(R.drawable.destroy_ship);
				final BitmapDrawable shooting = (BitmapDrawable) show2.getResources().getDrawable(R.drawable.shooting);
				if (myTargetHit==0 && game.getIsButtonUse()[x][y]==0  ){
				sp.play(soundIdShoot, 1, 1, 0, 0, 1);
				SystemClock.sleep(pauseOfAction);
				
				

				
				if(game.getBatleEnemyField()[x][y]==1){
					game.setIsButtonUse(x,y);
					game.getShootButtons()[x][y].setBackground(destroy_ship);
					enemyTargetHit=1;
					sp.play(soundIdExplosion, 1, 1, 0, 0, 1);
					game.setEnemyLife(game.getEnemyLife()-1);
					if(game.isTargetEnemy()!=true){
					for(int i=0; i<EnemyShips.size();i++){
						for(int j=0; j<EnemyShips.get(i).getCount();j++){
							if(EnemyShips.get(i).getLocation().get(j).getX()==x && EnemyShips.get(i).getLocation().get(j).getY()==y){
								game.setCount2(EnemyShips.get(i).getCount()-1);
								game.setNumberOfEnemyShip(i);
								game.setTargetEnemy(true);
								
								if(game.getCount2()==0){
									for(int i2=0; i2<EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().size();i2++){
										game.setIsButtonUse(EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getX(),EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getY());
										game.getShootButtons()[EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getX()][EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getY()].setBackground(shooting);
										game.setTargetEnemy(false);
										
									}
								}				
							}
						}
					}
				}
					
				else if(game.isTargetEnemy()==true){
					if(game.getBatleEnemyField()[x][y]==1){
						game.getShootButtons()[x][y].setBackground(destroy_ship);
						game.setCount2(game.getCount2()-1); 
						
						if(game.getCount2()==0){
							for(int i2=0; i2<EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().size();i2++){
						game.setIsButtonUse(EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getX(),EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getY());
						game.getShootButtons()[EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getX()][EnemyShips.get(game.getNumberOfEnemyShip()).getAreaAroundShip().get(i2).getY()].setBackground(shooting);
						game.setTargetEnemy(false);
						}
					}	
				}
			}
		}

					if(game.getEnemyLife()==0){
						redirectToWin(v);
				}
				
				else if(game.getBatleEnemyField()[x][y]!=1){
					game.setIsButtonUse(x,y);
					
					
					game.getShootButtons()[x][y].setBackground(shooting);
					sp.play(soundIdMissTarget, 1, 1, 0, 0, 1);
					enemyTargetHit=0;
						
				}
				
					
				//  -------------------------- Enemy shooting----------------
					
				if(enemyTargetHit==0 && game.getMyLife()>0){
					
					myTargetHit=1;
					while(myTargetHit==1){
						SystemClock.sleep(pauseOfAction);
						sp.play(soundIdShoot, 1, 1, 0, 0, 1);
						SystemClock.sleep(pauseOfAction);
				if(game.getEnemyLife()!=0){
					if(game.isTarget()==false){
					int x=(int)(0+10*Math.random());
					int y=(int)(0+10*Math.random());
					while (game.getShootField()[x][y]!=false){
						x=(int)(0+10*Math.random());
						y=(int)(0+10*Math.random());
					}
					game.getShootField()[x][y]=true;
					
					
					for(int i=0; i<MyShips.size();i++){
						for(int j=0; j<MyShips.get(i).getCount();j++){
							if(MyShips.get(i).getLocation().get(j).getX()==x && MyShips.get(i).getLocation().get(j).getY()==y){
								game.setCount(MyShips.get(i).getCount()-1);
								game.setNumberOfShip(i);
								game.setTarget(true);
								game.setLastX(x);
								game.setLastY(y);
								game.setMyLife(game.getMyLife()-1);
								myTargetHit=1;
								if(game.getMyLife()==0){
								redirectToLose(v);
								}
								if(game.getCount()==0){
										for(int i2=0; i2<MyShips.get(game.getNumberOfShip()).getAreaAroundShip().size();i2++){					
											game.getButtons()[MyShips.get(game.getNumberOfShip()).getAreaAroundShip().get(i2).getX()][MyShips.get(game.getNumberOfShip()).getAreaAroundShip().get(i2).getY()].setBackground(shooting);
											game.setTarget(false);
											myTargetHit=0;
										}
									}
								}
							}
						}
					
				
					if(game.getBatleMyField()[x][y]==1){
						sp.play(soundIdExplosion, 1, 1, 0, 0, 1);
						game.getButtons()[x][y].setBackground(destroy_ship);
						myTargetHit=1;
					}
					if(game.getBatleMyField()[x][y]!=1){
						sp.play(soundIdMissTarget, 1, 1, 0, 0, 1);
						game.getButtons()[x][y].setBackground(shooting);
						myTargetHit=0;
					}
					}
					else  {
						int x=game.getLastX();
						int y=game.getLastY();
						if(game.isLeft()==true){y=y-1-game.getDop();}
						else if(game.isRigth()==true){y=y+1+game.getDop();}
						else if(game.isUp()==true){x=x-1-game.getDop();}
						else if(game.isDown()==true){x=x+1+game.getDop();}
						else {  game.setYes(false);
						while(game.isYes()==false){
							game.setShot((int)(0+4*Math.random()));
							System.out.println("shot = "+game.getShot());
							System.out.println("while   "+x+"  "+y);
						if(game.getShot()==0 && y-1>=0 ){
							if(game.getShootField()[x][y-1]!=true){
							y-=1; game.setYes(true);
							}
						} else if (game.getShot()==1 && y+1!=10  ){
							if(game.getShootField()[x][y+1]!=true){
							y+=1; game.setYes(true);
							}
						} else if(game.getShot()==2 && x-1>=0){
							if(game.getShootField()[x-1][y]!=true ){
							x-=1; game.setYes(true);
							}
						} else if (game.getShot()==3 && x+1!=10){
							if(game.getShootField()[x+1][y]!=true){
							x+=1; game.setYes(true);
							}
						}
					}
				}
						if(game.getBatleMyField()[x][y]==1){
							sp.play(soundIdExplosion, 1, 1, 0, 0, 1);
							game.getButtons()[x][y].setBackground(destroy_ship);
							game.setCount(game.getCount()-1);
							game.setDop(game.getDop()+1);
							if(game.getShot()==0) game.setLeft(true);
							if(game.getShot()==1)game.setRigth(true);
							if(game.getShot()==2)game.setUp(true); 
							if(game.getShot()==3)game.setDown(true); 
							game.getShootField()[x][y]=true;
							if(x==0 && game.isUp()==true){
								game.setDop(0);
								game.setYes(false);
								game.setUp(false); 	
							}
							if(x==9 && game.isDown()==true){
								game.setDop(0);
								game.setYes(false); 	
								game.setDown(false); 
							}
							if(y==0 && game.isLeft()==true){
								game.setDop(0);
								game.setYes(false);
								game.setLeft(false); 	
							}
							if(y==9 && game.isRigth()==true){
								game.setYes(false);	
								game.setRigth(false); 
							}
							game.setMyLife(game.getMyLife()-1);
							myTargetHit=1;
							if(game.getMyLife()==0){
							redirectToLose(v);
							}				
						}
						if(game.getBatleMyField()[x][y]!=1){
							sp.play(soundIdMissTarget, 1, 1, 0, 0, 1);
							game.getButtons()[x][y].setBackground(shooting);
							game.setDop(0);
							game.setYes(false);
							game.setLeft(false);
							game.setRigth(false);
							game.setUp(false);
							game.setDown(false);
							game.getShootField()[x][y]=true;
							myTargetHit=0;
					}					
						if(game.getCount()==0){
							for(int i2=0; i2<MyShips.get(game.getNumberOfShip()).getAreaAroundShip().size();i2++){
								game.getButtons()[MyShips.get(game.getNumberOfShip()).getAreaAroundShip().get(i2).getX()][MyShips.get(game.getNumberOfShip()).getAreaAroundShip().get(i2).getY()].setBackground(shooting);
								game.getShootField()[MyShips.get(game.getNumberOfShip()).getAreaAroundShip().get(i2).getX()][MyShips.get(game.getNumberOfShip()).getAreaAroundShip().get(i2).getY()]=true;
								game.setTarget(false);
							}
							game.setTarget(false);
							game.setLeft(false);
							game.setRigth(false);
							game.setUp(false);
							game.setDown(false);
							game.setDop(0);
							
						  }
					   }
				    }
			      }	
		     	}
		      } 
			}
		  }
		);
	  }


		@Override
		public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {	
		}
		
		 public void redirectToWin(View view){
			  Intent intent = new Intent(this,WinActivity.class);
			  startActivity(intent);
			 }
		 
		 public void redirectToLose(View view){
			  Intent intent = new Intent(this,LoseActivity.class);
			  startActivity(intent);
			 }
		 

		@Override
		protected void onStop() {
			soundIdShoot = sp.load(this, R.raw.shot,1);
			 sp.play(soundIdShoot, 1, 1, 0, 0, 1);
			super.onStop();
	} 
		
		
}
	