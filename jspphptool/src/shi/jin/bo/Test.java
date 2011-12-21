/**
 * 
 */
package shi.jin.bo;

import com.jspphp.tools.*;
import java.util.*;

/**
 * @author 史金波
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Map
		 */
		// Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "史金波1");
		map.put("2", "史金波2");
		map.put("3", "史金波3");
		map.put("4", "史金波4");
		map.put("5", "史金波5");
		System.out.println(JsonUtils.mapToJson(map));
		/*
		 * List
		 */
		List<String> list = new ArrayList<String>();
		list.add("史金波1");
		list.add("史金波2");
		list.add("史金波3");
		list.add("史金波4");

		System.out.println(JsonUtils.listToJson(list));

	}
}
