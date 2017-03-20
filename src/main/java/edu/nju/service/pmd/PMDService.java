package edu.nju.service.pmd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.dao.pmd.PMDDao;
import edu.nju.entities.pmd.PMD_FileIssues;
import edu.nju.entities.pmd.PMD_Measure;

@Service
public class PMDService {
	
	@Autowired
	private PMDDao dao;
	
	/**
	 * 下载项目到本地
	 */
	public void download(){
		setIter();
	}
	
	/**
	 * 分析项目，以及将相关内容存在数据库
	 */
	public void analyze(){
		int iter=getIter();
		String names[] = getDir("E:/Documents/graduate/project/iter"+iter);
		String []rules = {"basic","naming","unusedcode","codesize","clone","coupling"};
//		try {
//			for(int i=0;i<names.length;i++){
//				String path = "E:\\Documents\\graduate\\project\\iter"+iter+"\\" + names[i];
//				String resultFolder = "E:\\Documents\\graduate\\report\\iter"+iter+"\\"+names[i];
//				createDir(resultFolder);
//				for(int j=0;j<rules.length;j++){
//					String rulePath = "rulesets/java/"+rules[j]+".xml";
//					String resultPath = resultFolder+"\\"+rules[j]+".html";
//					String command = "cmd /C pmd -d "+path+" -f html -r "+resultPath+" -R "+rulePath;
//					Runtime.getRuntime().exec(command,null,new File("E://Documents//graduate//pmd-bin-5.5.0//pmd-bin-5.5.0//bin"));
//					
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		for(int i=0;i<names.length;i++){
//			PMD_Measure measure= new PMD_Measure();
//			measure.setGroupName(names[i]);
//			measure.setIter(iter);
//			String resultFolder = "E:\\Documents\\graduate\\report\\iter"+iter+"\\"+names[i];
//			String resultPath = "file:///"+resultFolder+"\\basic.html";
//			measure.setBasic(readHtml(resultPath));
//			resultPath="file:///"+resultFolder+"\\naming.html";
//			measure.setNaming(readHtml(resultPath));
//			resultPath="file:///"+resultFolder+"\\unusedcode.html";
//			measure.setUnusedcode(readHtml(resultPath));
//			resultPath="file:///"+resultFolder+"\\codesize.html";
//			measure.setCodesize(readHtml(resultPath));
//			resultPath="file:///"+resultFolder+"\\clone.html";
//			measure.setClone(readHtml(resultPath));
//			resultPath="file:///"+resultFolder+"\\coupling.html";
//			measure.setCoupling(readHtml(resultPath));
//			dao.saveIssueNum(measure);
//		}
		for(int i=0;i<names.length;i++){
			for(int j=0;j<rules.length;j++){
				parseHTML(1,names[i],rules[j]);
			}
		}
		
	}
	
	public boolean setIter(){
		return dao.setIter();
	}
	
	public int getIter(){
		return dao.getIter();
	}
	
	public void parseHTML(int iter,String name,String type){
		//E:\\Documents\\graduate\\report\\iter1\\StockEy\\coupling.html
		String path = "E:\\Documents\\graduate\\report\\iter"+iter+"\\"+name+"\\"+type+".html";
		 File input = new File(path); 
		 try {
			Document doc = Jsoup.parse(input,"UTF-8","http://www.oschina.net/");
			Elements trs = doc.select("tr");
			 ArrayList<String> qArr = new ArrayList<String>();
			for(int i=1;i<trs.size();i++){
				String info=trs.get(i).select("td>a").text();
				if(!qArr.contains(info)){
					PMD_FileIssues issues = new PMD_FileIssues();
					qArr.add(info);
					String link=trs.get(i).select("td>a").attr("href");
					String filePath=trs.get(i).select("td").get(1).text();
					int line = Integer.parseInt(trs.get(i).select("td").get(2).text());
					issues.setFilePath(filePath);
					issues.setIter(iter);
					issues.setLine(line);
					issues.setProblem(info);
					issues.setLink(link);
					issues.setIssueType(type);
					issues.setGroupName(name);
					dao.saveFileIssues(issues);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	  public boolean createDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (dir.exists()) {  
	            return false;  
	        }  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	        //创建目录  
	        if (dir.mkdirs()) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }  
	
	public int readHtml(String filePath){
        try {
        	URL url = new URL(filePath);
			url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(url
					.openStream()));
			String line = null;
			int i=0;
			while ((line = br.readLine()) != null) {
				if(line.contains("<tr")){
					i++;
				}
			}
			i=i-1;
			return i;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}
	
	public String[] getDir(String strPath){
		File f=new File(strPath); 
		if(f.isDirectory()) 
		{ 
			File[] fList=f.listFiles();
			String[] names=new String[fList.length];
			for(int j=0;j<fList.length;j++) {
				names[j]=fList[j].getName();
			}
			return names;
		}  
		return null;
	}
	
	/**
	 * 获得全部组的相关信息
	 */
	public List<PMD_Measure> getAllGroup(int iter){
		List<PMD_Measure> list = dao.getAllGroup(iter);
		return list;
	}
	
	
	/**
	 * @param iter 第几次迭代
	 * @param issueType 问题类型
	 * 获取某一组的问题列表
	 */
	public List<PMD_FileIssues> getOneGroup(int iter,String issueType,String groupName){
		List<PMD_FileIssues> list = dao.getOneGroup(iter,issueType,groupName);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).toString());
		}
		return list;
	}
	
	/**
	 * @param iter
	 * @param issueType
	 * 导出详细问题
	 */
	public void exportDetail(int iter,String type,String groupName,HttpServletResponse resp){
		String path = "E:\\Documents\\graduate\\report\\iter"+iter+"\\"+groupName+"\\"+type+".html";
		commonDownload(path,resp);
	}
	
	public void commonDownload(String path,HttpServletResponse response){
	        try {
	            // path是指欲下载的文件的路径。
	            File file = new File(path);
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
//	            String ext = filename.substring(filename.lastIndexOf(".") + 1)
//	                    .toUpperCase();

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
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	}
	
	
	/**
	 * @param iter
	 * @param issueType
	 * 获得某一组每个种类问题的数量
	 */
	public PMD_Measure getMeasure(int iter,String groupName){
		PMD_Measure measure=dao.getMeasure(iter,groupName);
		return measure;
	}

	/**
	 * 获得全部组所有迭代的中位数(有几次迭代就计算几次迭代)
	 */
	public ArrayList<Double[]> getAve() {
		ArrayList<Double[]> list=dao.getAve();
		System.out.println(list.size()+list.get(0).length);
		return list;
	}

	/**
	 * @param groupName
	 * 获得某个组当前的问题数量
	 */
	public PMD_Measure getCurrent(String groupName) {
		int iter = getIter();
		PMD_Measure measure = getMeasure(iter,groupName);
		return measure;
	}
	
	
}
