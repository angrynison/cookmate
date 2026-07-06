package member.dto;

public class JwtDto {

    String authentication;

    public JwtDto() {}

    public JwtDto(String authentication) {
        this.authentication = authentication;
    }
}
