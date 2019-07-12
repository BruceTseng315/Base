package com.kevin.config;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-08-14
 * Time: 13:16
 */
public class PageInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5469640246859168627L;

    private Integer totalCount;
    private Integer pageTotal;
    private Integer pageNo;
    private Integer pageSize;

    public PageInfo() {
        this(1, 10);
    }

    public PageInfo(Integer pageNo, Integer pageSize) {
        this(null, 0, pageNo, pageSize);
    }

    public PageInfo(Integer totalCount, Integer pageTotal, Integer pageNo, Integer pageSize) {
        super();
        this.totalCount = totalCount;
        this.pageTotal = pageTotal;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;

        if (totalCount > 0) {
            int count = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                count++;
            }
            this.pageTotal=count;
        }

    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Pageinfo [totalCount=" + totalCount + ", pageTotal=" + pageTotal + ", pageNo=" + pageNo + ", pageSize="
                + pageSize + "]";
    }
}
