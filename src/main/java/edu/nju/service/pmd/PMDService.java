package edu.nju.service.pmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
		
	}
	
	/**
	 * 分析项目，以及将相关内容存在数据库
	 */
	public void analyze(int iter){
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
		
	}
	
	public void parseHTML(String path){
		//E:\\Documents\\graduate\\report\\iter1\\StockEy\\coupling.html
		 File input = new File(path); 
		 try {
			Document doc = Jsoup.parse(input,"UTF-8","http://www.oschina.net/");
			 Elements links = doc.select("a[href]"); // 具有 href 属性的链接
			 ArrayList<String> qArr = new ArrayList<String>();
			for(int i=0;i<links.size();i++){
				PMD_FileIssues issues = new PMD_FileIssues();
				String ques = links.get(i).text();
				if(!qArr.contains(ques)){
					qArr.add(ques);
				}
			}
			System.out.println(qArr.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	public void getOneGroup(int iter,String issueType){
		
	}
	
	/**
	 * @param iter
	 * @param issueType
	 * 导出详细问题
	 */
	public void exportDetail(int iter,String issueType){
		
	}
	
	/**
	 * @param iter
	 * @param issueType
	 * 获得每个种类问题的数量
	 */
	public void getSum(int iter,String issueType){
		
	}
	
}
