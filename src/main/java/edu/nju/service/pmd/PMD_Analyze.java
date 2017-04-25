package edu.nju.service.pmd;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.dao.GroupDao;
import edu.nju.dao.pmd.PMDDao;
import edu.nju.utils.pmd.AnalyzeThread;
import edu.nju.utils.pmd.Constants;
import edu.nju.utils.pmd.RESCODE;

@Service
public class PMD_Analyze {

	@Autowired
	private PMDDao dao;

	@Autowired
	private GroupDao groupDao;

	private JSONObject resultObj;

	/**
	 * 下载项目到本地
	 */
	public boolean download() {
		// 假设下载时，会获得小组名以及项目名。然后以小组名新建文件夹。将项目放到该文件夹下。假设小组名是唯一的。
		setIter();
		return true;
	}

	/**
	 * 分析项目，以及将相关内容存在数据库
	 */
	public boolean analyze(HttpServletRequest request) {
		int iter = dao.getIter();
		String names[] = getDir("E:/Documents/graduate/project/iter" + iter);// 小组名
		String[] rules = {"basic", "naming", "unusedcode", "codesize", "braces", "coupling"};
		String basePath = request.getServletContext().getRealPath("/") + "pmd-bin-5.5.0/bin";
		try {
			for(int i = 0; i < names.length; i++) {
				String path = "E:\\Documents\\graduate\\project\\iter" + iter + "\\" + names[i];
				String resultFolder = "E:\\Documents\\graduate\\report\\iter" + iter + "\\" + names[i];
				createDir(resultFolder);
				for(int j = 0; j < rules.length; j++) {
					String rulePath = "rulesets/java/" + rules[j] + ".xml";
					String resultPath = resultFolder + "\\" + rules[j] + ".html";
					String command = "cmd /C pmd -d " + path + " -f html -r " + resultPath + " -R " + rulePath;
					Runtime.getRuntime().exec(command, null, new File(basePath));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		AnalyzeThread thread = new AnalyzeThread(dao, names, iter, rules, groupDao);
		thread.start();

		return true;
	}

	public boolean setIter() {
		return dao.setIter();
	}

	public String getIter() {
		resultObj = new JSONObject();
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, dao.getIter());
		return resultObj.toString();
	}

	public boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	public String[] getDir(String strPath) {
		File f = new File(strPath);
		if (f.isDirectory()) {
			File[] fList = f.listFiles();
			String[] names = new String[fList.length];
			for(int j = 0; j < fList.length; j++) {
				names[j] = fList[j].getName();
			}
			return names;
		}
		return null;
	}
}
