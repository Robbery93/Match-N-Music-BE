package nl.robbertij.matchnmusic.dto.response;

public class AuthenticationResponseDto {

    private final String jwt;

    public AuthenticationResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
