package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import dao.WeatherDao;


public class WeatherService {
	WeatherDao weatherdao=null;
	
	public WeatherService() {
		weatherdao=new WeatherDao();
	}

	public List codySet_temp(int cody_temp) {
		return weatherdao.cody(cody_temp);
	}

}


