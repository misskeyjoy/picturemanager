package misskey.com.pictruemanager.bean;

import java.util.List;

/**
 * Created by yy156 on 2015/6/6.
 */
public class CamePhotoList {
    private static final  long serialVersionUID=1L;
    private List<PhotoInfo> list;
    public List<PhotoInfo> getList() {
        return list;
    }
    public void setList(List<PhotoInfo> list) {
        this.list = list;
    }
}
