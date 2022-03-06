package security.domain;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {
    public AccessDeniedException(String msg) {
        super(msg);
    }
}
