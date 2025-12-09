//package com.thliems.sport_booking.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class AuthInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) throws Exception {
//
//        Object user = request.getSession().getAttribute("USER");
//
//        // Nếu chưa login → redirect về trang login
//        if (user == null) {
//            response.sendRedirect("/login");
//            return false; // dừng request tại đây
//        }
//
//        return true; // tiếp tục xử lý controller
//    }
//}
