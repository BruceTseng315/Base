package com.kevin.model.request;

public class AntitrashRequest {
    private String query;
    private String domain;
    private Boolean flag;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "AntitrashRequest{" +
                "query='" + query + '\'' +
                ", domain='" + domain + '\'' +
                ", flag=" + flag +
                '}';
    }
}
