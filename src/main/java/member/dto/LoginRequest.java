package member.dto;

public record LoginRequest(
        String loginId,
        String password
) {
}
