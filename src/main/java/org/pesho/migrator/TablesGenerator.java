package org.pesho.migrator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class TablesGenerator {
	
	class Node {
		int id;
		String label;
		String description;
		int order;
		int parent_id;
	}
	
	public static void main(String[] args) {
		ArrayList<String> ordered = new ArrayList<>();
		ArrayList<Integer> order = new ArrayList<>();
		ordered.addAll(Arrays.asList("������_-1", "noi_0","esenen_0", "zimen_0", "proleten_0", "leten_0",
				"noi1_1", "noi2_1", "noi3_1"));
		order.addAll(Arrays.asList(1, 1, 2, 3, 4, 5,
				1, 2, 3));
		
		int br = -1;
		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < ordered.size(); i++) {
			map.put(ordered.get(i), ++br);
		}
		HashMap<Integer, Integer> children = new HashMap<>();
		children.put(1, 3);
		
		File archived = new File("D:\\infos_archive\\archived");
		File[] files = archived.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				String[] s1 = f1.getName().split("_");
				String[] s2 = f2.getName().split("_");
				for (int i = 0; i < 3; i++) {
					if (!s1[i].equals(s2[i])) {
						if (i != 1) return s1[i].compareTo(s2[i]);
						else return Integer.compare(Integer.valueOf(s1[i]), Integer.valueOf(s2[i]));
					}
				}
				
				return 0;
			}
		});
		
		for (File f: files) {
			String[] split = f.getName().split("_");
			ArrayList<String> levels = new ArrayList<>();
			levels.add(split[0]);
			levels.add(split[1]);
			levels.add(split[2]);

			if (split[0].startsWith("noi")) levels.add(0, "noi");
			
			int parent_id = 0;
			for (String level: levels) {
				String label = level + "_" + parent_id;
				if (!map.containsKey(label)) {
					map.put(label, ++br);
					ordered.add(label);

					int current = 1;
					if (children.containsKey(parent_id)) current += children.get(parent_id);
					children.put(parent_id, current);
					order.add(current);
				}
				parent_id = map.get(label);
			}
		}
		
		for (int i = 0; i < ordered.size(); i++) {
			String[] split = ordered.get(i).split("_");
			if (split[0].equals("noi")) split[0]="���������� ���������";
			if (split[0].equals("esenen")) split[0]="������ ������";
			if (split[0].equals("zimen")) split[0]="����� ������";
			if (split[0].equals("proleten")) split[0]="�������� ������";
			if (split[0].equals("leten")) split[0]="����� ������";
			if (split[0].equals("noi1")) split[0]="�������� ����";
			if (split[0].equals("noi2")) split[0]="�������� ����";
			if (split[0].equals("noi3")) split[0]="���������� ����";
			
			if (split[1].equals("-1")) {
				split[1]="NULL";
			} else {
				split[1] = (Integer.valueOf(split[1])+1)+"";
			}
			
			System.out.println(String.format("%d;%s;%s;%d;%s", 
					i+1, split[0], "", order.get(i), split[1]));
		}
	}

}
