package support.domain;

public enum ViewPath {
    QNA_SHOW("/qna/show"),
    QNA_EDIT("/qna/form");

    private String path;

    ViewPath(String path) {
        this.path = path;
    }

    public static String getViewPath(ViewPath viewPath) {
        return viewPath.path;
    }
}
