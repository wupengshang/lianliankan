package com.llk;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Gui{
	public static void main(String[] args){
	MainGui m1 = new MainGui(0);
	//s1.test();
	
	}
}
class MainGui extends JFrame{
	private GridLayout grid1 = new GridLayout(10,8);
	public static Location first_loc = new Location();
	public static Location second_loc = new Location();
	public static Picture [][] temp = new Picture [10][8]; 
	public static ImageIcon[] images = new ImageIcon[10];//10��ͼƬ����
	public static LinkedList<Picture> link = new LinkedList<Picture>();
	public static int realtype = 0;
	Random ran2 = new Random();
	
	
	public MainGui (int level){
		//��ʼ��ͼƬ
		imageinit();
		//��ʼ����ʾ����
		for(int i=0;i < temp.length;i++){
			for(int j = 0;j< temp[i].length;j++){
				//temp[i][j] = new Picture(i*temp[i].length + j,j);//�˴����������ͺ�ID
				temp[i][j] = new Picture(j,images[j]);
				temp[i][j].type = j;
				temp[i][j].lp.x = i;
				temp[i][j].lp.y = j;
				temp[i][j].colour = 0;
				temp[i][j].state = false;
				//temp[i][j].add(images[i]);;
			}	
		}
		//�����������λ�ã��˴�Ӧ��Ҫ������ѡ�õ����ָ�����ͼƬ���ࣩ	
		for(int i=0; i < temp.length; i++){
			for(int j=0; j < temp[i].length; j++){
				Picture t = new Picture();
				int loc=0;
				t = temp[i][j];
				loc = ran2.nextInt(temp.length * temp[0].length);
				temp[i][j] = temp[loc / temp[i].length][loc % temp[i].length];
				temp[loc / temp[i].length][loc % temp[i].length] = t;	
			}			
		}
		//����Ϊ���񲼾�ģʽ
		setLayout(grid1);
		System.out.println(temp.length);
		System.out.println(temp[0].length);
		//��Picture�Ķ�����ӵ�������,ע��˴���Picture�Ǽ̳���Button��ʵ����ActionListener�ӿڵ�
		for(int i=0;i < temp.length;i++){
			for(int j = 0;j < temp[i].length;j++){
				temp[i][j].lp.x = i;
				temp[i][j].lp.y = j;	
				this.add(temp[i][j]);								
			}	
		}
		//���ô��ڵĴ�С
	setSize(400,500);
    setVisible(true);
		
	}
	public static void action(){
			int x1,y1,x2,y2,type1,type2;
			type1 = temp[first_loc.x][first_loc.y].type;
			type2 = temp[second_loc.x][second_loc.y].type;
			if(type1 == type2 && !(temp[first_loc.x][first_loc.y].equals(temp[second_loc.x][second_loc.y]))){		
				System.out.println("mission 1 completed");
				if(check(first_loc,second_loc)){
					temp[first_loc.x][first_loc.y].setVisible(false);//unvisible
					temp[second_loc.x][second_loc.y].setVisible(false);
					temp[first_loc.x][first_loc.y].type = -1;//change the type to -1/empty
					temp[second_loc.x][second_loc.y].type = -1;
				}				
			}
			else{
				System.out.println("hehe");
				//refresh();
			}
			refresh();
	}
	
	//the two picture's location could be disappeared or not
	//�������λ���Ƿ��ܹ�ʵ�ַ���Ҫ�������
	public static boolean check(Location l1,Location l2){

		Picture p1 = new Picture();
		Picture p2 = new Picture();
		Picture exc = new Picture();
		int x1,x2,y1,y2;
		boolean status = false;
		x1 = l1.x;
		x2 = l2.x;
		y1 = l1.y;
		y2 = l2.y;
		p1 = temp[x1][y1];
		p2 = temp[x2][y2];
		realtype = p1.type;
		//p1�Ѿ����ʹ�
		p1.colour = 2;
		p1.state = true;
		search(p1);
		while((exc = link.poll()) != null){
			exc.colour = 2;
			exc.state = true;
	
			if(exc.lp.x == x2 && exc.lp.y == y2){
				//System.out.println("success");
				status = true;
				//printroad(nodes[4][4]);
				break;
			}
			else{
				search(exc);	
			}
		}	
				
		return status;
	}
	public static void search(Picture p){
		int x1 = p.lp.x;
		int y1 = p.lp.y;
		if((x1 < temp.length - 1)&&(temp[x1+1][y1].state == false)&&(temp[x1+1][y1].type == realtype || temp[x1+1][y1].type == -1)){
			temp[x1+1][y1].colour = 1;//��ɫ�����
			temp[x1+1][y1].x0 = x1;
			temp[x1+1][y1].y0 = y1;
			link.offer(temp[x1+1][y1]);	
			System.out.println("1");
		}//�±�һ��
		if((x1 > 0)&&(temp[x1-1][y1].state == false)&&(temp[x1-1][y1].type == realtype || temp[x1-1][y1].type == -1)){
			temp[x1-1][y1].colour = 1;//��ɫ�����
			temp[x1-1][y1].x0 = x1;
			temp[x1-1][y1].y0 = y1;
			link.offer(temp[x1-1][y1]);	
			System.out.println("2");
		}//�ϱ�һ��
		if((y1 > 0)&&(temp[x1][y1-1].state == false)&&(temp[x1][y1-1].type == realtype || temp[x1][y1-1].type == -1)){
			temp[x1][y1-1].colour = 1;//��ɫ�����
			temp[x1][y1-1].x0 = x1;
			temp[x1][y1-1].y0 = y1;
			link.offer(temp[x1][y1-1]);
			System.out.println("3");
		}//���һ��
		if((y1 < temp[0].length - 1)&&(temp[x1][y1+1].state == false)&&(temp[x1][y1+1].type == realtype || temp[x1][y1+1].type == -1)){
			temp[x1][y1+1].colour = 1;//��ɫ�����
			temp[x1][y1+1].x0 = x1;
			temp[x1][y1+1].y0 = y1;
			link.offer(temp[x1][y1+1]);
			System.out.println("4");
		}//���һ��
	}
	public static void refresh(){
		for(int i=0;i< temp.length;i++){
			for(int j=0;j < temp[i].length;j++){
				temp[i][j].colour = 0;
				temp[i][j].state = false;
			}
		}
		while(link.poll() != null);
		System.out.println("5");
	}
	public static void imageinit(){
		images[0] = new ImageIcon("./src/images/500px.png");
		images[1] = new ImageIcon("./src/images/ADN.png");
		images[2] = new ImageIcon("./src/images/Behance.png");
		images[3] = new ImageIcon("./src/images/Facebook.png");
		images[4] = new ImageIcon("./src/images/GitHub.png");
		images[5] = new ImageIcon("./src/images/LastFm.png");
		images[6] = new ImageIcon("./src/images/LinkedIn.png");
		images[7] = new ImageIcon("./src/images/StackOverflow.png");
		images[8] = new ImageIcon("./src/images/Tumblr.png");
		images[9] = new ImageIcon("./src/images/Yelp.png");
	}
}	
//Class Piture shows every button with picture 
class Picture extends JButton implements ActionListener{

	public static int count = 1;//�˴���ض���Ϊһ��static ����
	public String  name;
	public int id;
	//�����㷨����Ϣ
	public int type; 
	public int colour;
	public int x0,y0;
	public boolean state;
	//ͼƬλ������
	Location lp = new Location();
	
	public Picture(int type,ImageIcon icon){
		super(String.valueOf(type),icon);
		this.type = type;
		this.addActionListener(this);
	}
	
	public Picture() {
		
	}//Ĭ�ϵĹ��췽��
	public Picture(int id,int type){
		super(String.valueOf(type));
		this.id = id;
		this.type = type;
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent eve){
		System.out.println(this.lp.x + "," + this.lp.y);
		if(count == 1){
			MainGui.first_loc = lp;
			count++;
		}
		else if(count == 2){
			count = 1;
			MainGui.second_loc = lp;
			MainGui.action();
		}
	
	}
}
class Location{
	public int x;
	public int y;	
	
}

