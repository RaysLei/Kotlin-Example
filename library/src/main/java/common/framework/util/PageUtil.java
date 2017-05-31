package common.framework.util;

public class PageUtil {
	private int page = 0;
	private int currentPage = 1;
	private int pageSize = 6;

	public PageUtil() {
	}

	public PageUtil(int pageSize) {
		this.pageSize = pageSize;
	}

    /**
     * 第一页
     * @return
     */
    public int getFirstPage() {
        page = 1;
        currentPage = 1;
        return currentPage;
    }

    /**
     * 上一页
     * @return
     */
    public int getPrewPage() {
        currentPage = page - 1;
        return currentPage;
    }

    /**
     * 下一页
     * @return
     */
    public int getNextPage() {
        currentPage = page + 1;
        return currentPage;
    }

    /**
     * 获取数据成功时调用
     */
    public void skipSuccess() {
        page = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int page) {
        this.page = page;
        currentPage = page;
    }

    /**
     * 是否为第一页
     * @return
     */
    public boolean isFirstPage() {
        return currentPage == 1;
    }

    /**
     * 是否还有更多
     * @param size
     * @return
     */
    public boolean isMore(int size) {
        return size == pageSize;
    }
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
