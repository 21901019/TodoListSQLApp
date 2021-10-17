package com.todo.service;

import java.util.*;
import java.io.*;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date, place, people;
		int is_completed;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸��߰�]\n"+ "���� > ");
		title = sc.next();
		if(l.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("ī�װ� > ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		
		System.out.print("��� > ");
		people = sc.nextLine().trim();
		
		System.out.print("��� > ");
		place = sc.nextLine().trim();
		
		System.out.print("�Ϸ� ����(yes = 1, no = 0) > ");
		is_completed = sc.nextInt();
		
		System.out.print("��������(������ �����ø� �˶��� �ö�ɴϴ�)ex:�� > ");
		due_date = sc.next();

		TodoItem t = new TodoItem(title, desc, category, due_date, people, place, is_completed);
		if(l.addItem(t) > 0) {
			System.out.println("�߰��Ǿ����ϴ�.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� ����]\n"+"������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)> 0)
			System.out.println("�����Ǿ����ϴ�.");
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date, people, place;
		int is_completed;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int num = sc.nextInt();

		System.out.print("�� ���� > ");
		new_title = sc.next().trim();
		sc.nextLine();		
		System.out.print("�� ī�װ� > ");
		new_category = sc.next();
		sc.nextLine();
		System.out.print("�� ���� > ");
		new_desc = sc.nextLine().trim();
		System.out.print("��� > ");
		people = sc.nextLine().trim();
		System.out.print("��� > ");
		place = sc.nextLine().trim();
		System.out.print("�Ϸ� ����(yes = 1, no = 0) > ");
		is_completed = sc.nextInt();
		sc.nextLine();
		System.out.print("�� ��������(������ �����ø� �˶��� �ö�ɴϴ�)ex:�� > ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date, people, place, is_completed);
		t.setId(num);
		if(l.updateItem(t) > 0)
			System.out.println("�����Ǿ����ϴ�.");

	}
		

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+l.getList().size()+"��]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void find(TodoList l, String key) {
		int i = 1;
		int num = 0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(key)) {
				System.out.println(i+". "+item);
				num++;
			}
			else if(item.getDesc().contains(key)) {
				System.out.println(i+". "+item);
				num++;
			}
			i++;
		}
		System.out.println("�� "+num+"���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", count);
	}
	
	public static void saveList(TodoList l, String filename) {
			try {
				FileWriter fw = new FileWriter(filename);
				for(TodoItem item : l.getList()) {
					fw.write(item.toSaveString());
					fw.flush();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		
	}
	
	public static void loadList(TodoList l, String filename) throws IOException {
			File f = new File(filename);
			if(f.exists()) {
				int i = 0;
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String fr;
				
				while((fr = br.readLine())!= null){
					StringTokenizer st = new StringTokenizer(fr, "##");
					String category = st.nextToken();
					int is_completed = Integer.parseInt(st.nextToken());
					String title = st.nextToken();
					String desc = st.nextToken();
					String people = st.nextToken();
					String place = st.nextToken();
					String due_date = st.nextToken();
					String current_date = st.nextToken();
					TodoItem t = new TodoItem(title, desc, category, due_date, current_date, people, place, is_completed);
					l.addItem(t);
					i += 1;	
				}
				System.out.println(i+"���� �׸��� �о����ϴ�.");
				
			}
			else {
				System.out.println("todolist.txt ������ �����ϴ�.");
			}
		

	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}

	public static void checkday(TodoList l) {
		int k = 0;
		Calendar cal = Calendar.getInstance();
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		String week = "";
		if(dayofweek == 1) {
			week = "��";
		}
		else if(dayofweek == 2) {
			week = "��";
		}
		else if(dayofweek == 3) {
			week = "ȭ";
		}
		else if(dayofweek == 4) {
			week = "��";
		}
		else if(dayofweek == 5) {
			week = "��";
		}
		else if(dayofweek == 6) {
			week = "��";
		}
		else if(dayofweek == 7) {
			week = "��";
		}
		
		System.out.print("�ݺ��Ǵ� ���� �� ���� �ؾ��ϴ� ���� ");
		for (TodoItem item : l.getList()) {
			if(week.equals(item.getDue_date())) {
				System.out.print("\n"+item.toString());
				k+=1;
			}
		}
		if(k == 0) {
			System.out.println("�����ϴ�.");
		}
		else {
			System.out.println("\n�� �ֽ��ϴ�.");
		}
		
	}

}

