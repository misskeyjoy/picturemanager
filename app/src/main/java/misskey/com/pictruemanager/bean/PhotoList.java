package misskey.com.pictruemanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by misskey on 15-6-4.
 */
public class PhotoList implements Serializable {
    private static final  long serialVersionUID=1L;
    private List<PhotoInfo>  list;
    public List<PhotoInfo> getList() {
        return list;
    }
    public void setList(List<PhotoInfo> list) {
        this.list = list;
    }
}
