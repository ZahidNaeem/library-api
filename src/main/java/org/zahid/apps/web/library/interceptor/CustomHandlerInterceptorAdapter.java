package org.zahid.apps.web.library.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CustomHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

//    private static final Logger LOG = LogManager.getLogger(CustomHandlerInterceptorAdapter.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) {
//        LOG.info("----------------------------------------CustomHandlerInterceptorAdapter preHandle called----------------------------------------");
////        try {
////            final BufferedReader reader = request.getReader();
////            final StringBuilder builder = new StringBuilder();
////            String line;
////            while ((line = reader.readLine()) != null) {
////                builder.append(line);
////            }
////            LOG.info("Request Body: {}", builder.toString());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        return true; // return false if you want to abort the execution chain
//    }
}
