package pl.estrix.app.backend.user.model;


public enum Role {
    ROLE_USER, ROLE_ADMIN, ROLE_OPERATOR, ROLE_SUPERUSER
//    ROLE_OPERATOR(1), ROLE_USER(2), ROLE_SUPERUSER(3), ROLE_ADMIN(4);
//
//    private int code;
//
//    Role(int code) {
//        this.code = code;
//    }
//
//    public static Role getByCode(int code) {
//        return Arrays.stream(values()).filter(ll -> ll.getCode() == code).findFirst().orElseThrow(IllegalStateException::new);
//    }
//
//    public int getCode() {
//        return code;
//    }
}
