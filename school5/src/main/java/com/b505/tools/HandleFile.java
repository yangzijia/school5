/*
*@包名：com.b505.tools        
*@文档名：HandleFile.java 
*@功能：处理接收到的数据 
*@作者：李振强        
*@创建时间：2014.5.14
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.tools;

import java.io.File;

import org.springframework.stereotype.Component;
@Component
public class HandleFile
{
	
	/*
	 * @方法名：checkAndDeletedFile(String URL)
	 * @功能：检测并删除N天之前的文件
	 * @功能说明：检测并删除N天之前的文件
	 * @作者：李振强
	 * @创建时间：2014.5.14
	 * @修改时间：2014.5.14
	 */
	public static boolean checkAndDeletedFile(String URL) throws Exception
	{
		File file = new File(URL);
		FileUpload fu = new FileUpload();
		boolean status = fu.checkAndDeletedFile(file);
		System.out.println("检测文件并删除N天之前的文件......");
		return status;
	}
}
