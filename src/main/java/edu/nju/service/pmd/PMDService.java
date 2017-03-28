package edu.nju.service.pmd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.dao.pmd.PMDDao;
import edu.nju.entities.pmd.PMD_FileIssues;
import edu.nju.entities.pmd.PMD_Measure;
import edu.nju.utils.pmd.Constants;
import edu.nju.utils.pmd.RESCODE;

@Service
public class PMDService {
	
	@Autowired
	private PMDDao dao;
	
	private JSONObject resultObj;
	
	/**
	 * 获得全部组的相关信息
	 */
	public String getAllGroup(int iter){
		resultObj = new JSONObject();
		List<PMD_Measure> list = dao.getAllGroup(iter);
		if (list == null || list.isEmpty()) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}

		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY,  RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, list);
		return resultObj.toString();
	}
	
	
	/**
	 * @param iter 第几次迭代
	 * @param issueType 问题类型
	 * 获取某一组的问题列表
	 */
	public String getOneGroup(int iter,String issueType,String groupName){
		resultObj = new JSONObject();
		List<PMD_FileIssues> list = dao.getOneGroup(iter,issueType,groupName);
		if (list == null || list.isEmpty()) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}

		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY,  RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, list);
		return resultObj.toString();
	}
	
	/**
	 * @param iter
	 * @param issueType
	 * 导出详细问题
	 */
	public String exportDetail(int iter,String type,String groupName,HttpServletResponse response){
		resultObj = new JSONObject();
		String path = "E:\\Documents\\graduate\\report\\iter"+iter+"\\"+groupName+"\\"+type+".html";
		try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String filenameString = new String(filename.getBytes("gbk"),
                    "iso-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + filenameString);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response
                    .getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
    		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
        } catch (IOException ex) {
            ex.printStackTrace();
        	resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.CREATE_ERROR.getMsg());
        }
        return resultObj.toString();

	}
	
	
	
	/**
	 * @param iter
	 * @param issueType
	 * 获得某一组每个种类问题的数量
	 */
	public String getMeasure(String groupName){
		resultObj = new JSONObject();
		int iter=dao.getIter();
		List<PMD_Measure> list=new ArrayList<PMD_Measure>();
		for(int i=1;i<iter+1;i++){
			PMD_Measure measure=dao.getMeasure(i,groupName);
			list.add(measure);
		}
		if (list == null||list.size()==0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}

		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY,  RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, list);
		return resultObj.toString();
	}

	/**
	 * 获得全部组所有迭代的中位数(有几次迭代就计算几次迭代)
	 */
	public String getAve() {
		resultObj = new JSONObject();
		List<Double[]> list=dao.getAve();
		if (list == null || list.isEmpty()) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}

		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY,  RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, list);
		return resultObj.toString();
	}

	/**
	 * @param groupName
	 * 获得某个组当前的问题数量
	 */
	public String getCurrent(String groupName) {
		resultObj = new JSONObject();
		int iter = dao.getIter();
		PMD_Measure measure = dao.getMeasure(iter,groupName);
		if (measure == null) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}

		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY,  RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, new JSONObject(measure));
		return resultObj.toString();
	}
	
	
}
