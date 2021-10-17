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
		
		System.out.print("[항목추가]\n"+ "제목 > ");
		title = sc.next();
		if(l.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		System.out.print("카테고리 > ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("사람 > ");
		people = sc.nextLine().trim();
		
		System.out.print("장소 > ");
		place = sc.nextLine().trim();
		
		System.out.print("완료 여부(yes = 1, no = 0) > ");
		is_completed = sc.nextInt();
		
		System.out.print("마감일자(요일을 적으시면 알람이 올라옵니다)ex:월 > ");
		due_date = sc.next();

		TodoItem t = new TodoItem(title, desc, category, due_date, people, place, is_completed);
		if(l.addItem(t) > 0) {
			System.out.println("추가되었습니다.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭제]\n"+"삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)> 0)
			System.out.println("삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date, people, place;
		int is_completed;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n"+ "수정할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();

		System.out.print("새 제목 > ");
		new_title = sc.next().trim();
		sc.nextLine();		
		System.out.print("새 카테고리 > ");
		new_category = sc.next();
		sc.nextLine();
		System.out.print("새 내용 > ");
		new_desc = sc.nextLine().trim();
		System.out.print("사람 > ");
		people = sc.nextLine().trim();
		System.out.print("장소 > ");
		place = sc.nextLine().trim();
		System.out.print("완료 여부(yes = 1, no = 0) > ");
		is_completed = sc.nextInt();
		sc.nextLine();
		System.out.print("새 마감일자(요일을 적으시면 알람이 올라옵니다)ex:월 > ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date, people, place, is_completed);
		t.setId(num);
		if(l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");

	}
		

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.getList().size()+"개]");
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
		System.out.println("총 "+num+"개의 항목을 찾았습니다.");
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
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
				System.out.println(i+"개의 항목을 읽었습니다.");
				
			}
			else {
				System.out.println("todolist.txt 파일이 없습니다.");
			}
		

	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
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
			week = "일";
		}
		else if(dayofweek == 2) {
			week = "월";
		}
		else if(dayofweek == 3) {
			week = "화";
		}
		else if(dayofweek == 4) {
			week = "수";
		}
		else if(dayofweek == 5) {
			week = "목";
		}
		else if(dayofweek == 6) {
			week = "금";
		}
		else if(dayofweek == 7) {
			week = "토";
		}
		
		System.out.print("반복되는 일정 중 오늘 해야하는 일은 ");
		for (TodoItem item : l.getList()) {
			if(week.equals(item.getDue_date())) {
				System.out.print("\n"+item.toString());
				k+=1;
			}
		}
		if(k == 0) {
			System.out.println("없습니다.");
		}
		else {
			System.out.println("\n이 있습니다.");
		}
		
	}

}

