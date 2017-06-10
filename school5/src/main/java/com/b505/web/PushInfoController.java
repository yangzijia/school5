/**
 * 推送功能接口
 * @author 杨子佳
 * @date 2017-04-25
 */
package com.b505.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.AppPushInfo;
import com.b505.bean.LoginUser;
import com.b505.bean.MsgToPush;
import com.b505.json.JsonAnalyze;
import com.b505.json.UserJsonAnalyze;
import com.b505.tools.SessionGet;
import com.b505.tools.TryCatchUserService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

@Controller
public class PushInfoController {
	
	@Autowired
	private UserJsonAnalyze userJsonAnalyze;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private SessionGet sessionGet;
	
	private static String titles;
	private static String texts;
	private static String sendtypes;
	private JsonAnalyze jsonAnalyze = new JsonAnalyze();
	
	
	//采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	private	static String appId = "Kk3twfpwDDA8iyJiSzOS2A";  
	private	static String appKey = "t2SbpKlIyv7UDYYN29SSp8";  
	private static String masterSecret = "hkBTvjbQwx7RBh2ga2udB9";  
	private	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	@RequestMapping(value = "/testapppush.html")
	public String testapppush()throws Exception{
		return "testapppush";
	}
	
	@RequestMapping(value = "/apppushinfo.html")
	@ResponseBody
	public String PushInfoToApp(@RequestBody String requestJsonBody)throws Exception{
		//解析传来的json数据
		Map<String,Object> map = new HashMap<String,Object>();
		map = jsonAnalyze.json2Map(requestJsonBody);
		String result = "error_NoUser";
		titles = map.get("title").toString();
		texts = map.get("text").toString();
		sendtypes = map.get("sendtype").toString();      //推送的类型
		
		Map<String, String> msg = new HashMap<>();  
        msg.put("title", titles);  
        msg.put("titleText", texts);  
        //透传参数
        msg.put("transText", "这是一条透传参数");
        msg.put("url", "http://getui.com");
		
        List<LoginUser> users = null;
        
		
		if(sendtypes.equals("LeaderAll")){
			//领导推所有人
			result = toApp(titles, texts);
		}else if(sendtypes.equals("LeaderAllStudent")){
			//领导推所有学生（学生，班委）
			users = tryCatchUserService.getUserByrole("Role_Student","Role_ClassAdmin");
		}else if(sendtypes.equals("LeaderAllHeadTeacher")){
			//领导推所有辅导员（老师，辅导员）
			users = tryCatchUserService.getUserByrole("Role_HeadTeacher","Role_Teacher");
		}else if(sendtypes.equals("LeaderAllLeader")){
			//领导推所有领导（领导，学生科，学院教学科）
			users = tryCatchUserService.getUserByrole("Role_Leader","Role_StudentAdmin","Role_CollegeAdmin");
		}
		
		//根据clientid推送消息
		if(users!=null){
			//String [] cids = new String[users.size()];
			ArrayList<MsgToPush> als = new ArrayList<MsgToPush>();
			for(int i=0;i<users.size();i++){
				LoginUser us = users.get(i);
				//cids[i] = us.getClientid();
				MsgToPush mp = new MsgToPush();
				mp.setCid(us.getClientid());
				mp.setGeter(us.getNickName());
				als.add(mp);
			}
			for(MsgToPush al : als) {  
				System.out.println("正在发送消息。。。");  
				IPushResult ret = pushMsgToSingle(al.getCid(), msg,al.getGeter());  
				result = ret.getResponse().get("result").toString();
				System.out.println(ret.getResponse().toString());  
			}
		}
		
		Map<String,Object> resultmap = new HashMap<String,Object>();
		resultmap.put("status", result);
	    return jsonAnalyze.map2Json(resultmap);
	}
	
    /**
     * 苹果手机推送消息
     * @param ios_title
     * @param ios_text
     * @return
     */
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String ios_title,String ios_text){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(ios_text);
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("LocKey");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(ios_title);
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }
	
	/**
	 * 客户端端给所有用户推送
	 * 这个方法不需要clientid
	 */
	public String toApp(String an_title,String an_text) throws Exception {
		
		//saveToAppPush(an_title,an_text,"null");
		
		IGtPush push = new IGtPush(host, appKey, masterSecret);

        NotificationTemplate template = NotificationTemplateDemo(an_title, an_text);
        
        
        AppMessage message = new AppMessage();
        message.setData(template);

        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions(); 
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        //手机类型
        List<String> phoneTypeList = new ArrayList<String>();
        //省份
        List<String> provinceList = new ArrayList<String>();
        //自定义tag
        List<String> tagList = new ArrayList<String>();

        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG,tagList);
        message.setConditions(cdt); 
        IPushResult ret = null;
        ret = push.pushMessageToApp(message,"任务别名_toApp");
        String result = "";
        if(ret != null){
        	System.out.println(ret.getResponse().toString());
        	result = ret.getResponse().get("result").toString();
        }else{
        	System.out.println("服务器响应异常");
        	result = "服务器响应异常";
        }
        /*Map<String,Object> map = new HashMap<String,Object>();
        map.put("status", result);*/
		return result;
	}
	
	/** 
     * 3. 要传送到客户端的 msg 
     * 3.1 标题栏：key = title,  
     * 3.2 通知栏内容： key = titleText, 
     * 3.3 穿透内容：key = transText  
     */  
    @SuppressWarnings("deprecation")
	private static NotificationTemplate getNotifacationTemplate(Map<String, String> msg){  
        // 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用  
        NotificationTemplate template = new NotificationTemplate();  
        // 设置appid，appkey  
        template.setAppId(appId);  
        template.setAppkey(appKey);  
        // 穿透消息设置为，1 强制启动应用  
        template.setTransmissionType(1);  
        // 设置穿透内容  
        System.out.println(msg.get("title") + "::" + msg.get("titleText") + "::" + msg.get("transText"));  
        template.setTransmissionContent(msg.get("transText"));  
        
        /*
	     * 苹果手机推送传送的消息 
	     */
	    APNPayload apl = new APNPayload();
	    //应用上icon上显示的数字
	    apl.setBadge(1);
	    //推送直接带有透传数据
	    apl.setContentAvailable(1);
	    //通知消息体
	    //简单模式APNPayload.SimpleMsg
	    //apl.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
	    
	    //字典模式使用APNPayload.DictionaryAlertMsg
	    apl.setAlertMsg(getDictionaryAlertMsg(msg.get("titleText"),msg.get("transText")));
	    
	    //通知铃声文件名
	    //apl.setSound("default");
	    //在客户端通知栏触发特定的action和button显示
	    //apl.setCategory("$由客户端定义");
	    
	    template.setAPNInfo(apl);
        
        // 设置style  
        Style0 style = new Style0();  
        // 设置通知栏标题和内容  
        style.setTitle(msg.get("title"));  
        style.setText(msg.get("titleText"));  
        // 设置通知，响铃、震动、可清除  
        style.setRing(true);  
        style.setVibrate(true);  
        style.setClearable(true);  
        // 设置  
        template.setStyle(style);  
          
        return template;  
    }  

    /**
     * 对单个用户推送消息  
     * 1. cid 
     * 2. 要传到客户端的 msg 
     * 2.1 标题栏：key = title,  
     * 2.2 通知栏内容： key = titleText, 
     * 2.3 穿透内容：key = transText  
     */  
    public IPushResult pushMsgToSingle(String cid, Map<String, String> msg, String geter_sno) throws Exception {  
    	
    	saveToAppPush(msg.get("title"),msg.get("titleText"),geter_sno);
    	
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求  
        IGtPush push = new IGtPush(appKey, masterSecret);  
        // 创建信息模板  
        NotificationTemplate template = getNotifacationTemplate(msg);  
        //定义消息推送方式为，单推  
        SingleMessage message = new SingleMessage();  
        // 设置推送消息的内容  
        message.setData(template);  
        // 设置推送目标  
        Target target = new Target();  
        target.setAppId(appId);  
        // 设置cid  
        target.setClientId(cid);  
        // 获得推送结果  
        IPushResult result = push.pushMessageToSingle(message, target);  
        /* 
         * 1. 失败：{result=sign_error} 
         * 2. 成功：{result=ok, taskId=OSS-0212_1b7578259b74972b2bba556bb12a9f9a, status=successed_online} 
         * 3. 异常 
         */  
        return result;  
    }  
	
	/**
	 * （全部推送）
	 * @param an_title
	 * @param an_text
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("deprecation")
	public NotificationTemplate NotificationTemplateDemo(String an_title, String an_text) throws Exception{
    	
    	this.saveToAppPush(an_title,an_text,"allusers");
    	
    	//在android端推送消息保存到数据库
    	
    	NotificationTemplate template = new NotificationTemplate();
    	template.setAppId(appId);
    	template.setAppkey(appKey);
    	template.setTitle(an_title);
    	template.setText(an_text);
    	template.setLogo("logo1.png");
        template.setLogoUrl("/images/push/logo1.png");
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    
	    /*
	     * 苹果手机推送传送的消息 
	     */
	    APNPayload apl = new APNPayload();
	    //应用上icon上显示的数字
	    apl.setBadge(1);
	    //推送直接带有透传数据
	    apl.setContentAvailable(1);
	    //通知消息体
	    //简单模式APNPayload.SimpleMsg
	    //apl.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
	    
	    //字典模式使用APNPayload.DictionaryAlertMsg
	    apl.setAlertMsg(getDictionaryAlertMsg(an_title,an_text));
	    
	    //通知铃声文件名
	    //apl.setSound("default");
	    //在客户端通知栏触发特定的action和button显示
	    //apl.setCategory("$由客户端定义");
	    
	    template.setAPNInfo(apl);
	    
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(1);
	    template.setTransmissionContent("需要透传的内容哦");
	    return template;
    }
    
    
    
    /**
     * 将推送的消息保存到数据库
     * @param sa_title
     * @param sa_text
     * @throws Exception
     */
    public void saveToAppPush(String sa_title, String sa_text, String sa_geter_no)throws Exception{
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date  currentTime = new Date();
		String strDate = formatter.format(currentTime);
    	
		String studentNickname = sessionGet.getUserInfo().getUsername();
		//根据studentNickname 查询出推送人的真实姓名
		studentNickname = tryCatchUserService.getNameBysa_geter_no(studentNickname);
		
    	//将推送的消息存入数据库
    	AppPushInfo api = new AppPushInfo();
    	api.setPush_text(sa_title);
    	api.setPush_title(sa_text);
    	api.setPush_sender(studentNickname);
    	api.setPush_time(strDate);
    	api.setPush_geter(sa_geter_no);
    	tryCatchUserService.savepushinfo(api);
    }
    
    //@RequestBody String requestJsonBody
    /**
     * 返回给客户端根据userNickname查询出接受到的推送消息。
     * @return
     * @throws Exception
     * @author yangzijia
     * @date 2017-04-30
     */
    @RequestMapping(value = "/getpushinfos.html")
	@ResponseBody
    public String getPushinfoByNickname() throws Exception{
    	String userNickname = sessionGet.getUserInfo().getUsername();
    	//根据userNickname查询出接受到的推送消息。
    	List<AppPushInfo> api = tryCatchUserService.findPushInfosByPush_geter(userNickname);
    	JSONArray jsona = new JSONArray();
    	if(api!=null){
    		for(int i=0;i<api.size();i++){
        		jsona.add(i, api.get(i));
        	}
    	}
    	JSONObject json = new JSONObject();
    	json.put("pushinfos", jsona);
    	System.out.println(json.toString());
    	return json.toString();
    }
    
    /**
     * 根据客户端传来的id删除推送消息中的信息
     * @param requestJsonBody
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deletepushinfos.html")
   	@ResponseBody
    public String deletePushinfoByid(@RequestBody String requestJsonBody) throws Exception{
    	//解析传来的json数据
		Map<String,Object> map = new HashMap<String,Object>();
		map = jsonAnalyze.json2Map(requestJsonBody);
		int push_id = Integer.parseInt(map.get("push_id").toString());
    	String status = "error";
    	tryCatchUserService.deletePushinfosByid(push_id);
    	status = "success";
    	
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	returnMap.put("status", status);
    	return jsonAnalyze.map2Json(returnMap);
    }
	
}
