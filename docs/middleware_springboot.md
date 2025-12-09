
# Middleware trong Spring Boot (Tóm tắt để đọc sau)

---

## 1. HandlerInterceptor – Middleware chính của Spring

Dùng để:

- Kiểm tra đăng nhập (preHandle)
- Chặn request khi không đủ quyền
- Log request
- Redirect khi chưa đăng nhập
- Thêm data chung cho view

**Ví dụ AuthInterceptor:**

```java
@Override
public boolean preHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler) throws Exception {

    Object user = request.getSession().getAttribute("USER");

    if (user == null) {
        response.sendRedirect("/login");
        return false;
    }

    return true;
}
```

**Đăng ký Interceptor trong WebConfig:**

```java
registry.addInterceptor(new AuthInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/login", "/register", "/css/**", "/js/**");
```

---

## 2. ControllerAdvice – Middleware cho toàn bộ View

Dùng để:

- Thêm biến global vào tất cả template Thymeleaf
- Xử lý lỗi toàn hệ thống

**Ví dụ:**

```java
@ModelAttribute("appName")
public String appName() {
    return "Sport Booking System";
}
```

---

## 3. Filter (Servlet Filter) – Middleware cấp thấp

Dùng để:

- Log request
- Chặn IP
- Chỉnh sửa header

**Ví dụ:**

```java
public void doFilter(ServletRequest request,
                     ServletResponse response,
                     FilterChain chain){
    System.out.println("Request đến: " + request.getRemoteAddr());
    chain.doFilter(request, response);
}
```

---

## Kết luận

- **Interceptor** → Middleware chính (giống Express middleware)
- **ControllerAdvice** → Middleware cho tất cả View
- **Filter** → Middleware thấp hơn (ít dùng)
