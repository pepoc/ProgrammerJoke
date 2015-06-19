package com.pepoc.programmerjoke.net.http.request;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pepoc.programmerjoke.net.http.HttpRequest;
import com.pepoc.programmerjoke.net.http.HttpRequestManager.OnHttpResponseListener;

public class RequestListContent extends HttpRequest {
	
	public RequestListContent(OnHttpResponseListener onHttpResponseListener) {
		this.URL = "get_goods.php";
		this.onHttpResponseListener = onHttpResponseListener;
	}

	@Override
	public Object parseResponseResult(String result) {
		List<String> datas = new ArrayList<String>();
		try {
			JSONObject obj = new JSONObject(result);
			JSONArray goodsLists = obj.getJSONArray("goodsList");
			for (int i = 0; i < goodsLists.length(); i++) {
				JSONObject goodsList = goodsLists.getJSONObject(i);
				String goodsName = goodsList.getString("goodsName");
				datas.add(goodsName);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return datas;
	}

}
