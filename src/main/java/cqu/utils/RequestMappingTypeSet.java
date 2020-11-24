package cqu.utils;

import java.util.Objects;

public class RequestMappingTypeSet {
    public String value;
    public RequestMethod method;
    public String consumes="application/json";
    public String produces="application/json";

    public RequestMappingTypeSet(String value, RequestMethod method, String consumes, String produces) {
        this.value = value;
        this.method = method;
        this.consumes = consumes;
        this.produces = produces;
    }

    public RequestMappingTypeSet(String value, RequestMethod method) {
        this.value = value;
        this.method = method;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getConsumes() {
        return consumes;
    }

    public void setConsumes(String consumes) {
        this.consumes = consumes;
    }

    public String getProduces() {
        return produces;
    }

    public void setProduces(String produces) {
        this.produces = produces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestMappingTypeSet)) return false;
        RequestMappingTypeSet that = (RequestMappingTypeSet) o;
        return Objects.equals(getValue(), that.getValue()) &&
                getMethod() == that.getMethod() &&
                Objects.equals(getConsumes(), that.getConsumes()) &&
                Objects.equals(getProduces(), that.getProduces());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getMethod(), getConsumes(), getProduces());
    }
}
