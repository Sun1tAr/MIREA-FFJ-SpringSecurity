package my.learn.mireaffjspringsecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.learn.mireaffjspringsecurity.dto.ErrorResponseDTO;
import my.learn.mireaffjspringsecurity.entity.UserEntity;
import my.learn.mireaffjspringsecurity.service.AuthService;
import my.learn.mireaffjspringsecurity.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final ObjectMapper mapper;
    private final JwtService jwtService;

    private final String HEADER_AUTH = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        // если нет заголовка авторизации - скипаем
        if (request.getHeader(HEADER_AUTH) == null ||
                !request.getHeader(HEADER_AUTH).startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(HEADER_AUTH).substring(7);

        // если пользователь в контекст не добавлен - пробуем добавить
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication auth;
            if (!jwtService.tokenIsValid(token)) {
                putErrorToResponseBody(response, HttpStatus.UNAUTHORIZED, "INVALID_JWT");
                return;
            }

            UserEntity user;
            try {
                user = authService.loadUserByUsername(jwtService.getLoginFromJwt(token));
            } catch (UsernameNotFoundException e) {
                putErrorToResponseBody(response, HttpStatus.UNAUTHORIZED, "USER_NOT_FOUND");
                return;
            }

            auth = new UsernamePasswordAuthenticationToken(
                    user.getLogin(),
                    token,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    /** Добавляет тело с описанием проблемы в Http response
     * @param response сущность ответа
     * @param status статус ответа
     * @param message описание проблемы
     */
    private void putErrorToResponseBody(HttpServletResponse response, HttpStatus status, String message) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse;
        jsonResponse = mapper.writeValueAsString(new ErrorResponseDTO(message));

        try {
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("Не удалось обновить тело ответа: {}", e.getMessage());
        }
    }

}
