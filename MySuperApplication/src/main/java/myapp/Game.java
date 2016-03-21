package main.java.myapp;



import android.widget.Button;

public class Game {
	 private boolean[][] ShootField = new boolean[10][10];
	 private boolean Target = false;
	 private boolean TargetEnemy = false;
	 private int LastX;
	 private int LastY;
	 private boolean Left = false;
	 private boolean Rigth = false;
	 private boolean Up = false;
	 private boolean Down = false;
	 private int NumberOfShip = 0;
	 private int NumberOfEnemyShip = 0;
	 private int count = 0;
	 private int count2 = 0;
	 private int dop = 0;
	 private boolean yes = false;
	 private int shot = 0;
	 private int MyLife = 20;
	 private int EnemyLife = 20;
	 private int sizeOfButton=30;
	 
	 
    private Button[][] buttons = new Button[10][10];
	private Button[][] ShootButtons = new Button[10][10];
	
	private int isButtonUse[][]=new int[10][10];
	private int batleMyField [][]=new int[10][10];
	private int batleEnemyField [][]=new int[10][10];
		
		public boolean addShip(Ship ship, int who) {

			int count = ship.getCount();
			int x = (int) (0 + 10 * Math.random());
			int y = (int) (0 + 10 * Math.random());

			int xy = (int) (0 + 2 * Math.random());
			if (xy == 0) {
				while (checkX(x, y, count, who) != true) {
					System.out.println("Proverka");
					x = (int) (0 + 10 * Math.random());
					y = (int) (0 + 10 * Math.random());
					
				}
				for (int i = 0; i < count; i++) {
					ship.addLocation(x+i,y);
					areaAroundShipX(x, y, i, ship,who);
					if (who==1)batleMyField [x+i][y]=1;
					if (who==2)batleEnemyField [x+i][y]=1;
				}
				areaAroundShipX2(x, y, count, ship,who);
			}
			if (xy == 1) {
				System.out.println("Proverka");
				while (checkY(x, y, count, who) != true) {
					x = (int) (0 + 10 * Math.random());
					y = (int) (0 + 10 * Math.random());
				}
				for (int i = 0; i < count; i++) {
					ship.addLocation(x,y+i);
					areaAroundShipY(x, y, i, ship, who);
					if (who==1)batleMyField [x][y+i]=1;
					if (who==2)batleEnemyField [x][y+i]=1;
				}
				areaAroundShipY2(x, y, count, ship, who);
				
			}
			return true;
		}
		
		public boolean checkX(int x, int y, int size, int who) {
			if (x + size > 10)
				return false;
			for (int i = 0; i < size; i++) {
				if(who==1) { 
					if(batleMyField[x+i][y]==1 || batleMyField[x+i][y]==2 )
						return false;
				} else {
				if (batleEnemyField[x+i][y]==1 || batleEnemyField[x+i][y]==2 )
					return false;
				}
			}
			return true;
		}

		public void areaAroundShipX(int x, int y, int i, Ship ship, int who) {
			if (y != 0) {
				if(who==1)batleMyField[x+i][y-1]=2;
				else batleEnemyField [x+i][y-1]=2;
				ship.setAreaAroundShip(x+i, y-1);
			}
			if (y != 9) {
				if(who==1)batleMyField[x+i][y+1]=2;
				else batleEnemyField [x+i][y+1]=2;
				 ship.setAreaAroundShip(x+i, y+1);
			}
		}

		public void areaAroundShipX2(int x, int y, int count, Ship ship, int who) {
			int p = count - 1;
			if (x != 0) {
				if(who==1)batleMyField[x-1][y]=2;
				else batleEnemyField [x-1][y]=2;
				ship.setAreaAroundShip(x-1, y);
			}
			if (x != 0 && y != 0) {
				if(who==1)batleMyField[x-1][y-1]=2;
				else batleEnemyField [x-1][y-1]=2;
				ship.setAreaAroundShip(x-1, y-1);
			}
			if (x != 0 && y != 9) {
				if(who==1)batleMyField[x-1][y+1]=2;
				else batleEnemyField [x-1][y+1]=2;
				ship.setAreaAroundShip(x-1, y+1);
			}
			if (x + p != 9) {
				if(who==1)batleMyField[x+count][y]=2;
				else batleEnemyField [x+count][y]=2;
				ship.setAreaAroundShip(x+count, y);
			}
			if (x + p != 9 && y != 0) {
				if(who==1)batleMyField[x+count][y-1]=2;
				else batleEnemyField [x+count][y-1]=2;
				ship.setAreaAroundShip(x+count, y-1);
			}
			if (x + p != 9 && y != 9) {
				if(who==1)batleMyField[x+count][y+1]=2;
				else batleEnemyField [x+count][y+1]=2;
				ship.setAreaAroundShip(x+count, y+1);
			}
		}

		public boolean checkY(int x, int y, int size, int who) {
			if (y + size > 10)
				return false;
			for (int i = 0; i < size; i++) {
				if(who==1) { 
					if(batleMyField[x][y+i]==1 || batleMyField[x][y+i]==2 )
						return false;
				} else {
				if (batleEnemyField[x][y+i]==1 || batleEnemyField[x][y+i]==2 )
					return false;
				}
			}
			return true;
		}
				

		public void areaAroundShipY(int x, int y, int i, Ship ship, int who) {
			if (x != 0) {
				if(who==1)batleMyField[x-1][y+i]=2;
				else batleEnemyField [x-1][y+i]=2;
				ship.setAreaAroundShip(x-1, y+i);
			}
			if (x != 9) {
				if(who==1)batleMyField[x+1][y+i]=2;
				else batleEnemyField [x+1][y+i]=2;
				ship.setAreaAroundShip(x+1, y+i);
			}
		}

		public void areaAroundShipY2(int x, int y, int count, Ship ship, int who) {
			int p = count - 1;
			if (y != 0) {
				if(who==1)batleMyField[x][y-1]=2;
				else batleEnemyField[x][y-1]=2;
				ship.setAreaAroundShip(x, y-1);
			}
			if (y != 0 && x != 0) {
				if(who==1)batleMyField[x-1][y-1]=2;
				else batleEnemyField [x-1][y-1]=2;
				ship.setAreaAroundShip(x-1, y-1);
			}
			if (y != 0 && x != 9) {
				if(who==1)batleMyField[x+1][y-1]=2;
				else batleEnemyField [x+1][y-1]=2;
				ship.setAreaAroundShip(x+1, y-1);
			}
			if (y + p != 9) {
				if(who==1)batleMyField[x][y+count]=2;
				else batleEnemyField [x][y+count]=2;
				ship.setAreaAroundShip(x, y+count);
			}
			if (y + p != 9 && x != 0) {
				if(who==1)batleMyField[x-1][y+count]=2;
				else batleEnemyField [x-1][y+count]=2;
				ship.setAreaAroundShip(x-1, y+count);
			}
			if (y + p != 9 && x != 9) {
				if(who==1)batleMyField[x+1][y+count]=2;
				else batleEnemyField [x+1][y+count]=2;
				ship.setAreaAroundShip(x+1, y+count);
			}
		}
		
		public Game(){	
		}

		public boolean[][] getShootField() {
			return ShootField;
		}

		public void setShootField(boolean[][] shootField) {
			ShootField = shootField;
		}

		public boolean isTarget() {
			return Target;
		}

		public void setTarget(boolean target) {
			Target = target;
		}

		public boolean isTargetEnemy() {
			return TargetEnemy;
		}

		public void setTargetEnemy(boolean targetEnemy) {
			TargetEnemy = targetEnemy;
		}

		public int getLastX() {
			return LastX;
		}

		public void setLastX(int lastX) {
			LastX = lastX;
		}

		public int getLastY() {
			return LastY;
		}

		public void setLastY(int lastY) {
			LastY = lastY;
		}

		public boolean isLeft() {
			return Left;
		}

		public void setLeft(boolean left) {
			Left = left;
		}

		public boolean isRigth() {
			return Rigth;
		}

		public void setRigth(boolean rigth) {
			Rigth = rigth;
		}

		public boolean isUp() {
			return Up;
		}

		public void setUp(boolean up) {
			Up = up;
		}

		public boolean isDown() {
			return Down;
		}

		public void setDown(boolean down) {
			Down = down;
		}

		public int getNumberOfShip() {
			return NumberOfShip;
		}

		public void setNumberOfShip(int numberOfShip) {
			NumberOfShip = numberOfShip;
		}

		public int getNumberOfEnemyShip() {
			return NumberOfEnemyShip;
		}

		public void setNumberOfEnemyShip(int numberOfEnemyShip) {
			NumberOfEnemyShip = numberOfEnemyShip;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getCount2() {
			return count2;
		}

		public void setCount2(int count2) {
			this.count2 = count2;
		}

		public int getDop() {
			return dop;
		}

		public void setDop(int dop) {
			this.dop = dop;
		}

		public boolean isYes() {
			return yes;
		}

		public void setYes(boolean yes) {
			this.yes = yes;
		}

		public int getShot() {
			return shot;
		}

		public void setShot(int shot) {
			this.shot = shot;
		}

		public int getMyLife() {
			return MyLife;
		}

		public void setMyLife(int myLife) {
			MyLife = myLife;
		}

		public int getEnemyLife() {
			return EnemyLife;
		}

		public void setEnemyLife(int enemyLife) {
			EnemyLife = enemyLife;
		}

		public Button[][] getButtons() {
			return buttons;
		}

		public void setButtons(Button[][] buttons) {
			this.buttons = buttons;
		}

		public Button[][] getShootButtons() {
			return ShootButtons;
		}

		public void setShootButtons(Button[][] shootButtons) {
			ShootButtons = shootButtons;
		}

		public int getSizeOfButton() {
			return sizeOfButton;
		}

		public void setSizeOfButton(int sizeOfButton) {
			this.sizeOfButton = sizeOfButton;
		}

		public int[][] getBatleMyField() {
			return batleMyField;
		}

		public int[][] getBatleEnemyField() {
			return batleEnemyField;
		}

		public int[][] getIsButtonUse() {
			return isButtonUse;
		}

		public void setIsButtonUse(int x, int y) {
			this.isButtonUse[x][y]=1;
		}
		
		
		
		
}
