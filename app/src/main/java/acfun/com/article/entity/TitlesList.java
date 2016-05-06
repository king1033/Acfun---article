package acfun.com.article.entity;

import java.util.List;

/**
 *
 */
public class TitlesList {

    /**
     * success : true
     * msg : 查询成功
     * status : 200
     * data : {"page":{"pageNo":1,"pageSize":10,"totalCount":276063,"orderBy":0,"list":[]}}
     */

    private boolean success;
    private String msg;
    private int status;
    /**
     * page : {"pageNo":1,"pageSize":10,"totalCount":276063,"orderBy":0,"list":[]}
     */

    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * pageNo : 1
         * pageSize : 10
         * totalCount : 276063
         * orderBy : 0
         * list : []
         */

        private Page page;

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }

        public static class Page {
            private int pageNo;
            private int pageSize;
            private int totalCount;
            private int orderBy;
            private List<Title> list;

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(int orderBy) {
                this.orderBy = orderBy;
            }

            public List<Title> getList() {
                return list;
            }

            public void setList(List<Title> list) {
                this.list = list;
            }
        }
    }
}
