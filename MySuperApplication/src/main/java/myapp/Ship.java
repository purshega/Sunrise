package main.java.myapp;

import java.util.ArrayList;

public class Ship {
	private int count;
	private ArrayList<AreaAroundShip> areaAroundShip=new ArrayList<AreaAroundShip>();
	private ArrayList<Location> location=new ArrayList<Location>();
	
	public void addLocation(int x, int y){
		location.add(new Location(x,y));
	}
	
	
	
	
	public ArrayList<Location> getLocation() {
		return location;
	}




	public void setAreaAroundShip(int x, int y){
		areaAroundShip.add(new AreaAroundShip(x,y));
	}
	
	public ArrayList<AreaAroundShip> getAreaAroundShip(){
		return areaAroundShip;
	}
	

	public Ship(){
		
	}
	
	public Ship(int count){
		this.count=count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}