package net.imyeyu.netdisk.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.imyeyu.netdisk.bean.IOHistory;
import net.imyeyu.netdisk.ctrl.Main;
import net.imyeyu.utils.YeyuUtils;

public class IOHistoryLoader {
	
	private File file = new File("Netdisk.history");;

	public List<IOHistory> get() {
		List<IOHistory> list = new ArrayList<IOHistory>();;
		try {
			String data = file.exists() ? data = YeyuUtils.file().fileToString(file, "UTF-8") : def();
			JsonParser jp = new JsonParser();
			JsonArray ja = (JsonArray) jp.parse(data);
			JsonObject jo;
			for (int i = 0; i < ja.size(); i++) {
				jo = ja.get(i).getAsJsonObject();
				list.add(new IOHistory(jo.get("name").getAsString(), jo.get("path").getAsString(), jo.get("isLocal").getAsBoolean()));
			}
		} catch (JsonSyntaxException e) {
			def();
		}
		return list;
	}
	
	public void set() {
		try {
			YeyuUtils.file().stringToFile(file, new Gson().toJson(Main.getIoHistories()));
		} catch (Exception e) {
			YeyuUtils.file().stringToFile(file, "[]");
		}
	}
	
	private String def() {
		YeyuUtils.file().stringToFile(file, "");
		return "[]";
	}
}