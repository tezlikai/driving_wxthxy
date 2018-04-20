package com.wxthxy.driving.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JsonUtil
 * 
 * @author Administrator
 */
public class TC_JsonUtil {

	private TC_JsonUtil() {
		throw new AssertionError();
	}

	public static <T> T Json2Obj(String jsonStr, Class<T> clazz)
			throws Exception {
		if (TextUtils.isEmpty(jsonStr) || null == clazz) {
			return null;
		}
		/** resolve jsonArrayStr error -S **/
		if (jsonStr.indexOf("[") == 0) {
			return null;
		}
		/** resolve jsonArrayStr error -E **/
		Gson gson = new Gson();
		T obj = gson.fromJson(jsonStr, clazz);
		return obj;
	}




	public static <T> List<T> jsonToList(String json, Class<T[]> clazz)
	{
		Gson gson = new Gson();
		T[] array = gson.fromJson(json, clazz);
		return Arrays.asList(array);
	}

	/**
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
	{
		Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
		ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

		ArrayList<T> arrayList = new ArrayList<>();
		for (JsonObject jsonObject : jsonObjects)
		{
			arrayList.add(new Gson().fromJson(jsonObject, clazz));
		}
		return arrayList;
	}




	public static <T> List<T> Json2ObjList(String json, Class<T> clazz)
			throws Exception {
		if (TextUtils.isEmpty(json) || null == clazz) {
			return null;
		}
		ArrayList<T> lcs = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			int idx = json.indexOf("[");
			if (idx < 0) {
				return lcs;
			}
			json = json.substring(idx, json.length() - 1);

			JsonArray jarray = parser.parse(json).getAsJsonArray();
			for (JsonElement obj : jarray) {
				T cse = gson.fromJson(obj, clazz);
				lcs.add(cse);
			}
			return lcs;
		} catch (Exception exp) {
			return lcs;
		}
	}

	public static String Obj2Json(Object obj) throws Exception {
		if (null == obj)
			return "";
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
}
