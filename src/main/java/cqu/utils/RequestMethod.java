package cqu.utils;

public enum RequestMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private RequestMethod() {
    }
    public static RequestMethod transferType(String s){
        switch(s){
            case "GET": return GET;
            case "HEAD": return HEAD;
            case "POST": return POST;
            case "PUT": return PUT;
            case "PATCH": return PATCH;
            case "DELETE": return DELETE;
            case "OPTIONS": return OPTIONS;
            case "TRACE": return TRACE;
            default:return null;
        }
    }
}
