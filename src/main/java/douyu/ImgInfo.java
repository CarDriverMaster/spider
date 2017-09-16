package douyu;

import java.io.Serializable;

public class ImgInfo implements Serializable{

    private String url;
    private String filePath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
