package response;

public class RegLogResponse {
    private String token;
    public RegLogResponse(String token){
        this.token=token;
    }
    public String getToken(){
        return token;
    }
}
