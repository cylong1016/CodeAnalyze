package edu.nju.utils.pmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.nju.dao.GroupDao;
import edu.nju.dao.pmd.PMDDao;
import edu.nju.entities.StudentGroup;
import edu.nju.entities.StudentScore;
import edu.nju.entities.pmd.PMD_FileIssues;
import edu.nju.entities.pmd.PMD_Measure;

public class AnalyzeThread  extends Thread {
	private PMDDao dao;
	private String names[];
	private int iter;
	private String rules[];
	private GroupDao groupDao;
	private boolean running;
	
	public AnalyzeThread(PMDDao dao,String names[],int iter,String[] rules,GroupDao groupDao){
		this.dao=dao;
		this.names=names;
		this.iter=iter;
		this.rules=rules;
		this.groupDao=groupDao;
		running=true;
	}
	
	public void run() {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < names.length; i++) {
					map.clear();
					map.put("groupName", names[i]);
					List<StudentGroup> groupList = groupDao.getGroupByQuery(map);
					if (groupList.size() > 0) {
						long groupId = groupList.get(0).getId();//一个组名对应一组
						PMD_Measure measure = new PMD_Measure();
						measure.setGroupName(names[i]);
						measure.setGroupId(groupId);
						measure.setIter(iter);
						String resultFolder = "E:\\Documents\\graduate\\report\\iter" + iter + "\\" + names[i];
						String resultPath = resultFolder + "\\basic.html";
						int basic=readHtml(resultPath);
						measure.setBasic(basic);
						resultPath =  resultFolder + "\\naming.html";
						int naming=readHtml(resultPath);
						measure.setNaming(naming);
						resultPath =  resultFolder + "\\unusedcode.html";
						int unusedcode=readHtml(resultPath);
						measure.setUnusedcode(unusedcode);
						resultPath =  resultFolder + "\\codesize.html";
						int codesize=readHtml(resultPath);
						measure.setCodesize(codesize);
						resultPath = resultFolder + "\\braces.html";
						int braces=readHtml(resultPath);
						measure.setBraces(braces);
						resultPath =  resultFolder + "\\coupling.html";
						int coupling=readHtml(resultPath);
						measure.setCoupling(coupling);
						StudentScore s_score=new StudentScore();
						double d_score=(double)basic/10+(double)naming/100+(double)unusedcode/20+
								(double)codesize/15+(double)braces/25+(double)coupling/110;
						d_score=100-d_score;
						s_score.setCheckId(iter);
						s_score.setGroupId(groupId);
						s_score.setToolName("pmd");
						s_score.setScore((int)d_score);
						dao.savePMDScore(s_score);
						dao.saveIssueNum(measure);
					}
				}
				for (int i = 0; i < names.length; i++) {
					for (int j = 0; j < rules.length; j++) {
						parseHTML(iter, names[i], rules[j]);
					}
				}
				System.out.println("over");
			} catch (Exception e) {
			}
	}
	
	public void parseHTML(int iter, String name, String type) {
		String path = "E:\\Documents\\graduate\\report\\iter" + iter + "\\" + name + "\\" + type + ".html";
		File input = new File(path);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupName", name);
		List<StudentGroup> groupList = groupDao.getGroupByQuery(map);
		if (groupList.size() > 0) {
			try {
				Document doc = Jsoup.parse(input, "UTF-8", "http://www.oschina.net/");
				Elements trs = doc.select("tr");
				ArrayList<String> qArr = new ArrayList<String>();
				for (int i = 1; i < trs.size(); i++) {
					String info = trs.get(i).select("td>a").text();
					if (!qArr.contains(info)) {
						PMD_FileIssues issues = new PMD_FileIssues();
						qArr.add(info);
						String link = trs.get(i).select("td>a").attr("href");
						String filePath = trs.get(i).select("td").get(1).text();
						filePath = filePath.replace("E:\\Documents\\graduate\\project\\iter" + iter + "\\", "");
						int line = Integer.parseInt(trs.get(i).select("td").get(2).text());
						issues.setFilePath(filePath);
						issues.setIter(iter);
						issues.setLine(line);
						issues.setProblem(info);
						issues.setLink(link);
						issues.setIssueType(type);
						StudentGroup groupDetail = groupList.get(0);
						long groupId = groupDetail.getId();
						issues.setGroupName(name);
						issues.setGroupId(groupId);
						dao.saveFileIssues(issues);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int readHtml(String filePath) {
		running = true;
		while(running){
			try {
				File file=new File(filePath);
				System.out.println(file.exists()+" "+file.renameTo(file));
				if(file.exists()&&file.renameTo(file)){
					URL url = new URL("file:///" + filePath);
					url.openConnection();
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
					String line = null;
					int i = 0;
					while ((line = br.readLine()) != null) {
						if (line.contains("<tr")) {
							i++;
						}
					}
					i = i - 1;
					running = false;
					return i;
				}else{
					Thread.sleep(500);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
