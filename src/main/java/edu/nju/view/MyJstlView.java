package edu.nju.view;

import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by floyd on 2017/3/22.
 */
public class MyJstlView extends InternalResourceView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 把model对象作为 request attribute 传入
        exposeModelAsRequestAttributes(model, request);

        // Determine the path for the request dispatcher.
        String dispatcherPath = prepareForRendering(request, response);

        // 设置根路径
        request.setAttribute("partial", dispatcherPath);

        // 每一个jsp都走template.jsp
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/common/template.jsp");
        requestDispatcher.include(request, response);

    }
}
