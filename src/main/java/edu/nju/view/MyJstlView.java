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

        // Determine the path for the request dispatcher.
        String dispatcherPath = prepareForRendering(request, response);

        // set original view being asked for as a request parameter

        request.setAttribute("partial", dispatcherPath);

        // force everything to be template.jsp
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/common/template.jsp");
        requestDispatcher.include(request, response);

    }
}
